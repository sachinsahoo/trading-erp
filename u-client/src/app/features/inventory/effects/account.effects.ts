import { Injectable } from '@angular/core';
import { Actions, Effect, ofType } from '@ngrx/effects';
import { select, Store } from '@ngrx/store';
import { State } from 'app/core/settings/settings.model';
import { of } from 'rxjs';
import { concatMap, exhaustMap, map, withLatestFrom } from 'rxjs/operators';
import {
  loadBalanceSheet,
  loadBalanceSheetSuccess,
  loadBalanceSheetFailure,
  loadLedger,
  loadLedgerSuccess,
  loadLedgerFailure,
  loadAccountListSuccess,
  accountFailure,
  loadAccountList
} from '../action/account.action';
import {
  loadOrdersSuccess,
  orderFailure,
  saveInvoice,
  saveOrder,
  updateInvoiceResultInStore,
  updateOrderResultInStore,
  updateOrderStatus,
  updateSelectedInvoiceIdInStore,
  updateSelectedOrderInStore
} from '../action/order.action';
import { selectOrderList } from '../selectors/order.selectors';
import { AccountService } from '../service/api/account.service';
import { TestService } from '../service/api/test.service';

@Injectable()
export class AccountEffects {
  constructor(
    private actions$: Actions,
    private store: Store<State>,
    private accountService: AccountService,
    private testService: TestService
  ) {}


  //--------------------
  // Load Account List
  //--------------------

  @Effect()
  loadAccounts$ = this.actions$.pipe(
    ofType(loadAccountList),
    exhaustMap(action =>
      this.accountService.list().pipe(
        map(data => {
          if (data.status === 'SUCCESS') {
            return loadAccountListSuccess({ accounts: data.accounts });
          } else {
            return accountFailure({ errorMessage: data.errorMessage });
          }
        })
      )
    )
  );

  //--------------------
  // Load Balance Sheet
  //--------------------

  @Effect()
  loadBalance$ = this.actions$.pipe(
    ofType(loadBalanceSheet),
    exhaustMap(action =>
      this.testService.balancesheet().pipe(
        map(data => {
          console.log('Balance Sheet call ' + data);
          if (data.status === 'SUCCESS') {
            return loadBalanceSheetSuccess({ balanceSheet: data.balanceSheet });
          } else {
            return loadBalanceSheetFailure({ errorMessage: data.errorMessage });
          }
        })
      )
    )
  );

  //--------------------
  // Load Ledger
  //--------------------

  @Effect()
  loadLedger$ = this.actions$.pipe(
    ofType(loadLedger),
    exhaustMap(action =>
      this.accountService.ledger(action.searchRequest).pipe(
        map(data => {
          if (data.status === 'SUCCESS') {
            return loadLedgerSuccess({ journals: data.journals, searchRequest: data.searchRequest });
          } else {
            return loadLedgerFailure({ errorMessage: data.errorMessage });
          }
        })
      )
    )
  );
}
