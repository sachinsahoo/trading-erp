import { Stock } from './stock';

export class Product {
  id: number;
  tenantid: number;
  stock: Array<Stock>;
  name: string;
  costPrice: number;
  salesPrice: number;
  barcode: number;
  isService: string;
  internalReference: string;
  group: string;
  category: string;
  uom: string;
  hsnCode: string;
  gstApplicability: string;
  cgstRate: number;
  sgstRate: number;
  igstRate: number;
  description: string;
  quantity: number;
  unit: string;
  pendingarrival: number;
  pendingdispatch: number;

  rupCostPrice: string;
  rupSalesPrice: string;

}


