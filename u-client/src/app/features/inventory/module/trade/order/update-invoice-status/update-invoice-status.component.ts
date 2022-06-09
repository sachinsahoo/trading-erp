import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Inject
} from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { select, Store } from '@ngrx/store';
import { selectOrderByInvId } from 'app/features/inventory/selectors/order.selectors';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { Invoice } from 'app/features/inventory/model/invoice';
import { Validators, FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { AppState } from 'app/core/core.state';
import { Router } from '@angular/router';

interface SelectItem {
  value: string;
  name: string;
}

@Component({
  selector: 'update-invoice-status',
  templateUrl: './update-invoice-status.component.html',
  styleUrls: ['./update-invoice-status.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateInvoiceStatusComponent implements OnInit {
  selectedOrder$: Observable<any> = this.store.pipe(
    select(selectOrderByInvId(this.invid))
  );
  subscriptions: Array<Subscription> = [];

  // Form related
  transFormGroup = this.fb.group(UpdateInvoiceStatusComponent.createTransForm());

  // Local
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
    public dialogRef: MatDialogRef<UpdateInvoiceStatusComponent>,
    @Inject(MAT_DIALOG_DATA) public invid: number,
    private store: Store<AppState>,
    private fb: FormBuilder,
    public dialog: MatDialogModule,
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
