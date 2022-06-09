import { Injectable } from '@angular/core';
import { Actions, createEffect, Effect, ofType } from '@ngrx/effects';
import { select, Store } from '@ngrx/store';
import { State } from 'app/core/settings/settings.model';
import { concatMap, exhaustMap, map, withLatestFrom } from 'rxjs/operators';
import { TestService } from '../service/api/test.service';
import {
  loadTransactions,
  loadTransactionsSuccess,
  transactionFailure,
  getTransaction,
  updateTransactionResultInStore,
  saveTransaction,
  updateSelectedTransactionInStore, searchTransactions, transactionsLoading, resetTransactionError
} from '../action/transaction.action';
import { TransactionService } from '../service/api/transaction.service';
import { combineLatest, of } from 'rxjs';
import { selectActiveSelfCompanyId, selectTransactionList, selectTransactionSearchRequest } from '../selectors/order.selectors';
import { Router } from '@angular/router';
import { reload } from '../action/order.action';

@Injectable()
export class TransactionEffects {
  constructor(
    private actions$: Actions,
    private store: Store<State>,
    private testService: TestService,
    private transactionService: TransactionService,
    private router: Router
  ) { }

  @Effect()
  searchTransactions$ = this.actions$.pipe(
    ofType(searchTransactions),
    concatMap(action =>
      of(action).pipe(withLatestFrom(
        this.store.pipe(select(selectActiveSelfCompanyId)),
      ))),
    exhaustMap(([action, selfid])  =>

      this.testService.transList().pipe(
        map(data => {
          
          if (data.status === 'SUCCESS') {
            return loadTransactionsSuccess({ transactions: data.transactions, searchRequest: data.searchRequest });
          } else {
            return transactionFailure({ errorMessage: data.errorMessage });
          }
        })
      )
    )
  );

  @Effect()
  isLoading$ = this.actions$.pipe(
    ofType(searchTransactions, loadTransactions, saveTransaction, getTransaction),
    map(action => {
      this.store.dispatch(transactionsLoading({ isLoading: false }));
      this.store.dispatch(resetTransactionError());
      return transactionsLoading({ isLoading: true });
    }));


  @Effect()
  isLoadingDone$ = this.actions$.pipe(
    ofType(loadTransactionsSuccess, transactionFailure, updateTransactionResultInStore),
    map(action => {
      return transactionsLoading({ isLoading: false });
    }));


  @Effect()
  loadTransactions$ = this.actions$.pipe(
    ofType(loadTransactions),
    exhaustMap(action =>
      this.transactionService.list().pipe(
        map(data => {
          if (data.status === 'SUCCESS') {
            return loadTransactionsSuccess({ transactions: data.transactions, searchRequest: data.searchRequest });
          } else {
            return transactionFailure({ errorMessage: data.errorMessage });
          }
        })
      )
    )
  );

  @Effect()
  getTransaction$ = this.actions$.pipe(
    ofType(getTransaction),
    exhaustMap(action =>
      this.transactionService.get(action.id).pipe(
        map(data => {
          this.store.dispatch(transactionsLoading({ isLoading: false }));
          if (data.status === 'SUCCESS') {
            return updateTransactionResultInStore({
              transaction: data.transaction
            });
          } else {
            return transactionFailure({ errorMessage: data.errorMessage });
          }
        })
      )
    )
  );

  @Effect()
  saveTransaction$ = this.actions$.pipe(
    ofType(saveTransaction),
    exhaustMap(action =>
      this.transactionService.save(action.transaction).pipe(
        map(data => {
          this.store.dispatch(transactionsLoading({ isLoading: false }));
          if (data.status === 'SUCCESS') {
            return updateTransactionResultInStore({
              transaction: data.transaction
            });
          } else {
            return transactionFailure({ errorMessage: data.errorMessage });
          }
        })
      )
    )
  );

  //--------------------------------------------------------------
  // Common Effect to Update / Insert response { Transaction } to Store
  // update selected transaction
  // Update Transaction List in Store
  //--------------------------------------------------------------

  @Effect()
  updateTransactionResultInStore$ = this.actions$.pipe(
    ofType(updateTransactionResultInStore),
    concatMap(action =>
      of(action).pipe(
        withLatestFrom(
          combineLatest([
            this.store.pipe(select(selectTransactionList)),
            this.store.pipe(select(selectTransactionSearchRequest))
          ])
        )
      )
    ),
    map(([action, [transList, searchRequest]]) => {
      let index = transList.findIndex(tran => {
        return tran.id === action.transaction.id;
      });
      if (index > -1) {
        transList[index] = JSON.parse(JSON.stringify(action.transaction));
      } else {
        transList.unshift(JSON.parse(JSON.stringify(action.transaction)));
      }
      this.store.dispatch(loadTransactionsSuccess({ transactions: JSON.parse(JSON.stringify(transList)), searchRequest: searchRequest }));

      if (action.transaction.type == 'Journal') {
        this.router.navigate(['trade/journal/edit', action.transaction.id]);
      }

      return updateSelectedTransactionInStore({ transaction: action.transaction });

    })
  );





}
