import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'input-products',
  templateUrl: './input-products.component.html',
  styleUrls: ['./input-products.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InputProductsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
