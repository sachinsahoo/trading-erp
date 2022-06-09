import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Inject
} from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Store } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Account } from 'app/features/inventory/model/account';

@Component({
  selector: 'select-group-modal',
  templateUrl: './select-group-modal.component.html',
  styleUrls: ['./select-group-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SelectGroupModalComponent implements OnInit {

  public side: number = 1
  
  constructor(
    public dialogRef: MatDialogRef<SelectGroupModalComponent>,
    @Inject(MAT_DIALOG_DATA) public account: Account,
    private store: Store<AppState>,
    public dialog: MatDialog,
    private router: Router
    
  ) {}

  ngOnInit() {}

  

  selectGroup(account: Account) {
    // Dialog config
    if(!account.group) {
      this.viewLedger(account);
    } else {

      const dialogConfig = new MatDialogConfig();
      dialogConfig.id = 'select-group';
      dialogConfig.disableClose = true;
      dialogConfig.width = '800px';
      dialogConfig.data = account;
  
      // Open Dialog
      const dialogRef = this.dialog.open(SelectGroupModalComponent, dialogConfig);
    }
   
  }

  viewLedger(acct: Account) {    
    this.router.navigate(['trade/ledger', acct.id]);
    this.dialogRef.close();
  }

  onClose() {
    this.dialogRef.close();
  }
}
