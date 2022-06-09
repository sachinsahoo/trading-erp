import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { YearlyReportComponent } from './yearly-report.component';

describe('YearlyReportComponent', () => {
  let component: YearlyReportComponent;
  let fixture: ComponentFixture<YearlyReportComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ YearlyReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(YearlyReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
