import { CommonModule } from '@angular/common';
import { NgModule, Optional, SkipSelf, ErrorHandler } from '@angular/core';
import {
  HttpClientModule,
  HttpClient,
  HTTP_INTERCEPTORS
} from '@angular/common/http';
import {
  StoreRouterConnectingModule,
  RouterStateSerializer
} from '@ngrx/router-store';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import {
  FaIconLibrary,
  FontAwesomeModule
} from '@fortawesome/angular-fontawesome';

import { environment } from '../../environments/environment';

import {
  AppState,
  reducers,
  metaReducers,
  selectRouterState
} from './core.state';
import { AuthEffects } from './auth/auth.effects';
import { selectIsAuthenticated, selectAuth } from './auth/auth.selectors';
import { CoreEffects } from './core.effects';
import { authLogin, authLogout } from './auth/auth.actions';
import { AuthGuardService } from './auth/auth-guard.service';
import {
  ROUTE_ANIMATIONS_ELEMENTS,
  routeAnimations
} from './animations/route.animations';
import { AnimationsService } from './animations/animations.service';
import { CustomSerializer } from './router/custom-serializer';
import { SessionStorageService } from './session-storage/session-storage';
import { LocalStorageService } from './local-storage/local-storage.service';
import { HttpErrorInterceptor } from './http-interceptors/http-error.interceptor';
import { NotificationService } from './notifications/notification.service';
import { SettingsEffects } from './settings/settings.effects';
import {
  selectSettingsLanguage,
  selectEffectiveTheme,
  selectSettingsStickyHeader
} from './settings/settings.selectors';

import { ProductEffects } from 'app/features/inventory/effects/product.effects';
import { TitleService } from './title/title.service';

import {
  faSortDown,
  faCog,
  faBars,
  faRocket,
  faPowerOff,
  faUserCircle,
  faPlayCircle,
  faAddressBook,
  faList,
  faTable,
  faChartLine,
  faChartBar,
  faPrint,
  faRupeeSign,
  faPlus,
  faPlusSquare,
  faPlusCircle,
  faWindowClose,
  faTimes,
  faFile,
  faBook,
  faBriefcase,
  faClock,
  faCreditCard,
  faDatabase,
  faEnvelope,
  faEnvelopeOpen,
  faUsers,
  faUser,
  faChartPie,
  faChartArea,
  faTruck,
  faBalanceScale,
  faBarcode,
  faBookmark,
  faBuilding,
  faBinoculars,
  faBullseye,
  faEdit,
  faCogs,
  faUserCog,
  faTrash,
  faExchangeAlt,
  faExclamation,
  faExclamationTriangle,
  faFilter,
  faHome,
  faPaperPlane,
  faShoppingCart,
  faShoppingBag,
  faShoppingBasket,
  faMoneyBill,
  faBolt,
  faPencilAlt,
  faPen,
  faPenAlt,
  faSave,
  faPenSquare,
  faPenFancy,
  faPenNib,
  faPencilRuler,
  faUniversity,
  faSuitcase,
  faSuitcaseRolling,
  faSun,
  faStar,
  faPlane,
  faSitemap,
  faDownload,
  faUpload,
  faSearch,
  faFilePdf,
  faFileExcel,
  faEllipsisV,
  faChevronLeft,
  faChevronCircleDown,
  faChevronDown,
  faClone, faPhone, faDiagnoses, faTag, faSearchPlus, faSearchMinus, faAddressCard, faFolder
} from '@fortawesome/free-solid-svg-icons';
import {
  faGithub,
  faMediumM,
  faTwitter,
  faInstagram,
  faYoutube,
  faEmpire
} from '@fortawesome/free-brands-svg-icons';
import { HttpRequestInterceptor } from './http-interceptors/http-request-interceptor';

export {
  TitleService,
  selectAuth,
  //authLogin,
  //authLogout,
  routeAnimations,
  AppState,
  SessionStorageService,
  LocalStorageService,
  selectIsAuthenticated,
  ROUTE_ANIMATIONS_ELEMENTS,
  AnimationsService,
  AuthGuardService,
  selectRouterState,
  NotificationService,
  selectEffectiveTheme,
  selectSettingsLanguage,
  selectSettingsStickyHeader
};

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(
    http,
    `${environment.i18nPrefix}/assets/i18n/`,
    '.json'
  );
}

@NgModule({
  imports: [
    // angular
    CommonModule,
    HttpClientModule,

    // ngrx
    StoreModule.forRoot(reducers, { metaReducers }),
    StoreRouterConnectingModule.forRoot(),
    EffectsModule.forRoot([
      CoreEffects,
      AuthEffects,
      SettingsEffects,
      ProductEffects
    ]),
    environment.production
      ? []
      : StoreDevtoolsModule.instrument({
          name: 'u - client'
        }),

    // 3rd party

    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],

  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpRequestInterceptor,
      multi: true
    },
    { provide: RouterStateSerializer, useClass: CustomSerializer }
  ],
  exports: [FontAwesomeModule, TranslateModule]
})
export class CoreModule {
  constructor(
    @Optional()
    @SkipSelf()
    parentModule: CoreModule,
    faIconLibrary: FaIconLibrary
  ) {
    if (parentModule) {
      throw new Error('CoreModule is already loaded. Import only in AppModule');
    }
    faIconLibrary.addIcons(
      faEllipsisV,
      faSortDown,
      faCog,
      faBars,
      faRocket,
      faPowerOff,
      faUserCircle,
      faPlayCircle,
      faGithub,
      faMediumM,
      faTwitter,
      faInstagram,
      faYoutube,
      faAddressBook,
      faList,
      faTable,
      faChartLine,
      faChartBar,
      faPrint,
      faRupeeSign,
      faPlus,
      faPlusSquare,
      faPlusCircle,
      faWindowClose,
      faTimes,
      faFile,
      faFilePdf,
      faFileExcel,
      faBook,
      faBriefcase,
      faClock,
      faCreditCard,
      faDatabase,
      faEnvelope,
      faEnvelopeOpen,
      faUsers,
      faUser,
      faChartPie,
      faChartArea,
      faTruck,
      faBalanceScale,
      faBarcode,
      faBookmark,
      faBuilding,
      faBinoculars,
      faBullseye,
      faEdit,
      faCogs,
      faUserCog,
      faTrash,
      faExchangeAlt,
      faExclamation,
      faExclamationTriangle,
      faFilter,
      faHome,
      faPaperPlane,
      faShoppingCart,
      faShoppingBag,
      faShoppingBasket,
      faMoneyBill,
      faBolt,
      faPencilAlt,
      faPen,
      faPenSquare,
      faPenFancy,
      faPenNib,
      faPencilRuler,
      faPenAlt,
      faSave,
      faUniversity,
      faEmpire,
      faSuitcase,
      faSuitcaseRolling,
      faSun,
      faStar,
      faSitemap,
      faDownload,
      faRupeeSign,
      faUpload,
      faSearch,
      faSearchPlus,
      faSearchMinus,
      faSearch,
      faChevronLeft,
      faChevronCircleDown,
      faChevronDown,
      faChevronLeft,
      faClone,
      faBarcode,
      faClock,
      faPhone,
      faTag,
      faAddressCard,
      faFolder

    );
  }
}
