package org.example;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import java.math.BigInteger;
import java.util.ArrayList;

public class Images {
    private XWPFDocument document;
    private ArrayList<House> houses = new ArrayList<>();
    private static int imageId = 1;

    public Images(XWPFDocument document, ArrayList<House> houses) {
        this.document = document;
        this.houses = houses;
    }

    public void createImages() {
        XWPFTable tableImage = document.createTable();
        CTTblWidth widthRepr = tableImage.getCTTbl().getTblPr().addNewTblW();
        widthRepr.setType(STTblWidth.DXA);
        widthRepr.setW(BigInteger.valueOf(9500));
        tableImage.removeTableAlignment();

        XWPFTableRow tableRowOne = tableImage.getRow(0);
            fillParagraph(tableRowOne.getCell(0).getParagraphArray(0), "");
            fillParagraph(tableRowOne.addNewTableCell().getParagraphArray(0), "");
            tableRowOne.getCell(0).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
            tableRowOne.getCell(1).getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);

        for (int i = 0; i < houses.size(); i=i+2) {
            XWPFTableRow tableRow = tableImage.createRow();
            House s = houses.get(i);
            String text = "Рисунок А." + (houses.indexOf(s)+1) + ". Общий вид сооружения по адресу: " +
                    s.getAddress() + " (ID объекта " + s.getId() + ")";
            fillParagraph(tableRow.getCell(0).getParagraphArray(0), text);
            if (houses.indexOf(s)<houses.size()-1) {
                s = houses.get(i + 1);
                text = "Рисунок А." + (houses.indexOf(s) + 1) + ". Общий вид сооружения по адресу: " +
                        s.getAddress() + " (ID объекта " + s.getId() + ")";
                fillParagraph(tableRow.getCell(1).getParagraphArray(0), text);
            }
            else fillParagraph(tableRow.getCell(1).getParagraphArray(0), "");

            if (houses.indexOf(s)<houses.size()-1) {
                tableRow = tableImage.createRow();
                fillParagraph(tableRow.getCell(0).getParagraphArray(0), "");
                fillParagraph(tableRow.getCell(1).getParagraphArray(0), "");
            }
        }

        System.out.println("images written successully");
    }

    private void fillParagraph(XWPFParagraph paragraph, String text) {
        paragraph.setSpacingBefore(0);
        paragraph.setSpacingAfter(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setFontSize(12);
        run.setFontFamily("Times New Roman");
        run.setText(text);
    }
}
