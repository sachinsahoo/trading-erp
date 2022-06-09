import { createAction, props } from '@ngrx/store';
import { Company } from '../model/company';
import { TransactionSearchRequest } from '../model/rs/transactionSearchRequest';
import { Transaction } from '../model/transaction';


//---------------------
// Search Transactions
//---------------------
export const searchTransactions = createAction(
  '[Transactions] Search Transactions',
  props<{ request: TransactionSearchRequest }>()
);

export const switchTransactions = createAction(
  '[Transactions] Switch Transactions',
  props<{ selfCId: number }>()
);


//---------------------
// Load Transactions
//---------------------
export const loadTransactions = createAction('[Transactions] Load Transactions');

export const resetTransactionError = createAction('[Transactions] Reset Error');

export const transactionsLoading = createAction(
  '[Transactions] Loading',
props<{ isLoading: boolean}>());

export const loadTransactionsSuccess = createAction(
  '[Transaction] Load Transactions Success',
  props<{ transactions: Transaction[], searchRequest: TransactionSearchRequest }>()
);

export const transactionFailure = createAction(
  '[Transaction] Load Transactions Failure',
  props<{ errorMessage: string }>()
);

//---------------------
// Save Transaction
//---------------------
export const saveTransaction = createAction(
  '[Transactions] Save Transaction',
  props<{ transaction: Transaction }>()
);

export const saveTransactionSuccess = createAction(
  '[Transaction] Save Transaction Success',
  props<{ transaction: Transaction }>()
);

export const saveTransactionFailure = createAction(
  '[Transaction] Save Transaction Failure',
  props<{ errorMessage: string }>()
);

//--------------------------------------
// Update Selected transaction in Store
//--------------------------------------
export const updateSelectedTransactionInStore = createAction(
  '[Transactions] Update Transaction in store',
  props<{ transaction: Transaction }>()
);

//---------------------
// Get Transaction
//---------------------
export const getTransaction = createAction(
  '[Transaction] Get Transaction',
  props<{ id: number}>()
);


//--------------------------------------
// Update transaction Result from API in Store
//--------------------------------------
export const updateTransactionResultInStore = createAction(
  '[Transaction] Get Transaction Success',
  props<{ transaction: Transaction}>()
);
