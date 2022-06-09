import { Injectable } from '@angular/core';
import { Actions, createEffect, Effect, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { State } from 'app/core/settings/settings.model';
import {
  loadProducts,
  loadProductsSuccess,
  productFailure,
  saveProduct,
  saveProductSuccess,
} from '../action/product.actions';
import { exhaustMap, map } from 'rxjs/operators';
import { ProductService } from '../service/api/product.service';
import { TestService } from '../service/api/test.service';

@Injectable()
export class ProductEffects {
  constructor(
    private actions$: Actions,
    private store: Store<State>,
    private testService: TestService,
    private productService: ProductService
  ) {}

  @Effect()
  loadProducts$ = this.actions$.pipe(
    ofType(loadProducts),
    exhaustMap(action =>
      this.testService.productList().pipe(
        map(data => {
          if (data.status === 'SUCCESS') {
            return loadProductsSuccess({ products: data.products });
          } else {
            return productFailure(data);
          }
        })
      )
    )
  );

   @Effect()
    saveProduct$ = this.actions$.pipe(
      ofType(saveProduct),
      exhaustMap(action =>
        this.productService.save(action.product).pipe(
          map(data => {
            if (data.status === 'SUCCESS') {
              return saveProductSuccess(data.products);
            } else {
              return productFailure(data.errorMessage);
            }
          })
        )
      )
    );
}
