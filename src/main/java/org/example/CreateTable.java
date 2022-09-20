package org.example;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CreateTable {
    private XWPFDocument document;
    private FileOutputStream out;
    private XWPFTableRow tableRow;
    private double width;
    private double height;
    private double length;

    public XWPFTable getTable() {
        return table;
    }

    private static XWPFTable table;

    public void createTable() throws IOException {
        //create table
        table = document.createTable();
        CTTblWidth widthRepr = table.getCTTbl().getTblPr().addNewTblW();
            widthRepr.setType(STTblWidth.DXA);
            widthRepr.setW(BigInteger.valueOf(9500));
        table.removeTableAlignment();

        //create first row
        XWPFTableRow tableRowOne = table.getRow(0);
        fillParagraph(tableRowOne.getCell(0).getParagraphArray(0), "Наименование харрактеристики");
        fillParagraph(tableRowOne.addNewTableCell().getParagraphArray(0), "Описание");


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите длину объекта:");
        length = Double.parseDouble(reader.readLine());
        System.out.println("Введите ширину объекта:");
        width = Double.parseDouble(reader.readLine());
        System.out.println("Введите высоту объекта:");
        height = Double.parseDouble(reader.readLine());


        File file = new File("src/main/java/resources/Table.yaml");
        InputStream inputStream = new FileInputStream(file);
        Yaml yaml = new Yaml();
        Map<String, String> data = yaml.load(inputStream);
        System.out.println(data);

        Set<String> set = data.keySet();
        set.forEach(s -> {
            tableRow = table.createRow();
//            tableRow.getCell(0).setColor("ff0000");
//            tableRow.getCell(1).setColor("ff0000");
//            tableRow.getCell(0).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
//            tableRow.getCell(0).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            fillParagraph(tableRow.getCell(0).getParagraphArray(0), s);
            fillParagraph(tableRow.getCell(1).getParagraphArray(0), data.get(s));
        });


        //document.write(out);
        //out.close();
        System.out.println("table written successully");
    }

    public CreateTable(XWPFDocument document, String path) throws FileNotFoundException {
        this.document = document;
        this.out = new FileOutputStream(new File(path));
    }

    private void fillParagraph(XWPFParagraph paragraph, String text) {
        StringBuilder stringBuilder = new StringBuilder(text);
        if (text.equals("В плане имеет Прямоугольную форму. Габариты –")) {
            String formattedDouble = new DecimalFormat("#0.00").format(length*width);
            stringBuilder.append(length + " х " + width + "м." + " Общая площадь: " + formattedDouble + "м^2.");

        }
        XWPFRun run = paragraph.createRun();
        run.setFontSize(13);
        run.setFontFamily("Times New Roman");
        run.setText(stringBuilder.toString());
    }
}
