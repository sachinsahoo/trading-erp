import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { UpdatePurchaseStatusComponent } from './update-purchase-status.component';

describe('UpdatePurchaseStatusComponent', () => {
  let component: UpdatePurchaseStatusComponent;
  let fixture: ComponentFixture<UpdatePurchaseStatusComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [UpdatePurchaseStatusComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdatePurchaseStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
