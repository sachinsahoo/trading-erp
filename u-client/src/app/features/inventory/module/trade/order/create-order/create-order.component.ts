import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CreateOrderComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
