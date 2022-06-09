import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EnterTransactionModalComponent } from './enter-transaction-modal.component';

describe('EnterTransactionModalComponent', () => {
  let component: EnterTransactionModalComponent;
  let fixture: ComponentFixture<EnterTransactionModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EnterTransactionModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnterTransactionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
