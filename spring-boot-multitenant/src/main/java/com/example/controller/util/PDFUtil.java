//package com.example.controller.util;
//
//import com.example.common.util.InventoryUtil;
//import com.example.common.util.MathUtil;
//import com.example.entity.dto.order.CompanyDto;
//import com.example.entity.dto.order.POPTaxDto;
//import com.example.entity.dto.order.POProductDto;
//import com.example.entity.dto.order.PurchaseOrderDto;
//import com.itextpdf.layout.property.TextAlignment;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.PdfDocument;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import java.io.FileOutputStream;
//
//public class PDFUtil  extends BasePDF{
//
//
//    public PDFUtil(PurchaseOrderDto order) {
//        super(order);
//    }
//
//    public void getPOPDF2() throws Exception {
//    }
//
//    public void getPOPDF1() throws Exception {
//
//        Document document = new Document();
//
//        PdfDocument document1 = new PdfDocument();
//
//        // Main Table
//        PdfPTable mainTable = new PdfPTable(2);
//        mainTable.getDefaultCell().setPadding(0);
//
//        // Party and Voucher Info
//        mainTable.setWidthPercentage(100);
//        mainTable.addCell(getPartyTable());
//        mainTable.addCell(getOrderVoucherInfoTable());
//
//        // Product Items
//        PdfPCell poProdCell = new PdfPCell();
//        poProdCell.setColspan(2);
//        poProdCell.setPadding(0);
//        poProdCell.addElement(getPOProductTable());
//        mainTable.addCell(poProdCell);
//
//        // Signatory
//        PdfPCell footTableCell = new PdfPCell();
//        footTableCell.setColspan(2);
//        footTableCell.setPadding(0);
//        footTableCell.addElement(getOrderFooterTable());
//        mainTable.addCell(footTableCell);
//
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/Users/leahgoldstein/temp/AddTableExample.pdf"));
//        document.open();
//
//        Paragraph title = new Paragraph("PURCHASE ORDER", compFont);
//        title.setAlignment(Element.ALIGN_CENTER);
//        title.setSpacingAfter(3);
//
//
//        document.add(title);
//        document.add(mainTable);
//
//
//        document.close();
//        writer.close();
//
//
//
//
//
//    }
//
//
//
//
//    public void getPOPDF(PurchaseOrderDto po) {
//        Document document = new Document();
//        try {
//
//            //PO  Values
//            CompanyDto customer = po.getCustomer() != null ? po.getCustomer() : new CompanyDto();
//            CompanyDto supplier = po.getSupplier() != null ? po.getSupplier() : new CompanyDto();
//
//            String orderDate = po.getOrderdate().toString();
//            String ref_no = po.getReferenceno();
//
//            String custName = po.getCustomerName();
//            String cgstin = customer.getGstin1();
//            String phone = customer.getPhone();
//            String c_email = customer.getEmail();
//            String c_pan = customer.getPan();
//
//            String c_i_add1 = customer.getInvoiceContact().getAddress1();
//            String c_i_add2 = customer.getInvoiceContact().getAddress2();
//            String c_i_state = "State :" + customer.getInvoiceContact().getState() + ", PIN :" +
//                    customer.getInvoiceContact().getPincode();
//
//            String c_d_add1 = customer.getDispatchContact().getAddress1();
//            String c_d_add2 = customer.getDispatchContact().getAddress2();
//            String c_d_state = "State :" + customer.getDispatchContact().getState() + ", PIN: " +
//                    customer.getDispatchContact().getPincode();
//
//            String sup_name = po.getSupplierName();
//            String s_gstin = supplier.getGstin1();
//            String s_i_add1 = supplier.getInvoiceContact().getAddress1();
//            String s_i_state = "State: " + supplier.getInvoiceContact().getState() + ", PIN- " +
//                    supplier.getInvoiceContact().getPincode();
//
//
//            // Fonts
//
//
//
//            // Parties ---------------------------------------------------------
//
//            // Invoice TO Cell
//            PdfPCell invToCell = getBorderCell();
//            invToCell.addElement(getParaTopAlign("Invoice To", labelFont));
//            invToCell.addElement(getParaWithMargin(custName, compFont, 2f, 0f));
//            invToCell.addElement(new Paragraph(c_i_add1, textFont));
//            invToCell.addElement(new Paragraph(c_i_add2, textFont));
//            invToCell.addElement(new Paragraph(cgstin, textFont));
//            invToCell.addElement(new Paragraph(c_i_state, textFont));
//            invToCell.addElement(new Paragraph(c_email, textFont));
//
//            // Dispatch TO Cell
//            PdfPCell disToCell = getBorderCell();
//            disToCell.addElement(getParaTopAlign("Dispatch To", labelFont));
//            disToCell.addElement(getParaWithMargin(custName, compFont, 2f, 0f));
//            disToCell.addElement(new Paragraph(c_d_add1, textFont));
//            disToCell.addElement(new Paragraph(c_d_add2, textFont));
//            disToCell.addElement(new Paragraph(cgstin, textFont));
//            disToCell.addElement(new Paragraph(c_i_state, textFont));
//            disToCell.addElement(new Paragraph(c_email, textFont));
//
//            // Supplier Cell
//            PdfPCell supCell = getBorderCell();
//            supCell.addElement(getParaTopAlign("Supplier", labelFont));
//            supCell.addElement(getParaWithMargin(sup_name, compFont, 2f, 0f));
//            supCell.addElement(new Paragraph(s_i_add1, textFont));
//            supCell.addElement(new Paragraph(s_gstin, textFont));
//            supCell.addElement(new Paragraph(s_i_state, textFont));
//
//
//            PdfPTable partiesTable = new PdfPTable(1);
//            partiesTable.setWidthPercentage(50);
//            partiesTable.addCell(invToCell);
//            partiesTable.addCell(disToCell);
//            partiesTable.addCell(supCell);
//
//            // Info -------------------------------------------------------
//
//            PdfPCell orderRefCell = new PdfPCell();
//            disToCell.setBorderColor(BaseColor.BLACK);
//            orderRefCell.addElement(new Paragraph("Order No", labelFont));
//            orderRefCell.addElement(new Paragraph(ref_no, compFont));
//
//            PdfPCell dateCell = new PdfPCell();
//            dateCell.setBorderColor(BaseColor.BLACK);
//            dateCell.addElement(new Paragraph("Dated", labelFont));
//            dateCell.addElement(new Paragraph(orderDate, compFont));
//
//            PdfPCell payModeCell = new PdfPCell();
//            payModeCell.setBorderColor(BaseColor.BLACK);
//            payModeCell.addElement(new Paragraph("Mode/Terms of Payment", labelFont));
//            payModeCell.addElement(new Paragraph("Advance", compFont));
//
//            PdfPCell disThroughCell = new PdfPCell();
//            disThroughCell.setBorderColor(BaseColor.BLACK);
//            disThroughCell.addElement(new Paragraph("Dispatch Through", labelFont));
//            disThroughCell.addElement(new Paragraph("By Road", compFont));
//
//            PdfPCell deliveryCell = new PdfPCell();
//            deliveryCell.setColspan(2);
//            deliveryCell.setBorderColor(BaseColor.BLACK);
//            deliveryCell.addElement(new Paragraph("Terms of Delivery", labelFont));
//            deliveryCell.addElement(new Paragraph("Immediate", compFont));
//
//
//            PdfPTable infoTable = new PdfPTable(2);
//            infoTable.setWidthPercentage(50);
//            infoTable.addCell(orderRefCell);
//            infoTable.addCell(dateCell);
//            infoTable.addCell(payModeCell);
//            infoTable.addCell(disThroughCell);
//            infoTable.addCell(deliveryCell);
//
//            // PO Products -----------------------------------------------------------------
//
//            PdfPTable poProductTable = new PdfPTable(5);
//            poProductTable.setWidthPercentage(100);
//            poProductTable.setWidths(new int[]{7, 43, 20, 10, 20});
//
//
//            // Headers
//
//            PdfPCell headerCell1 = getBorderCell();
//            headerCell1.addElement(new Paragraph("SL No", labelFont));
//            PdfPCell headerCell2 = getBorderCell();
//            headerCell2.addElement(new Paragraph("Description of Goods", labelFont));
//            PdfPCell headerCell3 = getBorderCell();
//            headerCell3.addElement(new Paragraph("Quantity", labelFont));
//            PdfPCell headerCell4 = getBorderCell();
//            headerCell4.addElement(new Paragraph("Rate", labelFont));
//            PdfPCell headerCell5 =getBorderCell();
//            headerCell5.addElement(new Paragraph("Amount", labelFont));
//
//
//            poProductTable.addCell(headerCell1);
//            poProductTable.addCell(headerCell2);
//            poProductTable.addCell(headerCell3);
//            poProductTable.addCell(headerCell4);
//            poProductTable.addCell(headerCell5);
//
//            Integer index = 1;
//            for (POProductDto pop : po.getPoproductlist()) {
//                String slno = index.toString();
//                String prod = pop.getProductName();
//                String prodDesc = pop.getProduct() != null ? pop.getProduct().getDescription() : "";
//                String quantity = pop.getQuantity().toString() + " Units";
//                String rate = MathUtil.rupeesFormat(pop.getPrice()) + "/-";
//                String amount = MathUtil.rupeesFormat(pop.getPrice().multiply(pop.getQuantity()));
//
//                // Product Cell
//                PdfPCell prodCell = getRightBorderCell(prod, textFont);
//                prodCell.addElement(new Paragraph(prodDesc, textFont));
//                prodCell.setPaddingBottom(20);
//
//                // Add Product, Qty, Rate and Amt
//                poProductTable.getDefaultCell().setBorder(0);
//                poProductTable.addCell(getRightBorderCell(slno, labelFont));
//                poProductTable.addCell(prodCell);
//                poProductTable.addCell(getRightBorderCell(quantity, textFont));
//                poProductTable.addCell(getRightBorderCell(rate, textFont));
//                poProductTable.addCell(getRightBorderCell(amount, compFont));
//
//                //Add Tax
//                for(POPTaxDto tax : pop.getTaxes()) {
//                    poProductTable.addCell(getRightBorderCell("", labelFont));
//                    poProductTable.addCell(getRightBorderCell(tax.getDesc(), labelFont));
//                    poProductTable.addCell(getRightBorderCell("", textFont));
//                    poProductTable.addCell(getRightBorderCell("", textFont));
//                    poProductTable.addCell(getRightBorderCell(
//                            MathUtil.rupeesFormat(InventoryUtil.calcTaxOnOrder(pop.getPrice(), pop.getQuantity(), tax.getPercent())), compFont));
//
//                }
//
//
//
//
//
//
//                // TODO add rate
//
//                index++;
//            }
//            // Total
//            poProductTable.addCell(new PdfPCell(new Paragraph("")));
//            poProductTable.addCell(new PdfPCell(new Paragraph("Total")));
//            poProductTable.addCell(new PdfPCell(new Paragraph("")));
//            poProductTable.addCell(new PdfPCell(new Paragraph("")));
//            poProductTable.addCell(new PdfPCell(new Paragraph(po.getRupTotalAmount(), compFont)));
//
//            // Footer Table -----------------------------------------------
//
//            PdfPTable footerTable = new PdfPTable(2);
//            footerTable.setWidthPercentage(100);
//
//            PdfPCell amtWordCell = getNoBorderCell();
//            amtWordCell.addElement(new Paragraph("Amount Chargeable (in Words)", labelFont));
//            amtWordCell.addElement(new Paragraph(RupeesInWord.convert(po.getTotalAmount().longValue()), textFont));
//            amtWordCell.setPaddingBottom(50);
//
//            PdfPCell panCell = getNoBorderCell("Company's PAN   " + c_pan, labelFont);
//            panCell.setPaddingBottom(20);
//
//            PdfPCell signCell = getBorderCell();
//            Paragraph cname = new Paragraph("For " + custName, labelFont);
//            cname.setAlignment(Element.ALIGN_CENTER);
//            cname.setSpacingAfter(25);
//
//            Paragraph asign = new Paragraph("Authorised Signatory", labelFont);
//            asign.setAlignment(Element.ALIGN_CENTER);
//            signCell.addElement(cname);
//            signCell.addElement(asign);
//            signCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//
//            PdfPCell blankCell = getNoBorderCell();
//
//            footerTable.addCell(amtWordCell);
//            footerTable.addCell(blankCell);
//            footerTable.addCell(panCell);
//            footerTable.addCell(signCell);
//
//
//
//            // Main Table
//            PdfPTable mainTable = new PdfPTable(2);
//            mainTable.getDefaultCell().setPadding(0);
//
//            mainTable.setWidthPercentage(100);
//            mainTable.addCell(partiesTable);
//            mainTable.addCell(infoTable);
//
//            PdfPCell poProdCell = new PdfPCell();
//            poProdCell.setColspan(2);
//            poProdCell.setPadding(0);
//            poProdCell.addElement(poProductTable);
//            mainTable.addCell(poProdCell);
//
//            PdfPCell footTableCell = new PdfPCell();
//            footTableCell.setColspan(2);
//            footTableCell.setPadding(0);
//            footTableCell.addElement(footerTable);
//            mainTable.addCell(footTableCell);
//
//
//            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/Users/leahgoldstein/temp/AddTableExample.pdf"));
//            document.open();
//
//            Paragraph title = new Paragraph("PURCHASE ORDER", compFont);
//            title.setAlignment(Element.ALIGN_CENTER);
//            title.setSpacingAfter(3);
//
//
//            document.add(title);
//            document.add(mainTable);
//
//
//            document.close();
//            writer.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.printStackTrace();
//        }
//    }
//
//
//}
