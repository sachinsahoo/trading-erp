import { InvProduct } from './invproduct';
import { PurchaseOrder } from './purchaseorder';

export class Invoice {
  id: number;
  type: string;
  oid: number;
  referenceno: string;

  productlist: Array<InvProduct>;

  taxAmount: number;
  totalAmount: number;
  balance: number;

  invdate: number;
  shipdate: number;
  receivedate: number;

  transportername: string;
  trackingno: string;
  truckno: string;
  containerno: string;
  status: string; // Draft / rfq / rfqsent/ PO

  taxes: string; // Ex GST@2%

  clientId: number;
  clientName: string;

  rupTaxAmount: string;
  rupTotalAmount: string;
  rupProfit: string;
  rupBalance: string;
}
