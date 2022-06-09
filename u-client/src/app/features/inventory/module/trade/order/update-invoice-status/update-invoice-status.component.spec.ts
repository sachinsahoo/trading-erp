import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { UpdateInvoiceStatusComponent } from './update-invoice-status.component';

describe('UpdateInvoiceStatusComponent', () => {
  let component: UpdateInvoiceStatusComponent;
  let fixture: ComponentFixture<UpdateInvoiceStatusComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateInvoiceStatusComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateInvoiceStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
