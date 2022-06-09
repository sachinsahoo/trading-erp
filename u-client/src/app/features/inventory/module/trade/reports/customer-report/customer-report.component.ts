import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  ViewChild
} from '@angular/core';
import { CustomerYearlyReport } from 'app/features/inventory/model/Report/customerYearlyReport';
import { PurchaseSaleYearlyReport } from 'app/features/inventory/model/Report/purchaseSaleYearlyReport';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MyChartDataSet } from 'app/features/inventory/model/Report/chartDataSet';
import { MyBarChart } from 'app/features/inventory/model/Report/barChart';
import { Store } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { CustomerMonthlyModalComponent } from './customer-monthly-modal/customer-monthly-modal.component';

@Component({
  selector: 'customer-report',
  templateUrl: './customer-report.component.html',
  styleUrls: ['./customer-report.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CustomerReportComponent implements OnInit {
  @ViewChild(MatSort) sort: MatSort;

  displayedCols: string[] = ['name', 'balance', 'saleamt', 'netProfit'];

  customerReports: CustomerYearlyReport[];
  yearlyReport: PurchaseSaleYearlyReport;

  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();

  barchartDetail: MyBarChart = new MyBarChart();
  dataset: MyChartDataSet = new MyChartDataSet();

  pYearly$: Observable<any> = this.report();

  // Constructor
  constructor(private http: HttpClient,  private store: Store<AppState>,
    public dialog: MatDialog) {}

  // Init
  ngOnInit() {
    this.report().subscribe(data => {
      this.yearlyReport = data.report;
      this.customerReports = data.report.customerReports;
      this.dataSource = new MatTableDataSource<any>(this.customerReports);
      this.dataSource.sort = this.sort;

      this.barchartDetail = this.yearlyReport.allCustomersBar;
    });
  }

  // Show Modal Window
  showMonthly(report: CustomerYearlyReport) {
     // Dialog config
     const dialogConfig = new MatDialogConfig();
     dialogConfig.id = 'customer-Detail-Report';
     dialogConfig.disableClose = true;
     dialogConfig.width = '1000px';
     dialogConfig.data = report;
 
     // Open Dialog
     const dialogRef = this.dialog.open(
       CustomerMonthlyModalComponent,
       dialogConfig
     );

  }

  // :todo move to service
  report(): Observable<any> {
    const url = '/assets/test_data/yearlyReportResponse.json';
    return this.http.get(url);
  }
}
