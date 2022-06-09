import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SelectGroupModalComponent } from './select-group-modal.component';

describe('SelectGroupModalComponent', () => {
  let component: SelectGroupModalComponent;
  let fixture: ComponentFixture<SelectGroupModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SelectGroupModalComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectGroupModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
