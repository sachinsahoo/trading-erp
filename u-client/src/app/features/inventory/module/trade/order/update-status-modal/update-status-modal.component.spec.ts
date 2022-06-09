import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { UpdateStatusModalComponent } from './update-status-modal.component';

describe('UpdateStatusModalComponent', () => {
  let component: UpdateStatusModalComponent;
  let fixture: ComponentFixture<UpdateStatusModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateStatusModalComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateStatusModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
