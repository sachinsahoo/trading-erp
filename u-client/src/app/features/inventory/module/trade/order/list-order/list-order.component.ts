import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { combineLatest, Observable, Subscription } from 'rxjs';
import { select, Store } from '@ngrx/store';
import { selectActiveSelfCompanyId, selectOrderError, selectOrderList, selectOrderLoading, selectOrderSearchRequest, selectReload } from 'app/features/inventory/selectors/order.selectors';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { Company } from 'app/features/inventory/model/company';
import { AppState } from 'app/core/core.state';
import { ActivatedRoute, Router } from '@angular/router';
import { ViewPurchaseModalComponent } from '../view-purchase-modal/view-purchase-modal.component';
import { TransactionsModalComponent } from '../../transaction/transactions-modal/transactions-modal.component';
import { searchOrders, updateSelectedOrderInStore } from 'app/features/inventory/action/order.action';
import { FormBuilder, Validators } from '@angular/forms';
import { FormUtil } from 'app/features/inventory/service/FormUtil';
import { OrderSearchRequest } from 'app/features/inventory/model/rs/orderSearchRequest';

@Component({
  selector: 'list-order',
  templateUrl: './list-order.component.html',
  styleUrls: ['./list-order.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListOrderComponent implements OnInit {
  
  // Observables
  activeSelfCompId$: Observable<any> = this.store.pipe(select(selectActiveSelfCompanyId));
  orders$: Observable<any> = this.store.pipe(select(selectOrderList));
  searchRequest$: Observable<any> = this.store.pipe(select(selectOrderSearchRequest));
  
  orderError$: Observable<any> = this.store.pipe(select(selectOrderError));
  orderLoading$: Observable<any> = this.store.pipe(select(selectOrderLoading));
  reload$: Observable<any> = this.store.pipe(select(selectReload));
  
  subscriptions: Array<Subscription> = [];

  // Data source
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  displayedCols: string[] = [
    'orderdate',
    'referenceno',
    'supplierName',
    'customerName',
    'status',
    'amount'
  ];

  // Filter Dropdowns
  displayedDdns: string[] = [
    'orderdateDdn',
    'referencenoDdn',
    'supplierNameDdn',
    'customerNameDdn',
    'statusDdn',
    'amountDdn'
  ];

  // Dropdown option list
  orderdateList: Array<String> = [];
  referencenoList: Array<String> = [];
  supplierList: Array<String> = [];
  customerList: Array<String> = [];
  statusList: Array<String> = [];
  amountList: Array<String> = [];

  // Filter Object
  filteredValues = {
    orderdate: '',
    referenceno: '',
    supplierName: '',
    customerName: '',
    status:  '',
    amount: ''
  }

  // Local Variables
  activeSelfId: number;
  orders: PurchaseOrder[];
  allOrders: PurchaseOrder[];
  allCompanies: Company[];
  orderType: string = '';
  isShown: boolean = false;
  searchRequest: OrderSearchRequest = new OrderSearchRequest();

  // Form related
  searchForm = this.fb.group(ListOrderComponent.createSearchForm());
  static createSearchForm(): any {
    return {
      myCompanyId: [''],
      datePreset: ['last3'],
      startDate: [''],
      endDate: ['']
    };
  }
  datePresets = FormUtil.getDatePresets();


  constructor(
    private store: Store<AppState>,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private changeDetection: ChangeDetectorRef
  ) { }

  // Init -------------------------------
  ngOnInit() {

    combineLatest([this.route.data, this.searchRequest$, this.orders$, this.activeSelfCompId$])
      .pipe()
      .subscribe(([data, searchRequest, orders, selfId]) => {
        
        if(this.activeSelfId !== selfId){
          this.activeSelfId = selfId;
          this.submitSearch();
        }

        if (data != null) {
          this.orderType = data.type;
        }
        if (orders != null) {
          this.initDatasource(orders);
        }
        if (searchRequest != null) {
          this.searchRequest = searchRequest;
          console.log(this.searchRequest);
          this.populateSearchForm();
        }

      });


  }

  // --------------------
  // Populate  Search Form
  // --------------------
  populateSearchForm() {

    let d1: Date = new Date();
    let d2: Date = new Date();
    if (this.searchRequest.startDate != null) {
      d1 = new Date(0);
      d1.setUTCSeconds(this.searchRequest.startDate);
    }

    if (this.searchRequest.endDate != null) {
      d2 = new Date(0);
      d2.setUTCSeconds(this.searchRequest.endDate);
    }

    this.searchForm.controls.datePreset.setValue(this.searchRequest.datePreset);
    this.searchForm.controls.startDate.setValue(d1);
    this.searchForm.controls.endDate.setValue(d2);

  }

  // --------------------
  // Submit Search
  // --------------------

  submitSearch() {


    this.searchRequest.datePreset = this.searchForm.controls.datePreset.value;
    if (this.searchRequest.datePreset == 'custom') {
      this.searchRequest.startDate = this.searchForm.controls.startDate.value != null ? this.searchForm.controls.startDate.value.getTime() / 1000 : null;
      this.searchRequest.endDate = this.searchForm.controls.endDate.value != null ? this.searchForm.controls.endDate.value.getTime() / 1000 : null;
    }
    this.searchRequest.myCompanyId = this.activeSelfId;

    this.store.dispatch(searchOrders({ searchRequest: this.searchRequest }));

  }

  initDatasource(orderList: PurchaseOrder[]) {
    if (orderList != null) {
      this.allOrders = orderList;
      this.orders = orderList.filter(order => order.type === this.orderType);
      this.getFilterLists(this.orders );
      this.dataSource.data = this.orders;
      this.dataSource.filterPredicate = this.orderFilterPredicate();
      this.changeDetection.detectChanges();
    }
  }


  // ----------------------
  // Custom Filter Related
  // ----------------------

  filterLists(list) {
    return list.filter((x, i, a) => x && a.indexOf(x) === i).sort();
  }

  getFilterLists(orderList: PurchaseOrder[]) {
    if(orderList) {
      
      

      this.referencenoList = this.filterLists(orderList.map(data => data.referenceno))
      this.orderdateList = this.filterLists(orderList.map(data => data.orderdate))
      this.supplierList = this.filterLists(orderList.map(data => data.supplierName))
      this.customerList = this.filterLists(orderList.map(data => data.customerName))
      this.statusList = this.filterLists(orderList.map(data => data.status))
      this.amountList = this.filterLists(orderList.map(data => data.rupTotalAmount))
    }
  }

  orderFilterPredicate() {
    const filterPred = (data, filter: string) => {
      const search = JSON.parse(filter);

      const orderdateSearch = !search['orderdate'] || data['orderdate'] === search['orderdate'];
      const referenceSearch = !search['referenceno'] || data['referenceno'] === search['referenceno'];
      const supplierSearch = !search['supplierName'] || data['supplierName'] === search['supplierName'];
      const customerSearch = !search['customerName'] || data['customerName'] === search['customerName'];
      const statusSearch = !search['status'] || data['status'] === search['status'];
      const amountSearch = !search['amount'] || data['rupTotalAmount'] == search['amount'];

      return (orderdateSearch && referenceSearch&&  supplierSearch && customerSearch && statusSearch && amountSearch);
    };
    return filterPred;
  }

  applyFilter(filterVal: any, key:string){
    this.filteredValues[key] = filterVal;
    this.dataSource.filter = JSON.stringify(this.filteredValues);
  }

  clearFilters() {
    this.dataSource.filter = '';
    this.resetFilteredValues();
  }

  resetFilteredValues(){
    this.filteredValues = {
      orderdate: '',
      referenceno: '',
      supplierName: '',
      customerName: '',
      status: '',
      amount: ''
    };
  }

  openPurchase(element: PurchaseOrder) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'view-purchase';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = element.id;

    // Open Dialog
    const dialogRef = this.dialog.open(
      ViewPurchaseModalComponent,
      dialogConfig
    );
  }

  getOrderURL() {
    return this.orderType == 'purchase'
      ? 'trade/purchase'
      : this.orderType == 'sale'
        ? 'trade/sale'
        : 'trade/comm';
  }

  getOrderLabel() {
    console.log(this.orderType);
    return this.orderType == 'purchase'
      ? 'Purchases'
      : this.orderType == 'sale'
        ? 'Sales'
        : 'Commissions';
  }

  viewOrderList() {
    let navStr: string = this.getOrderURL().concat('/list');

    console.log(navStr);
    this.router.navigate([navStr]);
  }

  viewInvoices() { }

  viewComms() { }

  viewOrder(element: PurchaseOrder) {
    let navStr = 'trade/order/view';
    this.router.navigate([navStr, element.id]);
  }

  openTrans(element: PurchaseOrder) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'company-transactions';
    dialogConfig.disableClose = true;
    dialogConfig.width = '1000px';
    dialogConfig.data = { orderId: element.id };

    // Open Dialog
    const dialogRef = this.dialog.open(
      TransactionsModalComponent,
      dialogConfig
    );
  }

  createOrder() {
    let navLink = 'trade/order/edit';
    this.router.navigate([navLink, this.orderType, 0]);
  }
  reports() {

  }

  toggleShow() {
    this.isShown = !this.isShown;
  }
}
