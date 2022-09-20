package org.example;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;

public class CreateParagraph {
    private XWPFDocument document;
    private FileOutputStream out;

    public void createParagraph(String text) throws IOException {
        // set font
        XWPFParagraph p1 = document.createParagraph();

        // Set Text to Bold and font size to 22 for first paragraph
        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setItalic(true);
        r1.setFontSize(13);
        r1.setText(text);
        r1.setFontFamily("Times New Roman");

        System.out.println("paragraph written successully");
    }

    public void insertParagraph(String text) throws IOException {
        // set font
        XWPFParagraph p1 = document.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);

        // Set Text to Bold and font size to 22 for first paragraph
        XWPFRun r1 = p1.createRun();
        r1.addBreak();
        r1.addBreak();
        r1.setBold(true);
        r1.setItalic(true);
        r1.setFontSize(13);
        r1.setText(text);
        r1.setFontFamily("Times New Roman");

        System.out.println("paragraph written successully");
    }

    public CreateParagraph(XWPFDocument document, String path) throws IOException {
        this.document = document;
        this.out = new FileOutputStream(new File(path));
    }
}
