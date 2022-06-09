import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Inject, ChangeDetectorRef
} from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import {
  selectOrderList,
  selectCompanyList,
  selectCustomerReports,
  selectSupplierReports
} from 'app/features/inventory/selectors/order.selectors';
import { select, Store } from '@ngrx/store';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { Company } from 'app/features/inventory/model/company';
import { CustomerYearlyReport } from 'app/features/inventory/model/Report/customerYearlyReport';
import { SupplierYearlyReport } from 'app/features/inventory/model/Report/supplierYearlyReport';
import { AppState } from 'app/core/core.state';
import { CompanyEditModalComponent } from '../company-edit-modal/company-edit-modal.component';
import { CustomerMonthlyModalComponent } from '../../reports/customer-report/customer-monthly-modal/customer-monthly-modal.component';
import { SupplierMonthlyModalComponent } from '../../reports/supplier-report/supplier-monthly-modal/supplier-monthly-modal.component';
import { Contact } from 'app/features/inventory/model/contact';
import { ViewPurchaseModalComponent } from '../../order/view-purchase-modal/view-purchase-modal.component';
import { AddContactModalComponent } from '../../modal/add-contact-modal/add-contact-modal.component';

@Component({
  selector: 'company-view-modal',
  templateUrl: './company-view-modal.component.html',
  styleUrls: ['./company-view-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CompanyViewModalComponent implements OnInit {
  // Observable
  orders$: Observable<any> = this.store.pipe(select(selectOrderList));
  companies$: Observable<any> = this.store.pipe(select(selectCompanyList));
  customerReports$: Observable<any> = this.store.pipe(
    select(selectCustomerReports)
  );
  supplierReports$: Observable<any> = this.store.pipe(
    select(selectSupplierReports)
  );

  subscriptions: Array<Subscription> = [];

  // Datasource
  poDataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  soDataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  contactDataSouce: MatTableDataSource<any> = new MatTableDataSource<any>();
  contactDispCols = ['name', 'phone', 'address', 'edit'];

  columnDefinitions = [
    { def: 'orderdate', show: true },
    { def: 'referenceno', show: true },
    { def: 'supplierName', show: true },
    { def: 'customerName', show: true },
    { def: 'totalAmount', show: true },
    { def: 'status', show: true }
  ];

  // local variables
  allOrders: PurchaseOrder[];
  allCompanies: Company[];
  purchases: Array<PurchaseOrder>;
  sales: Array<PurchaseOrder>;
  customerReport: CustomerYearlyReport;
  supplierReport: SupplierYearlyReport;

  // Constructor ------
  constructor(
    public dialogRef: MatDialogRef<CompanyViewModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Company,
    public dialog: MatDialog,
    private store: Store<AppState>,
    private changeDetection: ChangeDetectorRef,
  ) {}

  // Display cols dynamically for Self, Customer or Supplier
  getDisplayedCols(orderType: string): string[] {
    let showSupplier =
      this.data.type == 'customer' ||
      (orderType == 'purchase' && this.data.type == 'self');
    let showCustomer =
      this.data.type == 'supplier' ||
      (orderType == 'sale' && this.data.type == 'self');
    this.columnDefinitions[2].show = showSupplier;
    this.columnDefinitions[3].show = showCustomer;
    return this.columnDefinitions.filter(cd => cd.show).map(cd => cd.def);
  }

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

    // subscribe companies
    this.subscriptions.push(
      this.companies$.subscribe(companies => {
        if (companies != null) {
          this.allCompanies = companies;
        }
      })
    );

    // subscribe Customer Report TODO : move logic to selector
    this.subscriptions.push(
      this.customerReports$.subscribe(cReports => {
        if (cReports != null) {
          this.customerReport = cReports.find(item => {
            return item.cid === this.data.id;
          });
        }
      })
    );

    // subscribe Supplier Report : move logic to selector
    this.subscriptions.push(
      this.supplierReports$.subscribe(sReports => {
        if (sReports != null) {
          this.supplierReport = sReports.find(item => {
            return item.cid === this.data.id;
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

    this.contactDataSouce = new MatTableDataSource<any>(this.data.contactList);
  }

  // Init Datasources for Purchase & Sales ------
  initDatasource(orders: PurchaseOrder[]) {
    let ctrl = this;
    this.allOrders = orders;
    this.purchases = [];
    this.sales = [];
    let selCompId = this.data.id;

    // TODO : move logic to selector
    this.allOrders
      .filter(function(c) {
        return c.customerid == selCompId || c.supplierid == selCompId;
      })
      .forEach(function(purch) {
        if (purch.type == 'purchase') {
          ctrl.purchases.push(purch);
        } else if (purch.type == 'sale') {
          ctrl.sales.push(purch);
        }
      });

    this.poDataSource = new MatTableDataSource<any>(this.purchases);
    this.soDataSource = new MatTableDataSource<any>(this.sales);
  }

  // Edit Product Modal  ------
  editCompany() {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'edit-company';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = this.data;

    // Open Dialog
    const dialogRef = this.dialog.open(CompanyEditModalComponent, dialogConfig);
  }

  editContact(data: Contact) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'edit-product';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    let contact = new Contact();
    dialogConfig.data = {contact:data, companyId:this.data.id};

    // Open Dialog
    const dialogRef = this.dialog.open(
      AddContactModalComponent,
      dialogConfig
    );
  }

  addContact() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'edit-product';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    let contact = new Contact();
    dialogConfig.data = {contact:contact, companyId:this.data.id};

    // Open Dialog
    const dialogRef = this.dialog.open(
      AddContactModalComponent,
      dialogConfig
    );
  }

  // Purchase Order Modal  ------
  viewOrder(element: PurchaseOrder) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'edit-product';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = element.id;

    // Open Dialog
    const dialogRef = this.dialog.open(
      ViewPurchaseModalComponent,
      dialogConfig
    );
  }

  // Report Modal  ------
  viewReport() {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'customer-report';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    if (this.data.type == 'customer') {
      dialogConfig.data = this.customerReport;
      // Open Dialog
      const dialogRef = this.dialog.open(
        CustomerMonthlyModalComponent,
        dialogConfig
      );
    }
    if (this.data.type == 'supplier') {
      dialogConfig.data = this.supplierReport;
      // Open Dialog
      const dialogRef = this.dialog.open(
        SupplierMonthlyModalComponent,
        dialogConfig
      );
    }
  }

  onClose() {
    //this.store.dispatch(approveTransModalClose());
    this.dialogRef.close();
  }
}

// hiding a column
//https://medium.com/@ole.ersoy/hiding-angular-material-data-table-columns-f8bdd5d62abd
