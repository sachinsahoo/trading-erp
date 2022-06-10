import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TransactionsModalComponent } from './transaction/transactions-modal/transactions-modal.component';
import { ProductReportComponent } from './reports/product-report/product-report.component';
import { CompanyListComponent } from './company/company-list/company-list.component';
import { CompanyViewModalComponent } from './company/company-view-modal/company-view-modal.component';
import { CompanyEditModalComponent } from './company/company-edit-modal/company-edit-modal.component';
import { ViewCompanyComponent } from './company/view-company/view-company.component';
import { YearlyReportComponent } from './reports/yearly-report/yearly-report.component';
import { InvBarChartComponent } from './reports/inv-bar-chart/inv-bar-chart.component';
import { CustomerReportComponent } from './reports/customer-report/customer-report.component';
import { SupplierReportComponent } from './reports/supplier-report/supplier-report.component';
import { ProductMonthlyModalComponent } from './reports/product-report/product-monthly-modal/product-monthly-modal.component';
import { SupplierMonthlyModalComponent } from './reports/supplier-report/supplier-monthly-modal/supplier-monthly-modal.component';
import { CustomerMonthlyModalComponent } from './reports/customer-report/customer-monthly-modal/customer-monthly-modal.component';
import { TransactionListComponent } from './transaction/transaction-list/transaction-list.component';

import { TradeComponent } from './trade.component';
import { ChartsModule } from 'ng2-charts';
import { SharedModule } from 'app/shared/shared.module';
import { EffectsModule } from '@ngrx/effects';
import { ProductEffects } from '../../effects/product.effects';
import { OrderEffects } from '../../effects/order.effects';
import { TransactionEffects } from '../../effects/invTransaction.effects';
import { CompanyEffects } from '../../effects/company.effects';
import { ReportEffects } from '../../effects/report.effects';
import { BarChartService } from '../../service/barchartservice';
import { DonutService } from '../../service/donutservice';
import { OrderDtoService } from '../../service/dtoService';
import { MAT_TABS_CONFIG } from '@angular/material/tabs';
import { AddContactModalComponent } from './modal/add-contact-modal/add-contact-modal.component';
import { EnterTransactionModalComponent } from './modal/enter-transaction-modal/enter-transaction-modal.component';
import { ViewPurchaseModalComponent } from './order/view-purchase-modal/view-purchase-modal.component';
import { ProductListComponent } from './product/product-list/product-list.component';
import { EditProductModalComponent } from './product/edit-product-modal/edit-product-modal.component';
import { ViewProductModalComponent } from './product/view-product-modal/view-product-modal.component';
import { ViewProductComponent } from './product/view-product/view-product.component';
import { ViewOrderComponent } from './order/view-order/view-order.component';
import { OrderContactsComponent } from './order/view-order/order-contacts/order-contacts.component';
import { RelatedSaleOrdersComponent } from './order/view-order/related-sale-orders/related-sale-orders.component';
import { RelatedPurchaseOrdersComponent } from './order/view-order/related-purchase-orders/related-purchase-orders.component';
import { UpdateStatusModalComponent } from './order/update-status-modal/update-status-modal.component';
import { UpdatePurchaseStatusComponent } from './order/update-purchase-status/update-purchase-status.component';
import { UpdateInvoiceStatusComponent } from './order/update-invoice-status/update-invoice-status.component';
import { EditInvoiceComponent } from './order/edit-invoice/edit-invoice.component';
import { ViewInvoiceComponent } from './order/view-invoice/view-invoice.component';
import { ListOrderComponent } from './order/list-order/list-order.component';
import { EditOrderComponent } from './order/edit-order/edit-order.component';
import { TradeRoutingModule } from './trade-routing.module';
import { PrintOrderModalComponent } from './order/print-order-modal/print-order-modal.component';
import { OrderComponent } from './order/view-order/order/order.component';
import { InvoiceComponent } from './order/view-invoice/invoice/invoice.component';
import { BalanceSheetComponent } from './account/balance-sheet/balance-sheet.component';
import { LedgerComponent } from './account/ledger/ledger.component';
import { AccountEffects } from '../../effects/account.effects';
import { SelectGroupModalComponent } from './account/select-group-modal/select-group-modal.component';
import { ProfitLossComponent } from './account/profit-loss/profit-loss.component';
import { ViewTransactionModalComponent } from './transaction/view-transaction-modal/view-transaction-modal.component';
import { EditJournalComponent } from './account/edit-journal/edit-journal.component';
import { EditPaymentModalComponent } from './transaction/edit-payment-modal/edit-payment-modal.component';
import { AccountGroupComponent } from './account/balance-sheet/account-group/account-group.component';
import { PayablesComponent } from './account/payables/payables.component';
import { InputCustomerComponent } from './order/edit-order/input-customer/input-customer.component';
import { InputShippingComponent } from './order/edit-order/input-shipping/input-shipping.component';
import { InputProductsComponent } from './order/edit-order/input-products/input-products.component';
import { InputPaymentsComponent } from './order/edit-order/input-payments/input-payments.component';

