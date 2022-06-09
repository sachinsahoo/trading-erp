import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Input,
  OnDestroy
} from '@angular/core';
import { MyBarChart } from 'app/features/inventory/model/Report/barChart';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';

import { Label, Color } from 'ng2-charts';
import { BarChartService } from 'app/features/inventory/service/barchartservice';
import { Subscription } from 'rxjs';

@Component({
  selector: 'inv-bar-chart',
  templateUrl: './inv-bar-chart.component.html',
  styleUrls: ['./inv-bar-chart.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InvBarChartComponent implements OnInit, OnDestroy {
  //@Input() barChartDetail;

  @Input() barChartDetail: MyBarChart;
  @Input() color: number;

  barChartLabels: Label[] = [];
  barChartData: ChartDataSets[] = [{ data: [] }];
  private subscriptions: Subscription = new Subscription();

  // Settings
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartPlugins = [];

  barChartOptions: ChartOptions = {
    responsive: true,
    scales: { xAxes: [{}], yAxes: [{}] }
  };

  lineChartColors: Color[] = [
    {
      backgroundColor: '#757575',
      borderColor: '#757575'
    },
    {
      backgroundColor: '#4db6ac',
      borderColor: '#4db6ac'
    }
  ];

  lineChartColors1: Color[] = [
    {
      backgroundColor: '#4db6ac',
      borderColor: '#4db6ac'
    }
  ];

  constructor() {}

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  ngOnInit() {
    if (this.color == 1) {
      this.lineChartColors = this.lineChartColors1;
    }
    this.barChartLabels = this.barChartDetail.label;
    this.barChartData = this.barChartDetail.dataSets;
  }
}
