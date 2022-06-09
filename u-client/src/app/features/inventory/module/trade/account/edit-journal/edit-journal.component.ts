import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { Observable, Subscription, combineLatest } from 'rxjs';
import { select, Store } from '@ngrx/store';
import {
  selectActiveTransaction,
  selectTransactionById,
  selectBalanceSheet,
  selectAccountList,
  selectSelfCompanyList, selectReload, selectActiveSelfCompany, selectActiveSelfCompanyId
} from 'app/features/inventory/selectors/order.selectors';
import { Transaction } from 'app/features/inventory/model/transaction';
import {
  FormGroup,
  FormArray,
  Validators,
  FormBuilder,
  FormGroupDirective
} from '@angular/forms';
import { Journal } from 'app/features/inventory/model/journal';
import { AppState } from 'app/core/core.state';
import { MatDialog } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import {
  updateSelectedTransactionInStore,
  getTransaction,
  saveTransaction
} from 'app/features/inventory/action/transaction.action';
import { flatMap, startWith, map } from 'rxjs/operators';
import { AccountService } from 'app/features/inventory/service/api/account.service';
import { Account } from 'app/features/inventory/model/account';
import { Company } from 'app/features/inventory/model/company';

@Component({
  selector: 'edit-journal',
  templateUrl: './edit-journal.component.html',
  styleUrls: ['./edit-journal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EditJournalComponent implements OnInit {
  // Observable
  transaction$: Observable<any> = this.store.pipe(
    select(selectActiveTransaction)
  );
  accountList$: Observable<any> = this.store.pipe(select(selectAccountList));
  reload$: Observable<any> = this.store.pipe(select(selectReload));
  selfCompanyList$: Observable<any> = this.store.pipe(
    select(selectSelfCompanyList)
  );

  selfActiveCompanyId$: Observable<any> = this.store.pipe(select(selectActiveSelfCompanyId));

  subscriptions: Array<Subscription> = [];

  // Local
  transid: number;
  transaction: Transaction;
  accountList: Array<Account>;
  selfCompanyList: Array<Company>;
  selfActiveCompanyId: number;

  accountOptions: Observable<Account[]>;

  // Form Related
  transactionForm: FormGroup = this.fb.group({
    journals: this.fb.array([]),
    date: [''],
    myCompanyId: [''],
    notes: [''],
    type: [''],
    amount: ['']
  });
  journalFormArray: FormArray;
  mode: boolean;
  touchedRows: any;

  journalForm = this.fb.group(EditJournalComponent.createJournalForm());

  static createJournalForm(): any {
    return {
      account: ['', [Validators.required]],
      desc: [''],
      drcrtype: [''],
      dramount: [''],
      cramount: [''],
      isEditable: [false]
    };
  }

  constructor(
    private store: Store<AppState>,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private changeDetection: ChangeDetectorRef,
  ) { }

  ngOnInit() {
    

    combineLatest([this.route.params, this.accountList$, this.selfCompanyList$, this.selfActiveCompanyId$])
      .pipe()
      .subscribe(([params, acList, selfCompList, selfActiveCompId]) => {
       
        this.transid = params['id'];
        // For new Journal
        if (this.transid == 0) {
          this.transaction = new Transaction();
          this.transaction.type = 'journal';
          this.transaction.myCompanyId = selfActiveCompId;

          // Edit Journal
        } else {
          this.store
            .select(selectTransactionById(this.transid))
            .subscribe(transaction => {
              if (transaction != null) {
                this.transaction = transaction;
              } else {
                this.store.dispatch(getTransaction({ id: this.transid }));
              }
            });
        }

        this.selfActiveCompanyId = selfActiveCompId;
        if (acList != null) {
          this.accountList = acList;
          this.populateAccountDropDown();
        }
        console.log(selfCompList);
        if (selfCompList != null) {
          this.selfCompanyList = selfCompList;
        }

        this.initForm();
      });
  }
  // End Init -----------

  ngAfterOnInit() {
    this.journalFormArray = this.transactionForm.get('journals') as FormArray;
  }

  //------------------------------
  // Initialize Transaction Form
  //------------------------------

  initForm() {
    var d = new Date();
    if (this.transaction.id) {
      d = new Date(0);
      var utcSeconds = this.transaction.date;
      d.setUTCSeconds(utcSeconds);
    }

    this.transactionForm = this.fb.group({
      journals: this.fb.array([]),
      date: [d],
      myCompanyId: [this.transaction.myCompanyId, [Validators.required]],
      notes: [this.transaction.notes, [Validators.required]],
      type: ['journal'],
      amount: [this.transaction.amount]
    });
    this.populateJournalForms();
  }

  populateJournalForms() {
    let control = this.transactionForm.get('journals') as FormArray;

    if (this.transaction.journals) {
      this.transaction.journals.forEach(journal => {
        control.push(this.getJournalForm(journal));
      });
    }
  }

  getJournalForm(journal: Journal): FormGroup {
    let jForm = this.fb.group(EditJournalComponent.createJournalForm());
    jForm.controls.account.setValue(
      this.accountList.find(ac => {
        return ac.id == journal.accid;
      })
    );
    jForm.controls.isEditable.setValue(false);
    jForm.controls.drcrtype.setValue(journal.drcrtype);
    jForm.controls.dramount.setValue(
      journal.drcrtype == 1 ? journal.amount : ''
    );
    jForm.controls.cramount.setValue(
      journal.drcrtype == 2 ? journal.amount : ''
    );
    return jForm;
  }

  get getFormControls() {
    const control1 = this.transactionForm.get('journals') as FormArray;
    return control1;
  }

  //------------------------------
  // Submit Journal Form
  //------------------------------

  submitJournal(formDirective: FormGroupDirective) {
    let journals = this.transactionForm.get('journals') as FormArray;

    if (this.validateJournalForm()) {
      let jForm = this.fb.group(EditJournalComponent.createJournalForm());
      let dramount = this.journalForm.controls.dramount.value;
      this.journalForm.controls.drcrtype.setValue(dramount > 0 ? 1 : 2);
      jForm.setValue(JSON.parse(JSON.stringify(this.journalForm.value)));

      journals.push(jForm);
      this.resetJournalForm(formDirective);
    }
  }

  resetJournalForm(formDirective: FormGroupDirective) {
    // Reset Form
    this.journalForm.controls.account.reset('');
    this.journalForm.controls.account.setErrors(null);
    this.journalForm.reset('');
    formDirective.resetForm();
  }

  validateJournalForm() {
    // Account not found
    if (this.journalForm.controls.account.value.id == null) {
      this.journalForm.controls.account.setErrors({
        notFound: true
      });
      return false;
    }

    // duplicate account
    if (
      this.getJournalsFromFormArray().findIndex(
        journal => journal.accid == this.journalForm.controls.account.value.id
      ) > -1
    ) {
      this.journalForm.controls.account.setErrors({
        duplicate: true
      });
      return false;
    }
    return true;
  }

  getJournalsFromFormArray() {
    let journalArray: Journal[] = new Array<Journal>();
    let jFormArray = this.transactionForm.get('journals') as FormArray;
    for (let i = 0; i < jFormArray.length; i++) {
      const element: FormGroup = jFormArray.at(i) as FormGroup;
      let journal: Journal =
        this.transaction.journals != null ? this.transaction.journals[i] : null;
      if (journal == null) journal = new Journal();

      journal.accid = element.controls.account.value.id;
      journal.drcrtype = element.controls.dramount.value > 0 ? 1 : 2;
      journal.amount =
        journal.drcrtype == 1
          ? element.controls.dramount.value
          : element.controls.cramount.value;
      journal.desc =
        journal != null
          ? journal.desc
          : this.transactionForm.controls.description.value;
      journal.transDate =
        this.transactionForm.controls.date.value.getTime() / 1000;

      journalArray.push(journal);
    }
    return journalArray;
  }

  validateJournalFormAtIndex(group: FormGroup) {
    return true;
  }

  validateDrCrAmount() {
    // either debit or credit amount required
    // debit or credit value b/w 1 and 10000000000
  }

  // -------------------------
  // Submit Transaction Form
  // -------------------------
  submitForm() {
    if (this.validateTransactionForm()) {
      if (this.transaction.id == null) {
        this.transaction.type = 'Journal';
      }
      this.transaction.date =
        this.transactionForm.controls.date.value.getTime() / 1000;
      this.transaction.myCompanyId = this.transactionForm.controls.myCompanyId.value;
      this.transaction.notes = this.transactionForm.controls.notes.value;
      this.transaction.journals = this.getJournalsFromFormArray();
      this.store.dispatch(saveTransaction({ transaction: this.transaction }));
      console.log(this.transaction);
    }
  }

  validateTransactionForm() {
    let jArray: Journal[] = this.getJournalsFromFormArray();

    // Total debit == total credit
    let totDebit = 0,
      totCredit = 0;
    jArray.forEach(function (j) {
      totDebit = totDebit + (j.drcrtype == 1 ? j.amount : 0);
      totCredit = totCredit + (j.drcrtype == 2 ? j.amount : 0);
    });

    if (totDebit != totCredit) {
      this.transactionForm.setErrors({ totMismatch: true });
      return false;
    }
    // description min 3 characters

    return true;
  }

  //-----------------
  // Form Events
  //-----------------

  onDrCrChange(type: String) {
    if (type === 'dr') {
      this.journalForm.controls.cramount.setValue('');
    } else if (type == 'cr') {
      this.journalForm.controls.dramount.setValue('');
    }
  }

  onDrCrChangeIndex(type: String, index: number) {
    const control = this.transactionForm.get('journals') as FormArray;
    let form = control.at(index) as FormGroup;
    if (type == 'dr') {
      form.controls.cramount.setValue('');
    } else if (type == 'cr') {
      form.controls.dramount.setValue('');
    }
  }

  deleteRow(index: number) {
    const control = this.transactionForm.get('journals') as FormArray;
    control.removeAt(index);
  }

  editRow(group: FormGroup) {
    group.get('isEditable').setValue(true);
  }

  doneRow(group: FormGroup) {
    if (this.validateJournalFormAtIndex(group)) {
      group.get('isEditable').setValue(false);
      let dramount = group.get('dramount').value;
      group.get('drcrtype').setValue(dramount > 0 ? 1 : 2);
    }
  }

  //-----------------------
  // Account Drop Down
  //-----------------------
  populateAccountDropDown() {
    this.accountOptions = this.journalForm.controls.account.valueChanges.pipe(
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

  //--------------------------------
  // Navigation
  //-------------------------------

  viewTransactions() {
    let navStr = 'trade/transactions';
    this.router.navigate([navStr]);
  }
}
