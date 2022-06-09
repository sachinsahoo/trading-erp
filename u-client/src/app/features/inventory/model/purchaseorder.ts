import { Contact } from './contact';
import { Company } from './company';
import { POProduct } from './poproduct';
import { Invoice } from './invoice';

export class PurchaseOrder {
  id: number;
  mycompanyid: number;
  type: string;

  supplierid: number;
  supplierName: string;
  customerid: number;
  customerName: string;

  referenceno: string;
  linkedPOReferenceno: string;
  orderdate: number;
  confirmdate: number;

  status: string; // Draft / rfq / rfqsent/ PO

  taxes: string; // Ex GST@2%
  taxAmount: number;
  totalAmount: number;
  totalcommpay: number;
  profit: number;
  balance: number;
  poproductlist: Array<POProduct> = new Array<POProduct>();

  supplier: Company;
  customer: Company;

  invoices: Array<Invoice>;

  rupTaxAmount: string;
  rupTotalAmount: string;
  rupTotalCommPay: string;
  rupTotalCommRec: string;
  rupProfit: string;
  rupBalance: string;
}
