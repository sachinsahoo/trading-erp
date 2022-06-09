import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductListComponent } from './product/product-list/product-list.component';
import { TradeComponent } from './trade.component';
import { ListOrderComponent } from './order/list-order/list-order.component';
import { ViewOrderComponent } from './order/view-order/view-order.component';
import { ViewInvoiceComponent } from './order/view-invoice/view-invoice.component';
import { EditInvoiceComponent } from './order/edit-invoice/edit-invoice.component';
import { CompanyListComponent } from './company/company-list/company-list.component';
import { TransactionListComponent } from './transaction/transaction-list/transaction-list.component';
import { YearlyReportComponent } from './reports/yearly-report/yearly-report.component';
import { EditOrderComponent } from './order/edit-order/edit-order.component';
import { AuthGuardService } from 'app/core/core.module';
import { BalanceSheet } from '../../model/balanceSheet';
import { BalanceSheetComponent } from './account/balance-sheet/balance-sheet.component';
import { LedgerComponent } from './account/ledger/ledger.component';
import { ProfitLossComponent } from './account/profit-loss/profit-loss.component';
import { EditJournalComponent } from './account/edit-journal/edit-journal.component';
import { PayablesComponent } from './account/payables/payables.component';

const routes: Routes = [
  {
    path: '',
    component: TradeComponent,
    canActivate: [AuthGuardService],
    children: [
      {
        path: '',
        redirectTo: 'product/list',
        pathMatch: 'full'
      },
      //Product Routing -----------------------------
      {
        path: '',
        redirectTo: 'product/list',
        pathMatch: 'full'
      },
      {
        path: 'product/list',
        component: ProductListComponent,
        data: { title: 'anms.examples.menu.crud' }
      },

      // Purchase / Sales Routing -----------------------------
      {
        path: 'purchase',
        redirectTo: 'purchase/list',
        pathMatch: 'full'
      },
      {
        path: 'purchase/list',
        component: ListOrderComponent,
        data: { type: 'purchase' }
      },
      {
        path: 'sale/list',
        component: ListOrderComponent,
        data: { type: 'sale' }
      },

      {
        path: 'order/view/:id',
        component: ViewOrderComponent,
        data: { type: 'purchase' }
      },

      {
        path: 'order/edit/:type/:id',
        component: EditOrderComponent,
        data: { type: 'purchase' }
      },

      {
        path: 'invoice/view/:id',
        component: ViewInvoiceComponent
      },
      {
        path: 'invoice/edit/:oid/:id',
        component: EditInvoiceComponent
      },

      //company -----  ----------------------------
      {
        path: 'supplier',
        redirectTo: 'supplier/list',
        pathMatch: 'full'
      },

      {
        path: 'supplier/list',
        component: CompanyListComponent,
        data: { type: 'supplier' }
      },
      {
        path: 'agent/list',
        component: CompanyListComponent,
        data: { type: 'agent' }
      },

      {
        path: 'mycompany/list',
        component: CompanyListComponent,
        data: { type: 'self' }
      },
      {
        path: 'customer/list',
        component: CompanyListComponent,
        data: { type: 'customer' }
      },

      // transactions -----------------------------
      {
        path: 'transactions',
        component: TransactionListComponent,
        data: { title: 'anms.examples.menu.crud' }
      },

      // Accounts -----------------------------
      {
        path: 'balance',
        component: BalanceSheetComponent,
        data: { title: 'anms.examples.menu.crud' }
      },
      {
        path: 'payables',
        component: PayablesComponent,
        data: { title: 'anms.examples.menu.crud' }
      },


      {
        path: 'profit',
        component: ProfitLossComponent,
        data: { title: 'anms.examples.menu.crud' }
      },

      {
        path: 'ledger/:id',
        component: LedgerComponent,
        data: { title: 'anms.examples.menu.crud' }
      },
      {
        path: 'journal/edit/:id',
        component: EditJournalComponent,
        data: { title: 'anms.examples.menu.crud' }
      },

      // Report  ------------------------------
      {
        path: 'report',
        component: YearlyReportComponent,
        data: { title: 'anms.examples.menu.crud' }
      }

      /*{
        path: '**',
        redirectTo: 'product'
      }*/
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TradeRoutingModule {}
