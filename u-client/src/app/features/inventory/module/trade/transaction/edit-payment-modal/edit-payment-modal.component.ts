import { Component, OnInit, ChangeDetectionStrategy, Inject } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { saveTransaction } from 'app/features/inventory/action/transaction.action';
import { Invoice } from 'app/features/inventory/model/invoice';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { Transaction } from 'app/features/inventory/model/transaction';
import { selectCompanyById, selectOrderById, selectOrderByInvId, selectSelfCompanyList } from 'app/features/inventory/selectors/order.selectors';
import { combineLatest, Observable, Subscription } from 'rxjs';

@Component({
  selector: 'edit-payment-modal',
  templateUrl: './edit-payment-modal.component.html',
  styleUrls: ['./edit-payment-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EditPaymentModalComponent implements OnInit {



  // Observable
  selectedOrder$: Observable<any> = this.store.pipe(select(selectOrderById(this.transaction.orderId)));
  client$: Observable<any> = this.store.pipe(select(selectCompanyById(this.transaction.custCompanyId)));
  company$: Observable<any> = this.store.pipe(select(selectCompanyById(this.transaction.myCompanyId)));
  subscriptions: Array<Subscription> = [];

  // Local
  order: PurchaseOrder;
  invoice: Invoice;

  // Form related
  transFormGroup = this.fb.group(EditPaymentModalComponent.createTransForm());
  static createTransForm(): any {
    return {
      amount: ['', [Validators.required, Validators.min(1),
      Validators.max(9199254749)]],
      transDate: ['', [Validators.required]]
    };
  }




  constructor(
    public dialogRef: MatDialogRef<EditPaymentModalComponent>,
    @Inject(MAT_DIALOG_DATA) public transaction: Transaction,
    private store: Store<AppState>,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private router: Router
  ) { }



  ngOnInit() {
    

    // Subscribe
    this.subscriptions.push(combineLatest([this.selectedOrder$, this.company$, this.client$])
      .pipe()
      .subscribe(([order, comp, client]) => {

        if (comp) {
          this.transaction.myCompany = comp;
        }
        if (client) {
          this.transaction.custCompany = client;
        }
        if (order) {
          this.order = order;
          this.invoice = this.order.invoices.find(inv => inv.id === this.transaction.invoiceId);
        }
        this.populateForm();
      }));


    // Close on Escape
    this.dialogRef.keydownEvents().subscribe(event => {
      if (event.key === 'Escape') {
        this.onClose();
      }
    });
  }

  populateForm() {
   
    var d = new Date();
    if (this.transaction.id) {
      d = new Date(0);
      var utcSeconds = this.transaction.date;
      d.setUTCSeconds(utcSeconds);
    }
    this.transFormGroup.controls.transDate.setValue(d);
    this.transFormGroup.controls.amount.setValue(this.transaction.amount);

  }


  submitMain() {

    this.transaction.amount = this.transFormGroup.controls.amount.value;
    this.transaction.date = this.transFormGroup.controls.transDate.value.getTime() / 1000;

    this.store.dispatch(saveTransaction({ transaction: this.transaction }));
  }
  onClose() {
    //this.store.dispatch(approveTransModalClose());
    this.dialogRef.close();
  }
}
