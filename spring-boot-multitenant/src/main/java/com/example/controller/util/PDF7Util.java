package com.example.controller.util;

import com.example.entity.dto.order.PurchaseOrderDto;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.ByteArrayOutputStream;


public class PDF7Util extends Base7PDF{


    public PDF7Util(PurchaseOrderDto order) throws Exception{
        super(order);
    }

    public ByteArrayOutputStream getPOPDF2() throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        //PdfDocument pdfDoc = new PdfDocument(new PdfWriter("/Users/leahgoldstein/temp/AddTableExample1.pdf"));
        Document document = new Document(pdfDoc, PageSize.A4);
        PdfFont font = fontRobotoRegular;
        document.setFont(font);

        float[] columnWidths = {1, 1};
        Table mainTable =  getTable(columnWidths).setPadding(0).setMargin(0).setBorder(new SolidBorder(Color.DARK_GRAY, 0.5f));
        mainTable.setWidthPercent(100);

        mainTable.addCell(getPartyTable());
        mainTable.addCell(getOrderVoucherInfoTable());

        // Product Items
        Cell poProdCell = new Cell(1,2).setMargin(0).setPadding(0);

        poProdCell.add(getPOProductTable());
        mainTable.addCell(poProdCell);

        // Signatory
        Cell footTableCell =new Cell(1,2).setMargin(0).setPadding(0);
        footTableCell.add(getOrderFooterTable());
        mainTable.addCell(footTableCell);

        Paragraph title = getPara("PURCHASE ORDER").setTextAlignment(TextAlignment.CENTER);

        document.add(title);
        document.add(mainTable);
        document.close();

        return baos;


    }

}
