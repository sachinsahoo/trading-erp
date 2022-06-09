import { createAction, props } from '@ngrx/store';
import { BalanceSheet } from '../model/balanceSheet';
import { Journal } from '../model/journal';
import { Account } from '../model/account';
import { JournalSearchRequest } from '../model/rs/journalSearchRequest';

// ------------------
// Load Account List
// ------------------
export const loadAccountList = createAction('[Account] Load Account List');

export const loadAccountListSuccess = createAction(
  '[Account] Load Account List Success',
  props<{ accounts: Array<Account> }>()
);

export const accountFailure = createAction(
  '[Account] Account Failure',
  props<{ errorMessage: string }>()
);

// ------------------
// Load BalanceSheet
// ------------------
export const loadBalanceSheet = createAction(
  '[Account] Load Balance Sheet 112',
  props<{ selfCompanyId: number }>()
  );

export const loadBalanceSheetSuccess = createAction(
  '[Account] Load Balance Sheet Success',
  props<{ balanceSheet: BalanceSheet }>()
);

export const loadBalanceSheetFailure = createAction(
  '[Account] Load Balance Sheet Failure',
  props<{ errorMessage: string }>()
);

// ------------------
// Load Ledger
// ------------------
export const loadLedger = createAction(
  '[Account] Load Ledger Sheet',
  props<{ searchRequest: JournalSearchRequest }>()
);

export const loadLedgerSuccess = createAction(
  '[Account] Load Ledger Success',
  props<{ journals: Array<Journal> , searchRequest: JournalSearchRequest}>()
);

export const loadLedgerFailure = createAction(
  '[Account] Load Ledger Failure',
  props<{ errorMessage: string }>()
);
