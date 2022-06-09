import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Inject
} from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Store, select } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { Observable, Subscription, combineLatest } from 'rxjs';
import {
  selectSaleOrdersRelatedToPOID,
  selectOrderById,
  selectCompanyList
} from 'app/features/inventory/selectors/order.selectors';
import { Company } from 'app/features/inventory/model/company';
import { updateSelectedOrderInStore } from 'app/features/inventory/action/order.action';
import { Router } from '@angular/router';
import { PrintOrderModalComponent } from '../print-order-modal/print-order-modal.component';

@Component({
  selector: 'view-purchase-modal',
  templateUrl: './view-purchase-modal.component.html',
  styleUrls: ['./view-purchase-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ViewPurchaseModalComponent implements OnInit {
  //Observables
  selectedOrder$: Observable<any> = this.store.pipe(
    select(selectOrderById(this.oid))
  );

  companies$: Observable<any> = this.store.pipe(select(selectCompanyList));
  subscriptions: Array<Subscription> = [];

  //datasource for PO Product
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  soDataSource: MatTableDataSource<any> = new MatTableDataSource<any>();

  displayedColumns = ['Slno', 'Description', 'Quantity', 'Rate', 'Amount'];
  soDisplayedColumns = [
    'orderdate',
    'referenceno',
    'customerName',
    'totalAmount',
    'status'
  ];

  // Local Variables
  order: PurchaseOrder = new PurchaseOrder();
  relatedSaleOrders: PurchaseOrder[] = [];
  allCompanies: Company[];

  /** Gets the total cost of all transactions. */
  getTotalCost() {
    return 9054843;
  }

  // Constructor -------
  constructor(
    public dialogRef: MatDialogRef<ViewPurchaseModalComponent>,
    @Inject(MAT_DIALOG_DATA) public oid: number,
    private store: Store<AppState>,
    public dialog: MatDialog,
    private router: Router
  ) {}

  // Init -----------
  ngOnInit() {
    // Combine Observables [Order by ID] and [all Companies]
    combineLatest(this.selectedOrder$, this.companies$).subscribe(data => {
      this.order = data[0];
      this.allCompanies = data[1];
      this.populateOrder();
    });

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

    this.dialogRef.keydownEvents().subscribe(event => {
      if (event.key === 'Escape') {
        this.onClose();
      }
    });
  }

  populateOrder() {
    if (this.order != null) {
      this.order.customer = this.getCompnayById(this.order.customerid);
      this.order.supplier = this.getCompnayById(this.order.supplierid);
      this.dataSource = new MatTableDataSource<any>(this.order.poproductlist);
    } else {
    }
  }

  editOrder() {
    this.store.dispatch(updateSelectedOrderInStore({ order: this.order }));
    let navStr =
      this.order.type == 'purchase'
        ? 'trade/purchase/edit'
        : 'trade/sale/edit';
    this.router.navigate([navStr]);
    this.dialogRef.close();
  }

  onClose() {
    //this.store.dispatch(approveTransModalClose());
    this.dialogRef.close();
  }

  printPreview() {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'print-purchase';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = this.order;

    // Open Dialog
    const dialogRef = this.dialog.open(
      PrintOrderModalComponent,
      dialogConfig
    );
  }

  viewRelatedSO(data: PurchaseOrder) {}

  // Get Company By ID
  getCompnayById(cid: number): Company {
    let company: Company;
    if (this.allCompanies != null) {
      company = this.allCompanies.filter(function(comp) {
        return comp.id == cid;
      })[0];
    }

    return company;
  }
}
