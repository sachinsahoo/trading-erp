import browser from 'browser-detect';
import {
  Component,
  OnInit,
  ElementRef,
  OnDestroy,
  ChangeDetectorRef
} from '@angular/core';
import { Store, select } from '@ngrx/store';
import { combineLatest, Observable } from 'rxjs';

import { environment as env } from '../../environments/environment';

import {
  routeAnimations,
  LocalStorageService,
  selectIsAuthenticated,
  selectSettingsStickyHeader,
  selectSettingsLanguage,
  selectEffectiveTheme,
  AppState,
  SessionStorageService
} from '../core/core.module';

import * as AuthActions from '../core/auth/auth.actions';
import {
  actionSettingsChangeAnimationsPageDisabled,
  actionSettingsChangeLanguage
} from '../core/settings/settings.actions';
import { Idle, EventTargetInterruptSource } from '@ng-idle/core';
import { Keepalive } from '@ng-idle/keepalive';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { SessionTimeoutModalComponent } from './session-timeout-modal/session-timeout-modal.component';
import { STimeout } from 'app/core/model/stimeout';
import {
  selectActiveSelfCompany,
  selectCompanyList,
  selectSelfCompanyList
} from 'app/features/inventory/selectors/order.selectors';
import { Company } from 'app/features/inventory/model/company';
import {
  loadCompanies,
  switchCompany
} from 'app/features/inventory/action/company.action';
import { switchTransactions } from 'app/features/inventory/action/transaction.action';
import { reload } from 'app/features/inventory/action/order.action';

@Component({
  selector: 'anms-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  animations: [routeAnimations]
})
export class AppComponent implements OnInit, OnDestroy {
  // Observables
  selfCompanyList$: Observable<any> = this.store.pipe(
    select(selectSelfCompanyList)
  );
  selfActiveCompany$: Observable<any> = this.store.pipe(
    select(selectActiveSelfCompany)
  );

  // Local
  selfCompanies: Array<Company>;
  allCompany: Company = new Company();
  activeSelfCompany: Company;

  hide: boolean = false;
  isProd = env.production;
  envName = env.envName;
  version = env.versions.app;
  year = new Date().getFullYear();
  //logo = require('../../assets/download.jpeg');
  languages = ['en', 'de', 'sk', 'fr', 'es', 'pt-br', 'zh-cn', 'he'];

  //
  navigation = [
    { link: 'trade', label: 'Trade' },
    { link: 'reports', label: 'Reports' }
  ];

  navigationSideMenu = [
    ...this.navigation,
    { link: 'settings', label: 'Settings' }
  ];

  isAuthenticated$: Observable<boolean>;
  stickyHeader$: Observable<boolean>;
  language$: Observable<string>;
  theme$: Observable<string>;

  constructor(
    private store: Store<AppState>,
    private storageService: SessionStorageService,
    private idle: Idle,
    private keepalive: Keepalive,
    private element: ElementRef,
    public dialog: MatDialog,
    private changeDetection: ChangeDetectorRef
  ) {}

  private static isIEorEdgeOrSafari() {
    return ['ie', 'edge', 'safari'].includes(browser().name);
  }

  ngOnInit(): void {
    //this.initIdle();
    // this.storageService.testLocalStorage();
    if (AppComponent.isIEorEdgeOrSafari()) {
      this.store.dispatch(
        actionSettingsChangeAnimationsPageDisabled({
          pageAnimationsDisabled: true
        })
      );
    }

    this.isAuthenticated$ = this.store.pipe(select(selectIsAuthenticated));
    this.stickyHeader$ = this.store.pipe(select(selectSettingsStickyHeader));
    this.language$ = this.store.pipe(select(selectSettingsLanguage));
    this.theme$ = this.store.pipe(select(selectEffectiveTheme));

    this.isAuthenticated$.subscribe(authenticated => {
      console.log('is Authenticated ' + authenticated);
      if (authenticated == true) {
        this.hide = true;
        this.initIdle();
      } else {
        this.hide = false;
      }
    });

    combineLatest([this.selfCompanyList$, this.selfActiveCompany$])
      .pipe()
      .subscribe(([selfComps, activeSelfComp]) => {
        console.log('App Init called ---------------');
        this.allCompany.name = 'All Companies';
        this.allCompany.id = 0;

        if (selfComps) {
          this.selfCompanies = [];
          this.selfCompanies.push(this.allCompany);
          this.selfCompanies = this.selfCompanies.concat(selfComps);
          this.activeSelfCompany = this.allCompany;
          this.changeDetection.detectChanges();
        }
        if (activeSelfComp != null) {
          this.activeSelfCompany = activeSelfComp;
        } else {
          this.activeSelfCompany = this.allCompany;
        }
        this.changeDetection.detectChanges();
      });
  }

