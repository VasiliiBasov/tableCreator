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

        System.out.println("paragraph written successfully");
    }

    public void createParagraph(String text, Boolean center) throws IOException {
        // set font
        XWPFParagraph p1 = document.createParagraph();
        if (center) p1.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setItalic(true);
        r1.setFontSize(13);
        r1.setText(text);
        r1.setFontFamily("Times New Roman");

        System.out.println("paragraph written successfully");
    }

    public void createParagraph(String text, Boolean center, Boolean addBreakPage) throws IOException {
        // set font
        XWPFParagraph p1 = document.createParagraph();
        if (center) p1.setAlignment(ParagraphAlignment.CENTER);
        if (addBreakPage) p1.setPageBreak(true);

        XWPFRun r1 = p1.createRun();

        if (!addBreakPage) {
            r1.addBreak();
        }
        if (!center) {
            r1.addTab();
        }
        r1.setBold(true);
        r1.setFontSize(13);
        r1.setText(text);
        r1.setFontFamily("Times New Roman");

        System.out.println("paragraph written successfully");
    }

    public CreateParagraph(XWPFDocument document, String path) throws IOException {
        this.document = document;
        this.out = new FileOutputStream(new File(path));
    }

    public XWPFDocument getDocument() {
        return document;
    }

    public void setDocument(XWPFDocument document) {
        this.document = document;
    }

    public FileOutputStream getOut() {
        return out;
    }

    public void setOut(FileOutputStream out) {
        this.out = out;
    }
}
