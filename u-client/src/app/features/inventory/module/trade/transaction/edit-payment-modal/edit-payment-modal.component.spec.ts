import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EditPaymentModalComponent } from './edit-payment-modal.component';

describe('EditPaymentModalComponent', () => {
  let component: EditPaymentModalComponent;
  let fixture: ComponentFixture<EditPaymentModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EditPaymentModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditPaymentModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
