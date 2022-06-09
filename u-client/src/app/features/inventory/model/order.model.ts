import { AppState } from 'app/core/core.state';
import { Product } from './product';
import { PurchaseOrder } from './purchaseorder';
import { Company } from './company';
import { Transaction } from './transaction';
import { PurchaseSaleYearlyReport } from './Report/purchaseSaleYearlyReport';
import { Account } from './account';
import { BalanceSheet } from './balanceSheet';
import { Journal } from './journal';
import { TransactionSearchRequest } from './rs/transactionSearchRequest';
import { OrderSearchRequest } from './rs/orderSearchRequest';
import { JournalSearchRequest } from './rs/journalSearchRequest';

export interface OrderState {
  productList: Array<Product>;
  selectedProduct: Product;

  // Company
  companyList: Array<Company>;
  activeSelfCompanyId: number;

  // Order
  purchaseList: Array<PurchaseOrder>;
  selectedOrder: PurchaseOrder;
  selectedInvId: number;
  orderSearchRequest: OrderSearchRequest;

  // Transaction
  transactionList: Array<Transaction>;
  selectedTransaction: Transaction;
  transactionSearchRequest: TransactionSearchRequest

  // Account
  accountList: Array<Account>;
  balanceSheet: BalanceSheet;
  ledger: Array<Journal>;
  journalSearchRequest: JournalSearchRequest;

  // Report
  report: PurchaseSaleYearlyReport;

  // errorMessage
  companyError: String;
  productError: String;
  orderError: String;
  transactionError: String;
  reportError: String;

  // reload
  reload: Date;

  // Loading
  isTrLoading: boolean;
  isPrLoading: boolean;
  isAcLoading: boolean;
  isCoLoading: boolean;
  isOrLoading: boolean;
}

export interface State extends AppState {
  order: OrderState;
}
