import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ViewPurchaseModalComponent } from './view-purchase-modal.component';

describe('ViewPurchaseModalComponent', () => {
  let component: ViewPurchaseModalComponent;
  let fixture: ComponentFixture<ViewPurchaseModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ViewPurchaseModalComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewPurchaseModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
