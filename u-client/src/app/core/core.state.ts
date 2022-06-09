import {
  ActionReducerMap,
  MetaReducer,
  createFeatureSelector,
  createSelector
} from '@ngrx/store';
import { routerReducer, RouterReducerState } from '@ngrx/router-store';

import { environment } from '../../environments/environment';

import { initStateFromLocalStorage } from './meta-reducers/init-state-from-local-storage.reducer';
import { debug } from './meta-reducers/debug.reducer';
import { AuthState } from './auth/auth.models';
import { authReducer } from './auth/auth.reducer';
import { RouterStateUrl } from './router/router.state';
import { settingsReducer } from './settings/settings.reducer';
import { SettingsState } from './settings/settings.model';
import { OrderState } from 'app/features/inventory/model/order.model';
import { orderReducer } from 'app/features/inventory/reducer/order.reducer';
import { clearState } from './meta-reducers/clear-state.reducer';

export const reducers: ActionReducerMap<AppState> = {
  auth: authReducer,
  settings: settingsReducer,
  order: orderReducer,
  router: routerReducer
};

export const metaReducers: MetaReducer<AppState>[] = [
  initStateFromLocalStorage,
  clearState
];

metaReducers.unshift(debug);
// if (!environment.production) {
//    if (!environment.test) {
//     metaReducers.unshift(debug);
//   }
// }

export const selectState = createSelector(
  (state: AppState) => state,
  (state: AppState) => state
);
export const selectAuthState = createFeatureSelector<AppState, AuthState>(
  'auth'
);

export const selectSettingsState = createFeatureSelector<
  AppState,
  SettingsState
>('settings');

export const selectOrderState = createFeatureSelector<AppState, OrderState>(
  'order'
);

export const selectRouterState = createFeatureSelector<
  AppState,
  RouterReducerState<RouterStateUrl>
>('router');

export interface AppState {
  auth: AuthState;
  settings: SettingsState;
  order: OrderState;
  router: RouterReducerState<RouterStateUrl>;
}
