import { DonutChart } from './donutChart';
import { MyBarChart } from './barChart';
import { SupplierMonthlyReport } from './supplierMonthlyReport';
import { ProductSupplierYearlyReport } from './productSupplierYearlyReport';

export class SupplierYearlyReport {
  cid: number;
  name: string;
  purchaseamt: number;
  balance: number;

  rupPurchaseAmt: string;
  rupBalance: string;

  barChart: MyBarChart; 
  productBarchart: MyBarChart;

  monthlyReport: SupplierMonthlyReport[];
  productReport: ProductSupplierYearlyReport[];

}

