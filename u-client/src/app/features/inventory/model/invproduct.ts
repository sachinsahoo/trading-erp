import { Stock } from './stock';
import { Product } from './product';
import { POProduct } from './poproduct';

export class InvProduct {
  id: number;
  tenantid: number;
  invid: number;
  invref: string;
  popid: number;
  prodid: number;

  quantity: number;
  poproduct: POProduct;

  shipdate: number;
  receivedate: number;

  clientId: number;
  clientName: string;
}
