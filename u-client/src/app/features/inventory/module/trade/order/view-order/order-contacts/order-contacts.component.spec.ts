import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { OrderContactsComponent } from './order-contacts.component';

describe('OrderContactsComponent', () => {
  let component: OrderContactsComponent;
  let fixture: ComponentFixture<OrderContactsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [OrderContactsComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderContactsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
