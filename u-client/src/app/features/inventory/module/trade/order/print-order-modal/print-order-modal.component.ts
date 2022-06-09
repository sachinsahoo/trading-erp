import { Component, OnInit, ChangeDetectionStrategy, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { Store } from '@ngrx/store';
import { AppState } from 'app/core/core.state';

@Component({
  selector: 'print-order-modal',
  templateUrl: './print-order-modal.component.html',
  styleUrls: ['./print-order-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PrintOrderModalComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<PrintOrderModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PurchaseOrder,
    private store: Store<AppState>,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.dialogRef.keydownEvents().subscribe(event => {
      if (event.key === 'Escape') {
        this.onClose();
      }
    });
  }

  onClose() {
    //this.store.dispatch(approveTransModalClose());
    this.dialogRef.close();
  }
}
