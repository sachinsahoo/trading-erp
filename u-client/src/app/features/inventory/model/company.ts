import { Stock } from './stock';
import { Contact } from './contact';

export class Company {
  id: number;
  tenantid: number;
  type: string;
  name: string;
  phone: string;
  email: string;
  tan: string;
  pan: string;
  gstin1: string;
  bankname: string;
  accountType: string;
  bankaccno: string;
  ifsccode: string;
  balance: number;
  payable: number;
  receivable: number;
  bank: number;
  invoiceContactId: number;
  dispatchContactId: number;
  contactList: Contact[];
  invoiceContact: Contact;
  dispatchContact: Contact;

  rupPayable: string;
  rupRecievable: string;
  rupBank: string;
  rupBalance: string;


}

