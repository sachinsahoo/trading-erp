import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AddContactModalComponent } from './add-contact-modal.component';

describe('AddContactModalComponent', () => {
  let component: AddContactModalComponent;
  let fixture: ComponentFixture<AddContactModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AddContactModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddContactModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
