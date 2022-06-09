/*
-------------- Container

constructor(private store: Store<AppState>, public dialog: MatDialog) {}

editProduct(data: Product) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'edit-product';
    dialogConfig.disableClose = true;
    dialogConfig.width = '800px';
    dialogConfig.data = data;

    // Open Dialog
    const dialogRef = this.dialog.open(
      EditProductModalComponent,
      dialogConfig
    );
   
 }


 ----------------- Modal

 export class ViewProductModalComponent implements OnInit {

 

constructor(
  public dialogRef: MatDialogRef<ViewProductModalComponent>,
  @Inject(MAT_DIALOG_DATA) public data: any,
  public dialog: MatDialog,
  private store: Store<AppState>
) {}

ngOnInit() {
  this.dialogRef.keydownEvents().subscribe(event => {
    if (event.key === 'Escape') {
      this.onClose();
    }
  });
}

editProduct(data: Product) {
  // Dialog config
  const dialogConfig = new MatDialogConfig();
  dialogConfig.id = 'edit-product';
  dialogConfig.disableClose = true;
  dialogConfig.width = '800px';
  dialogConfig.data = data;

  // Open Dialog
  const dialogRef = this.dialog.open(
    EditProductModalComponent,
    dialogConfig
  );
 
}

onClose() {
  //this.store.dispatch(approveTransModalClose());
  this.dialogRef.close();
}
}

------- Modal HTML


<h1 mat-dialog-title> {{data.name}}</h1>

<mat-divider></mat-divider>

<mat-dialog-content class="mt-4 mb-1">
    <!-- <mat-progress-spinner diameter="24" mode="indeterminate" *ngIf="isLoading$ | async">
    </mat-progress-spinner> -->

   

    
</mat-dialog-content>

<mat-divider></mat-divider>

<mat-dialog-actions align="end" class="mt-1">
    <button id="cancel-btn" mat-raised-button (click)="onClose()">Close</button>
    
</mat-dialog-actions>








 */
