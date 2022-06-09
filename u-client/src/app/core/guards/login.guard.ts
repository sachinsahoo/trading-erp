import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Store, select } from '@ngrx/store';



import { AppState } from '../core.state';
import { selectIsAuthenticated } from '../auth/auth.selectors';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {

  constructor(
    private store: Store<AppState>,
    private router: Router,
  ) { }
  isAuth$: Observable<any> = this.store.pipe(select(selectIsAuthenticated));
  canActivate(): Promise<boolean> {
    return new Promise(resolve => {
      this.isAuth$.subscribe(
        isAuth => {
           console.log(isAuth);
          if (isAuth === false) {

            resolve(true);
          } else {
            resolve(false);
          //  this.router.navigate(['login']);

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
