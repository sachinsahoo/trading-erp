import { Stock } from './stock';
import { Product } from './product';
import { OrderItemTax } from './orderitemtax';

export class OrderItem {
  id: number;
  orderid: number
  productid: number;
  productname: string;
  product: Product;
  unit_price: number;
  comm_pay: number;
  quantity:number;
  tax: number;
  taxes: Array<OrderItemTax>;
  discount: number;

  //order details
  poref: string;
  created: number;
  confirm_date: number;
  complete_date: number;
  status: string;
}
