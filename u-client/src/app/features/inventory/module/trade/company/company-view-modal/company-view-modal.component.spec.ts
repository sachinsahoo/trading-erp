import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CompanyViewModalComponent } from './company-view-modal.component';

describe('CompanyViewModalComponent', () => {
  let component: CompanyViewModalComponent;
  let fixture: ComponentFixture<CompanyViewModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CompanyViewModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompanyViewModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
