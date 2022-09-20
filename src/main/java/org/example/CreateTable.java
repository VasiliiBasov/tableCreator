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

    private House house;
    private boolean flag = true;

    public XWPFTable getTable() {
        return table;
    }

    private static XWPFTable table;

    public void createTable(String path) throws IOException {

        table = document.createTable();
        CTTblWidth widthRepr = table.getCTTbl().getTblPr().addNewTblW();
        widthRepr.setType(STTblWidth.DXA);
        widthRepr.setW(BigInteger.valueOf(9500));
        table.removeTableAlignment();

        XWPFTableRow tableRowOne = table.getRow(0);
        if (flag) {
            fillParagraph(tableRowOne.getCell(0).getParagraphArray(0), "Наименование харрактеристики");
            fillParagraph(tableRowOne.addNewTableCell().getParagraphArray(0), "Описание");
            tableRowOne.getCell(0).setColor("A9A9A9");
            tableRowOne.getCell(1).setColor("A9A9A9");
            tableRowOne.getCell(0).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            tableRowOne.getCell(1).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            flag = false;
        }
        else {
            fillParagraph(tableRowOne.getCell(0).getParagraphArray(0), "1 Адрес объекта");
            fillParagraph(tableRowOne.addNewTableCell().getParagraphArray(0), house.getAddress());
            flag = true;
        }

        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        Yaml yaml = new Yaml();
        Map<String, String> data = yaml.load(inputStream);
        System.out.println(data);

        Set<String> set = data.keySet();
        set.forEach(s -> {
            tableRow = table.createRow();
            fillParagraph(tableRow.getCell(0).getParagraphArray(0), s);
            fillParagraph(tableRow.getCell(1).getParagraphArray(0), data.get(s));
        });

        System.out.println("table written successully");
    }

    public void createStatement(String path) throws FileNotFoundException {
        table = document.createTable();
        CTTblWidth widthRepr = table.getCTTbl().getTblPr().addNewTblW();
        widthRepr.setType(STTblWidth.DXA);
        widthRepr.setW(BigInteger.valueOf(9500));
        table.removeTableAlignment();

        XWPFTableRow tableRowOne = table.getRow(0);
            fillParagraph(tableRowOne.getCell(0).getParagraphArray(0), "Элемент");
            fillParagraph(tableRowOne.addNewTableCell().getParagraphArray(0), "Местоположение дефекта или повреждения");
            fillParagraph(tableRowOne.addNewTableCell().getParagraphArray(0), "Описание дефекта или повреждения");
            fillParagraph(tableRowOne.addNewTableCell().getParagraphArray(0), "Рекомендации");
        for (int i = 0; i < 4; i++) {
            tableRowOne.getCell(i).setColor("A9A9A9");
            tableRowOne.getCell(i).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
        }

        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        Yaml yaml = new Yaml();
        Map<String, String> data = yaml.load(inputStream);
        System.out.println(data);

        Set<String> set = data.keySet();
        var ref = new Object() {
            int i = 0;
        };
        set.forEach(s -> {
            tableRow = table.createRow();
            fillParagraph(tableRow.getCell(ref.i).getParagraphArray(0), s);
            ref.i++;
            fillParagraph(tableRow.getCell(ref.i).getParagraphArray(0), data.get(s));
            ref.i++;
            if (ref.i == 4) ref.i = 0;
        });

        System.out.println("statement written successfully");
    }

    public CreateTable(XWPFDocument document, String path, House house) throws FileNotFoundException {
        this.document = document;
        this.out = new FileOutputStream(new File(path));
        this.house = house;
    }

    private void fillParagraph(XWPFParagraph paragraph, String text) {
        paragraph.setSpacingBefore(0);
        paragraph.setSpacingAfter(0);
        StringBuilder stringBuilder = new StringBuilder(text);
        if (text.equals("В плане имеет Прямоугольную форму. Габариты –")) {
            String formattedDouble = new DecimalFormat("#0.00").format(house.getLength()*house.getWidth());
            stringBuilder.append(house.getLength() + " х " + house.getWidth() + "м." + " Общая площадь: " + formattedDouble + "м^2.");

        }
        XWPFRun run = paragraph.createRun();
        run.setFontSize(13);
        run.setFontFamily("Times New Roman");
        run.setText(stringBuilder.toString());
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
