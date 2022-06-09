import { ActionReducer } from '@ngrx/store';

import { AppState } from '../core.state';

export function clearState(
  reducer: ActionReducer<AppState>
): ActionReducer<AppState> {
  return function(state, action) {
    if (action.type === '[Auth] Logout') {
      state = undefined;
    } else if (action.type === '[Search] Load Searches') {
      state = {
        auth: state.auth,
        settings: state.settings,
        router: state.router,
        order: state.order
      };
    }
    return reducer(state, action);
  };
}
