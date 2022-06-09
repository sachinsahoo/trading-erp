import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Input
} from '@angular/core';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { Invoice } from 'app/features/inventory/model/invoice';
import { POProduct } from 'app/features/inventory/model/poproduct';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AppState } from 'app/core/core.state';
import { Store } from '@ngrx/store';
import { Router } from '@angular/router';
import { UpdateStatusModalComponent } from '../../update-status-modal/update-status-modal.component';
import { UpdateInvoiceStatusComponent } from '../../update-invoice-status/update-invoice-status.component';
import { EditPaymentModalComponent } from '../../../transaction/edit-payment-modal/edit-payment-modal.component';

@Component({
  selector: 'invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InvoiceComponent implements OnInit {
  // Local Vars
  @Input() order: PurchaseOrder;
  @Input() invoice: Invoice;

  displayedColumns = ['Slno', 'Description', 'Quantity', 'Rate', 'Amount'];

  constructor(
    private store: Store<AppState>,
    public dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit() {
    this.populateInvoicePOProducts();
  }

  populateInvoicePOProducts() {
    let poplist = this.order.poproductlist;
    this.invoice.productlist.forEach(invprod => {
      invprod.poproduct = poplist.find(pop => pop.id === invprod.popid);
    });

  }

  enterTrans() {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'print-purchase';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = this.invoice.id;

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
