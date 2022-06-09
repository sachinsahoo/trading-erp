import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'related-purchase-orders',
  templateUrl: './related-purchase-orders.component.html',
  styleUrls: ['./related-purchase-orders.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RelatedPurchaseOrdersComponent implements OnInit {
  constructor() {}

  ngOnInit() {}
}
