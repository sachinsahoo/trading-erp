import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EditJournalComponent } from './edit-journal.component';

describe('EditJournalComponent', () => {
  let component: EditJournalComponent;
  let fixture: ComponentFixture<EditJournalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EditJournalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditJournalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
