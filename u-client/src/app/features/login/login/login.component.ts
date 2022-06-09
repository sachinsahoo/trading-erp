import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from '../../../core/core.state';
import { FormBuilder, Validators } from '@angular/forms';
import { ROUTE_ANIMATIONS_ELEMENTS } from '../../../core/core.module';
import { authLogin } from '../../../core/auth/auth.actions';
import { LoginDetail } from '../../../core/model/login';

@Component({
  selector: 'anms-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginComponent implements OnInit {
  routeAnimationsElement = ROUTE_ANIMATIONS_ELEMENTS;

  loginFormGroup = this.fb.group(LoginComponent.createLoginForm());

  static createLoginForm(): any {
    return {
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    };
  }

  constructor(private store: Store<AppState>, public fb: FormBuilder) {}

  ngOnInit(): void {}

  onSubmit() {
    let detail = new LoginDetail();
    detail.username = this.loginFormGroup.value.username;
    detail.password = this.loginFormGroup.value.password;

    this.store.dispatch(authLogin({ loginDetail: detail }));
  }
}
