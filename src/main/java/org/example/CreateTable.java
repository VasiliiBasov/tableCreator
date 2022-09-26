package org.example;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

public class CreateTable {
    private XWPFDocument document;
    private FileOutputStream out;
    private XWPFTableRow tableRow;

    private House house;
    private boolean flag = true;
    private boolean flagSquar = false;
    private boolean flagVolume = false;

    public XWPFTable getTable() {
        return table;
    }

    private static XWPFTable table;

    public void createTable(String path) throws IOException, House.DocumentNotCreated {

        table = document.createTable();
        CTTblWidth widthRepr = table.getCTTbl().getTblPr().addNewTblW();
        widthRepr.setType(STTblWidth.DXA);
        widthRepr.setW(BigInteger.valueOf(9500));
        table.removeTableAlignment();

        XWPFTableRow tableRowOne = table.getRow(0);
        if (flag) {
            fillParagraph(tableRowOne.getCell(0).getParagraphArray(0), "Наименование характеристики");
            fillParagraph(tableRowOne.addNewTableCell().getParagraphArray(0), "Описание");
            tableRowOne.getCell(0).setColor("A9A9A9");
            tableRowOne.getCell(1).setColor("A9A9A9");
            tableRowOne.getCell(0).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            tableRowOne.getCell(1).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            flag = false;
        } else {
            fillParagraph(tableRowOne.getCell(0).getParagraphArray(0), "1 Адрес объекта");
            fillParagraph(tableRowOne.addNewTableCell().getParagraphArray(0), house.getAddress());
            flag = true;
        }

        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        Yaml yaml = new Yaml();
        Map<String, String> data = yaml.load(inputStream);

        Set<String> set = data.keySet();
        set.forEach(s -> {
            tableRow = table.createRow();
            try {
                fillParagraph(tableRow.getCell(0).getParagraphArray(0), s);
            } catch (House.DocumentNotCreated e) {
                throw new RuntimeException(e);
            }
            try {
                fillParagraph(tableRow.getCell(1).getParagraphArray(0), String.valueOf(data.get(s)));
            } catch (House.DocumentNotCreated e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("table written successully");
    }

    public void createStatement(String path) throws IOException, House.DocumentNotCreated {
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
        InputStream inputStream = Files.newInputStream(file.toPath());
        Yaml yaml = new Yaml();
        Map<String, String> data = yaml.load(inputStream);

        Set<String> set = data.keySet();
        final int[] i = {0};
        final int[] j = {0};
        tableRow = table.createRow();
        set.forEach(s -> {
            try {
                fillParagraph(tableRow.getCell(i[0]).getParagraphArray(0), s);
            } catch (House.DocumentNotCreated e) {
                throw new RuntimeException(e);
            }
            i[0]++;
            try {
                fillParagraph(tableRow.getCell(i[0]).getParagraphArray(0), data.get(s));
            } catch (House.DocumentNotCreated e) {
                throw new RuntimeException(e);
            }
            i[0]++;
            if (i[0] == 4 && j[0] < 1) {
                i[0] = 0;
                tableRow = table.createRow();
                j[0]++;
            }
        });

        System.out.println("statement written successfully");
    }

    public CreateTable(XWPFDocument document, String path, House house) throws FileNotFoundException {
        this.document = document;
        this.out = new FileOutputStream(new File(path));
        this.house = house;
    }

    private void fillParagraph(XWPFParagraph paragraph, String text) throws House.DocumentNotCreated {
        paragraph.setSpacingBefore(0);
        paragraph.setSpacingAfter(0);
        StringBuilder stringBuilder = new StringBuilder(text);
        if (text.contains("date")) {
            text = text.replaceFirst("date", house.getDate());
            stringBuilder = new StringBuilder(text);
        }
        if (text.contains("width")) {
            text = text.replaceFirst("width", String.valueOf(house.getWidth()).replaceAll("\\.", ","));
            stringBuilder = new StringBuilder(text);
        }
        if (text.contains("height")) {
            text = text.replaceFirst("height", String.valueOf(house.getHeight()).replaceAll("\\.", ","));
            stringBuilder = new StringBuilder(text);
        }
        if (text.contains("length")) {
            text = text.replaceFirst("length", String.valueOf(house.getLength()).replaceAll("\\.", ","));
            stringBuilder = new StringBuilder(text);
        }
        if (text.contains("appointment")) {
            text = text.replaceFirst("appointment", String.valueOf(house.getAppointment()));
            stringBuilder = new StringBuilder(text);
        }
        if (text.contains("roof")) {
            text = text.replaceFirst("roof", String.valueOf(house.getRoof()));
            stringBuilder = new StringBuilder(text);
        }
        if (text.contains("volume")) {
            text = text.replaceFirst("volume", String.valueOf(new DecimalFormat("#0.00").format(house.getWidth() * house.getLength() * house.getHeight())).replaceAll("\\.", ","));
            stringBuilder = new StringBuilder(text);
            flagVolume = true;
        }
        if (text.contains("square")) {
            text = text.replaceFirst("square", String.valueOf(new DecimalFormat("#0.00").format(house.getLength() * house.getWidth())).replaceAll("\\.", ","));
            stringBuilder = new StringBuilder(text);
            flagSquar = true;
        }

        if (text.contains("def")) {
            text = text.replaceAll("def", "-");
            stringBuilder = new StringBuilder(text);
        }

        if (text.contains("condition")) {
            XWPFRun run = paragraph.createRun();
            run.setFontSize(13);
            run.setFontFamily("Times New Roman");
            String textOfRun = stringBuilder.toString();
            String[] stringsOnNewLines = textOfRun.split("condition");
            if (stringsOnNewLines.length > 0) run.setText(stringsOnNewLines[0]);
            XWPFRun newrun = paragraph.createRun();
            newrun.setFontSize(13);
            newrun.setFontFamily("Times New Roman");
            newrun.setBold(true);
            newrun.setItalic(true);
            newrun.setText(String.valueOf(house.getCondition()));
            return;
        }

        if (text.contains("@")) {
            XWPFRun run = paragraph.createRun();
            run.setFontSize(13);
            run.setFontFamily("Times New Roman");
            run.setText(stringBuilder.toString());
            int i_paragraph = 0;
            while (i_paragraph < paragraph.getRuns().size()) { //BETTER THE WHILE
                XWPFRun xwpfRun = paragraph.getRuns().get(i_paragraph);

                // Split runs by new line character.
                String textOfRun = xwpfRun.getText(0);
                if (textOfRun.contains("@")) {
                    String[] stringsOnNewLines = textOfRun.split("@");

                    for (int i = 0; i < stringsOnNewLines.length; i++) {

                        String textForLine = stringsOnNewLines[i];
                        if (i == stringsOnNewLines.length - 1) {
                            xwpfRun.setText(textForLine, 0);
                        } else {
                            paragraph.insertNewRun(i);
                            XWPFRun newRun = paragraph.getRuns().get(i);
                            CTRPr rPr = newRun.getCTR().isSetRPr() ? newRun.getCTR().getRPr() : newRun.getCTR().addNewRPr();
                            rPr.set(xwpfRun.getCTR().getRPr());
                            newRun.setText(textForLine);
                            newRun.addBreak();//ADD THE NEW LINE
                        }
                    }
                }
                i_paragraph++;
            }
            return;
        }

        XWPFRun run = paragraph.createRun();
        run.setFontSize(13);
        run.setFontFamily("Times New Roman");
        run.setText(stringBuilder.toString());
        if (flagSquar) {
            XWPFRun run1 = paragraph.createRun();
            run.setFontSize(13);
            run.setFontFamily("Times New Roman");
            run1.setSubscript(VerticalAlign.SUPERSCRIPT);
            run1.setText("2");
            flagSquar = false;
        }
        if (flagVolume) {
            XWPFRun run1 = paragraph.createRun();
            run.setFontSize(13);
            run.setFontFamily("Times New Roman");
            run1.setSubscript(VerticalAlign.SUPERSCRIPT);
            run1.setText("3");
            flagVolume = false;
        }
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }


}
