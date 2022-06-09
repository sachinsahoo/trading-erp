import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { Observable, combineLatest, Subscription } from 'rxjs';
import { selectActiveOrder, selectOrderById, selectProductList, selectCompanyList, selectActiveInvoiceId, selectOrderError, selectOrderByInvId } from 'app/features/inventory/selectors/order.selectors';
import { Store, select } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { FormBuilder, FormGroup, FormArray, FormGroupDirective, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { flatMap } from 'rxjs/operators';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { Invoice } from 'app/features/inventory/model/invoice';
import { Company } from 'app/features/inventory/model/company';
import { Product } from 'app/features/inventory/model/product';
import { POProduct } from 'app/features/inventory/model/poproduct';
import { InvProduct } from 'app/features/inventory/model/invproduct';
import { updateSelectedOrderInStore, saveInvoice } from 'app/features/inventory/action/order.action';

@Component({
  selector: 'edit-invoice',
  templateUrl: './edit-invoice.component.html',
  styleUrls: ['./edit-invoice.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EditInvoiceComponent implements OnInit {

  // Observables
  selectActiveOrder$: Observable<any> = this.store.pipe(
    select(selectActiveOrder)
  );
  selectedInvoiceId$: Observable<any> = this.store.pipe(select(selectActiveInvoiceId));
  products$: Observable<any> = this.store.pipe(select(selectProductList));
  companies$: Observable<any> = this.store.pipe(select(selectCompanyList));
  orderError$: Observable<any> = this.store.pipe(select(selectOrderError));

  //relatedSaleOrders$: Observable<any> = this.store.pipe(select(selectSaleOrdersRelatedToPOID(this.oid)));
  subscriptions: Array<Subscription> = [];


  invTransportFormGroup = this.fb.group(EditInvoiceComponent.createTransportForm());

  static createTransportForm(): any {
    return {
      invdate: ['', [Validators.required]],
      status: ['', [Validators.required]],
      transportername: ['', Validators.required],
      truckno: ['', Validators.required],
      containerno: ['', Validators.required],
    };
  }


  // Local
  selectedOrder: PurchaseOrder;
  orderType: string;
  selectedInvId: number;
  invoice: Invoice;
  totalOtherInvoicedProdList: Array<InvProduct> = new Array<InvProduct>();

  allCompanies: Array<Company>;
  products: Product[];

  // Form Related
  productTableForm: FormGroup;
  popFormArray: FormArray;
  mode: boolean;
  touchedRows: any;




  constructor(

    private store: Store<AppState>,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute
  ) {
    console.log('Helllllll o');
  }

  ngOnInit() {



    this.route.params.pipe(
      flatMap(params => {
        return combineLatest(
          [this.route.params,
          this.store.pipe(select(selectOrderById(params['oid']))),
          this.companies$,
          this.products$]
        );
      })
    )
      .subscribe(([params, order, companies, products]) => {
        this.selectedOrder = order;
        this.selectedInvId = params['id'];
        this.allCompanies = companies;
        this.products = products
        this.populateOrder();
      });

    // Init Form
    this.touchedRows = [];
    this.productTableForm = this.fb.group({
      tableRows: this.fb.array([]),

      invdate: ['', [Validators.required]],
      status: ['', [Validators.required]],
      transportername: ['', Validators.required],
      truckno: ['', Validators.required],
      containerno: ['', Validators.required],
    });
    this.populateRows();




  } // End Init -----------------------

  ngAfterOnInit() {
    this.popFormArray = this.productTableForm.get('tableRows') as FormArray;
  }

  populateRows() {
    const control = this.productTableForm.get('tableRows') as FormArray;


    // Populate transport info if Edit Invoice --
    if (this.invoice.id != null) {
      var utcSeconds = this.selectedOrder.orderdate;
      var d = new Date(0);
      d.setUTCSeconds(utcSeconds);
      this.productTableForm.controls.invdate.setValue(d);
      this.productTableForm.controls.status.setValue(this.invoice.status);
      this.productTableForm.controls.transportername.setValue(this.invoice.transportername);
      this.productTableForm.controls.truckno.setValue(this.invoice.truckno);
      this.productTableForm.controls.containerno.setValue(this.invoice.containerno);

    }


    this.invoice.productlist.forEach(invp => {
      control.push(this.getPOPForm(invp));
    })

  }

  getPOPForm(invp: InvProduct) {

    let totalInvP: InvProduct = this.totalOtherInvoicedProdList.find(tip => { return tip.popid === invp.popid });


    return this.fb.group({
      prodName: [invp.poproduct.productName],
      price: [invp.poproduct.price],
      popId: [invp.popid],
      invId: [invp.id],
      orderQty: [invp.poproduct.quantity],
      invCreatedQty: [totalInvP.quantity],
      quantity: [invp.quantity],
      isEditable: [false]
    });

  }


  deleteRow(index: number) {
    const control = this.productTableForm.get('tableRows') as FormArray;
    control.removeAt(index);
  }

  editRow(group: FormGroup) {
    group.get('isEditable').setValue(true);
  }


  doneRow(group: FormGroup) {
    if (this.validateInvProductForm(group)) {
      group.get('isEditable').setValue(false);
    }

  }

  validateInvProductForm(group: FormGroup) {
    let qty = group.controls.quantity.value;
    let popid: number = group.controls.popId.value;

    let totalInvP = this.totalOtherInvoicedProdList.find(tip => { return tip.popid === popid });

    if ((parseFloat(qty) + totalInvP.quantity) > totalInvP.poproduct.quantity) {
      group.controls.quantity.setErrors({ exceeds: true })
      return false;
    }
    return true;

  }


  saveInvPopDetails() {
    console.log(this.productTableForm.value);
  }


  get getFormControls() {
    const control1 = this.productTableForm.get('tableRows') as FormArray;
    return control1;
  }


  // ---------------------------
  // Submit Product Table Form
  // ---------------------------
  submitForm() {
    const control = this.productTableForm.get('tableRows') as FormArray;
    //this.touchedRows1 = control.controls.filter(row => row.touched).map(row => row.value);
    let invoiceObj: Invoice = JSON.parse(JSON.stringify(this.invoice));


    invoiceObj.productlist.forEach(invp => {
      invp.quantity = parseFloat(control.controls.find(row => row.get('popId').value == invp.popid).get('quantity').value);
    });

    invoiceObj.invdate = this.productTableForm.controls.invdate.value.getTime() / 1000;
    invoiceObj.status = this.productTableForm.controls.status.value;
    invoiceObj.transportername = this.productTableForm.controls.transportername.value;
    invoiceObj.truckno = this.productTableForm.controls.truckno.value;
    invoiceObj.containerno = this.productTableForm.controls.containerno.value;

    this.store.dispatch(saveInvoice({ invoice: invoiceObj }));

  }


  createTotalInvoicedQuantity() {
    this.totalOtherInvoicedProdList = new Array<InvProduct>();
    // total Order Qty - invoice already created
    this.totalOtherInvoicedProdList = this.selectedOrder.poproductlist.map(pop => {
      let invPop = new InvProduct();
      invPop.popid = pop.id;
      invPop.quantity = 0;
      invPop.poproduct = pop
      return invPop;
    });

    // Reduce Qty of remaining by Qty of invoice already created
    if (this.selectedOrder.invoices != null) {

      this.selectedOrder.invoices
        .filter(inv => { return inv.id != this.invoice.id })
        .forEach(inv => {
          inv.productlist.forEach(invp => {
            let totalInvP: InvProduct = this.totalOtherInvoicedProdList.find(tip => {
              return (tip.popid === invp.popid)
            });
            if (totalInvP != null) {
              totalInvP.quantity = totalInvP.quantity + invp.quantity;
            }
          })
        })
    }
  }



  populateOrder() {



    if (this.selectedOrder != null) {
      this.orderType = this.selectedOrder.type;
      this.selectedOrder.customer = this.getCompnayById(this.selectedOrder.customerid);
      this.selectedOrder.supplier = this.getCompnayById(this.selectedOrder.supplierid);
      if (this.selectedOrder.poproductlist != null) {
        this.selectedOrder.poproductlist.forEach(pop => {
          pop.product = this.products.find(p => p.id === pop.productId);
        })
      }
    }

    if (this.selectedInvId != null && this.selectedInvId > 0) {
      this.invoice = this.selectedOrder.invoices.find(inv => { return inv.id == this.selectedInvId });
      this.createTotalInvoicedQuantity();
    } else {
      // New Invoice
      this.invoice = new Invoice();
      this.invoice.oid = this.selectedOrder.id;
      this.invoice.status = 'draft';
      this.createTotalInvoicedQuantity();
      this.invoice.productlist = this.selectedOrder.poproductlist.map(pop => {
        let invPop = new InvProduct();
        invPop.popid = pop.id;
        let totalInvP = this.totalOtherInvoicedProdList.find(inv => { return inv.popid == pop.id });
        invPop.quantity = totalInvP.poproduct.quantity - totalInvP.quantity;
        return invPop;
      });
    }

    this.populateInvoicePOProducts();

  }

  populateInvoicePOProducts() {
    let poplist = this.selectedOrder.poproductlist;
    this.invoice.productlist.forEach(invprod => {
      invprod.poproduct = poplist.find(pop => pop.id === invprod.popid);
    });
  }

  // Get Company By ID
  getCompnayById(cid: number): Company {
    let company: Company;
    if (this.allCompanies != null) {
      company = this.allCompanies.filter(function (comp) {
        return comp.id == cid;
      })[0];
    }

    return company;
  }

  // --------------------------
  // Navigation Functions
  // --------------------------

  getOrderURL() {
    return this.orderType == 'purchase' ? '/trade/purchase' : (this.orderType == 'sale' ? '/trade/sale' : '/trade/comm');
  }

  getOrderLabel() {
    console.log(this.orderType);
    return this.orderType == 'purchase' ? 'Purchases' : (this.orderType == 'sale' ? 'Sales' : 'Commissions');
  }

  viewOrderList() {
    let navStr: string = this.getOrderURL().concat('/list');
    this.router.navigate([navStr]);
  }

  viewOrder() {

    let navStr = 'trade/order/view';
    this.router.navigate([navStr, this.selectedOrder.id]);
  }

  viewInvoice() {
    let navStr: string = 'trade/invoice/view';
    this.router.navigate([navStr, this.selectedInvId]);
  }

}
