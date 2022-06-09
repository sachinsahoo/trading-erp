import { Injectable } from '@angular/core';
import { Actions, createEffect, Effect, ofType } from '@ngrx/effects';
import { Store, select } from '@ngrx/store';
import { exhaustMap, map, concatMap, withLatestFrom } from 'rxjs/operators';
import { TestService } from '../service/api/test.service';
import { loadOrders, loadOrdersSuccess, orderFailure, saveOrder, updateOrderResultInStore, updateSelectedOrderInStore, saveInvoice, updateInvoiceResultInStore, updateSelectedInvoiceIdInStore, updateOrderStatus, searchOrders, orderLoading, resetOrder, reload } from '../action/order.action';
import { PurchaseService } from '../service/api/purchase.service';
import { combineLatest, of, pipe } from 'rxjs';
import { selectActiveSelfCompanyId, selectOrderList, selectOrderSearchRequest } from '../selectors/order.selectors';
import { State } from 'app/core/settings/settings.model';
import { InvoiceService } from '../service/api/invoice.service';
import { Router } from '@angular/router';

@Injectable()
export class OrderEffects {
  constructor(
    private actions$: Actions,
    private store: Store<State>,
    private router: Router,
    private testService: TestService,
    private purchaseService: PurchaseService,
    private invoiceService: InvoiceService
  ) { }



   //----------------
  // Load Order
  //----------------

  @Effect()
  searchOrders$ = this.actions$.pipe(
    ofType(searchOrders),
    concatMap(action =>
      of(action).pipe(withLatestFrom(
        this.store.pipe(select(selectActiveSelfCompanyId)),
      ))),
    exhaustMap(([action, selfid])  =>
      this.testService.purchaseList().pipe(
        map(data => {
          if (data.status === 'SUCCESS') {
            return loadOrdersSuccess({ orders: data.orders , searchRequest: data.searchRequest});
          } else {
            return orderFailure({ errorMessage: data.errorMessage });
          }
        })
      )
    )
  );

  //----------------
  // Load Order
  //----------------

  @Effect()
  loadOrders$ = this.actions$.pipe(
    ofType(loadOrders),
    exhaustMap(action =>
      this.purchaseService.list().pipe(
        map(data => {
          if (data.status === 'SUCCESS') {
            return loadOrdersSuccess({ orders: data.orders , searchRequest: data.searchRequest});
          } else {
            return orderFailure({ errorMessage: data.errorMessage });
          }
        })
      )
    )
  );


  //----------------
  //  Order Loading
  //----------------

  @Effect()
  isLoading$ = this.actions$.pipe(
    ofType(searchOrders, loadOrders, saveOrder, updateOrderStatus, saveInvoice),
    map(action => {
      this.store.dispatch(resetOrder());
      return orderLoading({ isLoading: true });
    }));

    @Effect()
  isLoadingDone$ = this.actions$.pipe(
    ofType(loadOrdersSuccess, updateOrderResultInStore, updateInvoiceResultInStore, orderFailure),
    map(action => {
      this.store.dispatch(reload({date: new Date()}));
      return orderLoading({ isLoading: false });
    }));

  //----------------
  // Save Order
  //----------------


  @Effect()
  saveOrder$ = this.actions$.pipe(
    ofType(saveOrder),
    exhaustMap(action =>
      
      this.purchaseService.save(action.order).pipe(
        map(data => {       
          if (data.status === 'SUCCESS') {
            console.log('save order result ----' + data.purchaseOrder);
            return updateOrderResultInStore({ order: data.purchaseOrder });
          } else {
            return orderFailure({ errorMessage: data.errorMessage });
          }
        })
      )
    )
  );

  //--------------------
  // Update Order Status
  //--------------------

  @Effect()
  updateOrderStatus$ = this.actions$.pipe(
    ofType(updateOrderStatus),
    exhaustMap(action =>
      
      this.purchaseService.updatestatus(action.oid, action.date, action.status).pipe(
        map(data => {       
          if (data.status === 'SUCCESS') {
            console.log('save order result ----' + data.purchaseOrder);
            return updateOrderResultInStore({ order: data.purchaseOrder });
          } else {
            return orderFailure({ errorMessage: data.errorMessage });
          }
        })
      )
    )
  );

  //----------------
  // Save Invoice
  //----------------
  @Effect()
  saveInvoice$ = this.actions$.pipe(
    ofType(saveInvoice),
    exhaustMap(action =>
      
      this.invoiceService.save(action.invoice).pipe(
        map(data => {       
          if (data.status === 'SUCCESS') {
            
            this.store.dispatch(updateSelectedInvoiceIdInStore({ invId: data.invoice.id}));
            this.store.dispatch(updateInvoiceResultInStore({invoice: data.invoice}));
          
            return updateOrderResultInStore({ order: data.purchaseOrder });
          } else {
            return orderFailure({ errorMessage: data.errorMessage });
          }
        })
      )
    )
  );






//--------------------------------------------------------------
// Common Effect to Update / Insert response { Order } to Store
//-------------------------------------------------------------- 

  @Effect()
  updateResultOrder$ = this.actions$.pipe(
    ofType(updateOrderResultInStore),
    concatMap(action =>
      of(action).pipe(withLatestFrom(
        combineLatest([
          this.store.pipe(select(selectOrderList)),
          this.store.pipe(select(selectOrderSearchRequest))
        ])
        
      ))
    ),
    map(([action, [orderList, searchRequest]]) => {
      let index = orderList.findIndex(order => { 
        return (order.id === action.order.id); 
      });
      if (index > -1) {
        orderList[index] = JSON.parse(JSON.stringify(action.order));
      } else {
        orderList.unshift(JSON.parse(JSON.stringify(action.order)));
      }
      this.store.dispatch(loadOrdersSuccess({ orders: orderList, searchRequest: searchRequest }));
      this.router.navigate(['trade/order/edit', action.order.type, action.order.id]);
      console.log('Edit order Result Update from server --------');
      return updateSelectedOrderInStore({order: action.order});
    })
  );




}
