package org.example;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;

public class App {
    static XWPFDocument document = new XWPFDocument();
    private static FileOutputStream out;
    private static String path;
    private static int tableId = 0;


    public static void main(String[] args) throws IOException, InvalidFormatException {
        test1();
    }

    public App(String path) throws IOException {
        this.out = new FileOutputStream(new File(path));
        this.path = path;
    }

    private static void test1() throws InvalidFormatException, IOException {
        App app = new App("C:/Users/vasek/OneDrive/Desktop/TableCreator/hello.docx");
        House house1 = new House();


        CreateParagraph paragraph1 = new CreateParagraph(document, app.path);
        paragraph1.createParagraph("Таблица 3." + app.getTableId() + " – Конструктивные данные по результатам обследования сооружения по адресу: " + house1.getAddress() + " (ID объекта " + house1.getId() + ")");

        CreateTable table1 = new CreateTable(document, app.path);
        table1.createTable();

        paragraph1.insertParagraph("Паспорт здания: " + house1.getAddress() + " (ID объекта " + house1.getId() + ")");

        document.write(out);
        out.close();


//            XWPFParagraph p2 = document.createParagraph();
//            //Set color for second paragraph
//            XWPFRun r2 = p2.createRun();
//            r2.setText("I am second paragraph. My Text is Red in color and is embossed");
//            r2.setColor("ff0000");
//            r2.setEmbossed(true);
//
//            XWPFParagraph p3 = document.createParagraph();
//            //Set strike for third paragraph and capitalization
//            XWPFRun r3 = p3.createRun();
//            r3.setStrikeThrough(true);
//            r3.setCapitalized(true);
//            r3.setText("I am third paragraph. My Text is strike through and is capitalized");
//
//            XWPFParagraph p4 = document.createParagraph();
//            p4.setWordWrapped(true);
//            p4.setPageBreak(true);  // new page break
//            p4.setIndentationFirstLine(600);
//
//            XWPFRun r4 = p4.createRun();
//            r4.setFontSize(40);
//            r4.setItalic(true);
//            //r4.setTextPosition(100);
//            r4.setText("Line 1");
//            r4.addBreak();
//            r4.setText("Line 2");
//            r4.addBreak();
//            r4.setText("Line 3");
//
//            XWPFTable table = doc.createTable(2, 6);
//            CTTblWidth widthRepr = table.getCTTbl().getTblPr().addNewTblW();
//            widthRepr.setType(STTblWidth.DXA);
//            widthRepr.setW(BigInteger.valueOf(9500));
//            XWPFParagraph paragraph = doc.createParagraph();
//            fillTable(table);
//            //fillParagraph(paragraph);
////            XWPFTable table = doc.createTable(); //Здесь всё просто, создаем таблицу в документе и работаем с ней.
////            XWPFTableCell cell = table.createRow().createCell();
////            //XWPFCell cell = table.createRow().createCell();//Добавим к таблице ряд, к ряду - ячейку, и используем её.
////            cell.setText("Ghfhvbevyev");
////            cell = table.createRow().createCell();
////            cell.setText("Ghfhvbevyev");
//
//            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
//            System.out.println(extractor.getText());
//        }
//        finally {
//
//        }
//
//            // save it to .docx file
//            try (FileOutputStream out = new FileOutputStream(fileName)) {
//                doc.write(out);
//            }
//    }
//
//    static void fillParagraph(XWPFParagraph paragraph) {
//
//        XWPFRun run = paragraph.createRun();
//        run.setFontSize(14);
//        run.setFontFamily("Times New Roman");
//        run.setText("My text");
//        run.addBreak();
//        run.setText("New line");
//    }
//
//    static void fillTable(XWPFTable table) {
//        XWPFTableRow firstRow = table.getRows().get(0);
//        XWPFTableRow secondRow = table.getRows().get(1);
//        XWPFTableRow thirdRow = table.createRow();
//        fillRow(firstRow);
//        fillRow(secondRow);
//        fillRow(thirdRow);
//    }
//
//    static void fillRow(XWPFTableRow row) {
//        List<XWPFTableCell> cellsList = row.getTableCells();
//        cellsList.forEach(cell -> fillParagraph(cell.addParagraph()));
//    }
    }

    public int getTableId() {
        tableId++;
        return tableId;
    }
}
