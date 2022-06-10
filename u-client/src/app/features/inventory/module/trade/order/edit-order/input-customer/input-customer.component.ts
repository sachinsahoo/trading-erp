import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
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
import {MatStepperModule} from '@angular/material/stepper';

@Component({
  selector: 'input-customer',
  templateUrl: './input-customer.component.html',
  styleUrls: ['./input-customer.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InputCustomerComponent implements OnInit {

// Observables
companies$: Observable<any> = this.store.pipe(select(selectCompanyList));
reload$: Observable<any> = this.store.pipe(select(selectReload));
subscriptions: Array<Subscription> = [];
selectedOrder$: Observable<any> = this.store.pipe(select(selectActiveOrder));


// Local

selectedOrder: PurchaseOrder = new PurchaseOrder();
customers: Company[];
myCompanies: Company[];
allCompanies: Company[];

// Dropdown Options
customerOptions: Observable<Company[]>;

// Radio
selectedTax: string;

// Steppers
customerFormGroup = this.fb.group({
  firstCtrl: ['', Validators.required],
  supplierInput: ['', [Validators.required]],
  customerInput: ['', Validators.required],
  orderDate: ['', Validators.required]
});

productFormGroup = this.fb.group({
  secondCtrl: ['', Validators.required],
  poproducts: this.fb.array([]),
});
shippingFormGroup = this.fb.group({
  secondCtrl: ['', Validators.required],
});

paymentFormGroup = this.fb.group({
  secondCtrl: ['', Validators.required],
});


isLinear = false;


// Form Related
orderFormGroup: FormGroup = this.fb.group({
 
  
});

popFormGroup = this.fb.group(InputCustomerComponent.createProductForm());
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
    this.reload$
  ]).pipe()
    .subscribe(([routeParams, companies, reload]) => {
      
      if (companies) {
        this.allCompanies = companies;
        this.populateCompanyDropDowns();
      }


      this.store.pipe(select(selectOrderById(routeParams['id']))).subscribe(selOrder => {
        if (selOrder) {
          this.selectedOrder = selOrder;
          this.populateOrder();
          this.populateForm();
          this.changeDetection.detectChanges();
        }
      });

    });

} // End  Init ---------


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
}


// ----------------------
// After Init
//-----------------------
ngAfterOnInit() {
  this.poFormArray = this.orderFormGroup.get('poproducts') as FormArray;
}

//------------------------------
// Initialize Order Form
//------------------------------





// ----------------------
// Submit Main
//-----------------------
submitMain() {
  this.validate();

  
  this.selectedOrder.customer = this.orderFormGroup.controls.customerInput.value;
  this.selectedOrder.customerName = this.orderFormGroup.controls.customerInput.value.name;
  this.selectedOrder.customerid = this.orderFormGroup.controls.customerInput.value.id;

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
 
  this.customers = this.allCompanies.filter(function (comp) {
    return comp.type == 'customer';
  });
  this.myCompanies = this.allCompanies.filter(function (comp) {
    return comp.type == 'self';
  });

  // todo if type = purchase set  customer = mycompany

  this.customerOptions = this.orderFormGroup.controls.customerInput.valueChanges.pipe(
    startWith(''),
    map(value => (typeof value === 'string' ? value : value.name)),
    map(name => (name ? this._filterCustomer(name) : this.customers.slice()))
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



populateOrder() {
  if (this.selectedOrder != null) {
    this.selectedOrder.customer = this.getCompnayById(
      this.selectedOrder.customerid
    );
    this.selectedOrder.supplier = this.getCompnayById(
      this.selectedOrder.supplierid
    );
   
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


}
