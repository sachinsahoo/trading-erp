import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'view-product',
  templateUrl: './view-product.component.html',
  styleUrls: ['./view-product.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ViewProductComponent implements OnInit {
  constructor() {}

  ngOnInit() {}
}
