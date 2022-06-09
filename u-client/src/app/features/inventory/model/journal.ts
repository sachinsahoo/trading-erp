import { Stock } from './stock';
import { Contact } from './contact';

export class Journal {
  id: number;
  desc: string;
  transid: number;
  transDate: number;
  drcrtype: number;
  accid: number;
  acname: string;
  accode: number;
  amount: number;
  rupAmount: string;
}
