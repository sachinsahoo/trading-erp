package com.example.controller.util;

import com.example.common.util.InventoryUtil;
import com.example.common.util.MathUtil;
import com.example.entity.dto.order.POPTaxDto;
import com.example.entity.dto.order.POProductDto;
import com.example.entity.dto.order.PurchaseOrderDto;
import com.example.entity.report.OrderPDFData;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

public class Base7PDF {


    protected OrderPDFData orderPDFData;

    // New Fonts
    protected static PdfFont fontRobotoRegular;
    public static final String REGULAR = "src/main/resources/fonts/Roboto-Regular.ttf";

    public Base7PDF(PurchaseOrderDto order) throws Exception {
        orderPDFData = new OrderPDFData(order);

        // Fonts
        String path = Thread.currentThread().getContextClassLoader().getResource("fonts/Roboto-Regular.ttf").getPath();
        PdfFontFactory.register(path);
        fontRobotoRegular = PdfFontFactory.createFont(path, true);


    }

    public OrderPDFData getOrderPDFData() {
        return orderPDFData;
    }

    public void setOrderPDFData(OrderPDFData orderPDFData) {
        this.orderPDFData = orderPDFData;
    }


    public Cell getPartyTable() {
        float [] colw = {1};
        Table partiesTable = getTable(colw);
        partiesTable.addCell(getInvoiceToCell());
        partiesTable.addCell(getDispatchToCell());
        partiesTable.addCell(getSupplierCell());
        partiesTable.setWidthPercent(100).setMargin(0);
        return new Cell().add(partiesTable).setMargin(0).setPadding(0).setBorderBottom(Border.NO_BORDER);
    }

