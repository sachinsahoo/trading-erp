import { createAction, props } from '@ngrx/store';
import { LoginDetail } from '../model/login';

export const authLogin = createAction(
  '[Auth] Login',
  props<{ loginDetail: LoginDetail }>()
);

export const authLogout = createAction('[Auth] Logout');
export const authLoginSuccess = createAction('[Auth] Success');
export const authLoginFailure = createAction('[Auth] Failure');
