import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  Input
} from '@angular/core';
import { PurchaseOrder } from 'app/features/inventory/model/purchaseorder';

@Component({
  selector: 'order-contacts',
  templateUrl: './order-contacts.component.html',
  styleUrls: ['./order-contacts.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OrderContactsComponent implements OnInit {
  // Local Vars
  @Input() order: PurchaseOrder;

  constructor() {}

  ngOnInit() {}
}
