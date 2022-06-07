//package com.example.controller.util;
//
//import com.example.common.util.InventoryUtil;
//import com.example.common.util.MathUtil;
//import com.example.entity.dto.order.CompanyDto;
//import com.example.entity.dto.order.POPTaxDto;
//import com.example.entity.dto.order.POProductDto;
//import com.example.entity.dto.order.PurchaseOrderDto;
//import com.example.entity.order.PurchaseOrder;
//import com.example.entity.report.OrderPDFData;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import java.io.FileOutputStream;
//
//public class BasePDF {
//
//
//    protected OrderPDFData orderPDFData;
//
//    BaseFont baseFont ;
//
//    protected  Font labelFont = new Font(baseFont, 9);
//    protected  Font smallFont = new Font(baseFont, 7);
//    protected  Font mini = new Font(baseFont, 6);
//    protected  Font micro = new Font(baseFont, 5);
//
//    protected Font compFont = new Font(baseFont, 10, Font.BOLD);
//    protected Font textFont = new Font(baseFont, 10);
//
//    public BasePDF(PurchaseOrderDto order) {
//        orderPDFData = new OrderPDFData(order);
//
//        Font font = FontFactory.getFont("/fonts/Roboto-Regular.ttf",
//                BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, BaseColor.BLACK);
//        BaseFont baseFont = font.getBaseFont();
//
//    }
//
//    public OrderPDFData getOrderPDFData() {
//        return orderPDFData;
//    }
//
//    public void setOrderPDFData(OrderPDFData orderPDFData) {
//        this.orderPDFData = orderPDFData;
//    }
//
//
//    public PdfPTable getPartyTable() {
//        PdfPTable partiesTable = new PdfPTable(1);
//        partiesTable.setWidthPercentage(50);
//        partiesTable.addCell(getInvoiceToCell());
//        partiesTable.addCell(getDispatchToCell());
//        partiesTable.addCell(getSupplierCell());
//        return partiesTable;
//    }
//
//    public PdfPCell getInvoiceToCell() {
//        // Invoice TO Cell
//        PdfPCell invToCell = getBorderCell();
//        invToCell.addElement(getParaTopAlign("Invoice To", labelFont));
//        invToCell.addElement(getParaWithMargin(orderPDFData.getCustName(), compFont, 2f, 0f));
//        invToCell.addElement(new Paragraph(orderPDFData.getC_i_add1(), textFont));
//        invToCell.addElement(new Paragraph(orderPDFData.getC_i_add2(), textFont));
//        invToCell.addElement(new Paragraph(orderPDFData.getCgstin(), textFont));
//        invToCell.addElement(new Paragraph(orderPDFData.getC_i_state(), textFont));
//        invToCell.addElement(new Paragraph(orderPDFData.getC_email(), textFont));
//        return  invToCell;
//    }
//
//    public PdfPCell getDispatchToCell() {
//        // Dispatch TO Cell
//        PdfPCell disToCell = getBorderCell();
//        disToCell.addElement(getParaTopAlign("Dispatch To", labelFont));
//        disToCell.addElement(getParaWithMargin(orderPDFData.getCustName(), compFont, 2f, 0f));
//        disToCell.addElement(new Paragraph(orderPDFData.getC_d_add1(), textFont));
//        disToCell.addElement(new Paragraph(orderPDFData.getC_d_add2(), textFont));
//        disToCell.addElement(new Paragraph(orderPDFData.getCgstin(), textFont));
//        disToCell.addElement(new Paragraph(orderPDFData.getC_i_state(), textFont));
//        disToCell.addElement(new Paragraph(orderPDFData.getC_email(), textFont));
//        return disToCell;
//    }
//
//    public PdfPCell getSupplierCell() {
//        // Supplier Cell
//        PdfPCell supCell = getBorderCell();
//        supCell.addElement(getParaTopAlign("Supplier", labelFont));
//        supCell.addElement(getParaWithMargin(orderPDFData.getSup_name(), compFont, 2f, 0f));
//        supCell.addElement(new Paragraph(orderPDFData.getS_i_add1(), textFont));
//        supCell.addElement(new Paragraph(orderPDFData.getS_gstin(), textFont));
//        supCell.addElement(new Paragraph(orderPDFData.getS_i_state(), textFont));
//        return supCell;
//    }
//
//    public PdfPTable getOrderVoucherInfoTable() {
//        // Info -------------------------------------------------------
//
//        PdfPCell orderRefCell = new PdfPCell();
//        orderRefCell.setBorderColor(BaseColor.BLACK);
//        orderRefCell.addElement(new Paragraph("Order No", labelFont));
//        orderRefCell.addElement(new Paragraph(orderPDFData.getRef_no(), compFont));
//
//        PdfPCell dateCell = new PdfPCell();
//        dateCell.setBorderColor(BaseColor.BLACK);
//        dateCell.addElement(new Paragraph("Dated", labelFont));
//        dateCell.addElement(new Paragraph(orderPDFData.getOrderDate(), compFont));
//
//        PdfPCell payModeCell = new PdfPCell();
//        payModeCell.setBorderColor(BaseColor.BLACK);
//        payModeCell.addElement(new Paragraph("Mode/Terms of Payment", labelFont));
//        payModeCell.addElement(new Paragraph("Advance", compFont));
//
//        PdfPCell disThroughCell = new PdfPCell();
//        disThroughCell.setBorderColor(BaseColor.BLACK);
//        disThroughCell.addElement(new Paragraph("Dispatch Through", labelFont));
//        disThroughCell.addElement(new Paragraph("By Road", compFont));
//
//        PdfPCell deliveryCell = new PdfPCell();
//        deliveryCell.setColspan(2);
//        deliveryCell.setBorderColor(BaseColor.BLACK);
//        deliveryCell.addElement(new Paragraph("Terms of Delivery", labelFont));
//        deliveryCell.addElement(new Paragraph("Immediate", compFont));
//
//
//        PdfPTable infoTable = new PdfPTable(2);
//        infoTable.setWidthPercentage(50);
//        infoTable.addCell(orderRefCell);
//        infoTable.addCell(dateCell);
//        infoTable.addCell(payModeCell);
//        infoTable.addCell(disThroughCell);
//        infoTable.addCell(deliveryCell);
//        return infoTable;
//    }
//
//    public PdfPTable getPOProductTable() throws Exception {
//        // PO Products -----------------------------------------------------------------
//
//        PdfPTable poProductTable = new PdfPTable(5);
//        poProductTable.setWidthPercentage(100);
//        poProductTable.setWidths(new int[]{7, 43, 20, 10, 20});
//
//
//        // Headers
//
//        PdfPCell headerCell1 = getBorderCell();
//        headerCell1.addElement(new Paragraph("SL No", labelFont));
//        PdfPCell headerCell2 = getBorderCell();
//        headerCell2.addElement(new Paragraph("Description of Goods", labelFont));
//        PdfPCell headerCell3 = getBorderCell();
//        headerCell3.addElement(new Paragraph("Quantity", labelFont));
//        PdfPCell headerCell4 = getBorderCell();
//        headerCell4.addElement(new Paragraph("Rate", labelFont));
//        PdfPCell headerCell5 =getBorderCell();
//        headerCell5.addElement(new Paragraph("Amount", labelFont));
//
//
//        poProductTable.addCell(headerCell1);
//        poProductTable.addCell(headerCell2);
//        poProductTable.addCell(headerCell3);
//        poProductTable.addCell(headerCell4);
//        poProductTable.addCell(headerCell5);
//
//        Integer index = 1;
//        for (POProductDto pop : orderPDFData.getOrder().getPoproductlist()) {
//            String slno = index.toString();
//            String prod = pop.getProductName();
//            String prodDesc = pop.getProduct() != null ? pop.getProduct().getDescription() : "";
//            String quantity = pop.getQuantity().toString() + " Units";
//            String rate = MathUtil.rupeesFormat(pop.getPrice()) + "/-";
//            String amount = MathUtil.rupeesFormat(pop.getPrice().multiply(pop.getQuantity()));
//
//            // Product Cell
//            PdfPCell prodCell = getRightBorderCell(prod, textFont);
//            prodCell.addElement(new Paragraph(prodDesc, textFont));
//            prodCell.setPaddingBottom(20);
//
//            // Add Product, Qty, Rate and Amt
//            poProductTable.getDefaultCell().setBorder(0);
//            poProductTable.addCell(getRightBorderCell(slno, labelFont));
//            poProductTable.addCell(prodCell);
//            poProductTable.addCell(getRightBorderCell(quantity, textFont));
//            poProductTable.addCell(getRightBorderCell(rate, textFont));
//            poProductTable.addCell(getRightBorderCell(amount, compFont));
//
//            //Add Tax
//            for(POPTaxDto tax : pop.getTaxes()) {
//                poProductTable.addCell(getRightBorderCell("", labelFont));
//                poProductTable.addCell(getRightBorderCell(tax.getDesc(), labelFont));
//                poProductTable.addCell(getRightBorderCell("", textFont));
//                poProductTable.addCell(getRightBorderCell("", textFont));
//                poProductTable.addCell(getRightBorderCell(
//                        MathUtil.rupeesFormat(InventoryUtil.calcTaxOnOrder(pop.getPrice(), pop.getQuantity(), tax.getPercent())), compFont));
//
//            }
//            // TODO add rate
//
//            index++;
//        }
//        // Total
//        poProductTable.addCell(new PdfPCell(new Paragraph("")));
//        poProductTable.addCell(new PdfPCell(new Paragraph("Total")));
//        poProductTable.addCell(new PdfPCell(new Paragraph("")));
//        poProductTable.addCell(new PdfPCell(new Paragraph("")));
//        poProductTable.addCell(new PdfPCell(new Paragraph(orderPDFData.getOrder().getRupTotalAmount(), compFont)));
//        return poProductTable;
//    }
//
//
//    // Footer Table -----------------------------------------------
//    public PdfPTable getOrderFooterTable() throws Exception {
//        PdfPTable footerTable = new PdfPTable(2);
//        footerTable.setWidthPercentage(100);
//
//        PdfPCell amtWordCell = getNoBorderCell();
//        amtWordCell.addElement(new Paragraph("Amount Chargeable (in Words)", labelFont));
//        amtWordCell.addElement(new Paragraph(RupeesInWord.convert(orderPDFData.getOrder().getTotalAmount().longValue()), textFont));
//        amtWordCell.setPaddingBottom(50);
//
//        PdfPCell panCell = getNoBorderCell("Company's PAN   " + orderPDFData.getC_pan(), labelFont);
//        panCell.setPaddingBottom(20);
//
//        PdfPCell signCell = getBorderCell();
//        Paragraph cname = new Paragraph("For " + orderPDFData.getCustName(), labelFont);
//        cname.setAlignment(Element.ALIGN_CENTER);
//        cname.setSpacingAfter(25);
//
//        Paragraph asign = new Paragraph("Authorised Signatory", labelFont);
//        asign.setAlignment(Element.ALIGN_CENTER);
//        signCell.addElement(cname);
//        signCell.addElement(asign);
//        signCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//
//        PdfPCell blankCell = getNoBorderCell();
//
//        footerTable.addCell(amtWordCell);
//        footerTable.addCell(blankCell);
//        footerTable.addCell(panCell);
//        footerTable.addCell(signCell);
//
//        return footerTable;
//    }
//
//
//
//
//
//    public PdfPCell getBorderCell() {
//        PdfPCell borderCell = new PdfPCell();
//        borderCell.setBorderColor(BaseColor.BLACK);
//        borderCell.setBorderWidth(0.5f);
//        borderCell.setPaddingLeft(5);
//        return borderCell;
//    }
//
//    public PdfPCell getBorderCell(String text, Font font) {
//        PdfPCell borderCell= getBorderCell();
//        borderCell.addElement(new Paragraph(text, font));
//
//        return borderCell;
//    }
//
//    public PdfPCell getRightBorderCell(String text, Font font) {
//        PdfPCell borderCell= getBorderCell();
//        borderCell.setBorder(0);
//        borderCell.setBorderWidthRight(0.5f);
//        borderCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//
//        borderCell.addElement(new Paragraph(text, font));
//
//        return borderCell;
//    }
//
//    public PdfPCell getNoBorderCell() {
//        PdfPCell noBorderCell = new PdfPCell();
//        noBorderCell.setBorder(Rectangle.NO_BORDER);
//        return noBorderCell;
//
//    }
//
//    public PdfPCell getNoBorderCell(String text, Font font) {
//        PdfPCell noBorderCell = getNoBorderCell();
//        noBorderCell.addElement(new Paragraph(text, font));
//        return noBorderCell;
//
//    }
//
//    public PdfPCell getNoBorderCellRight(String text, Font font) {
//        PdfPCell noBorderCell = getNoBorderCell();
//        Paragraph para = new Paragraph(text, font);
//        para.setAlignment(Element.ALIGN_RIGHT);
//        noBorderCell.addElement(para);
//        return noBorderCell;
//
//    }
//
//    public Paragraph getParaTopAlign(String text, Font font) {
//        Paragraph para = new Paragraph(text, font);
//        para.setAlignment(Element.ALIGN_TOP);
//        return para;
//    }
//    public Paragraph getParaRightAlign(String text, Font font) {
//        Paragraph para = new Paragraph(text, font);
//        para.setAlignment(Element.ALIGN_RIGHT);
//        return para;
//    }
//
//    public Paragraph getParaWithMargin(String text, Font font, Float spBefore, Float spAfter) {
//        Paragraph para = new Paragraph(text, font);
//        if(spBefore > 0) para.setSpacingBefore(spBefore);
//        if(spAfter > 0) para.setSpacingAfter(spAfter);
//        return para;
//    }
//
//    public void BasePDF() {
//        labelFont.setColor(BaseColor.DARK_GRAY);
//    }
//
//
//
//
//}
