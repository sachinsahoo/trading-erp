import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  ViewChild
} from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';

//import * as report_data from 'app/core/test_db/yearlyReportResponse.json';
import { FormGroup, FormControl } from '@angular/forms';
import { MyBarChart } from 'app/features/inventory/model/Report/barChart';
import { PurchaseSaleYearlyReport } from 'app/features/inventory/model/Report/purchaseSaleYearlyReport';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { BarChartService } from 'app/features/inventory/service/barchartservice';
import { InvBarChartComponent } from '../inv-bar-chart/inv-bar-chart.component';
import { MatTableDataSource } from '@angular/material/table';
import { report } from 'process';

@Component({
  selector: 'yearly-report',
  templateUrl: './yearly-report.component.html',
  styleUrls: ['./yearly-report.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class YearlyReportComponent implements OnInit {
  psYearlyReport: PurchaseSaleYearlyReport;
  displayedCols: string[] = ['month', 'purchase', 'sale', 'profit'];

  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();

  psMonthly$: Observable<any> = this.report();

  psYearlyBarChart: MyBarChart;

  constructor(private http: HttpClient, private barService: BarChartService) {}

  ngOnInit() {

    this.report().subscribe(data => {
      this.psYearlyReport = data.report;
      this.psYearlyBarChart = this.psYearlyReport.barChart;

      //this.barService.updateData(this.psYearlyReport.barChart);
      this.dataSource = new MatTableDataSource<any>(
        this.psYearlyReport.monthlyReport
      );
    });
  }

  // :todo move to service
  report(): Observable<any> {
    const url = '/assets/test_data/yearlyReportResponse.json';
    return this.http.get(url);
  }

  show() {
    //this.barChartDetail = this.psYearlyReport.barChart;
  }
}

export class DateRangePickerFormsExample {
  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl()
  });
}
