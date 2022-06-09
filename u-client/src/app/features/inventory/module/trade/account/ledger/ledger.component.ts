import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { AccountService } from 'app/features/inventory/service/api/account.service';
import { Journal } from 'app/features/inventory/model/journal';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Store, select } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { ActivatedRoute, Router } from '@angular/router';
import { loadLedger } from 'app/features/inventory/action/account.action';
import { combineLatest, Observable } from 'rxjs';
import { selectAccountList, selectActiveSelfCompanyId, selectJournalSearchRequest, selectLedger } from 'app/features/inventory/selectors/order.selectors';
import { ViewTransactionModalComponent } from '../../transaction/view-transaction-modal/view-transaction-modal.component';
import { FormBuilder, Validators } from '@angular/forms';
import { JournalSearchRequest } from 'app/features/inventory/model/rs/journalSearchRequest';
import { FormUtil } from 'app/features/inventory/service/FormUtil';
import { map, startWith } from 'rxjs/operators';
import { Account } from 'app/features/inventory/model/account';

@Component({
  selector: 'ledger',
  templateUrl: './ledger.component.html',
  styleUrls: ['./ledger.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LedgerComponent implements OnInit {
  // Observables
  searchRequest$: Observable<any> = this.store.pipe(select(selectJournalSearchRequest));
  journals$: Observable<any> = this.store.pipe(select(selectLedger));
  activeSelfCompanyId$: Observable<any> = this.store.pipe(select(selectActiveSelfCompanyId));
  accountList$: Observable<any> = this.store.pipe(select(selectAccountList));

  // Local
  journals: Array<Journal>;
  searchRequest: JournalSearchRequest = new JournalSearchRequest();
  accountOptions: Observable<Account[]>;
  accountId: number;
  selfId: number;
  isShown: boolean;
  accountList: Array<Account> = [];

  // Form related
  searchForm = this.fb.group(LedgerComponent.createSearchForm());
  static createSearchForm(): any {
    return {
      account: [''],
      acctId: [''],
      myCompanyId: [''],
      datePreset: ['last3'],
      startDate: [''],
      endDate: ['']
    };
  }
  datePresets = FormUtil.getDatePresets();


  //data source
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  displayedCols: string[] = ['transDate', 'desc', 'debit', 'credit'];



  constructor(
    private store: Store<AppState>,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private accountService: AccountService,
    private changeDetection: ChangeDetectorRef
  ) { }

  ngOnInit() {
    combineLatest([this.route.params, this.activeSelfCompanyId$, this.accountList$])
      .pipe()
      .subscribe(([routeParams, activeSelfId, acList]) => {

        this.accountList = acList;

        this.selfId = activeSelfId;
        this.searchRequest.myCompanyId = activeSelfId;
        this.accountId = routeParams['id'];
        this.searchRequest.acctId = routeParams['id'];
        let acct = this.accountList.find(a => { return a.id == this.searchRequest.acctId });
        this.searchForm.controls.account.setValue(acct);
        this.store.dispatch(loadLedger({ searchRequest: this.searchRequest }));
        this.populateAccountDropDown();
      });

    this.journals$.subscribe(ledger => {
      this.journals = ledger;
      this.dataSource = new MatTableDataSource<any>(ledger);

    });
  }


  // --------------------
  // Submit Search
  // --------------------

  submitSearch() {
    this.searchRequest.datePreset = this.searchForm.controls.datePreset.value;
    
    if (this.searchForm.controls.account.value.id) {
      this.searchRequest.acctId = this.searchForm.controls.account.value.id;
    } else {
      this.searchRequest.acctId = this.accountId;
    }

    this.searchRequest.myCompanyId = this.selfId;

    if (this.searchRequest.datePreset == 'custom') {
      this.searchRequest.startDate = this.searchForm.controls.startDate.value != null ? this.searchForm.controls.startDate.value.getTime() / 1000 : null;
      this.searchRequest.endDate = this.searchForm.controls.endDate.value != null ? this.searchForm.controls.endDate.value.getTime() / 1000 : null;
    }
    this.store.dispatch(loadLedger({ searchRequest: this.searchRequest }));
  }

  //-----------------------
  // Account Drop Down
  //-----------------------
  populateAccountDropDown() {
    this.accountOptions = this.searchForm.controls.account.valueChanges.pipe(
      startWith(''),
      map(value => (typeof value === 'string' ? value : value.name)),
      map(name => (name ? this._filterAccount(name) : this.accountList.slice()))
    );
  }

  private _filterAccount(name: string): Account[] {
    const filterValue = name.toLowerCase();

    return this.accountList.filter(
      option => option.name.toLowerCase().indexOf(filterValue) > -1

    );
  }

  displayAccountFn(account: Account): string {
    return account && account.name ? account.name : '';
  }

  accountSelected(event: any) {

  }




  viewTransaction(id: number) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'view-transaction';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = id;

    // Open Dialog
    const dialogRef = this.dialog.open(
      ViewTransactionModalComponent,
      dialogConfig
    );
  }

  toggleShow() {
    this.isShown = !this.isShown;
  }
}
