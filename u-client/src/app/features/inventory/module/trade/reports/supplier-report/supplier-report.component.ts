import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { SupplierYearlyReport } from 'app/features/inventory/model/Report/supplierYearlyReport';
import { PurchaseSaleYearlyReport } from 'app/features/inventory/model/Report/purchaseSaleYearlyReport';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { MyBarChart } from 'app/features/inventory/model/Report/barChart';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { SupplierMonthlyModalComponent } from './supplier-monthly-modal/supplier-monthly-modal.component';
import { Store } from '@ngrx/store';
import { AppState } from 'app/core/core.state';

@Component({
  selector: 'supplier-report',
  templateUrl: './supplier-report.component.html',
  styleUrls: ['./supplier-report.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SupplierReportComponent implements OnInit {
  displayedCols: string[] = ['name', 'balance', 'purchaseamt'];

  supplierReports: SupplierYearlyReport[];
  yearlyReport: PurchaseSaleYearlyReport;

  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();

  barchartDetail: MyBarChart = new MyBarChart();

  pYearly$: Observable<any> = this.report();

  // Constructor
  constructor(private http: HttpClient,private store: Store<AppState>,
    public dialog: MatDialog) {}

  // Init
  ngOnInit() {
    this.report().subscribe(data => {
      this.yearlyReport = data.report;
      this.supplierReports = this.yearlyReport.supplierReports;
      this.dataSource = new MatTableDataSource<any>(this.supplierReports);

      this.barchartDetail = this.yearlyReport.allSuppliersBar;
    });
  }

  // Show Modal Window
  showMonthly(report: SupplierYearlyReport) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'supplier-Detail-Report';
    dialogConfig.disableClose = true;
    dialogConfig.width = '1000px';
    dialogConfig.data = report;

    // Open Dialog
    const dialogRef = this.dialog.open(
      SupplierMonthlyModalComponent,
      dialogConfig
    );

 }

  // :todo move to service
  report(): Observable<any> {
    const url = '/assets/test_data/yearlyReportResponse.json';
    return this.http.get(url);
  }
}
