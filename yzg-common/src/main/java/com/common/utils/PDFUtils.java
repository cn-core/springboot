package com.common.utils;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;


import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 * yangzhiguo on 2017/7/7.
 */
public class PDFUtils {

    public void resetResponseToDownload() {
       /* Document document = new Document();
        try{
            response.setContentType("application/pdf");
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(new Paragraph("howtodoinjava.com"));
            document.add(new Paragraph(new Date().toString()));
            //Add more content here
        }catch(Exception e){
            e.printStackTrace();
        }
        document.close();*/
    }

    /**
     * Chapter 4    cmp_flatten_form   标题
     * Chapter 5    cmp_filled_out_job_application  浅灰文字&标题
     * Chapter 5    cmp_add_content     水印
     * Chapter 5    cmp_change_page 边框
     */

    private final String path = "D:\\test4.pdf";
    private final String image1 = "D:\\20170702200316.png";
    private final String image2 = "D:\\20170702210340.png";
    private Document document;

    /**
     * 字体
     */
    private PdfFont TIMES_ROMAN;            // 罗马新体
    private PdfFont TIMES;                  // 时间
    private PdfFont HELVETICA;              // 黑体
    private PdfFont HELVETICA_BOLD;         // 唱片名
    private PdfFont TIMES_BOLD;             // 时间粗体
    private PdfFont COURIER_BOLDOBLIQUE;    //
    private PdfFont COURIER_OBLIQUE;        // 斜体
    private PdfFont HELVETICA_BOLDOBLIQUE;  //
    private PdfFont notdef;                 //
    private PdfFont SYMBOL;                 // 标志
    private PdfFont TIMES_BOLDITALIC;       //
    private PdfFont ZAPFDINGBATS;           //

