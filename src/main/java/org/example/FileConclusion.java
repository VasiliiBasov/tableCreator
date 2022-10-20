package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.IOException;
import java.util.ArrayList;

public class FileConclusion {
    private XWPFDocument document;
    private ArrayList<House> houses;
    private static int imageId = 1;
    private String path;
    private Paragraph paragraph;

    public void createFileConclusion() throws IOException {
        paragraph.createParagraph("4 ЗАКЛЮЧЕНИЕ", false, false);
        paragraph.createParagraph("По результатам обследования несущих " +
                "и ограждающих конструкций " + houses.size() + " зданий и сооружений " +
                " подлежащих демонтажу на объекте по титулу:", false, false);
        houses.forEach(s -> {
            try {
                paragraph = new Paragraph(document, path);
                paragraph.createConclusion(s, App.currentPath + "/conclusion2.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public FileConclusion(XWPFDocument document, ArrayList<House> houses, String path) {
        this.document = document;
        this.houses = houses;
        this.path = path;
        try {
            this.paragraph = new Paragraph(document, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public XWPFDocument getDocument() {
        return document;
    }

    public void setDocument(XWPFDocument document) {
        this.document = document;
    }

    public ArrayList<House> getHouses() {
        return houses;
    }

    public void setHouses(ArrayList<House> houses) {
        this.houses = houses;
    }

    public static int getImageId() {
        return imageId;
    }

    public static void setImageId(int imageId) {
        FileConclusion.imageId = imageId;
    }
}
