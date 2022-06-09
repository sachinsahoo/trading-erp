import { Stock } from './stock';
import { Contact } from './contact';

export class Account {
  id: number;
  clientId: number;
  groupId: number;
  group: boolean;
  name: string;
  code: number;
  drcr: number;
  balance: number;
  level: number;
  accountType: number;
  rupBalance: string;

  acctList: Array<Account>;  
}


