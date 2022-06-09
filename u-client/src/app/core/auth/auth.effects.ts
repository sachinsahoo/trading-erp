import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ofType, createEffect, Actions } from '@ngrx/effects';
import { tap, exhaustMap, map } from 'rxjs/operators';

import { LocalStorageService } from '../local-storage/local-storage.service';

import * as AuthActions from './auth.actions';
import { AuthService } from '../services/auth/auth.service';
import { AppState, SessionStorageService } from '../core.module';
import { Store } from '@ngrx/store';
import { loadCompanies } from 'app/features/inventory/action/company.action';
import { reload } from 'app/features/inventory/action/order.action';
import { actionSettingsChangeHour } from '../settings/settings.actions';

export const AUTH_KEY = 'AUTH';

@Injectable()
export class AuthEffects {
  constructor(
    private actions$: Actions,
    private sessionStorageService: SessionStorageService,
    private router: Router,
    private store: Store<AppState>,
    private authService: AuthService
  ) {}

  login$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(AuthActions.authLogin),
        tap(() => {
          this.store.dispatch(loadCompanies());
          this.store.dispatch(reload({date: new Date()}));
          this.store.dispatch(AuthActions.authLoginSuccess());
          this.router.navigate(['trade/product/list']);
          //this.store.dispatch(AuthActions.authLoginSuccess());
        })
      ),
    { dispatch: false }
  );

  // login$ = createEffect(() => {
  //   return this.actions$.pipe(
  //     ofType(AuthActions.authLogin),
  //     exhaustMap(action =>
  //       this.authService.logIn(action.loginDetail).pipe(
  //         map(data => {
  //           if (data.status === 'SUCCESS') {
  //             this.store.dispatch(loadCompanies());
  //             this.store.dispatch(reload({date: new Date()}));
  //             this.router.navigate(['trade/product/list']);
  //             return AuthActions.authLoginSuccess();
  //           } else {
  //             return AuthActions.authLoginFailure();
  //           }
  //         })
  //       )
  //     )
  //   );
  // });

  logout = createEffect(
    () =>
      this.actions$.pipe(
        ofType(AuthActions.authLogout),
        tap(() => {
          this.router.navigate(['login']);
          // this.localStorageService.setItem(AUTH_KEY, {
          //  isAuthenticated: false
          //});
        })
      ),
    { dispatch: false }
  );
}
