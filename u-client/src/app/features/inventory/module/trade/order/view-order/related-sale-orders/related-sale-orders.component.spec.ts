import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { RelatedSaleOrdersComponent } from './related-sale-orders.component';

describe('RelatedSaleOrdersComponent', () => {
  let component: RelatedSaleOrdersComponent;
  let fixture: ComponentFixture<RelatedSaleOrdersComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [RelatedSaleOrdersComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatedSaleOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
