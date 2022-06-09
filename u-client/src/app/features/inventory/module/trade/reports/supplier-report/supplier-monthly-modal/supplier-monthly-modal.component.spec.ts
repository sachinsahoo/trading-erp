import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SupplierMonthlyModalComponent } from './supplier-monthly-modal.component';

describe('SupplierMonthlyModalComponent', () => {
  let component: SupplierMonthlyModalComponent;
  let fixture: ComponentFixture<SupplierMonthlyModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SupplierMonthlyModalComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupplierMonthlyModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
