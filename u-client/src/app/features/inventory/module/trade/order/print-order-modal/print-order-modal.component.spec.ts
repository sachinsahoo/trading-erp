import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PrintOrderModalComponent } from './print-order-modal.component';

describe('PrintOrderModalComponent', () => {
  let component: PrintOrderModalComponent;
  let fixture: ComponentFixture<PrintOrderModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PrintOrderModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrintOrderModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
