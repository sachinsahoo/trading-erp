import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { combineLatest, Observable, Subscription } from 'rxjs';
import { select, Store } from '@ngrx/store';
import { selectActiveSelfCompanyId, selectBalanceSheet, selectReload } from 'app/features/inventory/selectors/order.selectors';
import { BalanceSheet } from 'app/features/inventory/model/balanceSheet';
import { AppState } from 'app/core/core.state';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { SelectGroupModalComponent } from '../select-group-modal/select-group-modal.component';
import { loadBalanceSheet } from 'app/features/inventory/action/account.action';
import { Account } from 'app/features/inventory/model/account';

@Component({
  selector: 'profit-loss',
  templateUrl: './profit-loss.component.html',
  styleUrls: ['./profit-loss.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfitLossComponent implements OnInit {
  // Observables
  balanceSheet$: Observable<any> = this.store.pipe(select(selectBalanceSheet));
  activeSelfCompId$: Observable<any> = this.store.pipe(select(selectActiveSelfCompanyId));
  reload$: Observable<any> = this.store.pipe(select(selectReload));
  subscriptions: Array<Subscription> = [];

  // Local
  balanceSheet: BalanceSheet;
  activeSelfId: number;

  // Constructor  ----
  constructor(
    private store: Store<AppState>,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router
  ) {} // End ---
  ngOnDestroy(): void {
    this.subscriptions.forEach(s => {
      s.unsubscribe();
    });
  }
  ngOnInit() {
    
    combineLatest([ this.activeSelfCompId$, this.balanceSheet$])
    .pipe()
    .subscribe(([selfId, bs]) => {
      console.log("Init balance sheet ----------");
      if(this.activeSelfId !== selfId){
        this.activeSelfId = selfId;
        this.store.dispatch(loadBalanceSheet({selfCompanyId: this.activeSelfId}))
      }

      if (bs) {
        this.balanceSheet = bs;
      }
   
    });
  }

  selectGroup(account: Account) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'select-group';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = account;

    // Open Dialog
    const dialogRef = this.dialog.open(SelectGroupModalComponent, dialogConfig);
  }
}
