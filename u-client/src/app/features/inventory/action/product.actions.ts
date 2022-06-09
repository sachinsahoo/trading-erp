import { createAction, props } from '@ngrx/store';
import { Product } from '../model/product';

// Load Product
export const loadProducts = createAction('[Product] Load Products');

export const loadProductsSuccess = createAction(
  '[Product] Load Products Success',
  props<{ products: Product[] }>()
);

export const productFailure = createAction(
  '[Product]  Product Failure',
  props<{ errorMessage: string }>()
);

//Add Product
export const saveProduct = createAction(
  '[Product] Save Product',
  props<{ product: Product }>()
);

export const saveProductSuccess = createAction(
  '[Product] Save Product Success',
  props<{ product: Product }>()
);

