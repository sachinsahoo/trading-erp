import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Input
} from '@angular/core';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Store, select } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { Router } from '@angular/router';
import { selectSaleOrdersRelatedToPOID } from 'app/features/inventory/selectors/order.selectors';

@Component({
  selector: 'related-sale-orders',
  templateUrl: './related-sale-orders.component.html',
  styleUrls: ['./related-sale-orders.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RelatedSaleOrdersComponent implements OnInit {
  @Input() order: PurchaseOrder;

  //Observables
  subscriptions: Array<Subscription> = [];

  // Local Vars
  relatedSaleOrders: PurchaseOrder[];

  soDataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  soDisplayedColumns = [
    'orderdate',
    'referenceno',
    'customerName',
    'totalAmount',
    'status'
  ];

  constructor(
    private store: Store<AppState>,
    public dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit() {
    // Related Sale Orders
    this.subscriptions.push(
      this.store
        .pipe(select(selectSaleOrdersRelatedToPOID(this.order)))
        .subscribe(orderList => {
          if (orderList != null && orderList.length > 0) {
            this.relatedSaleOrders = orderList;
            this.soDataSource = new MatTableDataSource<any>(orderList);
          }
        })
    );
  }
}
