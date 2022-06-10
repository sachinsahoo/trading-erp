import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'input-shipping',
  templateUrl: './input-shipping.component.html',
  styleUrls: ['./input-shipping.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InputShippingComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
