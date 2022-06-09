import { Stock } from './stock';
import { Contact } from './contact';
import { Account } from './account';

export class BalanceSheet {
  assets: Array<Account>;  
  liabilities: Array<Account>;  
  incomes: Array<Account>;  
  expenses: Array<Account>;  
}



