import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ViewTransactionModalComponent } from './view-transaction-modal.component';

describe('ViewTransactionModalComponent', () => {
  let component: ViewTransactionModalComponent;
  let fixture: ComponentFixture<ViewTransactionModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewTransactionModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewTransactionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
