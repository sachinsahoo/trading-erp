import { DonutChart } from './donutChart';
import { CustomerYearlyReport } from './customerYearlyReport';
import { SupplierYearlyReport } from './supplierYearlyReport';
import { ProductMonthlyReport } from './productMonthlyReport';
import { MyBarChart } from './barChart';
import { ProductCustomerYearlyReport } from './productCustomerYearlyReport';
import { ProductSupplierYearlyReport } from './productSupplierYearlyReport';

export class ProductYearlyReport {
  start: number;
  end: number;

  prodid: number;
  prodname: string;
  stockQty: number;

  purchaseqty: number;
  purchaseamt: number;
  avgCostPrice: number;

  saleqty: number;
  saleamt: number;
  avgSalePrice: number;

  profit: number;

  rupPurchaseAmt: string;
  rupAvgCostPrice: string;
  rupSaleAmt: string;
  rupAvgSalePrice: string;
  rupProfit: string;


  barChart: MyBarChart;
  custBarchart: MyBarChart;
  supBarchart: MyBarChart;

  monthlyReport: ProductMonthlyReport[];
  customerReport: ProductCustomerYearlyReport[];
  supplierReport : ProductSupplierYearlyReport[];

}