import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProfitLossComponent } from './profit-loss.component';

describe('ProfitLossComponent', () => {
  let component: ProfitLossComponent;
  let fixture: ComponentFixture<ProfitLossComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ProfitLossComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfitLossComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