@NgModule({
  declarations: [
    //Modals
    AddContactModalComponent,
    EnterTransactionModalComponent,

    ViewPurchaseModalComponent,

    //Product
    ProductListComponent,
    EditProductModalComponent,
    ViewProductModalComponent,
    ViewProductComponent,

    //Purchase
    ViewOrderComponent,
    InvoiceComponent,

    //Transactions
    TransactionsModalComponent,

    // Company

    ProductReportComponent,
    CompanyListComponent,
    CompanyViewModalComponent,
    CompanyEditModalComponent,
    ViewCompanyComponent,

    // Report
    YearlyReportComponent,
    InvBarChartComponent,
    CustomerReportComponent,
    SupplierReportComponent,
    ProductMonthlyModalComponent,
    SupplierMonthlyModalComponent,
    CustomerMonthlyModalComponent,
    TransactionListComponent,
    OrderContactsComponent,
    RelatedSaleOrdersComponent,
    RelatedPurchaseOrdersComponent,
    UpdateStatusModalComponent,
    UpdatePurchaseStatusComponent,
    UpdateInvoiceStatusComponent,
    EditInvoiceComponent,
    ViewInvoiceComponent,
    TradeComponent,
    ListOrderComponent,
    EditOrderComponent,
    PrintOrderModalComponent,
    OrderComponent,
    BalanceSheetComponent,
    LedgerComponent,
    SelectGroupModalComponent,
    ProfitLossComponent,
    ViewTransactionModalComponent,
    EditJournalComponent,
    EditPaymentModalComponent,
    AccountGroupComponent,
    PayablesComponent,
    InputCustomerComponent,
    InputShippingComponent,
    InputProductsComponent,
    InputPaymentsComponent
  ],
  imports: [
    CommonModule,
    ChartsModule,
    SharedModule,
    TradeRoutingModule,
    EffectsModule.forFeature([
      ProductEffects,
      OrderEffects,
      TransactionEffects,
      AccountEffects,
      CompanyEffects,
      ReportEffects
    ])
  ],
  entryComponents: [
    EditProductModalComponent,
    ViewProductModalComponent,
    AddContactModalComponent,
    EnterTransactionModalComponent,
    TransactionsModalComponent,
    ViewPurchaseModalComponent,
    PrintOrderModalComponent,
    ProductMonthlyModalComponent,
    SupplierMonthlyModalComponent,
    CustomerMonthlyModalComponent,
    CompanyViewModalComponent,
    CompanyEditModalComponent,
    UpdateStatusModalComponent,
    UpdateInvoiceStatusComponent,
    UpdatePurchaseStatusComponent,
    SelectGroupModalComponent,
    ViewTransactionModalComponent,
    CompanyEditModalComponent,
    EditPaymentModalComponent

  ],
  providers: [
    BarChartService,
    DonutService,
    OrderDtoService,
    { provide: MAT_TABS_CONFIG, useValue: { animationDuration: '0ms' } }
  ]
})
export class TradeModule {}
