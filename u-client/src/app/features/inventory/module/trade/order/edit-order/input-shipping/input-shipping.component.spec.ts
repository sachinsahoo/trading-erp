import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputShippingComponent } from './input-shipping.component';

describe('InputShippingComponent', () => {
  let component: InputShippingComponent;
  let fixture: ComponentFixture<InputShippingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InputShippingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InputShippingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
