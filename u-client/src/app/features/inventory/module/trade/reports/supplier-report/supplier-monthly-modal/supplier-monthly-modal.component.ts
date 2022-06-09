import { Component, OnInit, ChangeDetectionStrategy, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { SupplierYearlyReport } from 'app/features/inventory/model/Report/supplierYearlyReport';
import { Store } from '@ngrx/store';
import { AppState } from 'app/core/core.state';

@Component({
  selector: 'supplier-monthly-modal',
  templateUrl: './supplier-monthly-modal.component.html',
  styleUrls: ['./supplier-monthly-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SupplierMonthlyModalComponent implements OnInit {
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  prodDataSource: MatTableDataSource<any> = new MatTableDataSource<any>();

  displayedCols: string[] = [
    'date',
    'purchaseamt',
  ];


  prodDisplayedCols: string[] = [
    'prodname',
    'purchaseqty',
    'avgCostPrice',
    'purchaseamt',
  ];




  constructor(
    public dialogRef: MatDialogRef<SupplierMonthlyModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SupplierYearlyReport,

    private store: Store<AppState>
  ) {}

  ngOnInit() {
    
    this.dataSource = new MatTableDataSource<any>(this.data.monthlyReport);
    this.prodDataSource = new MatTableDataSource<any>(this.data.productReport);

    this.dialogRef.keydownEvents().subscribe(event => {
      if (event.key === 'Escape') {
        this.onClose();
      }
    });
  }

  onClose() {
    this.dialogRef.close();
  }
}