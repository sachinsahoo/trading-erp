import { Company } from './company';
import { PurchaseOrder } from './purchaseorder';
import { Journal } from './journal';

export class Transaction {
  id: number;
  tenantid: number;
  date: number;
  notes: string;
  custCompanyId: number;
  myCompanyId: number;
  type: string;
  invoiceId: number;
  orderId: number;
  invRef: string;
  amount: number;
  custCompany: Company; // todo remove
  myCompany: Company;
  orderRef: string;
  custCompanyName: string;
  myCompanyName: string;
  rupAmount: string;
  journals: Array<Journal>;
}


