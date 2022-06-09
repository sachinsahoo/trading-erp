import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { InvBarChartComponent } from './inv-bar-chart.component';

describe('InvBarChartComponent', () => {
  let component: InvBarChartComponent;
  let fixture: ComponentFixture<InvBarChartComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ InvBarChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvBarChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
