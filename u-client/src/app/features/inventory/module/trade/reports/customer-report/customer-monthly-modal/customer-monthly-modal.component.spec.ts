import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CustomerMonthlyModalComponent } from './customer-monthly-modal.component';

describe('CustomerMonthlyModalComponent', () => {
  let component: CustomerMonthlyModalComponent;
  let fixture: ComponentFixture<CustomerMonthlyModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CustomerMonthlyModalComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerMonthlyModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
