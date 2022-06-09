
import { MyBarChart } from './barChart';
import { CustomerMonthlyReport } from './customerMonthlyReport';
import { ProductCustomerYearlyReport } from './productCustomerYearlyReport';

export class CustomerYearlyReport {
  cid: number;
  name: string;
  saleamt: number;
  balance: number;
  profit: number;

  rupSaleAmt :string;
  rupBalance: string;
  rupProfit: string;

  barChart: MyBarChart;
  productBarChart: MyBarChart;

  monthlyReport: CustomerMonthlyReport[];
  productReport: ProductCustomerYearlyReport[];

}


