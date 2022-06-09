import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Input,
  Inject
} from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { Product } from 'app/features/inventory/model/product';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { STimeout } from 'app/core/model/stimeout';

@Component({
  selector: 'session-timeout-modal',
  templateUrl: './session-timeout-modal.component.html',
  styleUrls: ['./session-timeout-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SessionTimeoutModalComponent implements OnInit {
  // @Input() countMinutes: number;
  // @Input() countSeconds: number;
  // @Input() progressCount: number;

  constructor(
    public dialogRef: MatDialogRef<SessionTimeoutModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: STimeout,
    public dialog: MatDialog,
    private router: Router,
    private store: Store<AppState>
  ) {}

  ngOnInit() {}

  continue() {
    this.dialogRef.close('continue');
  }
  logout() {
    this.dialogRef.close('logout');
  }
}
