import { Component, OnInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { Store } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { updateSelectedOrderInStore, updateSelectedInvoiceIdInStore } from 'app/features/inventory/action/order.action';
import { UpdatePurchaseStatusComponent } from '../../update-purchase-status/update-purchase-status.component';
import { Transaction } from 'app/features/inventory/model/transaction';
import { EditPaymentModalComponent } from '../../../transaction/edit-payment-modal/edit-payment-modal.component';

@Component({
  selector: 'order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OrderComponent implements OnInit {
// Local Vars
@Input() order: PurchaseOrder;

displayedColumns = ['Slno', 'Description', 'Quantity', 'Rate', 'Amount'];

constructor(
  private store: Store<AppState>,
  public dialog: MatDialog,
  private router: Router
) {}

ngOnInit() {}

editOrder() {
  let navStr ='trade/order/edit';
  this.router.navigate([navStr,this.order.type, this.order.id]);
}

viewInvoice(invoiceId: number) {
  // let selOrder = new PurchaseOrder();
  // selOrder.id = this.order.id
  // this.store.dispatch(updateSelectedOrderInStore({ order: selOrder}));
  // this.store.dispatch(updateSelectedInvoiceIdInStore({invId: invoiceId}))

  let navStr = 'trade/invoice/view';

  this.router.navigate([navStr, invoiceId]);


}

createTransaction(invoice){

    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'print-purchase';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';

    // Create New Transaction
    let transaction: Transaction = new Transaction();
     transaction.invoiceId = invoice.id;
   transaction.orderId = this.order.id;
     if(this.order.type == 'purchase') {
       transaction.type = 'Payment';
       transaction.myCompanyId = this.order.customerid;
       transaction.custCompanyId = this.order.supplierid;
     } else if(this.order.type == 'sale') {
       transaction.type = 'Receipt';
       transaction.myCompanyId = this.order.customerid;
       transaction.custCompanyId = this.order.supplierid;
     }
    //transaction.

    dialogConfig.data = transaction;
    // Open Dialog
    const dialogRef = this.dialog.open(EditPaymentModalComponent, dialogConfig);

}
editInvoice(invoiceId) {
  let navStr = 'trade/invoice/edit';
  this.router.navigate([navStr, this.order.id, invoiceId]);

}

updateStatus() {
  // Dialog config
  const dialogConfig = new MatDialogConfig();
  dialogConfig.id = 'purchase-status';
  dialogConfig.disableClose = true;
  dialogConfig.width = '800px';
  dialogConfig.data = this.order.id;

  // Open Dialog
  const dialogRef = this.dialog.open(
    UpdatePurchaseStatusComponent,
    dialogConfig
  );
}
}
