import { Injectable } from '@angular/core';
import { Actions, createEffect, Effect, ofType } from '@ngrx/effects';
import { select, Store } from '@ngrx/store';
import { State } from 'app/core/settings/settings.model';
import { concatMap, exhaustMap, map, switchMap, withLatestFrom } from 'rxjs/operators';
import { loadCompanies, loadCompaniesSuccess, loadCompaniesFailure, addCompany, addCompanySuccess, addCompanyFailure, addContact, switchCompany } from '../action/company.action';
import { TestService } from '../service/api/test.service';
import { CompanyService } from '../service/api/company.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { OrderSearchRequest } from '../model/rs/orderSearchRequest';
import { TransactionSearchRequest } from '../model/rs/transactionSearchRequest';
import { reload, searchOrders } from '../action/order.action';
import { searchTransactions, switchTransactions } from '../action/transaction.action';
import { loadBalanceSheet, loadLedger } from '../action/account.action';
import { combineLatest, of } from 'rxjs';
import { selectJournalSearchRequest, selectOrderSearchRequest, selectTransactionSearchRequest } from '../selectors/order.selectors';
import { JournalSearchRequest } from '../model/rs/journalSearchRequest';


@Injectable()
export class CompanyEffects {
  constructor(
    private actions$: Actions,
    private store: Store<State>,
    private testService: TestService,
    private companyService: CompanyService,
    private snackBar: MatSnackBar,
  ) { }





  // @Effect()
  // switchCompany$ = this.actions$.pipe(
  //   ofType(switchCompany),
  //   concatMap(action =>
  //     of(action).pipe(withLatestFrom(
  //       combineLatest([
  //         this.store.pipe(select(selectTransactionSearchRequest)),
  //         this.store.pipe(select(selectOrderSearchRequest)),
  //         this.store.pipe(select(selectJournalSearchRequest))
  //       ])
  //     ))),
  //   map(([action, [tranSearchReq, orderSearchReq, jourSearchReq]]) => {

  //     if (!tranSearchReq) {
  //       tranSearchReq = new TransactionSearchRequest();
  //     }
  //     if (!orderSearchReq) {
  //       orderSearchReq = new OrderSearchRequest();
  //     }
  //     if (!jourSearchReq) {
  //       jourSearchReq = new JournalSearchRequest();
  //     }
  //     tranSearchReq.myCompanyId = action.id;
  //     orderSearchReq.myCompanyId = action.id;
  //     jourSearchReq.myCompanyId = action.id;

  //     return ([
  //       loadBalanceSheet({ selfCompanyId: action.id }),
  //       searchTransactions({ request: tranSearchReq }),
  //       searchOrders({ searchRequest: orderSearchReq }),
  //       loadLedger({ searchRequest: jourSearchReq }),
  //       reload({ date: new Date() })


  //     ]);
  //     // this.store.dispatch(loadBalanceSheet({selfCompanyId: action.id}))
  //     // this.store.dispatch(searchTransactions({ request: tranSearchReq }));
  //     // this.store.dispatch(searchOrders({ searchRequest: orderSearchReq }));
  //     // this.store.dispatch(loadLedger({ searchRequest: jourSearchReq }));
  //     // return reload({ date: new Date() });
  //   }));


  
   
   
   


  @Effect()
  loadCompanies$ = this.actions$.pipe(
    ofType(loadCompanies),
    exhaustMap(action =>
      this.testService.companyList().pipe(
        map(data => {
          if (data.status === 'SUCCESS') {
            return loadCompaniesSuccess({ companies: data.companies });
          } else {
            return loadCompaniesFailure(data);
          }
        })
      )
    )
  );

  @Effect()
  saveCompany$ = this.actions$.pipe(
    ofType(addCompany),
    exhaustMap(action =>
      this.companyService.save(action.company).pipe(
        map(data => {
          if (data.status === 'SUCCESS') {
            console.log(data);
            this.snackBar.open("New Company Added Successfully.", "Close", { duration: 2000 });

            return loadCompanies();
          } else {
            return addCompanyFailure(data.errorMessage);
          }
        })
      )
    )
  );
  //Add new contact

  @Effect()
  saveContact$ = this.actions$.pipe(
    ofType(addContact),
    exhaustMap(action =>
      this.companyService.saveContact(action.contact, action.companyId).pipe(
        map(data => {
          if (data.status === 'SUCCESS') {
            console.log(data);
            this.snackBar.open("New Contact Added Successfully.", "Close", { duration: 2000 });

            return loadCompanies();
          } else {
            return addCompanyFailure(data.errorMessage);
          }
        })
      )
    )
  );
}

