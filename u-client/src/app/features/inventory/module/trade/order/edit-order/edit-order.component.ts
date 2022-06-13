import { Component, OnInit, ChangeDetectionStrategy, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { ROUTE_ANIMATIONS_ELEMENTS } from 'app/core/core.module';
import { Observable, Subscription, combineLatest } from 'rxjs';
import { select, Store } from '@ngrx/store';
import { selectActiveOrder, selectCompanyList, selectProductList, selectOrderError, selectOrderById, selectReload } from 'app/features/inventory/selectors/order.selectors';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { POProduct } from 'app/features/inventory/model/poproduct';
import { Validators, FormBuilder, FormGroupDirective, FormGroup, FormArray } from '@angular/forms';
import { Company } from 'app/features/inventory/model/company';
import { Product } from 'app/features/inventory/model/product';
import { MatAutocompleteTrigger, MatAutocomplete } from '@angular/material/autocomplete';
import { MatDialog } from '@angular/material/dialog';
import { MatRadioChange } from '@angular/material/radio';
import { MatTableDataSource } from '@angular/material/table';
import { AppState } from 'app/core/core.state';
import { ActivatedRoute, Router } from '@angular/router';
import { flatMap, startWith, map } from 'rxjs/operators';
import { updateSelectedOrderInStore, saveOrder } from 'app/features/inventory/action/order.action';
import { POPTax } from 'app/features/inventory/model/poptax';

@Component({
  selector: 'edit-order',
  templateUrl: './edit-order.component.html',
  styleUrls: ['./edit-order.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EditOrderComponent implements OnInit, OnDestroy {
  routeAnimationsElement = ROUTE_ANIMATIONS_ELEMENTS;

  // Observables
  selectedOrder$: Observable<any> = this.store.pipe(select(selectActiveOrder));
  companies$: Observable<any> = this.store.pipe(select(selectCompanyList));
  products$: Observable<any> = this.store.pipe(select(selectProductList));
  orderError$: Observable<any> = this.store.pipe(select(selectOrderError));
  reload$: Observable<any> = this.store.pipe(select(selectReload));
  subscriptions: Array<Subscription> = [];

  // Local
  orderType: string;
  selectedOrder: PurchaseOrder = new PurchaseOrder();
  poProducts: POProduct[] = [];
  editProduct: boolean = false;
  allCompanies: Company[];
  suppliers: Company[];
  customers: Company[];
  myCompanies: Company[];
  products: Product[];

  // Dropdown Options
  supplierOptions: Observable<Company[]>;
  customerOptions: Observable<Company[]>;
  myCompanyOptions: Observable<Company[]>;
  productOptions: Observable<Product[]>;

  // Radio
  selectedTax: string;


  // Form Related
  orderFormGroup: FormGroup = this.fb.group({
    poproducts: this.fb.array([]),
    supplierInput: ['', [Validators.required]],
    customerInput: ['', Validators.required],
    orderDate: ['', Validators.required]
  });

  popFormGroup = this.fb.group(EditOrderComponent.createProductForm());
  static createProductForm(): any {
    return {
      product: ['', [Validators.required]],
      quantity: [
        '',
        [Validators.required, Validators.min(1), Validators.max(9007199254)]
      ],
      price: [
        '',
        [Validators.required, Validators.min(1), Validators.max(9007199254)]
      ],
      commpay: [''],
      taxes: [new Array<POPTax>()],
      isEditable: [false]
    };
  }
  poFormArray: FormArray;
  mode: boolean;
  touchedRows: any;



  // ------------------
  // Constructor
  // ------------------
  constructor(
    private store: Store<AppState>,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router,
    private changeDetection: ChangeDetectorRef
  ) { } // End ---
  ngOnDestroy(): void {
    //this.subscriptions.forEach(s => { s.unsubscribe() });
  }

  // --------
  // Init
  // --------
  ngOnInit() {

    combineLatest([
      this.route.params,
      this.companies$,
      this.products$,
      this.reload$
    ]).pipe()
      .subscribe(([routeParams, companies, products, reload]) => {
        if (routeParams) {
          this.orderType = routeParams['type'];
        }

        if (companies) {
          this.allCompanies = companies;
          this.populateCompanyDropDowns();
        }

        if (products) {
          this.products = products
          this.populateProductDropDown(products);
        }

        this.store.pipe(select(selectOrderById(routeParams['id']))).subscribe(selOrder => {
          if (selOrder) {
            this.selectedOrder = selOrder;
            if (this.selectedOrder.id != null) {
              this.orderType = this.selectedOrder.type;
            }
            this.populateOrder();
            this.populateForm();
            this.changeDetection.detectChanges();
          }
        });

      });

  } // End  Init ---------


  // ----------------------
  // After Init
  //-----------------------
  ngAfterOnInit() {
    this.poFormArray = this.orderFormGroup.get('poproducts') as FormArray;
  }

  //------------------------------
  // Initialize Order Form
  //------------------------------

  populateForm() {
    var utcSeconds = this.selectedOrder.orderdate;
    var d = new Date(0); // The 0 there is the key, which sets the date to the epoch
    d.setUTCSeconds(utcSeconds);

    this.orderFormGroup.controls.orderDate.setValue(d);
    this.orderFormGroup.controls.supplierInput.setValue(
      this.selectedOrder.supplier
    );

    this.orderFormGroup.controls.customerInput.setValue(
      this.selectedOrder.customer
    );

    this.populatePOForms()
  }



  //------------------------------
  // Populate POP Form Array
  //------------------------------
  populatePOForms() {
    //debugger;
    let control = this.orderFormGroup.get('poproducts') as FormArray;

    let popFormArrayLength =  control.length;
    for (let i = 0; i < popFormArrayLength; i++) {
      control.removeAt(0);
    }

    if (this.selectedOrder.poproductlist) {
      this.selectedOrder.poproductlist.forEach(pop => {
        control.push(this.getPOForm(pop));
      });
    }
  }

  getPOForm(pop: POProduct): FormGroup {
    let popForm = this.fb.group(EditOrderComponent.createProductForm());
    popForm.controls.product.setValue(
      this.products.find(p => {
        return p.id == pop.productId;
      })
    );
    popForm.controls.quantity.setValue(pop.quantity);
    popForm.controls.price.setValue(pop.price);
    popForm.controls.commpay.setValue(pop.commpay);
    popForm.controls.isEditable.setValue(false);
    return popForm;
  }

  get getFormControls() {
    const control1 = this.orderFormGroup.get('poproducts') as FormArray;
    return control1;
  }

  // ----------------------
  // Submit POProduct Form
  //-----------------------
  submitProduct(formDirective: FormGroupDirective) {

    let control = this.orderFormGroup.get('poproducts') as FormArray;
    if (this.validateProductForm()) {
      let jForm : FormGroup = this.fb.group(EditOrderComponent.createProductForm());
      jForm.setValue(JSON.parse(JSON.stringify(this.popFormGroup.value)));
      control.push(jForm);

      // Reset Form
      this.popFormGroup.controls.product.reset('');
      this.popFormGroup.controls.product.setErrors(null);
      this.popFormGroup.reset('');
      formDirective.resetForm();
    }
  }

  validateProductForm(): boolean {
    // todo stock validation

    let control = this.orderFormGroup.get('poproducts') as FormArray;

    //Not Found
    if(! this.popFormGroup.controls.product.value.id) {
      this.popFormGroup.controls.product.setErrors({notFound :true})
        return false;
    }

    // Check for Product already added
    for (let i = 0; i < control.length; i++) {
      let element: FormGroup = control.at(i) as FormGroup;
      if(element.controls.product.value.id == this.popFormGroup.controls.product.value.id) {
        this.popFormGroup.controls.product.setErrors({duplicate :true})
        return false;
      }
    }

    // Not in stock
    if( this.popFormGroup.controls.quantity.value > this.popFormGroup.controls.product.value.quantity) {
      this.popFormGroup.controls.quantity.setErrors({notInStock :true});
        return false;
    }


    return true;
  }

  getPOProductsFromFormArray() {
    let popList: POProduct[] = new Array<POProduct>();
    let popFormArray = this.orderFormGroup.get('poproducts') as FormArray;

    for (let i = 0; i < popFormArray.length; i++) {
      const popForm: FormGroup = popFormArray.at(i) as FormGroup;
      let pop: POProduct = this.selectedOrder.poproductlist ? this.selectedOrder.poproductlist[i] : null;
      if (!pop) {
        pop = new POProduct();
      }
      pop.product = popForm.controls.product.value;
      pop.productId = popForm.controls.product.value.id;
      pop.price = popForm.controls.price.value;
      pop.quantity = popForm.controls.quantity.value;
      pop.commpay = popForm.controls.commpay.value;
      pop.taxes = popForm.controls.taxes.value;
      popList.push(pop);

    }
    return popList;
  }

  validatePOPFormAtIndex(group: FormGroup) {
    // TODO edit form at index validations
    return true;

  }

  //---------------
  // Form Events
  //---------------
  deleteRow(index: number) {
    const control = this.orderFormGroup.get('poproducts') as FormArray;
    control.removeAt(index);
  }

  editRow(group: FormGroup) {
    group.get('isEditable').setValue(true);
  }

  doneRow(group: FormGroup) {
    if (this.validatePOPFormAtIndex(group)) {
      group.get('isEditable').setValue(false);
    }
  }

  getTotalAmt(): number {
    let popFormArray = this.orderFormGroup.get('poproducts') as FormArray;

    let totalAmt: number = 0;
    for (let i = 0; i < popFormArray.length; i++) {
      const popForm: FormGroup = popFormArray.at(i) as FormGroup;

      totalAmt = totalAmt + popForm.controls.price.value * popForm.controls.quantity.value;
      let taxes: Array<POPTax> = popForm.controls.taxes.value;
      taxes.forEach(t => {
        totalAmt = totalAmt + (popForm.controls.price.value * popForm.controls.quantity.value * t.percent) / 100;
      });

      if (popForm.controls.commpay.value > 0) {
        totalAmt = totalAmt + popForm.controls.commpay.value * popForm.controls.quantity.value;
      }


    }

    return totalAmt;

  }

  // ----------------------
  // Submit Main
  //-----------------------
  submitMain() {
    this.validate();

    // Update Selected Purchase order
    this.selectedOrder.type = this.orderType;
    this.selectedOrder.orderdate =
      this.orderFormGroup.controls.orderDate.value.getTime() / 1000;
    this.selectedOrder.supplier = this.orderFormGroup.controls.supplierInput.value;
    this.selectedOrder.supplierName = this.orderFormGroup.controls.supplierInput.value.name;
    this.selectedOrder.supplierid = this.orderFormGroup.controls.supplierInput.value.id;
    this.selectedOrder.customer = this.orderFormGroup.controls.customerInput.value;
    this.selectedOrder.customerName = this.orderFormGroup.controls.customerInput.value.name;
    this.selectedOrder.customerid = this.orderFormGroup.controls.customerInput.value.id;
    this.selectedOrder.poproductlist = this.getPOProductsFromFormArray();

    this.store.dispatch(saveOrder({ order: this.selectedOrder }));
  }

  validate() {
    if (this.orderFormGroup.controls.customerInput.value.name == null) {
      this.orderFormGroup.controls.customerInput.setErrors({
        notFound: true
      });
      return false;
    }
    if (this.orderFormGroup.controls.supplierInput.value.name == null) {
      this.orderFormGroup.controls.supplierInput.setErrors({
        notFound: true
      });
      return false;
    }
    return true;

    //this.selectedCustomer.name === this.purchaseFormGroup.value.s
  }



  //------------
  // Add Taxes
  //------------
  taxRadioChange(event: MatRadioChange) {
    let tax: string = event.value;
    let popFormArray = this.orderFormGroup.get('poproducts') as FormArray;
    if (popFormArray != null) {
      for (let i = 0; i < popFormArray.length; i++) {
        let popForm = popFormArray.at(i) as FormGroup;

        if (tax === 'gst') {
          let popTaxes = new Array<POPTax>();
          let popTax1 = new POPTax();
          popTax1.code = 'cgst'
          popTax1.desc = 'cgst' + '@' + popForm.controls.product.value.cgstRate + '%';
          popTax1.percent = popForm.controls.product.value.cgstRate
          popTaxes.push(popTax1);

          let popTax2 = new POPTax();
          popTax2.code = 'sgst'
          popTax2.desc = 'sgst' + '@' + popForm.controls.product.value.sgstRate + '%';
          popTax2.percent = popForm.controls.product.value.sgstRate
          popTaxes.push(popTax2);

          popForm.controls.taxes.setValue(popTaxes);
        } else if (tax === 'igst') {
          let popTaxes = new Array<POPTax>();
          let popTax3 = new POPTax();
          popTax3.code = 'igst'
          popTax3.desc = 'igst' + '@' + popForm.controls.product.value.igstRate + '%';
          popTax3.percent = popForm.controls.product.value.igstRate
          popTaxes.push(popTax3);
          popForm.controls.taxes.setValue(popTaxes);
        }

      }

    }
  }

  // ---------------------------
  // Populate Company Dropdowns
  // ---------------------------
  populateCompanyDropDowns() {
    this.suppliers = this.allCompanies.filter(function (comp) {
      return comp.type == 'supplier';
    });
    this.customers = this.allCompanies.filter(function (comp) {
      return comp.type == 'customer';
    });
    this.myCompanies = this.allCompanies.filter(function (comp) {
      return comp.type == 'self';
    });

    // todo if type = purchase set  customer = mycompany

    this.supplierOptions = this.orderFormGroup.controls.supplierInput.valueChanges.pipe(
      startWith(''),
      map(value => (typeof value === 'string' ? value : value.name)),
      map(name => (name ? this._filterSupplier(name) : this.suppliers.slice()))
    );

    this.customerOptions = this.orderFormGroup.controls.customerInput.valueChanges.pipe(
      startWith(''),
      map(value => (typeof value === 'string' ? value : value.name)),
      map(name => (name ? this._filterCustomer(name) : this.customers.slice()))
    );
  }

  private _filterSupplier(name: string): Company[] {
    const filterValue = name.toLowerCase();

    return this.suppliers.filter(
      option => option.name.toLowerCase().indexOf(filterValue) === 0
    );
  }

  private _filterCustomer(name: string): Company[] {
    const filterValue = name.toLowerCase();

    return this.customers.filter(
      option => option.name.toLowerCase().indexOf(filterValue) === 0
    );
  }

  displayFn(company: Company): string {
    return company && company.name ? company.name : '';
  }

  // ------------------
  // Product Drop Down
  // ------------------
  populateProductDropDown(products: Product[]) {
    this.productOptions = this.popFormGroup.controls.product.valueChanges.pipe(
      startWith(''),
      map(value => (typeof value === 'string' ? value : value.name)),
      map(name => (name ? this._filterProduct(name) : this.products.slice()))
    );
  }

  private _filterProduct(name: string): Product[] {
    const filterValue = name.toLowerCase();

    return this.products.filter(
      option => option.name.toLowerCase().indexOf(filterValue) === 0
    );
  }

  displayProdFn(product: Product): string {
    return product && product.name ? product.name : '';
  }

  productSelected(selectedProduct: Product) {
    this.popFormGroup.controls.price.setValue(selectedProduct.costPrice);
  }


  populateOrder() {
    if (this.selectedOrder != null) {
      this.selectedOrder.customer = this.getCompnayById(
        this.selectedOrder.customerid
      );
      this.selectedOrder.supplier = this.getCompnayById(
        this.selectedOrder.supplierid
      );
      if (this.selectedOrder.poproductlist != null) {
        this.selectedOrder.poproductlist.forEach(pop => {
          pop.product = this.products.find(p => p.id === pop.productId);
        })
      }
      this.selectedTax = this.selectedOrder.taxes;
    } else {
      this.selectedOrder = new PurchaseOrder();
      this.selectedOrder.poproductlist = new Array<POProduct>();

    }
  }

  getCompnayById(cid: number): Company {
    let company: Company;
    if (this.allCompanies != null) {
      company = this.allCompanies.find(function (comp) {
        return comp.id == cid;
      });
    }

    return company;
  }

  resetAutoInput(val, trigger: MatAutocompleteTrigger, auto: MatAutocomplete) {
    setTimeout(_ => {
      auto.options.forEach(item => {
        item.deselect();
      });
      this.popFormGroup.controls.product.reset('');
      trigger.openPanel();
    }, 100);
  }

  //---------------------------
  // Navigation Functions
  //---------------------------

  getOrderURL() {
    return this.orderType == 'purchase' ? 'trade/purchase' : this.orderType == 'sale' ? 'trade/sale' : 'trade/comm';
  }

  getOrderLabel() {
    console.log(this.orderType);
    return this.orderType == 'purchase' ? 'Purchases' : (this.orderType == 'sale' ? 'Sales' : 'Commissions');
  }

  viewOrderList() {
    let navStr: string = this.getOrderURL().concat('/list');

    console.log(navStr);
    this.router.navigate([navStr]);
  }

  viewOrder() {
    let navStr = 'trade/order/view';
    this.router.navigate([navStr, this.selectedOrder.id]);
  }

  printPreview() {

  }


}
