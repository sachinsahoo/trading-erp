import { Stock } from './stock';
import { Product } from './product';
import { POPTax } from './poptax';

export class POProduct {
  id: number;
  tenantid: number;
  oid: number;
  lpoid: number;

  productId: number;
  productName: string;
  product: Product;

  commpay: number;
  commrec: number;
  price: number;
  costPrice: number;
  quantity: number;

  profit: number;

  taxes: Array<POPTax>;

  //order details
  poref: string;
  orderdate: number;
  confirmdate: number;
  status: string;
  clientId: number;
  clientName: string;
}
