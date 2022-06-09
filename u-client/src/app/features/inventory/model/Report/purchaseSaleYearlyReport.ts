import { DonutChart } from './donutChart';
import { CustomerYearlyReport } from './customerYearlyReport';
import { SupplierYearlyReport } from './supplierYearlyReport';
import { ProductYearlyReport } from './productYearlyReport';
import { MyBarChart } from './barChart';
import { PurchaseSaleMonthlyReport } from './purchaseSaleMonthlyReport';

export class PurchaseSaleYearlyReport {
  start: number;
  end: number;
  purchaseamt: number;
  saleamt: number;
  profit: number;

  rupPurchaseAmt: string;
  rupSaleAmt: string;
  rupProfit: string;
  rupAvgMonthlyProfit: string;

  
  monthlyReport: PurchaseSaleMonthlyReport[];
  
  barChart: MyBarChart;
  allProductsBar: MyBarChart;
  allSuppliersBar: MyBarChart;
  allCustomersBar: MyBarChart;

  customerReports: CustomerYearlyReport[];
  supplierReports: SupplierYearlyReport[];
  productReports: ProductYearlyReport[];
}
