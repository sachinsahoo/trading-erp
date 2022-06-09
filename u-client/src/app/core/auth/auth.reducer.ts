import { AuthState } from './auth.models';
import * as AuthActions from './auth.actions';
import { createReducer, on, Action } from '@ngrx/store';

export const initialState: AuthState = {
  isAuthenticated: false
};

const reducer = createReducer(
  initialState,
  on(AuthActions.authLoginSuccess, state => ({
    ...state,
    isAuthenticated: true
  })),
  on(AuthActions.authLogout, state => ({ ...state, isAuthenticated: false }))
);

export function authReducer(
  state: AuthState | undefined,
  action: Action
): AuthState {
  return reducer(state, action);
}
