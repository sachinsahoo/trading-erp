import { ActionReducer, INIT, UPDATE } from '@ngrx/store';

import { SessionStorageService } from '../session-storage/session-storage';
import { AppState } from '../core.state';

export function initStateFromLocalStorage(
  reducer: ActionReducer<AppState>
): ActionReducer<AppState> {
  return function(state, action) {
    const newState = reducer(state, action);
    if ([INIT.toString(), UPDATE.toString()].includes(action.type)) {
      return { ...newState, ...SessionStorageService.loadInitialState().app };
    }
    return newState;
  };
}
