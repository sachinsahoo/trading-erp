import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Inject
} from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { select, Store } from '@ngrx/store';
import {
  selectOrderByInvId,
  selectOrderById
} from 'app/features/inventory/selectors/order.selectors';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { Invoice } from 'app/features/inventory/model/invoice';
import { Validators, FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { AppState } from 'app/core/core.state';
import { Router } from '@angular/router';
import { updateOrderStatus } from 'app/features/inventory/action/order.action';

interface SelectItem {
  value: string;
  name: string;
}

@Component({
  selector: 'update-purchase-status',
  templateUrl: './update-purchase-status.component.html',
  styleUrls: ['./update-purchase-status.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdatePurchaseStatusComponent implements OnInit {
  selectedOrder$: Observable<any> = this.store.pipe(
    select(selectOrderById(this.oid))
  );
  subscriptions: Array<Subscription> = [];

  // Form related
  statusFormGroup = this.fb.group(
    UpdatePurchaseStatusComponent.createStatusForm()
  );

  order: PurchaseOrder;
  statusOptions: SelectItem[] = [];
  selectedStatus: string;

  static createStatusForm(): any {
    return {
      date: ['', [Validators.required]]
    };
  }

  constructor(
    public dialogRef: MatDialogRef<UpdatePurchaseStatusComponent>,
    @Inject(MAT_DIALOG_DATA) public oid: number,
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
        }
      })
    );
  }

  submitMain() {

    console.log('submit main----');
    let confirmDate: number;
    confirmDate = this.statusFormGroup.controls.date.value.getTime() / 1000;
    this.store.dispatch(
      updateOrderStatus({
        oid: this.order.id,
        date: confirmDate,
        status: 'confirm'
      })
    );
  }

  onClose() {
    //this.store.dispatch(approveTransModalClose());
    this.dialogRef.close();
  }
}
