import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { RelatedPurchaseOrdersComponent } from './related-purchase-orders.component';

describe('RelatedPurchaseOrdersComponent', () => {
  let component: RelatedPurchaseOrdersComponent;
  let fixture: ComponentFixture<RelatedPurchaseOrdersComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [RelatedPurchaseOrdersComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatedPurchaseOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
