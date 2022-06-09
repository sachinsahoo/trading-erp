import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Store, select } from '@ngrx/store';
import { Observable } from 'rxjs';

import { selectIsAuthenticated } from './auth.selectors';
import { AppState } from '../core.state';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  constructor(
    private store: Store<AppState>,
    private router: Router,
  ) { }

  // canActivate(): Observable<boolean> {
  //   return this.store.pipe(select(selectIsAuthenticated));
  // }
  isAuth$: Observable<any> = this.store.pipe(select(selectIsAuthenticated));
  canActivate(): Promise<boolean> {
    return new Promise(resolve => {
      this.isAuth$.subscribe(
        isAuth => {
           console.log(isAuth);
          if (isAuth === true) {
            resolve(true);
          } else {
            this.router.navigate(['login']);
            resolve(false);
          }
        }
      )
      // .then(res => {
      //   if (res) {
      //     resolve(true);
      //   } else {
      //     this.router.navigate(['login']);
      //     resolve(false);
      //   }
      // })
      // .catch(err => {
      //   resolve(false);
      // });
    });
  }
}
