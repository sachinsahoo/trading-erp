import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { CoreModule } from './core/core.module';
import {NgIdleKeepaliveModule} from '@ng-idle/keepalive';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app/app.component';
import { SharedModule } from './shared/shared.module';
import { SessionTimeoutModalComponent } from './app/session-timeout-modal/session-timeout-modal.component';
import { EffectsModule } from '@ngrx/effects';
import { ProductEffects } from './features/inventory/effects/product.effects';
import { OrderEffects } from './features/inventory/effects/order.effects';
import { TransactionEffects } from './features/inventory/effects/invTransaction.effects';
import { AccountEffects } from './features/inventory/effects/account.effects';
import { CompanyEffects } from './features/inventory/effects/company.effects';
import { ReportEffects } from './features/inventory/effects/report.effects';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  imports: [
    // angular
    BrowserAnimationsModule,
    BrowserModule,
    HttpClientModule,

    // core
    CoreModule,
    SharedModule,

    // app
    AppRoutingModule,
    NgIdleKeepaliveModule.forRoot()
  ],
 
  declarations: [AppComponent, SessionTimeoutModalComponent],
  bootstrap: [AppComponent]
})
export class AppModule {}



// entryComponents: [SessionTimeoutModalComponent]
