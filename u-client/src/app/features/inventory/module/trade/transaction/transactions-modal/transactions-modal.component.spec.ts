import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { TransactionsModalComponent } from './transactions-modal.component';

describe('TransactionsModalComponent', () => {
  let component: TransactionsModalComponent;
  let fixture: ComponentFixture<TransactionsModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ TransactionsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
