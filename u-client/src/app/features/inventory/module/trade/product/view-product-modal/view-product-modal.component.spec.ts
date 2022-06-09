import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ViewProductModalComponent } from './view-product-modal.component';

describe('ViewProductModalComponent', () => {
  let component: ViewProductModalComponent;
  let fixture: ComponentFixture<ViewProductModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ViewProductModalComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewProductModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
