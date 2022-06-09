import { DonutChart } from './donutChart';
import { CustomerYearlyReport } from './customerYearlyReport';
import { SupplierYearlyReport } from './supplierYearlyReport';

export class ProductMonthlyReport {
  
  month: number;
  year: number;
  date: number;

  prodid: number;
  prodname: number;

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



}

