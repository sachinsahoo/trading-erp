import { createAction, props } from '@ngrx/store';
import { PurchaseSaleYearlyReport } from '../model/Report/purchaseSaleYearlyReport';

// Load Report
export const loadReport = createAction('[Supplier] Load Suppliers');

export const loadReportSuccess = createAction(
  '[Report] Load Report Success',
  props<{ report: PurchaseSaleYearlyReport }>()
);

export const loadReportFailure = createAction(
  '[Report] Load Report Failure',
  props<{ errorMessage: string }>()
);


