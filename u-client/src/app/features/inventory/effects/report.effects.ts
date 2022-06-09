import { Injectable } from '@angular/core';
import { Actions, createEffect, Effect, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { State } from 'app/core/settings/settings.model';

import { exhaustMap, map } from 'rxjs/operators';
import { ProductService } from '../service/api/product.service';
import { TestService } from '../service/api/test.service';
import { loadReportSuccess, loadReport, loadReportFailure } from '../action/report.action';

@Injectable()
export class ReportEffects {
  constructor(
    private actions$: Actions,
    private store: Store<State>,
    private testService: TestService
  ) {}

  @Effect()
  loadReport$ = this.actions$.pipe(
    ofType(loadReport),
    exhaustMap(action =>
      this.testService.report().pipe(
        map(data => {
          if (data.status === 'SUCCESS') {
            return loadReportSuccess({ report: data.report });
          } else {
            return loadReportFailure({errorMessage: data.errorMessage} );
          }
        })
      )
    )
  );

}
