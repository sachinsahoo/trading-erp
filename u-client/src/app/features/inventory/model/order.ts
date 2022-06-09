import { Contact } from './contact';
import { OrderItem as OrderItem } from './orderitem';
import { Invoice } from './invoice';

export class Order {
  id: number;
  order_type: string;
  refno: string;
  total: number;
  taxes: number;

  supplierid: number;
  supplierName: string;
  customerid: number;
  customerName: string;
  agentid: number;
  agentName: string;

  status: string; // Draft / rfq / rfqsent/ PO

  created: number;
  confirm_date: number;
  complete_date: number

  orderItems: Array<OrderItem> = new Array<OrderItem>();

  supplier: Contact;
  customer: Contact;
  agent: Contact;

  invoices: Invoice[];

  balance: string;
}
