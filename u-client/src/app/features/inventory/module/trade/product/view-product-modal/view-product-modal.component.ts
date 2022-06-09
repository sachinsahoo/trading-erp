import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Inject
} from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Store, select } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { EditProductModalComponent } from '../edit-product-modal/edit-product-modal.component';
import { Product } from 'app/features/inventory/model/product';
import {
  selectOrderList,
  selectCompanyList,
  selectProductReports,
  selectInvoiceList
} from 'app/features/inventory/selectors/order.selectors';
import { Observable, Subscription } from 'rxjs';
import { PurchaseSaleMonthlyReport } from 'app/features/inventory/model/Report/purchaseSaleMonthlyReport';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { POProduct } from 'app/features/inventory/model/poproduct';
import { Company } from 'app/features/inventory/model/company';
import { ProductYearlyReport } from 'app/features/inventory/model/Report/productYearlyReport';
import { ProductMonthlyReport } from 'app/features/inventory/model/Report/productMonthlyReport';
import { ProductMonthlyModalComponent } from '../../reports/product-report/product-monthly-modal/product-monthly-modal.component';
import { ViewPurchaseModalComponent } from '../../order/view-purchase-modal/view-purchase-modal.component';
import { Invoice } from 'app/features/inventory/model/invoice';
import { Router } from '@angular/router';

@Component({
  selector: 'view-product-modal',
  templateUrl: './view-product-modal.component.html',
  styleUrls: ['./view-product-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ViewProductModalComponent implements OnInit {
  // Observable
  orders$: Observable<any> = this.store.pipe(select(selectOrderList));
  invoices$: Observable<any> = this.store.pipe(select(selectInvoiceList));
  productReport$: Observable<any> = this.store.pipe(
    select(selectProductReports)
  );

  subscriptions: Array<Subscription> = [];

  // Datasource
  invDataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  poDataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  soDataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  displayedCols: string[] = [
    'date',
    'poref',
    'client',
    'quantity',
    'price',
    'status'
  ];

  // local variables
  allOrders: PurchaseOrder[];
  allInvoices: Invoice[];
  poPurchases: Array<POProduct>;
  poSales: POProduct[];
  productReport: ProductYearlyReport;

  // Constructor ------
  constructor(
    public dialogRef: MatDialogRef<ViewProductModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Product,
    public dialog: MatDialog,
    private router: Router,
    private store: Store<AppState>
  ) {}

  // Init ------
  ngOnInit() {
    // subscribe orders
    this.subscriptions.push(
      this.orders$.subscribe(orders => {
        if (orders != null) {
          this.initDatasource(orders);
        }
      })
    );

    // subscribe invoices
    this.subscriptions.push(
      this.invoices$.subscribe(invoices => {
        if (invoices != null) {
          this.initDatasourceInv(invoices);
        }
      })
    );

    // subscribe companies
    this.subscriptions.push(
      this.productReport$.subscribe(pReports => {
        if (pReports != null) {
          this.productReport = pReports.find(item => {
            return item.prodid === this.data.id;
          });
        }
      })
    );

    // Close modal on Esc
    this.dialogRef.keydownEvents().subscribe(event => {
      if (event.key === 'Escape') {
        this.onClose();
      }
    });
  }

  // Init Datasources for Purchase & Sales ------
  initDatasource(orders: PurchaseOrder[]) {
    let ctrl = this;
    this.allOrders = orders;
    this.poPurchases = [];
    this.poSales = [];
    let selProdId = this.data.id;
    this.allOrders.forEach(function(purch) {
      let poPlist: POProduct[];
      poPlist = purch.poproductlist.filter(function(po) {
        return po.productId === selProdId;
      });
      if (poPlist != null && poPlist.length > 0) {
        poPlist.forEach(function(po) {
          if (purch.type == 'purchase') {
            ctrl.poPurchases.push(po);
          } else if (purch.type == 'sale') {
            ctrl.poSales.push(po);
          }
        });
      }
    });

    this.poDataSource = new MatTableDataSource<any>(this.poPurchases);
    this.soDataSource = new MatTableDataSource<any>(this.poSales);
  }

  // Init Datasources for Purchase & Sales ------
  initDatasourceInv(orders: PurchaseOrder[]) {
    let ctrl = this;
    this.allOrders = orders;
    this.poPurchases = [];
    this.poSales = [];
    let selProdId = this.data.id;
    this.allOrders.forEach(function(purch) {
      let poPlist: POProduct[];
      poPlist = purch.poproductlist.filter(function(po) {
        return po.productId === selProdId;
      });
      if (poPlist != null && poPlist.length > 0) {
        poPlist.forEach(function(po) {
          if (purch.type == 'purchase') {
            ctrl.poPurchases.push(po);
          } else if (purch.type == 'sale') {
            ctrl.poSales.push(po);
          }
        });
      }
    });

    this.poDataSource = new MatTableDataSource<any>(this.poPurchases);
    this.soDataSource = new MatTableDataSource<any>(this.poSales);
  }

  // Edit Product Modal  ------
  editProduct() {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'edit-product';
    dialogConfig.disableClose = true;
    dialogConfig.width = '1000px';
    dialogConfig.data = this.data;

    // Open Dialog
    const dialogRef = this.dialog.open(EditProductModalComponent, dialogConfig);
  }

  // Purchase Order Modal  ------
  viewOrder(element: POProduct) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'edit-product';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = element.oid;

    // Open Dialog
    const dialogRef = this.dialog.open(
      ViewPurchaseModalComponent,
      dialogConfig
    );
  }

  viewOrder1(element: POProduct) {
    let navStr = 'trade/order/view';
    this.router.navigate([navStr, element.oid]);
  }

  // Purchase Order Modal  ------
  viewReport() {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'product-report';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = this.productReport;

    // Open Dialog
    const dialogRef = this.dialog.open(
      ProductMonthlyModalComponent,
      dialogConfig
    );
  }

  onClose() {
    //this.store.dispatch(approveTransModalClose());
    this.dialogRef.close();
  }
}
