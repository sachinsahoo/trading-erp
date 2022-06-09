import { Action, createReducer, on } from '@ngrx/store';
import { loadCompaniesSuccess, switchCompany } from '../action/company.action';
import {
  loadOrdersSuccess,
  updateSelectedOrderInStore,
  updateOrderResultInStore,
  orderFailure,
  updateSelectedInvoiceIdInStore, reload, orderLoading, resetOrder
} from '../action/order.action';
import {
  saveProductSuccess,
  loadProductsSuccess,
  productFailure
} from '../action/product.actions';
import { loadTransactionsSuccess, resetTransactionError, transactionFailure, transactionsLoading, updateSelectedTransactionInStore } from '../action/transaction.action';
import { OrderState } from '../model/order.model';
import { loadReportSuccess } from '../action/report.action';
import {
  loadBalanceSheetSuccess,
  loadLedgerSuccess,
  loadAccountListSuccess
} from '../action/account.action';

export const orderFeatureKey = 'order';

export const initialState: OrderState = {
  productList: null,
  selectedProduct: null,
  companyList: null,
  activeSelfCompanyId: null,
  purchaseList: null,
  orderSearchRequest: null,
  selectedOrder: null,
  selectedInvId: null,
  transactionList: null,
  transactionSearchRequest: null,
  selectedTransaction: null,
  accountList: null,
  balanceSheet: null,
  ledger: null,
  journalSearchRequest: null,
  report: null,

  //error message
  companyError: null,
  productError: null,
  orderError: null,
  transactionError: null,
  reportError: null,
  reload: null,

  // Loading
  isTrLoading: null,
  isPrLoading: null,
  isAcLoading: null,
  isCoLoading: null,
  isOrLoading: null,

};

const reducer = createReducer(
  initialState,

  //-----------------
  //Reload
  //-----------------
  on(reload, (state, { date }) => ({
    ...state,
    reload: date
  })),

  //-----------------
  //Products
  //-----------------
  on(loadProductsSuccess, (state, { products }) => ({
    ...state,
    productList: products
  })),

  on(productFailure, (state, { errorMessage }) => ({
    ...state,
    productError: errorMessage
  })),

  //-----------------
  //Company
  //-----------------
  on(loadCompaniesSuccess, (state, { companies }) => ({
    ...state,
    companyList: companies
  })),
  on(switchCompany, (state, { id }) => ({
    ...state,
    activeSelfCompanyId: id
  })),

  //-----------------
  // Order
  //-----------------
  on(loadOrdersSuccess, (state, { orders, searchRequest }) => ({
    ...state,
    purchaseList: orders,
    orderSearchRequest: searchRequest
  })),

  on(updateSelectedOrderInStore, (state, { order }) => ({
    ...state,
    selectedOrder: order
  })),

  on(updateOrderResultInStore, (state, { order }) => ({
    ...state,
    selectedOrder: order
  })),

  on(orderFailure, (state, { errorMessage }) => ({
    ...state,
    orderError: errorMessage
  })),

  on(resetOrder, (state, {  }) => ({
    ...state,
    orderError: ''
  })),

  on(orderLoading, (state, { isLoading }) => ({
    ...state,
    isOrLoading: isLoading
  })),

  //-----------------
  // Invoice
  //-----------------
  on(updateSelectedInvoiceIdInStore, (state, { invId }) => ({
    ...state,
    selectedInvId: invId
  })),

  //-----------------
  // Transaction
  //-----------------
  on(loadTransactionsSuccess, (state, { transactions, searchRequest }) => ({
    ...state,
    transactionList: transactions,
    transactionSearchRequest: searchRequest
  })),

  on(transactionsLoading, (state, { isLoading }) => ({
    ...state,
    isTrLoading : isLoading
  })),

  on(resetTransactionError, (state, {  }) => ({
    ...state,
    transactionError : ''
  })),
  on(transactionFailure, (state, { errorMessage }) => ({
    ...state,
    transactionError : errorMessage
  })),

  on(updateSelectedTransactionInStore, (state, { transaction }) => ({
    ...state,
    selectedTransaction: transaction
  })),

  //-----------------
  // Account
  //-----------------
  on(loadAccountListSuccess, (state, { accounts }) => ({
    ...state,
    accountList: accounts
  })),

  on(loadBalanceSheetSuccess, (state, { balanceSheet }) => ({
    ...state,
    balanceSheet: balanceSheet
  })),

  on(loadLedgerSuccess, (state, { journals , searchRequest}) => ({
    ...state,
    ledger: journals,
    journalSearchRequest: searchRequest
  })),

  //-----------------
  // Report
  //-----------------
  on(loadReportSuccess, (state, { report }) => ({
    ...state,
    report: report
  }))
);

export function orderReducer(state: OrderState | undefined, action: Action) {
  return reducer(state, action);
}
