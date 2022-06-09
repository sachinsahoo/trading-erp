import { Component, OnInit, ChangeDetectionStrategy, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Store, select } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { selectTransactionById, selectActiveTransaction } from 'app/features/inventory/selectors/order.selectors';
import { updateSelectedTransactionInStore, getTransaction } from 'app/features/inventory/action/transaction.action';
import { Observable, Subscription } from 'rxjs';
import { Transaction } from 'app/features/inventory/model/transaction';
import { EditPaymentModalComponent } from '../edit-payment-modal/edit-payment-modal.component';

@Component({
  selector: 'view-transaction-modal',
  templateUrl: './view-transaction-modal.component.html',
  styleUrls: ['./view-transaction-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ViewTransactionModalComponent implements OnInit {

  // Observable
  transaction$: Observable<any> = this.store.pipe(select(selectActiveTransaction));
  subscriptions: Array<Subscription> = [];

  // Local
  transaction: Transaction;



  constructor(
    public dialogRef: MatDialogRef<ViewTransactionModalComponent>,
    @Inject(MAT_DIALOG_DATA) public transid: number,
    private store: Store<AppState>,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private router: Router
  ) { }

  ngOnInit() {
    this.store.select(selectTransactionById(this.transid)).subscribe(transaction => {
      if (transaction != null) {
        this.store.dispatch(updateSelectedTransactionInStore({ transaction: transaction }))
      } else {
        this.store.dispatch(getTransaction({ id: this.transid }))
      }
    });

    this.subscriptions.push(this.transaction$.subscribe(trans => {
      this.transaction = trans;
    }))


  }



  edit() {
    if (this.transaction.type == 'Journal') {
      let navStr = 'trade/journal/edit';
      this.router.navigate([navStr, this.transaction.id]);
    } else if (this.transaction.type == 'Payment' || this.transaction.type == 'Receipt') {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.id = 'edit-payment';
      dialogConfig.disableClose = true;
      dialogConfig.width = '800px';
      dialogConfig.data = this.transaction;

      // Open Dialog
      const dialogRef = this.dialog.open(
        EditPaymentModalComponent,
        dialogConfig
      );


    }
    this.dialogRef.close();
  }




  onClose() {
    //this.store.dispatch(approveTransModalClose());
    this.dialogRef.close();
  }
  viewOrder() {
    let navStr = 'trade/order/view';
    this.router.navigate([navStr, this.transaction.orderId]);
    this.dialogRef.close();
  }

  viewInvoice() {
    let navStr = 'trade/invoice/view';
    this.router.navigate([navStr, this.transaction.invoiceId]);
    this.dialogRef.close();
  }

}

