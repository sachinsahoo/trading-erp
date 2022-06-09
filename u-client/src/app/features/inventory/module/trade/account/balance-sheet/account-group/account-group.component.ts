import { Component, OnInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { Account } from 'app/features/inventory/model/account';
import { SelectGroupModalComponent } from '../../select-group-modal/select-group-modal.component';

@Component({
  selector: 'account-group',
  templateUrl: './account-group.component.html',
  styleUrls: ['./account-group.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AccountGroupComponent implements OnInit {


  @Input() accountList: Array<Account>;
  @Input() side: number;

  constructor(
    private store: Store<AppState>,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
  }

  selectGroup(account: Account) {
    // Dialog config
    if (!account.group) {
      this.viewLedger(account);
    } else {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.id = 'select-group' + account.level;
      dialogConfig.disableClose = true;
      dialogConfig.width = '800px';
      dialogConfig.data = account;

      // Open Dialog
      const dialogRef = this.dialog.open(SelectGroupModalComponent, dialogConfig);

    }

  }

  viewLedger(acct: Account) {
    this.router.navigate(['trade/ledger', acct.id]);
  }

}
