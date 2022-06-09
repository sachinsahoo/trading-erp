import { createEffect, Actions } from '@ngrx/effects';
import { Injectable } from '@angular/core';
import { Store, select } from '@ngrx/store';
import { AppState, selectState } from './core.state';
import { withLatestFrom, tap } from 'rxjs/operators';
import { SessionStorageService } from './session-storage/session-storage';

export const APP_STATE_KEY = 'APP';

@Injectable()
export class CoreEffects {
  constructor(
    private actions$: Actions,
    private store: Store<AppState>,
    private sessionStorageService: SessionStorageService
  ) {}

  persistState = createEffect(
    () =>
      this.actions$.pipe(
        withLatestFrom(this.store.pipe(select(selectState))),
        tap(([action, state]) =>
          this.sessionStorageService.setItem(APP_STATE_KEY, state)
        )
      ),
    { dispatch: false }
  );
}
