import { Component, OnInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { Invoice } from 'app/features/inventory/model/invoice';
import { Store, select } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { UpdateInvoiceStatusComponent } from '../update-invoice-status/update-invoice-status.component';
import { Observable, Subscription, combineLatest } from 'rxjs';
import { selectActiveOrder, selectActiveInvoiceId, selectProductList, selectCompanyList, selectOrderError, selectOrderById, selectOrderByInvId } from 'app/features/inventory/selectors/order.selectors';
import { flatMap } from 'rxjs/operators';
import { InvProduct } from 'app/features/inventory/model/invproduct';
import { Company } from 'app/features/inventory/model/company';
import { Product } from 'app/features/inventory/model/product';
import { updateSelectedOrderInStore, orderFailure } from 'app/features/inventory/action/order.action';
import { EditPaymentModalComponent } from '../../transaction/edit-payment-modal/edit-payment-modal.component';
import { Transaction } from 'app/features/inventory/model/transaction';

@Component({
  selector: 'view-invoice',
  templateUrl: './view-invoice.component.html',
  styleUrls: ['./view-invoice.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ViewInvoiceComponent implements OnInit {

  // Observables
  //selectActiveOrder$: Observable<any> = this.store.pipe(select(selectActiveOrder));
  //selectedInvoiceId$: Observable<any> = this.store.pipe(select(selectActiveInvoiceId));
  products$: Observable<any> = this.store.pipe(select(selectProductList));
  companies$: Observable<any> = this.store.pipe(select(selectCompanyList));
  orderError$: Observable<any> = this.store.pipe(select(selectOrderError));

  //relatedSaleOrders$: Observable<any> = this.store.pipe(select(selectSaleOrdersRelatedToPOID(this.oid)));
  subscriptions: Array<Subscription> = [];




  // Local Vars


  // Local
  selectedOrder: PurchaseOrder;
  orderType: string;
  selectedInvId: number;
  invoice: Invoice;
  allCompanies: Array<Company>;
  products: Product[];

  displayedColumns = ['Slno', 'Description', 'Quantity', 'Rate', 'Amount'];

  constructor(
    private store: Store<AppState>,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {

    this.route.params.pipe(
      flatMap(params => {
        return combineLatest(
          this.route.params,
          this.store.pipe(select(selectOrderByInvId(params['id']))),
          this.companies$,
          this.products$
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
    } else {
      console.log("Error : Cannnot find Invoice Id : " + this.selectedInvId)
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


  // ---------------------------
  // Navigation Functions
  // ---------------------------

  getOrderURL() {
    return this.orderType == 'purchase' ? 'trade/purchase' : (this.orderType == 'sale' ? 'trade/sale' : 'trade/comm');
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

  editInvoice() {
    let navStr = 'trade/invoice/edit';
    this.router.navigate([navStr, this.selectedOrder.id, this.selectedInvId]);

  }

  printPreview(){

  }
  deleteInvoice(){
    
  }

  enterTrans() {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'print-purchase';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';

    // Create New Transaction
    let transaction: Transaction = new Transaction();
    transaction.invoiceId = this.invoice.id;
    transaction.orderId = this.selectedOrder.id;
    if(this.selectedOrder.type == 'purchase') {
      transaction.type = 'Payment';
      transaction.myCompanyId = this.selectedOrder.customerid;
      transaction.custCompanyId = this.selectedOrder.supplierid;
    } else if(this.selectedOrder.type == 'sale') {
      transaction.type = 'Receipt';
      transaction.myCompanyId = this.selectedOrder.customerid;
      transaction.custCompanyId = this.selectedOrder.supplierid;
    }
    //transaction.

    dialogConfig.data = transaction;
    // Open Dialog
    const dialogRef = this.dialog.open(EditPaymentModalComponent, dialogConfig);
  }

  updateStatus() {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'print-purchase';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = this.invoice.id;

    // Open Dialog
    const dialogRef = this.dialog.open(
      UpdateInvoiceStatusComponent,
      dialogConfig
    );
  }






}
