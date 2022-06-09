import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import { Label, Color } from 'ng2-charts';
import { FormGroup, FormControl } from '@angular/forms';

import * as report_data from 'app/core/test_db/yearlyReportResponse.json';
import { PurchaseSaleYearlyReport } from 'app/features/inventory/model/Report/purchaseSaleYearlyReport';
import { ProductYearlyReport } from 'app/features/inventory/model/Report/productYearlyReport';
import { Observable } from 'rxjs';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { HttpClient } from '@angular/common/http';
import { BarChartService } from 'app/features/inventory/service/barchartservice';
import { DonutService } from 'app/features/inventory/service/donutservice';
import { PurchaseSaleMonthlyReport } from 'app/features/inventory/model/Report/purchaseSaleMonthlyReport';
import { MyBarChart } from 'app/features/inventory/model/Report/barChart';
import { MyChartDataSet } from 'app/features/inventory/model/Report/chartDataSet';
import { report } from 'process';
import { Store } from '@ngrx/store';
import { AppState } from 'app/core/core.state';
import { ProductMonthlyReport } from 'app/features/inventory/model/Report/productMonthlyReport';
import { ProductMonthlyModalComponent } from './product-monthly-modal/product-monthly-modal.component';

@Component({
  selector: 'product-report',
  templateUrl: './product-report.component.html',
  styleUrls: ['./product-report.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductReportComponent implements OnInit {
  displayedCols: string[] = [
    'prodname',
    'stockQty',
    'purchaseqty',
    'saleqty',
    'avgCostPrice',
    'avgSalePrice',
    'purchaseamt',
    'saleamt',
    'netProfit'
  ];

  productReports: ProductYearlyReport[];
  yearlyReport: PurchaseSaleYearlyReport;

  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();

  // Constructor
  constructor(
    private http: HttpClient,
    private barService1: BarChartService,
    private store: Store<AppState>,
    public dialog: MatDialog
  ) {}

  barchartDetail: MyBarChart = new MyBarChart();
  dataset: MyChartDataSet = new MyChartDataSet();

  pYearly$: Observable<any> = this.report();

  // Init
  ngOnInit() {
    this.report().subscribe(data => {
      this.yearlyReport = data.report;
      this.productReports = data.report.productReports;
      this.dataSource = new MatTableDataSource<any>(this.productReports);

      this.barchartDetail = this.yearlyReport.allProductsBar;
    });
  }

  showMonthly(report: ProductYearlyReport) {
    // Dialog config
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'company-transactions';
    dialogConfig.disableClose = true;
    dialogConfig.width = '1000px';
    dialogConfig.data = report;

    // Open Dialog
    const dialogRef = this.dialog.open(
      ProductMonthlyModalComponent,
      dialogConfig
    );
  }

  // :todo move to service
  report(): Observable<any> {
    const url = '/assets/test_data/yearlyReportResponse.json';
    return this.http.get(url);
  }
}
