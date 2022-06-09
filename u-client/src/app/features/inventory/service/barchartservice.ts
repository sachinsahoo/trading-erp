import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable()
export class BarChartService {
  chartDetail$: Observable<any>;
  private chartDetailSubject: Subject<any> = new Subject();

  constructor() {
    this.chartDetail$ = this.chartDetailSubject.asObservable();
  }

  public updateData(detail) {
    this.chartDetailSubject.next(detail);
  }
}
