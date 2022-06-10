import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputProductsComponent } from './input-products.component';

describe('InputProductsComponent', () => {
  let component: InputProductsComponent;
  let fixture: ComponentFixture<InputProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InputProductsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InputProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
