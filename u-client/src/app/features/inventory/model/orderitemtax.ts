import { Stock } from './stock';
import { Product } from './product';

export class OrderItemTax {
  id: number;
  orderitemid: number;
  taxname: string;
  percent: number;
  amount: number;
}
