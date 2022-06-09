import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProductMonthlyModalComponent } from './product-monthly-modal.component';

describe('ProductMonthlyModalComponent', () => {
  let component: ProductMonthlyModalComponent;
  let fixture: ComponentFixture<ProductMonthlyModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ProductMonthlyModalComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductMonthlyModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
