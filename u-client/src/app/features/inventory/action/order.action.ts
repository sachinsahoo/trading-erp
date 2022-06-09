import { createAction, props } from '@ngrx/store';
import { PurchaseOrder } from '../model/purchaseorder';
import { Invoice } from '../model/invoice';
import { OrderSearchRequest } from '../model/rs/orderSearchRequest';



export const reload = createAction(
  '[Order] Reload',
  props<{ date: Date}>()
);

// -------------
// Search Order
// -------------
export const searchOrders = createAction(
  '[Order] Search Orders',
  props<{ searchRequest: OrderSearchRequest }>()
  );


// -------------
// Load Order
// -------------
export const loadOrders = createAction('[Order] Load Orders');

export const resetOrder = createAction('[Order] Reset Orders');


export const loadOrdersSuccess = createAction(
  '[Order] Load Orders Success',
  props<{ orders: PurchaseOrder[], searchRequest: OrderSearchRequest }>()
);

export const orderFailure = createAction(
  '[Order] Load Orders Failure',
  props<{ errorMessage: string }>()
);

// -------------
// Search Order
// -------------
export const orderLoading = createAction(
  '[Order] Orders loading',
  props<{ isLoading: boolean }>()
  );

// -------------
// Update in store
// -------------
export const updateSelectedOrderInStore = createAction(
  '[Order] Update Order in store',
  props<{ order: PurchaseOrder }>()
);

// -------------
//Save Order
// -------------
export const saveOrder = createAction(
  '[Order] Save Order',
  props<{ order: PurchaseOrder }>()
);

export const updateOrderResultInStore = createAction(
  '[Order] Save Order Success',
  props<{ order: PurchaseOrder }>()
);



// -------------
//Update Status
// -------------
export const updateOrderStatus = createAction(
  '[Order] Update Status',
  props<{ oid: number; date: number; status: string }>()
);
export const updateOrderStatusSuccess = createAction(
  '[Order] Update Status Success',
  props<{ order: PurchaseOrder }>()
);


// Invoice ----------------------

// --------------------------
// Update Invoce Id in store
// --------------------------
export const updateSelectedInvoiceIdInStore = createAction(
  '[Order] Update Invoice Id in store',
  props<{ invId: number }>()
);


// -----------
// Save Invoce
// ----------- 

export const saveInvoice = createAction(
  '[Invoce] Save Invoice',
  props<{ invoice: Invoice }>()
);

export const updateInvoiceResultInStore = createAction(
  '[Invoce] Save Invoice',
  props<{ invoice: Invoice }>()
);
