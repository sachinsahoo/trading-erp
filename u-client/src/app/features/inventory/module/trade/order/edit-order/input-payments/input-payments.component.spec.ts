import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputPaymentsComponent } from './input-payments.component';

describe('InputPaymentsComponent', () => {
  let component: InputPaymentsComponent;
  let fixture: ComponentFixture<InputPaymentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InputPaymentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InputPaymentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
