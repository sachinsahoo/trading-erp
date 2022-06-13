import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  ViewChild
} from '@angular/core';
import { select, Store } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { loadProducts } from '../../action/product.actions';
import { loadCompanies, switchCompany } from '../../action/company.action';
import { loadOrders, reload, searchOrders } from '../../action/order.action';
import {
  loadTransactions,
  searchTransactions
} from '../../action/transaction.action';
import { loadReport } from '../../action/report.action';
import { routeAnimations } from 'app/core/core.module';
import { loadBalanceSheet, loadAccountList } from '../../action/account.action';
import { combineLatest, Observable } from 'rxjs';
import { selectActiveSelfCompanyId } from '../../selectors/order.selectors';
import { JournalSearchRequest } from '../../model/rs/journalSearchRequest';
import { TransactionSearchRequest } from '../../model/rs/transactionSearchRequest';
import { MatSidenavContainer } from '@angular/material/sidenav';
import {
  fadeInOutAnimations,
  showHideAnimations,
  simpleAnimations
} from 'app/core/animations/route.animations';
import {
  style,
  state,
  animate,
  transition,
  trigger
} from '@angular/animations';

@Component({
  selector: 'trade',
  templateUrl: './trade.component.html',
  styleUrls: ['./trade.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: [routeAnimations, fadeInOutAnimations]
})
export class TradeComponent implements OnInit {
  // Observable
  selfActiveCompanyId$: Observable<any> = this.store.pipe(
    select(selectActiveSelfCompanyId)
  );

  events: string[] = [];
  opened: boolean;
  showMenu: boolean = false;
  menuIndex: number = 999;

  @ViewChild('navcontainer') navcontainer: MatSidenavContainer;

  toggleMenu(index: number) {
    this.showMenu = !this.showMenu;
    //setTimeout(() => this.navcontainer._updateContentMargins());
  }
  // Local
  selfActiveCompanyId: number = 0;

  navigation = [
    // {
    //   label: '',
    //   icons: '',
    //   child: [
    //     {
    //       icons: 'dial-pad',
    //       link: '',
    //       label: ''
    //     }
    //   ]
    // },
    {
      label: 'Create order',
      icons: 'briefcase',
      link: 'order/create',

      submenu: false,
    },
    {
      label: 'Trading',
      icons: 'briefcase',
      link: 'product',

      submenu: false,

      child: [
        {
          icons: 'barcode',
          link: 'product',
          label: 'Product',

        },
        {
          icons: 'money-bill',
          link: 'purchase',
          label: 'Purchase',

        },
        {
          icons: 'money-bill',
          link: 'sale/list',
          label: 'Sales',

        }
      ]
    },
    {
      label: 'Contacts',
      icons: 'users',
      submenu: false,
      child: [
        {
          icons: 'user',
          link: 'supplier',
          label: 'supplier',
        },
        {
          icons: 'user',
          link: 'customer/list',
          label: 'Customers',
        },
        {
          icons: 'user',
          link: 'agent/list',
          label: 'Agent',
        },
        {
          icons: 'building',
          link: 'mycompany/list',
          label: 'Company',
        }
      ]
    },
    {
      label: 'Accounts',
      submenu: false,
      icons: 'book',
      child: [
        {
          icons: 'credit-card',
          link: 'transactions',
          label: 'Transactions',

        },
        {
          icons: 'balance-scale',
          link: 'profit',
          label: 'Profit & Loss',

        },
        {
          icons: 'balance-scale',
          link: 'balance',
          label: 'Balance Sheet',
        },
        {
          icons: 'balance-scale',
          link: 'payables',
          label: 'Payables',
        }
      ]
    },
    {
      label: 'Reports',
      submenu: false,
      icons: 'chart-area',
      child: [
        {
          icons: 'database',
          link: 'report',
          label: 'All Report',
        }
      ]
    }

    // { link: 'purchase', label: 'Purchase' },
    // { link: 'sale/list', label: 'Sales' },
    // { link: 'supplier', label: 'Suppliers' },
    // { link: 'mycompany/list', label: 'My Company' },
    // { link: 'customer/list', label: 'Customers' },
    // { link: 'transactions', label: 'Transactions' },
    // { link: 'balance', label: 'Balance Sheet' },
    // { link: 'profit', label: 'Profit & Loss' },
    // { link: 'report', label: 'Report' }
  ];

  constructor(private store: Store<AppState>) {}

  ngOnInit() {
    combineLatest([this.selfActiveCompanyId$])
      .pipe()
      .subscribe(([selfActiveCompId]) => {
        this.selfActiveCompanyId = selfActiveCompId;
      });
    this.loadAllData();
  }

  loadAllData() {
    this.store.dispatch(loadProducts());
    this.store.dispatch(loadCompanies());
    this.store.dispatch(
      searchOrders({ searchRequest: new JournalSearchRequest() })
    );
    this.store.dispatch(
      loadBalanceSheet({ selfCompanyId: this.selfActiveCompanyId })
    );
    this.store.dispatch(loadAccountList());
    this.store.dispatch(
      searchTransactions({ request: new TransactionSearchRequest() })
    );
    this.store.dispatch(loadReport());
    this.store.dispatch(switchCompany({ id: this.selfActiveCompanyId }));
    this.store.dispatch(reload({ date: new Date() }));
  }
}
