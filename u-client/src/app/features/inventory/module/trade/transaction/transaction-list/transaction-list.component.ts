import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef
} from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Store, select } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';
import { combineLatest, Observable, Subscription } from 'rxjs';
import {
  selectActiveSelfCompanyId,
  selectClientCompanyList,
  selectReload,
  selectSelfCompanyList,
  selectTransactionError,
  selectTransactionList,
  selectTransactionLoading,
  selectTransactionSearchRequest
} from 'app/features/inventory/selectors/order.selectors';
import { Transaction } from 'app/features/inventory/model/transaction';
import { ViewPurchaseModalComponent } from '../../order/view-purchase-modal/view-purchase-modal.component';
import { ViewTransactionModalComponent } from '../view-transaction-modal/view-transaction-modal.component';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { FormUtil } from 'app/features/inventory/service/FormUtil';
import { Company } from 'app/features/inventory/model/company';
import { map, startWith } from 'rxjs/operators';
import { TransactionSearchRequest } from 'app/features/inventory/model/rs/transactionSearchRequest';
import { searchTransactions } from 'app/features/inventory/action/transaction.action';
import { reload } from 'app/features/inventory/action/order.action';

@Component({
  selector: 'transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TransactionListComponent implements OnInit {
  // Observables
  transactions$: Observable<any> = this.store.pipe(
    select(selectTransactionList)
  );
  searchRequest$: Observable<any> = this.store.pipe(
    select(selectTransactionSearchRequest)
  );
  clientCompanies$: Observable<any> = this.store.pipe(
    select(selectClientCompanyList)
  );
  activeSelfId$: Observable<any> = this.store.pipe(
    select(selectActiveSelfCompanyId)
  );
  isTransLoading$: Observable<any> = this.store.pipe(
    select(selectTransactionLoading)
  );
  transactionError$: Observable<any> = this.store.pipe(
    select(selectTransactionError)
  );
  reload$: Observable<any> = this.store.pipe(select(selectReload));

  // Data Source
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  displayedFilterCols:string[]=[
    'dateFilter',

    'myCompanyFilter',
    'custCompanyFilter',
    'notesFilter',
    'typeFilter',
    'amountFilter'
  ];
  displayedCols: string[] = [
    'date',
    'myCompanyName',
    'custCompanyName',
    'notes',
    'type',
    'amount'
  ];

  // Dropdown option list
  dateList: Array<String> = [];
  myCompanyList: Array<String> = [];
  custCompanyList: Array<String> = [];
  notesList: Array<String> = [];
  typeList: Array<String> = [];
  amountList: Array<String> = [];

  // Filter Object
  filteredValues = {
    date: '',
    myCompanyName: '',
    custCompanyName: '',
    notes: '',
    type:  '',
    amount: ''
  }
  subscriptions: Array<Subscription> = [];

  // Local Variables
  activeSelfId: number = 0;
  searchRequest: TransactionSearchRequest = new TransactionSearchRequest();
  transactionList: Transaction[] = [];
  clientCompanyList: Array<Company>;
  isShown: boolean = false;

  // Dropdown Options
  clientOptions: Observable<Company[]>;

  // Form related
  searchForm = this.fb.group(TransactionListComponent.createSearchForm());
  static createSearchForm(): any {
    return {
      myCompanyId: [''],
      datePreset: ['last3'],
      clientType: ['all'],
      client: [''],
      clientId: [''],
      orderno: [''],
      invoiceno: [''],
      startDate: [''],
      endDate: [''],
      transType: ['all']
    };
  }
  datePresets = FormUtil.getDatePresets();
  clientPresets = FormUtil.getClientPresets();
  transPresets = FormUtil.getTransTypePresets();

  constructor(
    private store: Store<AppState>,
    public dialog: MatDialog,
    public router: Router,
    private fb: FormBuilder,
    private changeDetection: ChangeDetectorRef
  ) {}

  ngOnInit() {
    combineLatest([
      this.searchRequest$,
      this.transactions$,
      this.clientCompanies$,
      this.activeSelfId$
    ])
      .pipe()
      .subscribe(
        ([searchRequest, transactions, clientCompanies, activeSelfId]) => {
          if (this.activeSelfId !== activeSelfId) {
            this.activeSelfId = activeSelfId;
            this.submitSearch();
          }

          if (searchRequest != null) {
            this.searchRequest = searchRequest;
            this.populateSearchForm();
          }

          if (transactions != null) {
            this.transactionList = transactions;
            this.dataSource = new MatTableDataSource<any>(this.transactionList);
            this.initDatasource(transactions);
          }

          if (clientCompanies != null) {
            this.clientCompanyList = clientCompanies;
            this.populateClientDropDown();
          }
        }
      );
  }



  orderFilterPredicate() {
    const filterPred = (data, filter: string) => {
      const search = JSON.parse(filter);

      const dateSearch = !search['date'] || data['date'] === search['date'];
      const myCompanySearch = !search['myCompanyName'] || data['myCompanyName'] === search['myCompanyName'];
      const custCompanySearch = !search['custCompanyName'] || data['custCompanyName'] === search['custCompanyName'];
      const notesSearch = !search['notes'] || data['notes'] === search['notes'];
      const typeSearch = !search['type'] || data['type'] === search['type'];
      const amountSearch = !search['amount'] || data['amount'] == search['amount'];

      return (dateSearch && myCompanySearch &&  custCompanySearch && notesSearch && typeSearch && amountSearch);
    };
    return filterPred;
  }

  initDatasource(transaction){
    this.getFilterLists(transaction);
    // this.dataSource.data = this.transactionList;
    this.dataSource.filterPredicate = this.orderFilterPredicate();
    this.changeDetection.detectChanges();
  }

  //--------------------------
  // Populate Search Form
  //---------------------------
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
    this.searchForm.controls.clientType.setValue(this.searchRequest.clientType);
    this.searchForm.controls.startDate.setValue(d1);
    this.searchForm.controls.endDate.setValue(d2);
    if (this.searchRequest.clientId && this.clientCompanyList) {
      this.searchForm.controls.client.setValue(
        this.clientCompanyList.find(c => {
          return c.id == this.searchRequest.clientId;
        })
      );
    }
    this.searchForm.controls.transType.setValue(this.searchRequest.transType);
    this.searchForm.controls.orderno.setValue(this.searchRequest.orderno);
    this.searchForm.controls.invoiceno.setValue(this.searchRequest.invoiceno);
  }

  // ------------------------
  // Search Transactions
  // ------------------------
  submitSearch() {
    console.log('Search ' + this.searchForm.value);

    let request: TransactionSearchRequest = new TransactionSearchRequest();
    let client = this.searchForm.controls.client.value;
    request.clientId = client != null ? client.id : null;
    request.startDate =
      this.searchForm.controls.startDate.value.getTime != null
        ? this.searchForm.controls.startDate.value.getTime() / 1000
        : null;
    request.endDate =
      this.searchForm.controls.endDate.value.getTime != null
        ? this.searchForm.controls.endDate.value.getTime() / 1000
        : null;
    request.datePreset = this.searchForm.controls.datePreset.value;
    request.clientType = this.searchForm.controls.clientType.value;
    request.myCompanyId = this.activeSelfId;
    request.transType = this.searchForm.controls.transType.value;
    request.orderno = this.searchForm.controls.orderno.value;
    request.invoiceno = this.searchForm.controls.invoiceno.value;

    console.log('Search ' + request);

    this.store.dispatch(searchTransactions({ request: request }));
  }

  // ------------------------
  // CLient Company Dropdown
  // ------------------------
  populateClientDropDown() {
    this.clientOptions = this.searchForm.controls.client.valueChanges.pipe(
      startWith(''),
      map(value => (typeof value === 'string' ? value : value.name)),
      map(name =>
        name ? this._filterClients(name) : this.clientCompanyList.slice()
      )
    );
  }

  private _filterClients(name: string): Company[] {
    const filterValue = name.toLowerCase();

    return this.clientCompanyList.filter(
      option => option.name.toLowerCase().indexOf(filterValue) == 0
    );
  }

  displayFn(company: Company): string {
    return company && company.name ? company.name : '';
  }

  // to do open invoice

  viewPurchase(element: Transaction) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'view-purchase';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = element.invoiceId;

    // Open Dialog
    const dialogRef = this.dialog.open(
      ViewPurchaseModalComponent,
      dialogConfig
    );
  }

  viewTransaction(element: Transaction) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'view-purchase';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = element.id;

    // Open Dialog
    const dialogRef = this.dialog.open(
      ViewTransactionModalComponent,
      dialogConfig
    );
  }

  // editJournal(element: Transaction) {
  //   // Dialog config
  //   if(element == null) {
  //     element = new Transaction();
  //     element.id = 0;
  //   }
  //   const dialogConfig = new MatDialogConfig();
  //   dialogConfig.id = 'edit-journal';
  //   dialogConfig.disableClose = true;
  //   dialogConfig.width = '800px';
  //   dialogConfig.data = element.id;

  //   // Open Dialog
  //   const dialogRef = this.dialog.open(
  //     EnterJournalModalComponent,
  //     dialogConfig
  //   );
  // }

  createJournal() {
    let navStr = 'trade/journal/edit';
    this.router.navigate([navStr, 0]);
  }

  printPreview() {}

  import() {}

  toggleShow() {
    this.isShown = !this.isShown;
  }



  applyFilter(filterVal: any, key:string){


    this.filteredValues[key] = filterVal;
    this.dataSource.filter = JSON.stringify(this.filteredValues);

    console.log(this.dataSource);
  }

  clearFilters() {
    this.dataSource.filter = '';
    this.resetFilteredValues();
  }

  resetFilteredValues(){
    this.filteredValues = {
      date: '',
      myCompanyName: '',
      custCompanyName: '',
      notes: '',
      type: '',
      amount: ''
    };
  }

  filterLists(list) {
    return list.filter((x, i, a) => x && a.indexOf(x) === i).sort();
  }
  getFilterLists(transaction: Transaction[]) {
    if(transaction) {



      this.dateList = this.filterLists(transaction.map(data => data.date));
      this.myCompanyList = this.filterLists(transaction.map(data => data.myCompanyName));



      this.custCompanyList = this.filterLists(transaction.map(data => data.custCompanyName));
      this.notesList = this.filterLists(transaction.map(data => data.notes));
      this.typeList = this.filterLists(transaction.map(data => data.type));
      this.amountList = this.filterLists(transaction.map(data => data.amount));
    }
  }
}
