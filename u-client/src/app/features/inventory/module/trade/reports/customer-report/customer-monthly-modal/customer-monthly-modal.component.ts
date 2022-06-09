import { Component, OnInit, ChangeDetectionStrategy, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { CustomerYearlyReport } from 'app/features/inventory/model/Report/customerYearlyReport';
import { AppState } from 'app/core/core.state';
import { Store } from '@ngrx/store';

@Component({
  selector: 'customer-monthly-modal',
  templateUrl: './customer-monthly-modal.component.html',
  styleUrls: ['./customer-monthly-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CustomerMonthlyModalComponent implements OnInit {

  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  prodDataSource: MatTableDataSource<any> = new MatTableDataSource<any>();

  displayedCols: string[] = [
    'date',
    'saleamt',
    'profit'
  ];


  prodDisplayedCols: string[] = [
    'prodname',
    'saleqty',
    'avgSalePrice',
    'saleamt',
    'profit'
  ];




  constructor(
    public dialogRef: MatDialogRef<CustomerMonthlyModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: CustomerYearlyReport,

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
