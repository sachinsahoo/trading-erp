import { createSelector } from '@ngrx/store';
import { selectOrderState } from 'app/core/core.state';
import { OrderState } from '../model/order.model';
import { PurchaseSaleMonthlyReport } from '../model/Report/purchaseSaleMonthlyReport';
import { PurchaseOrder } from '../model/purchaseorder';
import { Invoice } from '../model/invoice';

export const selectReload = createSelector(
  selectOrderState,
  (state: OrderState) => state.reload
);
// -------------------------------------
// Product
// -------------------------------------
export const selectProductList = createSelector(
  selectOrderState,
  (state: OrderState) => state.productList
);

export const selectActiveProduct = createSelector(
  selectOrderState,
  (state: OrderState) => state.selectedProduct
);

export const selectProductError = createSelector(
  selectOrderState,
  (state: OrderState) => state.productError
);

// -------------------------------------
// Company
// -------------------------------------
export const selectCompanyList = createSelector(
  selectOrderState,
  (state: OrderState) => state.companyList
);

export const selectActiveSelfCompanyId = createSelector(
  selectOrderState,
  (state: OrderState) => state.activeSelfCompanyId
);

export const selectCompanyById = (id: number) =>
  createSelector(selectCompanyList, allCompanies => {
    if (id > 0) {
      return allCompanies.find(company => {
        return company.id == id;
      });
    } else {
      return null;
    }
  });

export const selectSelfCompanyList = createSelector(
  selectCompanyList,
  allCompanies => {
    if (allCompanies) {
      return allCompanies.filter(company => {
        return company.type == 'self';
      });
    } else {
      return null;
    }
  }
);

export const selectActiveSelfCompany = createSelector(
  selectOrderState,
  selectSelfCompanyList,
  (state, selfCompanies) => {
    if (selfCompanies) {
      return selfCompanies.find(company => {
        return company.id == state.activeSelfCompanyId;
      });
    } else {
      return null;
    }
  }
);

export const selectClientCompanyList = createSelector(
  selectCompanyList,
  allCompanies => {
    return allCompanies.filter(company => {
      return company.type != 'self';
    });
  }
);

export const selectCompanyError = createSelector(
  selectOrderState,
  (state: OrderState) => state.companyError
);

// -------------------------------------
// Purchase Order
// -------------------------------------

export const selectActiveOrder = createSelector(
  selectOrderState,
  (state: OrderState) => state.selectedOrder
);

export const selectActiveInvoiceId = createSelector(
  selectOrderState,
  (state: OrderState) => state.selectedInvId
);

export const selectOrderList = createSelector(
  selectOrderState,
  (state: OrderState) => state.purchaseList
);

export const selectOrderError = createSelector(
  selectOrderState,
  (state: OrderState) => state.orderError
);
export const selectOrderSearchRequest = createSelector(
  selectOrderState,
  (state: OrderState) => state.orderSearchRequest
);

export const selectOrderLoading = createSelector(
  selectOrderState,
  (state: OrderState) => state.isOrLoading
);

export const selectOrderById = (oid: number) =>
  createSelector(selectOrderList, allOrders => {
    if (allOrders && oid != null && oid > 0) {
      return allOrders.find(order => {
        return order.id == oid;
      });
    } else {
      return null;
    }
  });

export const selectOrderByInvId = (invid: number) =>
  createSelector(selectOrderList, allOrders => {
    if (allOrders && invid != null && invid > 0) {
      return allOrders.find(order => {
        return order.invoices.filter(inv => inv.id == invid).length > 0;
      });
    } else {
      return null;
    }
  });

export const selectSaleOrdersRelatedToPOID = (po: PurchaseOrder) =>
  createSelector(selectOrderList, allOrders => {
    if (allOrders && po != null && po.id != null && po.type == 'purchase') {
      return allOrders.filter(order => {
        return order.poproductlist.filter(pop => pop.lpoid == po.id).length > 0;
      });
    } else {
      return [];
    }
  });

export const selectLinkedPurchaseOrders = (pids: Array<number>) =>
  createSelector(selectOrderList, allOrders => {
    if (allOrders && pids != null) {
      return allOrders.filter(order => {
        return pids.includes(order.id);
      });
    } else {
      return [];
    }
  });

// -------------------------------------
// Invoices
// -------------------------------------
export const selectInvoiceList = () =>
  createSelector(selectOrderList, allOrders => {
    let invoices: Array<Invoice> = new Array<Invoice>();
    if (allOrders) {
      return allOrders.forEach(order => {
        if (order.invoices != null && order.invoices.length > 0)
          invoices.push(...order.invoices);
      });
    }
    return invoices;
  });

// -------------------------------------
// Transaction
// -------------------------------------
export const selectTransactionList = createSelector(
  selectOrderState,
  (state: OrderState) => state.transactionList
);

export const selectActiveTransaction = createSelector(
  selectOrderState,
  (state: OrderState) => state.selectedTransaction
);
export const selectTransactionError = createSelector(
  selectOrderState,
  (state: OrderState) => state.transactionError
);

export const selectTransactionSearchRequest = createSelector(
  selectOrderState,
  (state: OrderState) => state.transactionSearchRequest
);

export const selectTransactionLoading = createSelector(
  selectOrderState,
  (state: OrderState) => state.isTrLoading
);

export const selectTransactionById = (transid: number) =>
  createSelector(selectTransactionList, allTrans => {
    if (allTrans && transid != null) {
      return allTrans.find(tran => {
        return tran.id == transid;
      });
    } else {
      return null;
    }
  });

export const selectTransactionListByInvoiceId = (invid: number) =>
  createSelector(selectTransactionList, allTrans => {
    if (allTrans && invid != null) {
      return allTrans.filter(tran => {
        return tran.invoiceId == invid;
      });
    } else {
      return [];
    }
  });

export const selectTransactionListByCompanyId = (cid: number) =>
  createSelector(selectTransactionList, allTrans => {
    if (allTrans && cid != null) {
      return allTrans.filter(tran => {
        return tran.custCompanyId == cid || tran.myCompanyId == cid;
      });
    } else {
      return [];
    }
  });

// -------------------------------------
// Account
// -------------------------------------

export const selectAccountList = createSelector(
  selectOrderState,
  (state: OrderState) => state.accountList
);

export const selectBalanceSheet = createSelector(
  selectOrderState,
  (state: OrderState) => state.balanceSheet
);

export const selectLedger = createSelector(
  selectOrderState,
  (state: OrderState) => state.ledger
);

export const selectJournalSearchRequest = createSelector(
  selectOrderState,
  (state: OrderState) => state.journalSearchRequest
);

// -------------------------------------
// Report
// -------------------------------------
export const selectYearlyReport = createSelector(
  selectOrderState,
  (state: OrderState) => state.report
);

export const selectProductReports = createSelector(
  selectOrderState,
  (state: OrderState) => state.report.productReports
);

export const selectCustomerReports = createSelector(
  selectOrderState,
  (state: OrderState) => state.report.customerReports
);

export const selectSupplierReports = createSelector(
  selectOrderState,
  (state: OrderState) => state.report.supplierReports
);

export const selectReportError = createSelector(
  selectOrderState,
  (state: OrderState) => state.reportError
);