    @Before(value = "befroe")
    public void befroe() throws Exception {
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdf = new PdfDocument(pdfWriter);
        document = new Document(pdf);

        // 新罗马体
        TIMES_ROMAN = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        // 粗体
        PdfFont COURIER_BOLD = PdfFontFactory.createFont(FontConstants.COURIER_BOLD);
        // 时间
        TIMES = PdfFontFactory.createFont(FontConstants.TIMES);
        // 黑体
        HELVETICA = PdfFontFactory.createFont(FontConstants.HELVETICA);
        // 唱片名
        HELVETICA_BOLD = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        // 时间粗体
        TIMES_BOLD = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        //
        COURIER_BOLDOBLIQUE = PdfFontFactory.createFont(FontConstants.COURIER_BOLDOBLIQUE);
        // 斜体
        COURIER_OBLIQUE = PdfFontFactory.createFont(FontConstants.COURIER_OBLIQUE);
        //
        HELVETICA_BOLDOBLIQUE = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLDOBLIQUE);
        //
        notdef = PdfFontFactory.createFont(FontConstants.notdef);
        // 标志
        SYMBOL = PdfFontFactory.createFont(FontConstants.SYMBOL);
        //
        TIMES_BOLDITALIC = PdfFontFactory.createFont(FontConstants.TIMES_BOLDITALIC);
        //
        ZAPFDINGBATS = PdfFontFactory.createFont(FontConstants.ZAPFDINGBATS);
    }

    // =====================================<chapter 1>=======================================================
    @Test
    public void test1() throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(path));
        Document doc = new Document(pdfDoc);//构建文档对象
        PdfFont sysFont = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);//中文字体
        /*
        设置自动布局。表布局的模拟：固定CSS属性。
        注意，大表不支持自动布局。
        算法原理。
        1。列宽度不能小于列中任何单元格的最小宽度（按布局计算）。
        2。指定的表宽度比列和单元格宽度的总和具有更高的优先级。
        3.单元格和列宽度的百分比值比点值具有更高的优先级。
        4。单元格宽度比列宽度具有更高的优先级。
        5。如果列没有宽度，它将尝试达到最大值（按布局计算）。 */
        Table table = new Table(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1}).setWidth(UnitValue.createPercentValue(100));//构建表格以100%的宽度
        Cell cell1 = new Cell().add(new Paragraph("表格1")).setFont(sysFont);//向表格添加内容
        Cell cell2 = new Cell().add(new Paragraph("表格2")).setFont(sysFont);
        Cell cell3 = new Cell().add(new Paragraph("表格3")).setFont(sysFont);
        Cell cell4 = new Cell().add(new Paragraph("表格4")).setFont(sysFont);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        doc.add(table.setHorizontalAlignment(HorizontalAlignment.CENTER));//将表格添加入文档并页面居中
        doc.close();
    }

    /**
     * Simple List Example
     *
     * @throws IOException
     */
    @Test
    public void list() throws IOException {
        PdfWriter pdfWriter = new PdfWriter(path);
        // 构建文档对象
        PdfDocument pdf = new PdfDocument(pdfWriter);
        pdf.addNewPage(PageSize.A4.rotate());
        Document document = new Document(pdf);
        // 中文字体
        PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

        document.add(new Paragraph("Hello World!").setFont(font));
        List list = new List().setSymbolIndent(12).setListSymbol("\u2022").setFont(font);

        // 添加ListItem Object
        list.add(new ListItem("Never gonna give you up"))
                .add(new ListItem("Never gonna let you down"))
                .add(new ListItem("Never goona run around and desert you"))
                .add(new ListItem("Never goona make you cry"))
                .add(new ListItem("Never goona say goodbye"))
                .add(new ListItem("Never goona tell a lie and hurt you"));
        document.add(list);
        document.close();
    }

    /**
     * 制作表格
     *
     * @throws IOException
     */
    @Test
    public void table() throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(path);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);

        PdfFont sysFont = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);//中文字体
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        Table table = new Table(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1});
        table.setWidthPercent(100);
        BufferedReader br = new BufferedReader(new FileReader("D:\\testData.csv"));
        String line = br.readLine();
        process(table, line, sysFont, true);
        while ((line = br.readLine()) != null) {
            process(table, line, sysFont, false);
        }
        br.close();
        document.add(table);

        //Close document
        document.close();
    }

    private void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ",");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(token)).setFont(font));
            } else {
                table.addCell(new Cell().add(new Paragraph(token)).setFont(font));
            }
        }
    }

    /**
     * 添加图片
     *
     * @throws Exception
     */
    @Test
    public void image() throws Exception {
        PdfWriter writer = new PdfWriter(path);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        Image fox = new Image(ImageDataFactory.create(image1));
        Image dog = new Image(ImageDataFactory.create(image2));
        Paragraph paragraph = new Paragraph("The quick brown").add(fox)
                .add(" jumps over the lazy ").add(dog);
        document.add(paragraph);
        document.close();
    }

    /**
     * Hello World!
     *
     * @throws Exception
     */
    @Test
    public void helloWorld() throws Exception {
        document.add(new Paragraph("Hello World!"));
        document.close();
    }

    /**
     * 简单的列表的例子
     */
    @Test
    public void list2() throws IOException {
        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        document.add(new Paragraph("iText is").setFirstLineIndent(20).setFont(font));
        List list = new List().setSymbolIndent(200).setListSymbol("2").setFont(font);
        list.add(new ListItem("Never gonna give you up")).add(new ListItem("Never gonna let you down")).add(new ListItem
                ("Never gonna run around and desert you")).add(new ListItem("Never gonna make you cry")).add(new ListItem
                ("Never gonna say goodbye")).add(new ListItem("Never gonna tell a lie and hurt you"));
        document.add(list);
        document.close();
    }

    static PdfFont helvetica = null;
    static PdfFont helveticaBold = null;
    static DecimalFormat df = new DecimalFormat("#0.00");

    /**
     * 到处PDF文件到resposne流
     */
    public static void exportPdf(String fileName, String spreadhead, String subhead, List tables,
                                 HttpServletResponse response) throws IOException {
        // 设置response参数,可以打开下载页面
        response.reset();
        response.setContentType("application/pdf:charset=utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" +
                new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        // 处理中文乱码
        helvetica = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
        helveticaBold = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

        PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());

        PdfDocument pdf = new PdfDocument(pdfWriter);
        Document document = new Document(pdf);
        // 加载错误内容

        document.close();
        pdfWriter.close();
        pdf.close();
    }

    /**
     * 加载标题
     */
    private static void loadTitle(String text,Document document){
        document.add(new Paragraph(text).setFont(helvetica).setBold().setFontSize(12));
    }
}