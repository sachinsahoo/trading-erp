import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Inject
} from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { Store, select } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { selectOrderByInvId } from 'app/features/inventory/selectors/order.selectors';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { Invoice } from 'app/features/inventory/model/invoice';
import { saveTransaction } from 'app/features/inventory/action/transaction.action';

@Component({
  selector: 'update-status-modal',
  templateUrl: './update-status-modal.component.html',
  styleUrls: ['./update-status-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateStatusModalComponent implements OnInit {
  selectedOrder$: Observable<any> = this.store.pipe(
    select(selectOrderByInvId(this.invid))
  );
  subscriptions: Array<Subscription> = [];

  // Form related
  transFormGroup = this.fb.group(UpdateStatusModalComponent.createTransForm());

  order: PurchaseOrder;
  invoice: Invoice;

  static createTransForm(): any {
    return {
      amount: [
        '',
        [Validators.required, Validators.min(1), Validators.max(9199254749)]
      ],
      notes: ['', Validators.required],
      transDate: ['', [Validators.required]]
    };
  }
  constructor(
    public dialogRef: MatDialogRef<UpdateStatusModalComponent>,
    @Inject(MAT_DIALOG_DATA) public invid: number,
    private store: Store<AppState>,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit() {
    this.subscriptions.push(
      this.selectedOrder$.subscribe(order => {
        if (order != null) {
          this.order = order;
          this.invoice = this.order.invoices.find(inv => inv.id === this.invid);
        }
      })
    );
  }

  submitMain() {
    //this.store.dispatch(saveTransaction({transaction: null}));
  }

  onClose() {
    //this.store.dispatch(approveTransModalClose());
    this.dialogRef.close();
  }
}