    public Cell getInvoiceToCell() {
        // Invoice TO Cell
        Cell invToCell = getNormalCell("").setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(Color.DARK_GRAY, 1f));
        invToCell.add(getParaTopAlign("Invoice To"));
        invToCell.add(getParaWithMargin(orderPDFData.getCustName(), 2f, 0f));
        invToCell.add(getPara(orderPDFData.getC_i_add1()));
        invToCell.add(getPara(orderPDFData.getC_i_add2()));
        invToCell.add(getPara(orderPDFData.getCgstin()));
        invToCell.add(getPara(orderPDFData.getC_i_state()));
        invToCell.add(getPara(orderPDFData.getC_email()));
        return invToCell;
    }

    public Cell getDispatchToCell() {
        // Dispatch TO Cell
        Cell disToCell = getNormalCell("").setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(Color.DARK_GRAY, 1f));;
        disToCell.add(getParaTopAlign("Dispatch To"));
        disToCell.add(getParaWithMargin(orderPDFData.getCustName(), 2f, 0f));
        disToCell.add(getPara(orderPDFData.getC_d_add1()));
        disToCell.add(getPara(orderPDFData.getC_d_add2()));
        disToCell.add(getPara(orderPDFData.getCgstin()));
        disToCell.add(getPara(orderPDFData.getC_i_state()));
        disToCell.add(getPara(orderPDFData.getC_email()));
        return disToCell;
    }

    public Cell getSupplierCell() {
        // Supplier Cell
        Cell supCell = getNormalCell("").setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(Color.DARK_GRAY, 1f));;
        supCell.add(getParaTopAlign("Supplier"));
        supCell.add(getParaWithMargin(orderPDFData.getSup_name(), 2f, 0f));
        supCell.add(getPara(orderPDFData.getS_i_add1()));
        supCell.add(getPara(orderPDFData.getS_gstin()));
        supCell.add(getPara(orderPDFData.getS_i_state()));
        return supCell.setBorderBottom(Border.NO_BORDER);
    }

    public Cell getOrderVoucherInfoTable() {
        // Info -------------------------------------------------------

        Cell orderRefCell = getNormalCell("").setBorderTop(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER);
        orderRefCell.add(getPara("Order No").setFontSize(9f));
        orderRefCell.add(getPara(orderPDFData.getRef_no()).setBold());

        Cell dateCell = getNormalCell("").setBorderTop(Border.NO_BORDER).setBorderRight(Border.NO_BORDER);
        dateCell.add(getPara("Dated").setFontSize(9f));
        dateCell.add(getPara(orderPDFData.getOrderDate()).setBold());

        Cell payModeCell = getNormalCell("").setBorderLeft(Border.NO_BORDER);
        payModeCell.add(getPara("Mode/Terms of Payment").setFontSize(9f));
        payModeCell.add(getPara("Advance").setBold());

        Cell disThroughCell = getNormalCell("").setBorderRight(Border.NO_BORDER);
        disThroughCell.add(getPara("Dispatch Through").setFontSize(9f));
        disThroughCell.add(getPara("By Road").setBold());

        Cell deliveryCell = new Cell(1,2).setBorderBottom(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER);
        deliveryCell.add(getPara("Terms of Delivery").setFontSize(9f));
        deliveryCell.add(getPara("Immediate").setBold());


        float[] colwidth = {1,1};
        Table infoTable = getTable(colwidth).setWidthPercent(100).setMargin(0);
        infoTable.addCell(orderRefCell);
        infoTable.addCell(dateCell);
        infoTable.addCell(payModeCell);
        infoTable.addCell(disThroughCell);
        infoTable.addCell(deliveryCell);
        return new Cell().add(infoTable).setMargin(0).setPadding(0).setBorderBottom(Border.NO_BORDER);
    }

    public Table getPOProductTable() throws Exception {
        // PO Products -----------------------------------------------------------------

        float[] colwidths = {7, 43, 16, 14, 20};
        Table poProductTable = getTable(colwidths);


        // Headers

        Cell headerCell1 = getNormalCell("SL No").setFontSize(8f).setTextAlignment(TextAlignment.CENTER);
        Cell headerCell2 = getNormalCell("Description of Goods").setFontSize(8f).setTextAlignment(TextAlignment.CENTER);
        Cell headerCell3 = getNormalCell("Quantity").setFontSize(8f).setTextAlignment(TextAlignment.CENTER);
        Cell headerCell4 = getNormalCell("Rate").setFontSize(8f).setTextAlignment(TextAlignment.CENTER);
        Cell headerCell5 = getNormalCell("Amount").setFontSize(8f).setTextAlignment(TextAlignment.CENTER);

        poProductTable.addCell(headerCell1);
        poProductTable.addCell(headerCell2);
        poProductTable.addCell(headerCell3);
        poProductTable.addCell(headerCell4);
        poProductTable.addCell(headerCell5);

        Integer index = 1;
        for (POProductDto pop : orderPDFData.getOrder().getPoproductlist()) {
            String slno = index.toString();
            String prod = pop.getProductName();
            String prodDesc = pop.getProduct() != null ? pop.getProduct().getDescription() : "";
            String quantity = pop.getQuantity().toString() + pop.getProduct() != null ? pop.getProduct().getUnit() : "";
            String rate = MathUtil.rupeesFormat(pop.getPrice()) + "/-";
            String amount = MathUtil.rupeesFormat(pop.getPrice().multiply(pop.getQuantity()));

            // Product Cell
            Cell prodCell = getRightBorderCell(prod);
            prodCell.add(getPara(prodDesc));

            // Add Product, Qty, Rate and Amt
            poProductTable.setBorder(Border.NO_BORDER);
            poProductTable.addCell(getRightBorderCell(slno).setTextAlignment(TextAlignment.CENTER));
            poProductTable.addCell(prodCell);
            poProductTable.addCell(getRightBorderCell(quantity).setTextAlignment(TextAlignment.CENTER));
            poProductTable.addCell(getRightBorderCell(rate).setTextAlignment(TextAlignment.RIGHT).setPaddingRight(10f));
            poProductTable.addCell(getRightBorderCell(amount).setTextAlignment(TextAlignment.RIGHT).setPaddingRight(10f));

            //Add Tax
            for (POPTaxDto tax : pop.getTaxes()) {
                poProductTable.addCell(getRightBorderCell(""));
                poProductTable.addCell(getRightBorderCell(tax.getDesc()).setFontSize(9f).setTextAlignment(TextAlignment.RIGHT).setItalic());
                poProductTable.addCell(getRightBorderCell(""));
                poProductTable.addCell(getRightBorderCell(""));
                poProductTable.addCell(getRightBorderCell(
                        MathUtil.rupeesFormat(InventoryUtil.calcTaxOnOrder(pop.getPrice(), pop.getQuantity(), tax.getPercent())))
                        .setTextAlignment(TextAlignment.RIGHT).setPaddingRight(10f));
            }
            // Space Row
            poProductTable.addCell(getRightBorderCell("")).setPaddingBottom(20f);
            poProductTable.addCell(getRightBorderCell(""));
            poProductTable.addCell(getRightBorderCell(""));
            poProductTable.addCell(getRightBorderCell(""));
            poProductTable.addCell(getRightBorderCell(""));


            index++;
        }
        // Total
        poProductTable.addCell("");
        poProductTable.addCell(getNormalCell("Total").setTextAlignment(TextAlignment.RIGHT));
        poProductTable.addCell(getNormalCell(""));
        poProductTable.addCell(getNormalCell(""));
        poProductTable.addCell(getNormalCell(orderPDFData.getOrder().getRupTotalAmount()).setBold()
                .setTextAlignment(TextAlignment.RIGHT).setPaddingRight(10f));
        poProductTable.setWidthPercent(100).setMargin(0).setBorder(Border.NO_BORDER);
        return poProductTable;
    }


    // Footer Table -----------------------------------------------
    public Table getOrderFooterTable() throws Exception {
        float[] columnWidths = {1, 1};
        Table footerTable = getTable(columnWidths);


        Cell amtWordCell = getNormalCell("").setBorder(Border.NO_BORDER);
        amtWordCell.add(getPara("Amount Chargeable (in Words)"));
        amtWordCell.add(getPara(RupeesInWord.convert(orderPDFData.getOrder().getTotalAmount().longValue())));
        amtWordCell.setPaddingBottom(50);

        Cell panCell = getNoBorderCell("Company's PAN   " + orderPDFData.getC_pan());
        panCell.setPaddingBottom(20);

        Cell signCell = getNormalCell("");
        Paragraph cname = getPara("For " + orderPDFData.getCustName()).setTextAlignment(TextAlignment.CENTER);
        cname.setMarginBottom(25f);

        Paragraph asign = getPara("Authorised Signatory").setTextAlignment(TextAlignment.CENTER);

        signCell.add(cname);
        signCell.add(asign);
        signCell.setHorizontalAlignment(HorizontalAlignment.CENTER);

        Cell blankCell = getNormalCell("").setBorder(Border.NO_BORDER);

        footerTable.addCell(amtWordCell);
        footerTable.addCell(blankCell);
        footerTable.addCell(panCell);
        footerTable.addCell(signCell);
        footerTable.setWidthPercent(100).setMargin(0);

        return footerTable;
    }


    public Table getTable( float[] columnWidths) {
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        return table;
    }



    public Cell getRightBorderCell(String text) {
        Cell noBorderCell = getNoBorderCell(text).setBorderRight(new SolidBorder(Color.DARK_GRAY, 1f));

        return noBorderCell;
    }



    public Cell getNoBorderCell(String text) {
        Cell noBorderCell = getNormalCell(text).setBorder(Border.NO_BORDER);

        return noBorderCell;

    }

    public Paragraph getParaTopAlign(String text) {
        Paragraph para = new Paragraph(text).setFontSize(8);
        para.setTextAlignment(TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.TOP);
        return para;
    }

    public Paragraph getParaRightAlign(String text, float fontSize) {
        Paragraph para = new Paragraph(text).setFontSize(fontSize);
        para.setTextAlignment(TextAlignment.RIGHT);
        return para;
    }

    public Paragraph getParaWithMargin(String text,  Float spBefore, Float spAfter) {
        Paragraph para = new Paragraph(text).setMarginTop(spBefore).setMarginBottom(spAfter);
        return para;
    }

    public Paragraph getPara(String text) {
        Paragraph para = new Paragraph(text).setFontSize(10);
        return para;
    }




    public static Cell getNormalCell(String string) {


        Paragraph paragraph = new Paragraph(string).setFont(fontRobotoRegular);

        Cell cell = new Cell().add(paragraph);
        cell.setHorizontalAlignment(HorizontalAlignment.LEFT);

        cell.setFontSize(10f);
        return cell;
    }


}
