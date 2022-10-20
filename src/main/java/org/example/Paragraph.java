package org.example;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Paragraph {
    private XWPFDocument document;
    private FileOutputStream out;
    private final ArrayList<String> conclusion = new ArrayList<>();

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

    public Paragraph(XWPFDocument document, String path) throws IOException {
        this.document = document;
        this.out = new FileOutputStream(new File(path));
    }

    public void createConclusion(House house, String path) throws IOException {
        Files.lines(Paths.get(path)).forEach(conclusion::add);
        int line = 0;
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.BOTH);
        paragraph.createRun().addBreak();
        paragraph.createRun().addTab();
        if (house.getCondition().equals("ограниченно-работоспособное")) line = 4;
        System.out.println(conclusion);
        for (int i = 0; i < 4; i++) {
            XWPFRun run = paragraph.createRun();
            run.setFontSize(13);
            run.setFontFamily("Times New Roman");
            run.setText(conclusion.get(line) + " ");
            if (i < 3) {
                XWPFRun newrun = paragraph.createRun();
                newrun.setFontSize(13);
                newrun.setFontFamily("Times New Roman");
                newrun.setBold(true);
                newrun.setItalic(true);
                if (i == 0) {
                    newrun.setText(String.valueOf(house.getAddress()));
                } else if (i == 1) {
                    newrun.setText(String.valueOf(house.getId()));
                } else {
                    newrun.setText(String.valueOf(house.getCondition()));
                }
            }
            line++;
        }
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
