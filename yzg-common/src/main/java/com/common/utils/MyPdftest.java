package com.common.utils;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.BaseDirection;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.layout.property.TextAlignment;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class MyPdftest {


    private final String path = "D:\\test4.pdf";
    // private final String imagePath = "D:\\430211522960796593.png";
    private final String imagePath = "D:\\17047110317638133.png";

    @Test
    public void pdftest() throws IOException {
        //处理中文问题
        PdfFont helvetica = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
        PdfFont helveticaBold = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
        Image sign = new Image(ImageDataFactory.create(imagePath));
        PdfFont helveticas = PdfFontFactory.createFont(FontConstants.COURIER);
        PdfWriter writer = new PdfWriter(path);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdf);
        Paragraph paragraph = new Paragraph("\n收据").setTextAlignment(TextAlignment.CENTER)
                .setFont(helveticaBold).setFontSize(50);
        Paragraph paragraph1 = new Paragraph()// .setFixedPosition()   设置固定位置
                .add("我们已经收到了您的付款,感谢您使用我们的产品,如果有任何问题请于我们联系.")
                .setTextAlignment(TextAlignment.CENTER).setFont(helvetica).setFontSize(14);
        Paragraph paragraph2 = new Paragraph()
                .add("support@idata3d.com")
                .add("\n\n\n\n")
                .setFirstLineIndent(343)
                .setFont(helveticas)
                .setFontSize(14);

        Paragraph paragraph3 = new Paragraph()
                .add("日期")
                .setMarginLeft(20)
                .add("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t")
                // .setHyphenation()   // 断字符
                .add("2017-08-08")
                .add("\n\n")
                .setBold().setFont(helvetica).setFontSize(14)
                .setUnderline(Color.GRAY,-15,1,-25,
                        1, PdfCanvasConstants.TextRenderingMode.FILL);

        Paragraph paragraph4 = new Paragraph()
                .add("用户")
                .setMarginLeft(20)
                .add("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t")
                // .setHyphenation()   // 断字符
                .add("0123456789")
                .add("\n\n")
                .setBold().setFont(helvetica).setFontSize(14)
                .setUnderline(Color.GRAY,-15,1,-25,
                        1, PdfCanvasConstants.TextRenderingMode.FILL);
        Paragraph paragraph5 = new Paragraph()
                .add("金额")
                .setMarginLeft(20)
                .add("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t")
                // .setHyphenation()   // 断字符
                .add("￥899")
                .add("\n\n")
                .setBold().setFont(helvetica).setFontSize(14)
                .setUnderline(Color.GRAY,-15,1,-25,
                        1, PdfCanvasConstants.TextRenderingMode.FILL);
        Paragraph paragraph6 = new Paragraph()
                .add("付款渠道")
                .setMarginLeft(20)
                .add("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t")
                // .setHyphenation()   // 断字符
                .add("微信支付")
                .add("\n\n")
                .setBold().setFont(helvetica).setFontSize(14)
                .setUnderline(Color.GRAY,-15,1,-25,
                        1, PdfCanvasConstants.TextRenderingMode.FILL);
        Paragraph paragraph7 = new Paragraph()
                .add("订单编号")
                .setMarginLeft(20)
                .add("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t")
                // .setHyphenation()   // 断字符
                .add("1234569870")
                .add("\n\n\n\n\n\n\n")
                .setBold().setFont(helvetica).setFontSize(14)
                .setUnderline(Color.GRAY,-15,1,-25,
                        1, PdfCanvasConstants.TextRenderingMode.FILL);

        Paragraph paragraph8 = new Paragraph()
                .add(sign)
                .add("\t2016-2017 零点有数数据科技股份有限公司")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold().setFont(helvetica).setFontSize(14)
                .setFontColor(Color.GRAY);
        // Table table = new Table(new float[]{4, 4});
        // table.add

        document.add(paragraph);
        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(paragraph4);
        document.add(paragraph5);
        document.add(paragraph6);
        document.add(paragraph7);
        document.add(paragraph8);

        document.close();
    }


    @Test
    public void table() throws IOException
    {
        //处理中文问题
        PdfFont helvetica = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
        PdfFont courier = PdfFontFactory.createFont(FontConstants.COURIER);

        PdfWriter writer = new PdfWriter(path);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdf).setBaseDirection(BaseDirection.RIGHT_TO_LEFT);
        // Paragraph paragraph = new Paragraph("\n收据").setTextAlignment(TextAlignment.CENTER)
        //         .setFont(helvetica).setFontSize(50);
        // Paragraph paragraph1 = new Paragraph()
        //         .add("我们已经收到了您的付款,感谢您使用我们的产品,如果有任何问题请于我们联系.")
        //         .setTextAlignment(TextAlignment.CENTER).setFont(helvetica).setFontSize(14);
        //
        //
        // Paragraph paragraph2 = new Paragraph()
        //         .add("support@idata3d.com")
        //         .add("\n\n\n\n")
        //         .setFirstLineIndent(343)
        //         .setFont(courier)
        //         .setFontSize(14);
        //
        // Table table = new Table(new float[]{1, 1},true).setFixedLayout().setWidth(300);
        // Cell cell = new Cell().add("日期").setFontSize(14).setBold().setFont(helvetica);
        // Cell cell2 = new Cell().add("2017-02-30").setFont(courier);
        // table.addCell(cell).addCell(cell2);
        //
        // document.add(paragraph);
        // document.add(paragraph1);
        // document.add(paragraph2);
        // document.add(table);
        document.add(new Paragraph().add("零点有数"));
        document.close();
    }


    private final String SRC1 = "E:\\pdf\\88th_noms_announcement.pdf";
    private final String SRC2 = "E:\\pdf\\oscars_movies_checklist_2016.pdf";
    private final String DEST = "E:\\pdf\\88th_oscar_the_revenant_nominations_TOC.pdf";

    @Test
    public void  test2() throws IOException
    {
        final Map<String, Integer> TheRevenantNominations = new TreeMap<String, Integer>();
        TheRevenantNominations.put("Performance by an actor in a leading role", 4);
        TheRevenantNominations.put("Performance by an actor in a supporting role", 4);
        TheRevenantNominations.put("Achievement in cinematography", 4);
        TheRevenantNominations.put("Achievement in costume design", 5);
        TheRevenantNominations.put("Achievement in directing", 5);
        TheRevenantNominations.put("Achievement in film editing", 6);
        TheRevenantNominations.put("Achievement in makeup and hairstyling", 7);
        TheRevenantNominations.put("Best motion picture of the year", 8);
        TheRevenantNominations.put("Achievement in production design", 8);
        TheRevenantNominations.put("Achievement in sound editing", 9);
        TheRevenantNominations.put("Achievement in sound mixing", 9);
        TheRevenantNominations.put("Achievement in visual effects", 10);

        File file = new File(DEST);
        file.getParentFile().mkdirs();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        Document document = new Document(pdfDoc);
        document.add(new Paragraph(new Text("The Revenant nominations list"))
                .setTextAlignment(TextAlignment.CENTER));

        PdfDocument firstSourcePdf = new PdfDocument(new PdfReader(SRC1));
        for (Map.Entry<String, Integer> entry : TheRevenantNominations.entrySet()) {
            //Copy page
            PdfPage page  = firstSourcePdf.getPage(entry.getValue()).copyTo(pdfDoc);
            pdfDoc.addPage(page);

            //Overwrite page number
            Text text = new Text(String.format("Page %d", pdfDoc.getNumberOfPages() - 1));
            text.setBackgroundColor(Color.WHITE);
            document.add(new Paragraph(text).setFixedPosition(
                    pdfDoc.getNumberOfPages(), 549, 742, 100));

            //Add destination
            String destinationKey = "p" + (pdfDoc.getNumberOfPages() - 1);
            PdfArray destinationArray = new PdfArray();
            destinationArray.add(page.getPdfObject());
            destinationArray.add(PdfName.XYZ);
            destinationArray.add(new PdfNumber(0));
            destinationArray.add(new PdfNumber(page.getMediaBox().getHeight()));
            destinationArray.add(new PdfNumber(1));
            pdfDoc.addNamedDestination(destinationKey, destinationArray);

            // Add TOC line with bookmark
            Paragraph p = new Paragraph();
            p.addTabStops(new TabStop(540, TabAlignment.RIGHT, new DottedLine()));
            p.add(entry.getKey());
            p.add(new Tab());
            p.add(String.valueOf(pdfDoc.getNumberOfPages() - 1));
            p.setProperty(Property.ACTION, PdfAction.createGoTo(destinationKey));
            document.add(p);
        }
        firstSourcePdf.close();

        //Add the last page
        PdfDocument secondSourcePdf = new PdfDocument(new PdfReader(SRC2));
        PdfPage page  = secondSourcePdf.getPage(1).copyTo(pdfDoc);
        pdfDoc.addPage(page);

        //Add destination
        PdfArray destinationArray = new PdfArray();
        destinationArray.add(page.getPdfObject());
        destinationArray.add(PdfName.XYZ);
        destinationArray.add(new PdfNumber(0));
        destinationArray.add(new PdfNumber(page.getMediaBox().getHeight()));
        destinationArray.add(new PdfNumber(1));
        pdfDoc.addNamedDestination("checklist", destinationArray);

        //Add TOC line with bookmark
        Paragraph p = new Paragraph();
        p.addTabStops(new TabStop(540, TabAlignment.RIGHT, new DottedLine()));
        p.add("Oscars\u00ae 2016 Movie Checklist");
        p.add(new Tab());
        p.add(String.valueOf(pdfDoc.getNumberOfPages() - 1));
        p.setProperty(Property.ACTION, PdfAction.createGoTo("checklist"));
        document.add(p);
        secondSourcePdf.close();

        // close the document
        document.close();
    }

    @Test
    public void test3() throws IOException
    {
        PdfFont helvetica = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);
        Paragraph paragraph = new Paragraph()
                .add(new Text("日期").setFontSize(14).setFont(helvetica).setBold())
                .add(new Text("2017-02-03").setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(20).setFont(helvetica)).setTextAlignment(TextAlignment.CENTER)
                .setUnderline(Color.GRAY,-15,1,-25,
            1, PdfCanvasConstants.TextRenderingMode.FILL);
        document.add(paragraph);
        document.close();
    }
}
