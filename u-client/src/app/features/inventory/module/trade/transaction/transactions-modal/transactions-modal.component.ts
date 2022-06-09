import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Inject
} from '@angular/core';
import { Store, select } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import {
  trigger,
  state,
  style,
  transition,
  animate
} from '@angular/animations';
import { routeAnimations } from 'app/core/core.module';

import { Transaction } from 'app/features/inventory/model/transaction';
import { Subscription, Observable } from 'rxjs';
import {
  selectTransactionListByCompanyId,
  selectTransactionListByInvoiceId
} from 'app/features/inventory/selectors/order.selectors';
import { TransactionModalData } from 'app/features/inventory/model/transactionModalData';

@Component({
  selector: 'transactions-modal',
  templateUrl: './transactions-modal.component.html',
  styleUrls: ['./transactions-modal.component.scss'],
  animations: [routeAnimations],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TransactionsModalComponent implements OnInit {
  //transactions$: Observable<any> = this.store.pipe(select(selectSupplierList));

  // Observables
  companyTrans$: Observable<any> = this.store.pipe(
    select(selectTransactionListByCompanyId(this.data.cid))
  );
  orderTrans$: Observable<any> = this.store.pipe(
    select(selectTransactionListByInvoiceId(this.data.orderId))
  );

  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  displayedCols: string[] = [
    'date',
    'myCompanyName',
    'custCompanyName',
    'orderRef',
    'type',
    'amount'
  ];
  subscriptions: Array<Subscription> = [];

  // Local Variables
  transactionList: Transaction[] = [];
  companyName: string;
  orderRef: string;

  constructor(
    public dialogRef: MatDialogRef<TransactionsModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: TransactionModalData,

    private store: Store<AppState>
  ) {}

  ngOnInit() {
    // Transactions By Company
    this.subscriptions.push(
      this.companyTrans$.subscribe(transactions => {
        if (transactions != null && transactions.length > 0) {
          this.transactionList = transactions;
          this.dataSource = new MatTableDataSource<any>(this.transactionList);
          this.companyName =
            transactions[0].myCompanyId == this.data.cid
              ? transactions[0].myCompanyName
              : transactions[0].custCompanyName;
        }
      })
    );

    // Transactions By Order
    this.subscriptions.push(
      this.orderTrans$.subscribe(transactions => {
        if (transactions != null && transactions.length > 0) {
          this.transactionList = transactions;
          this.dataSource = new MatTableDataSource<any>(this.transactionList);
          this.orderRef = transactions[0].orderRef;
        }
      })
    );

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