  switchCompany(element: Company) {
    this.store.dispatch(switchCompany({ id: element.id }));
    this.store.dispatch(reload({ date: new Date() }));
  }

  onLoginClick() {
    //this.store.dispatch(authLogin());
  }

  onLogoutClick() {
    this.store.dispatch(AuthActions.authLogout());
  }

  onLanguageSelect({ value: language }) {
    this.store.dispatch(actionSettingsChangeLanguage({ language }));
  }

  //---------------------------------------------------------------
  //  Keep Alive
  //---------------------------------------------------------------

  idleState = 'NOT_STARTED';
  timedOut = false;
  lastPing?: Date = null;
  sTimeout: STimeout = new STimeout();
  timoutModalRef: any;

  initIdle() {
    // sets an idle timeout of 15 minutes.
    this.idle.setIdle(600);
    // sets a timeout period of 5 minutes.
    this.idle.setTimeout(300);
    // sets the interrupts like Keydown, scroll, mouse wheel, mouse down, and etc
    this.idle.setInterrupts([
      new EventTargetInterruptSource(
        this.element.nativeElement,
        'keydown DOMMouseScroll mousewheel mousedown mousemove touchstart touchmove scroll'
      )
    ]);

    this.idle.onIdleEnd.subscribe(() => {
      this.idleState = 'NO_LONGER_IDLE';
    });

    this.idle.onTimeout.subscribe(() => {
      this.idleState = 'TIMED_OUT';
      this.timedOut = true;
      this.closeProgressForm();
      this.logout();
    });

    this.idle.onIdleStart.subscribe(() => {
      (this.idleState = 'IDLE_START'), this.openProgressForm(1);
    });

    this.idle.onTimeoutWarning.subscribe((countdown: any) => {
      this.idleState = 'IDLE_TIME_IN_PROGRESS';
      this.sTimeout.count = Math.floor((countdown - 1) / 60) + 1;
      this.sTimeout.progressCount = this.reverseNumber(countdown);
      this.sTimeout.countMinutes = Math.floor(countdown / 60);
      this.sTimeout.countSeconds = countdown % 60;
    });

    // sets the ping interval to 15 seconds
    this.keepalive.interval(60);

    // Keepalive can ping request to an HTTP location to keep server session alive
    this.keepalive.request(
      'http://localhost:8080/smulti/api/user/keepalive.ws'
    );
    // Keepalive ping response can be read using below option
    this.keepalive.onPing.subscribe(response => {
      // Redirect user to logout screen stating session is timeout out if if response.status != 200
    });

    this.reset();
  }

  reverseNumber(countdown: number) {
    return 300 - (countdown - 1);
  }

  reset() {
    this.idle.watch();
    this.idleState = 'Started.';
    this.timedOut = false;
  }

  openProgressForm(count: number) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'session-timeout11';
    dialogConfig.disableClose = true;
    dialogConfig.width = '400px';
    dialogConfig.data = this.sTimeout;

    // Open Dialog
    this.timoutModalRef = this.dialog.open(
      SessionTimeoutModalComponent,
      dialogConfig
    );

    this.timoutModalRef.afterClosed().subscribe(data => {
      console.log('Dialog output:', data);
      if (data !== '' && 'logout' === data) {
        this.logout();
      } else if (data !== '' && 'continue' === data) {
        this.reset();
      }
    });
  }

  logout() {
    console.log('LOGOUT ----------');
    this.keepalive.stop();
    this.resetTimeOut();
    this.store.dispatch(AuthActions.authLogout());
  }

  closeProgressForm() {
    this.timoutModalRef.close();
  }

  resetTimeOut() {
    this.idle.stop();
    this.keepalive.stop();
  }

  //---------------------------------------------------------------
  //  Keep Alive    End
  //---------------------------------------------------------------

  ngOnDestroy(): void {
    this.idle.onIdleStart.unsubscribe();
    this.idle.onTimeoutWarning.unsubscribe();
    this.idle.onIdleEnd.unsubscribe();
  }
}
