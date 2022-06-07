package com.example.controller.manager;

import com.example.common.exception.InputValidationException;
import com.example.common.util.DateUtil;
import com.example.constant.CompanyType;
import com.example.controller.service.CompanyService;
import com.example.controller.service.ProductService;
import com.example.controller.service.ReportService;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.dto.order.ProductDto;
import com.example.entity.dto.report.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportManager extends BaseManager {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductManager productManager;


    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyManager companyManager;

    private List<ProductSupplierMonthlyReportDto> prodSupMonthlyList = new ArrayList<>();
    private List<ProductCustomerMonthlyReportDto> prodCustMonthlyList = new ArrayList<>();
    private List<CompanyDto> allCompanies = new ArrayList<>();
    private List<ProductDto> allProducts = new ArrayList<>();


    public PurchaseSaleYearlyReportDto getYearlyReport(Long start, Long end) throws Exception {

        allCompanies = companyManager.getAllCompanies();
        allProducts = productManager.getAllProducts();

        PurchaseSaleYearlyReportDto psyearlyReport = getPurchaseSaleReport(start, end);

        // Product Supplier Report
        prodSupMonthlyList = reportService.getProductSupplierMonthlyReport(start, end);

        // Product Customer Report
        prodCustMonthlyList = reportService.getProductCustomerMonthlyReport(start, end);

        // Product Report
        psyearlyReport.setProductReports(getProductReports(start, end));
        psyearlyReport.setAllProductsBar(getAllProductBarChart(psyearlyReport.getProductReports()));

        // Customer Report
        psyearlyReport.setCustomerReports(getCustomerReport(start, end));
        psyearlyReport.setAllCustomersBar(getAllCustomerBarChart(psyearlyReport.getCustomerReports()));

        // Supplier Report
        psyearlyReport.setSupplierReports(getSupplierReport(start, end));
        psyearlyReport.setAllSuppliersBar(getAllSupplierBarChart(psyearlyReport.getSupplierReports()));

        return psyearlyReport;

    }

    public PurchaseSaleYearlyReportDto getPurchaseSaleReport(Long start, Long end) throws InputValidationException, Exception {

        PurchaseSaleYearlyReportDto purchaseSaleYearlyReport = new PurchaseSaleYearlyReportDto(start, end);

        // get Monthly purchase sale data
        List<PurchaseSaleMonthlyReportDto> allpsMonthlyReports = reportService.getPurchaseSaleMonthlyReport(start, end);
        purchaseSaleYearlyReport.setMonthlyReport(allpsMonthlyReports);


        // Bar chart for Monthly purchase / sale
        BarChart barChart = getPuchaseSaleBarChart("", allpsMonthlyReports, start, end);
        purchaseSaleYearlyReport.setBarChart(barChart);


        // Total Purchase amount  -----------
        List<BigDecimal> purchaseAmounts = allpsMonthlyReports.stream().map(pmr -> pmr.getPurchaseamt()).collect(Collectors.toList());
        BigDecimal totPurchaseAmount = purchaseAmounts.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        purchaseSaleYearlyReport.setPurchaseamt(totPurchaseAmount);

        // Total Sales amount   -------------
        List<BigDecimal> saleAmountList = allpsMonthlyReports.stream().map(pmr -> pmr.getSaleamt()).collect(Collectors.toList());
        BigDecimal totSaleAmount = saleAmountList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        purchaseSaleYearlyReport.setSaleamt(totSaleAmount);

        // Total profit amount   -------------
        List<BigDecimal> profitAmountList = allpsMonthlyReports.stream().map(pmr -> pmr.getProfit()).collect(Collectors.toList());
        BigDecimal totProfitAmount = profitAmountList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        purchaseSaleYearlyReport.setProfit(totProfitAmount);

        return purchaseSaleYearlyReport;

    }


    public List<ProductYearlyReportDto> getProductReports(Long start, Long end) throws InputValidationException, Exception {

        List<ProductYearlyReportDto> productYearlyReports = new ArrayList<>();

        List<ProductMonthlyReportDto> allProductMonthlyReportDtos = reportService.getProductReport(start, end);
        List<ProductDto> productList = productManager.getAllProducts();

        for (ProductDto productDto : productList) {

            ProductYearlyReportDto productYearlyReport = new ProductYearlyReportDto(start, end);

            // Filter by product -id  ----------
            List<ProductMonthlyReportDto> prod1MonthlyRep = allProductMonthlyReportDtos.stream().
                    filter(p -> p.getProdid().equals(productDto.getId())).collect(Collectors.toList());
            productYearlyReport.setMonthlyReport(prod1MonthlyRep);

            // Skip if no data
            if (prod1MonthlyRep == null || prod1MonthlyRep.isEmpty()) {
                continue;
            }


            productYearlyReport.setProdid(productDto.getId());
            productYearlyReport.setProdname(productDto.getName());
            productYearlyReport.setStockQty(productDto.getQuantity());

            // Product 1 Customers Report & Bar chart
            productYearlyReport.setCustomerReport(getProductCustomerYearlyReport(start, end, productYearlyReport));
            productYearlyReport.setCustBarchart(getProdCustomerBarChart(productYearlyReport.getCustomerReport()));

            // Product 1 Suppliers Report & Bar chart
            productYearlyReport.setSupplierReport(getProductSupplierYearlyReport(start, end, productYearlyReport));
            productYearlyReport.setSupBarchart(getProdSupplierBarChart(productYearlyReport.getSupplierReport()));

            // Get Bar chart by Month for product -id ------
            Collections.sort(prod1MonthlyRep);
            BarChart barChart = getProductBarChart(productDto.getName(), prod1MonthlyRep, start, end);
            productYearlyReport.setBarChart(barChart);

            // Total Purchase amount  -----------
            List<BigDecimal> purchaseAmounts = prod1MonthlyRep.stream().map(pmr -> pmr.getPurchaseamt()).collect(Collectors.toList());
            BigDecimal totPurchaseAmount = purchaseAmounts.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            // Total Purchase Qty
            List<BigDecimal> purchaseQtys = prod1MonthlyRep.stream().map(pmr -> pmr.getPurchaseqty()).collect(Collectors.toList());
            BigDecimal totPurchaseQty = purchaseQtys.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            // Average Purchase Cost
            BigDecimal avgPurchaseCost = totPurchaseAmount.divide(totPurchaseQty, 3, RoundingMode.CEILING);
            // populate purchase
            productYearlyReport.setPurchaseqty(totPurchaseQty);
            productYearlyReport.setPurchaseamt(totPurchaseAmount);
            productYearlyReport.setAvgCostPrice(avgPurchaseCost);

            // Total Sales amount   -------------
            List<BigDecimal> saleAmountList = prod1MonthlyRep.stream().map(pmr -> pmr.getSaleamt()).collect(Collectors.toList());
            BigDecimal totSaleAmount = saleAmountList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            // Sale Qty
            List<BigDecimal> saleQtys = prod1MonthlyRep.stream().map(pmr -> pmr.getSaleqty()).collect(Collectors.toList());
            BigDecimal totSaleQty = saleQtys.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            // Avg sales Cost
            BigDecimal avgSalesCost = totSaleAmount.divide(totSaleQty, 3, RoundingMode.CEILING);
            // populate Sales
            productYearlyReport.setSaleqty(totSaleQty);
            productYearlyReport.setSaleamt(totSaleAmount);
            productYearlyReport.setAvgSalePrice(avgSalesCost);

            // Total profit amount   -------------
            List<BigDecimal> profitAmountList = prod1MonthlyRep.stream().map(pmr -> pmr.getProfit()).collect(Collectors.toList());
            BigDecimal totProfitAmount = profitAmountList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);


            // Net Profit
            productYearlyReport.setProfit(totProfitAmount);

            productYearlyReports.add(productYearlyReport);

        }

        // Create Donut chart sales ratio of product


        return productYearlyReports;


    }

    public List<ProductCustomerYearlyReportDto> getProductCustomerYearlyReport(Long start, Long end, ProductYearlyReportDto productYearlyReport) throws InputValidationException, Exception {

        List<ProductCustomerMonthlyReportDto> prod1CustMReportList = prodCustMonthlyList.stream()
                .filter(pc -> pc.getProdid().equals(productYearlyReport.getProdid())).collect(Collectors.toList());

        List<ProductCustomerYearlyReportDto> prod1CustYReportList = new ArrayList<>();

        List<CompanyDto> customers = allCompanies.stream().filter(c -> c.getType().equals("customer")).collect(Collectors.toList());

        for(CompanyDto customer : customers) {
            ProductCustomerYearlyReportDto prod1cust1YearlyReport = new ProductCustomerYearlyReportDto(start, end);

            List<ProductCustomerMonthlyReportDto> prod1Cust1MReportList = prod1CustMReportList.stream()
                    .filter(pc -> pc.getCid().equals(customer.getId())).collect(Collectors.toList());

            if(prod1Cust1MReportList == null || prod1Cust1MReportList.isEmpty()) {
                continue;
            }

            prod1cust1YearlyReport.setProdid(productYearlyReport.getProdid());
            prod1cust1YearlyReport.setProdname(productYearlyReport.getProdname());
            prod1cust1YearlyReport.setCid(customer.getId());
            prod1cust1YearlyReport.setName(customer.getName());

            // Total Sales quantity   -------------
            List<BigDecimal> saleQtyList = prod1Cust1MReportList.stream().map(pmr -> pmr.getSaleqty()).collect(Collectors.toList());
            BigDecimal totSaleQty = saleQtyList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            prod1cust1YearlyReport.setSaleqty(totSaleQty);

            // Total Sales amount   -------------
            List<BigDecimal> saleAmountList = prod1Cust1MReportList.stream().map(pmr -> pmr.getSaleamt()).collect(Collectors.toList());
            BigDecimal totSaleAmount = saleAmountList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            prod1cust1YearlyReport.setSaleamt(totSaleAmount);

            // Total profit amount   -------------
            List<BigDecimal> profitAmountList = prod1Cust1MReportList.stream().map(pmr -> pmr.getSaleamt()).collect(Collectors.toList());
            BigDecimal totProfitAmount = profitAmountList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            prod1cust1YearlyReport.setProfit(totProfitAmount);

            prod1cust1YearlyReport.setAvgSalePrice(totSaleAmount.divide(totSaleQty, 3, RoundingMode.CEILING));



            prod1CustYReportList.add(prod1cust1YearlyReport);

        }

        return prod1CustYReportList;

    }

    public List<ProductSupplierYearlyReportDto> getProductSupplierYearlyReport(Long start, Long end, ProductYearlyReportDto productYearlyReport) throws InputValidationException, Exception {

        List<ProductSupplierMonthlyReportDto> prod1SupMReportList = prodSupMonthlyList.stream()
                .filter(pc -> pc.getProdid().equals(productYearlyReport.getProdid())).collect(Collectors.toList());

        List<ProductSupplierYearlyReportDto> prod1SupYReportList = new ArrayList<>();

        List<CompanyDto> supplierList = allCompanies.stream().filter(c -> c.getType().equals("supplier")).collect(Collectors.toList());

        for(CompanyDto supplier : supplierList) {
            ProductSupplierYearlyReportDto prod1Sup1YearlyReport = new ProductSupplierYearlyReportDto(start, end);

            List<ProductSupplierMonthlyReportDto> prod1Sup1MReportList = prod1SupMReportList.stream()
                    .filter(pc -> pc.getCid().equals(supplier.getId())).collect(Collectors.toList());

            if(prod1Sup1MReportList == null || prod1Sup1MReportList.isEmpty()) {
                continue;
            }

            prod1Sup1YearlyReport.setProdid(productYearlyReport.getProdid());
            prod1Sup1YearlyReport.setProdname(productYearlyReport.getProdname());
            prod1Sup1YearlyReport.setCid(supplier.getId());
            prod1Sup1YearlyReport.setName(supplier.getName());

            // Total Purchase amount  -----------
            List<BigDecimal> purchaseAmounts = prod1Sup1MReportList.stream().map(pmr -> pmr.getPurchaseamt()).collect(Collectors.toList());
            BigDecimal totPurchaseAmount = purchaseAmounts.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            prod1Sup1YearlyReport.setPurchaseamt(totPurchaseAmount);

            // Total Purchase Quantity  -----------
            List<BigDecimal> purchaseQtyList = prod1Sup1MReportList.stream().map(pmr -> pmr.getPurchaseqty()).collect(Collectors.toList());
            BigDecimal totPurchaseQty = purchaseQtyList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            prod1Sup1YearlyReport.setPurchaseqty(totPurchaseQty);

            prod1Sup1YearlyReport.setAvgCostPrice(totPurchaseAmount.divide(totPurchaseQty, 3, RoundingMode.CEILING));

            prod1SupYReportList.add(prod1Sup1YearlyReport);

        }

        return prod1SupYReportList;

    }



    public List<CustomerYearlyReportDto> getCustomerReport(Long start, Long end) throws InputValidationException, Exception {

        List<CustomerYearlyReportDto> custYearlyReportList = new ArrayList<>();

        List<CustomerMonthlyReportDto> allCustMonthlyReportList = reportService.getCustomerMonthlyReport(start, end);
        List<CompanyDto> customerList = companyManager.getAllCompanies().stream().filter(c -> c.getType() != null &&
                CompanyType.CUSTOMER.getType().equalsIgnoreCase(c.getType())).collect(Collectors.toList());


        // Yearly Report for each customer
        for (CompanyDto customer : customerList) {

            CustomerYearlyReportDto custYearlyReport = new CustomerYearlyReportDto(start, end);

            // Filter by customer  -id  ----------
            List<CustomerMonthlyReportDto> cust1MonthlyRepList = allCustMonthlyReportList.stream().
                    filter(c -> c.getCid().equals(customer.getId())).collect(Collectors.toList());

            custYearlyReport.setMonthlyReport(cust1MonthlyRepList);
            // Skip if no data
            if (cust1MonthlyRepList == null || cust1MonthlyRepList.isEmpty()) {
                continue;
            }

            custYearlyReport.setCid(customer.getId());
            custYearlyReport.setName(customer.getName());
            custYearlyReport.setBalance(customer.getBalance());

            // Customer1 Products Report & Bar Chart
            custYearlyReport.setProductReport(getCustomerProductReport(start, end, custYearlyReport));
            custYearlyReport.setProductBarChart(getCustomerProdBarChart(custYearlyReport.getProductReport()));


            // Get Bar chart by Month for product -id ------
            Collections.sort(cust1MonthlyRepList);
            BarChart barChart = getCustBarChart(customer.getName(), cust1MonthlyRepList, start, end);
            custYearlyReport.setBarChart(barChart);


            // Total Sales amount   -------------
            List<BigDecimal> saleAmountList = cust1MonthlyRepList.stream().map(pmr -> pmr.getSaleamt()).collect(Collectors.toList());
            BigDecimal totSaleAmount = saleAmountList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            custYearlyReport.setSaleamt(totSaleAmount);

            // Total profit amount   -------------
            List<BigDecimal> profitAmountList = cust1MonthlyRepList.stream().map(pmr -> pmr.getProfit()).collect(Collectors.toList());
            BigDecimal totProfitAmount = profitAmountList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            custYearlyReport.setProfit(totProfitAmount);

            custYearlyReportList.add(custYearlyReport);

        }

        return custYearlyReportList;


    }

    public List<ProductCustomerYearlyReportDto> getCustomerProductReport(Long start, Long end, CustomerYearlyReportDto custYearlyReport) throws InputValidationException, Exception {
        List<ProductCustomerMonthlyReportDto> cust1ProdReportList = prodCustMonthlyList.stream()
                .filter(cp -> cp.getCid().equals(custYearlyReport.getCid())).collect(Collectors.toList());

        List<ProductCustomerYearlyReportDto> cust1ProdYReportList = new ArrayList<>();



        for(ProductDto product : allProducts) {
            ProductCustomerYearlyReportDto prod1Cust1YearlyReport = new ProductCustomerYearlyReportDto(start, end);

            List<ProductCustomerMonthlyReportDto> Cust1Prod1MReportList = cust1ProdReportList.stream()
                    .filter(pc -> pc.getProdid().equals(product.getId())).collect(Collectors.toList());

            if(Cust1Prod1MReportList == null || Cust1Prod1MReportList.isEmpty()) {
                continue;
            }

            prod1Cust1YearlyReport.setProdid(product.getId());
            prod1Cust1YearlyReport.setProdname(product.getName());
            prod1Cust1YearlyReport.setCid(custYearlyReport.getCid());
            prod1Cust1YearlyReport.setName(custYearlyReport.getName());


            // Total Sales quantity   -------------
            List<BigDecimal> saleQtyList = Cust1Prod1MReportList.stream().map(pmr -> pmr.getSaleqty()).collect(Collectors.toList());
            BigDecimal totSaleQty = saleQtyList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            prod1Cust1YearlyReport.setSaleqty(totSaleQty);


            // Total Sales amount   -------------
            List<BigDecimal> saleAmountList = Cust1Prod1MReportList.stream().map(pmr -> pmr.getSaleamt()).collect(Collectors.toList());
            BigDecimal totSaleAmount = saleAmountList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            prod1Cust1YearlyReport.setSaleamt(totSaleAmount);

            // Total profit amount   -------------
            List<BigDecimal> profitAmountList = Cust1Prod1MReportList.stream().map(pmr -> pmr.getProfit()).collect(Collectors.toList());
            BigDecimal totProfitAmount = profitAmountList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            prod1Cust1YearlyReport.setProfit(totProfitAmount);

            prod1Cust1YearlyReport.setAvgSalePrice(totSaleAmount.divide(totSaleQty, 3, RoundingMode.CEILING));


            cust1ProdYReportList.add(prod1Cust1YearlyReport);

        }

        return cust1ProdYReportList;

    }




    public List<SupplierYearlyReportDto> getSupplierReport(Long start, Long end) throws Exception {

        List<SupplierYearlyReportDto> supplierYearlyReportList = new ArrayList<>();

        List<SupplierMonthlyReportDto> allSuppliersMonthly = reportService.getSupplierMonthlyReport(start, end);

        List<CompanyDto> supplierList = companyManager.getAllCompanies().stream().filter(c -> c.getType() != null &&
                CompanyType.SUPPLIER.getType().equalsIgnoreCase(c.getType())).collect(Collectors.toList());

        for (CompanyDto supplier : supplierList) {

            SupplierYearlyReportDto supplierYearlyReport = new SupplierYearlyReportDto(start, end);

            // Filter by product -id  ----------
            List<SupplierMonthlyReportDto> supplier1MonthlyReports = allSuppliersMonthly.stream().
                    filter(s -> s.getCid().equals(supplier.getId())).collect(Collectors.toList());
            supplierYearlyReport.setMonthlyReport(supplier1MonthlyReports);

            if (supplier1MonthlyReports == null || supplier1MonthlyReports.isEmpty()) {
                continue;
            }

            supplierYearlyReport.setCid(supplier.getId());
            supplierYearlyReport.setName(supplier.getName());
            supplierYearlyReport.setBalance(supplier.getBalance());

            // Supplier 1 Products report & bar chart
            supplierYearlyReport.setProductReport(getSupplierProductReport(start, end, supplierYearlyReport));
            supplierYearlyReport.setProductBarchart(getSupplierProdBarChart(supplierYearlyReport.getProductReport()));


            // Get Bar chart by Month for supplier -id ------
            Collections.sort(supplier1MonthlyReports);
            BarChart barChart = getSupplierBarChart(supplier.getName(), supplier1MonthlyReports, start, end);
            supplierYearlyReport.setBarChart(barChart);

            // Total Purchase amount  -----------
            List<BigDecimal> purchaseAmounts = supplier1MonthlyReports.stream().map(pmr -> pmr.getPurchaseamt()).collect(Collectors.toList());
            BigDecimal totPurchaseAmount = purchaseAmounts.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            supplierYearlyReport.setPurchaseamt(totPurchaseAmount);

            // todo Net Profit
            supplierYearlyReportList.add(supplierYearlyReport);

        }

        // Create Donut chart sales ratio of product


        return supplierYearlyReportList;


    }


    public List<ProductSupplierYearlyReportDto> getSupplierProductReport(Long start, Long end, SupplierYearlyReportDto supYearlyReport) throws InputValidationException, Exception {
        List<ProductSupplierMonthlyReportDto> sup1ProdReportList = prodSupMonthlyList.stream()
                .filter(cp -> cp.getCid().equals(supYearlyReport.getCid())).collect(Collectors.toList());

        List<ProductSupplierYearlyReportDto> sup1ProdYReportList = new ArrayList<>();

        for(ProductDto product : allProducts) {
            ProductSupplierYearlyReportDto sup1Prod1YearlyReport = new ProductSupplierYearlyReportDto(start, end);

            List<ProductSupplierMonthlyReportDto> sup1Prod1MReportList = sup1ProdReportList.stream()
                    .filter(pc -> pc.getProdid().equals(product.getId())).collect(Collectors.toList());

            if(sup1Prod1MReportList == null || sup1Prod1MReportList.isEmpty()) {
                continue;
            }

            sup1Prod1YearlyReport.setProdid(product.getId());
            sup1Prod1YearlyReport.setProdname(product.getName());
            sup1Prod1YearlyReport.setCid(supYearlyReport.getCid());
            sup1Prod1YearlyReport.setName(supYearlyReport.getName());

            // Total Purchase amount  -----------
            List<BigDecimal> purchaseAmounts = sup1Prod1MReportList.stream().map(pmr -> pmr.getPurchaseamt()).collect(Collectors.toList());
            BigDecimal totPurchaseAmount = purchaseAmounts.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            sup1Prod1YearlyReport.setPurchaseamt(totPurchaseAmount);

            // Total Purchase quantity  -----------
            List<BigDecimal> purchaseQty = sup1Prod1MReportList.stream().map(pmr -> pmr.getPurchaseqty()).collect(Collectors.toList());
            BigDecimal totPurchaseQty = purchaseQty.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            sup1Prod1YearlyReport.setPurchaseqty(totPurchaseQty);

            sup1Prod1YearlyReport.setAvgCostPrice(totPurchaseAmount.divide(totPurchaseQty, 3, RoundingMode.CEILING));

            sup1ProdYReportList.add(sup1Prod1YearlyReport);

        }

        return sup1ProdYReportList;

    }

    public BarChart getPuchaseSaleBarChart(String name, List<PurchaseSaleMonthlyReportDto> psReport, Long start, Long end) {

        BarChart barChart = new BarChart();

        if (psReport == null || psReport.isEmpty()) {
            return null;
        }

        ChartDataSet purchaseDS = new ChartDataSet();
        ChartDataSet saleDS = new ChartDataSet();

        List<String> months = DateUtil.getYearMonths(start, end);
        for (String mon : months) {
            Optional<PurchaseSaleMonthlyReportDto> reportForMonth = psReport.stream().filter(pr -> pr.getYearMonthStr().equalsIgnoreCase(mon)).findFirst();
            if (reportForMonth.isPresent()) {
                purchaseDS.getData().add(reportForMonth.get().getPurchaseamt().longValue());
                saleDS.getData().add(reportForMonth.get().getSaleamt().longValue());
            } else {
                purchaseDS.getData().add(0l);
                saleDS.getData().add(0l);
            }
            barChart.getLabel().add(DateUtil.getLabelfromYM(mon));
        }
        purchaseDS.setLabel("Purchase Amount");
        saleDS.setLabel("Sales Amount");
        barChart.getDataSets().add(purchaseDS);
        barChart.getDataSets().add(saleDS);

        return barChart;
    }


    public BarChart getProductBarChart(String prodName, List<ProductMonthlyReportDto> prodReport, Long start, Long end) {

        BarChart barChart = new BarChart();

        if (prodReport == null || prodReport.isEmpty()) {
            return null;
        }

        ChartDataSet purchaseDS = new ChartDataSet();
        ChartDataSet saleDS = new ChartDataSet();

        List<String> months = DateUtil.getYearMonths(start, end);
        for (String mon : months) {
            Optional<ProductMonthlyReportDto> reportForMonth = prodReport.stream().filter(pr -> pr.getYearMonthStr().equalsIgnoreCase(mon)).findFirst();
            if (reportForMonth.isPresent()) {
                purchaseDS.getData().add(reportForMonth.get().getPurchaseamt().longValue());
                saleDS.getData().add(reportForMonth.get().getSaleamt().longValue());
            } else {
                purchaseDS.getData().add(0l);
                saleDS.getData().add(0l);
            }
            barChart.getLabel().add(DateUtil.getLabelfromYM(mon));
        }
        purchaseDS.setLabel("Purchase Amount");
        saleDS.setLabel("Sales Amount");
        barChart.getDataSets().add(purchaseDS);
        barChart.getDataSets().add(saleDS);

        return barChart;
    }

    public BarChart getCustBarChart(String CustName, List<CustomerMonthlyReportDto> custMReportList, Long start, Long end) {
        BarChart barChart = new BarChart();

        if (custMReportList == null || custMReportList.isEmpty()) {
            return null;
        }

        ChartDataSet saleDS = new ChartDataSet();

        List<String> months = DateUtil.getYearMonths(start, end);
        for (String mon : months) {
            Optional<CustomerMonthlyReportDto> reportForMonth = custMReportList.stream().filter(pr -> pr.getYearMonthStr().equalsIgnoreCase(mon)).findFirst();
            if (reportForMonth.isPresent()) {
                saleDS.getData().add(reportForMonth.get().getSaleamt().longValue());
            } else {
                saleDS.getData().add(0l);
            }
            barChart.getLabel().add(DateUtil.getLabelfromYM(mon));
        }

        saleDS.setLabel(CustName + ":  Sales ");
        barChart.getDataSets().add(saleDS);

        return barChart;
    }

    public BarChart getSupplierBarChart(String supName, List<SupplierMonthlyReportDto> supMReportList, Long start, Long end) {
        BarChart barChart = new BarChart();
        //:todo validate
        if (supMReportList == null || supMReportList.isEmpty()) {
            return null;
        }

        ChartDataSet purchaseDS = new ChartDataSet();

        List<String> months = DateUtil.getYearMonths(start, end);
        for (String mon : months) {
            Optional<SupplierMonthlyReportDto> reportForMonth = supMReportList.stream().filter(sp -> sp.getYearMonthStr().equalsIgnoreCase(mon)).findFirst();
            if (reportForMonth.isPresent()) {
                purchaseDS.getData().add(reportForMonth.get().getPurchaseamt().longValue());
            } else {
                purchaseDS.getData().add(0l);
            }
            barChart.getLabel().add(DateUtil.getLabelfromYM(mon));
        }

        purchaseDS.setLabel(supName + " :  Purchases");
        barChart.getDataSets().add(purchaseDS);

        return barChart;
    }


    public BarChart getAllProductBarChart(List<ProductYearlyReportDto> prodReportList) {
        BarChart barChart = new BarChart();

        if (prodReportList == null || prodReportList.isEmpty()) {
            return null;
        }

        ChartDataSet purchaseDS = new ChartDataSet();
        ChartDataSet saleDS = new ChartDataSet();

        //List<String> months = DateUtil.getYearMonths(start, end);
        for (ProductYearlyReportDto pYearlyReport : prodReportList) {

            purchaseDS.getData().add(pYearlyReport.getPurchaseamt().longValue());
            saleDS.getData().add(pYearlyReport.getSaleamt().longValue());

            barChart.getLabel().add(pYearlyReport.getProdname());
        }
        purchaseDS.setLabel("Purchase Amount");
        saleDS.setLabel("Sales Amount");
        barChart.getDataSets().add(purchaseDS);
        barChart.getDataSets().add(saleDS);

        return barChart;
    }

    public BarChart getAllCustomerBarChart(List<CustomerYearlyReportDto> custReportList) {
        BarChart barChart = new BarChart();

        if (custReportList == null || custReportList.isEmpty()) {
            return null;
        }
        ChartDataSet saleDS = new ChartDataSet();
        //List<String> months = DateUtil.getYearMonths(start, end);
        for (CustomerYearlyReportDto cYearlyReport : custReportList) {
            saleDS.getData().add(cYearlyReport.getSaleamt().longValue());
            barChart.getLabel().add(cYearlyReport.getName());
        }

        saleDS.setLabel("Sales Amount");

        barChart.getDataSets().add(saleDS);

        return barChart;
    }

    public BarChart getAllSupplierBarChart(List<SupplierYearlyReportDto> supReportList) {
        BarChart barChart = new BarChart();

        if (supReportList == null || supReportList.isEmpty()) {
            return null;
        }
        ChartDataSet purchaseDS = new ChartDataSet();
        //List<String> months = DateUtil.getYearMonths(start, end);
        for (SupplierYearlyReportDto cYearlyReport : supReportList) {
            purchaseDS.getData().add(cYearlyReport.getPurchaseamt().longValue());
            barChart.getLabel().add(cYearlyReport.getName());
        }

        purchaseDS.setLabel("Purchase Amount");

        barChart.getDataSets().add(purchaseDS);

        return barChart;
    }

    public BarChart getProdCustomerBarChart(List<ProductCustomerYearlyReportDto> custReportList) {
        BarChart barChart = new BarChart();

        if (custReportList == null || custReportList.isEmpty()) {
            return null;
        }
        ChartDataSet saleDS = new ChartDataSet();
        //List<String> months = DateUtil.getYearMonths(start, end);
        for (ProductCustomerYearlyReportDto cYearlyReport : custReportList) {
            saleDS.getData().add(cYearlyReport.getSaleamt().longValue());
            barChart.getLabel().add(cYearlyReport.getName());
        }

        saleDS.setLabel("Sales Amount");

        barChart.getDataSets().add(saleDS);

        return barChart;
    }

    public BarChart getProdSupplierBarChart(List<ProductSupplierYearlyReportDto> supReportList) {
        BarChart barChart = new BarChart();

        if (supReportList == null || supReportList.isEmpty()) {
            return null;
        }
        ChartDataSet purchaseDS = new ChartDataSet();
        //List<String> months = DateUtil.getYearMonths(start, end);
        for (ProductSupplierYearlyReportDto yearlyReport : supReportList) {
            purchaseDS.getData().add(yearlyReport.getPurchaseamt().longValue());
            barChart.getLabel().add(yearlyReport.getName());
        }

        purchaseDS.setLabel("Purchase Amount");

        barChart.getDataSets().add(purchaseDS);

        return barChart;
    }

    public BarChart getCustomerProdBarChart(List<ProductCustomerYearlyReportDto> prodReportList) {
        BarChart barChart = new BarChart();

        if (prodReportList == null || prodReportList.isEmpty()) {
            return null;
        }
        ChartDataSet saleDS = new ChartDataSet();
        //List<String> months = DateUtil.getYearMonths(start, end);
        for (ProductCustomerYearlyReportDto cYearlyReport : prodReportList) {
            saleDS.getData().add(cYearlyReport.getSaleamt().longValue());
            barChart.getLabel().add(cYearlyReport.getProdname());
        }

        saleDS.setLabel("Sales Amount");

        barChart.getDataSets().add(saleDS);

        return barChart;
    }

    public BarChart getSupplierProdBarChart(List<ProductSupplierYearlyReportDto> supReportList) {
        BarChart barChart = new BarChart();

        if (supReportList == null || supReportList.isEmpty()) {
            return null;
        }
        ChartDataSet purchaseDS = new ChartDataSet();

        for (ProductSupplierYearlyReportDto yearlyReport : supReportList) {
            purchaseDS.getData().add(yearlyReport.getPurchaseamt().longValue());
            barChart.getLabel().add(yearlyReport.getProdname());
        }

        purchaseDS.setLabel("Purchase Amount");

        barChart.getDataSets().add(purchaseDS);

        return barChart;
    }




}
