package org.example;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.ArrayList;

public class App {
    static XWPFDocument document = new XWPFDocument();
    private static FileOutputStream out;
    private static String path;
    private static int tableId = 0;
    public static String currentPath;
    public static ArrayList<House> houses = new ArrayList<>();

    static {
        try {
            currentPath = new File(".").getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public App(String path) throws IOException {
        out = new FileOutputStream(path);
        App.path = path;
    }

    public static void test1() throws IOException, House.DocumentNotCreated {
        App app = new App(currentPath + "/Tables.docx");
        House house1 = new House();
        houses.add(house1);
        DB.init();
        DB.addHouse(house1);


        Paragraph paragraph1 = new Paragraph(document, path);
        paragraph1.createParagraph("Таблица 3." + app.getTableId() + " – Конструктивные данные по результатам обследования сооружения по адресу: " + house1.getAddress() + " (ID объекта " + house1.getIdd() + ")", false, true);

        Tables table1 = new Tables(document, house1);
        table1.createTable(currentPath + house1.getType());

        paragraph1.createParagraph("Паспорт здания: " + house1.getAddress() + " (ID объекта " + house1.getIdd() + ")", true, false);

        table1.createTable(currentPath + "/Passport_" + house1.getType(1) + ".yaml", 12);

        if (!house1.isWorking()) {

            paragraph1.createParagraph("Ведомость дефектов:", true, false);
            if (!(house1.getType(3) == 3)) {
                table1.createStatement(currentPath + "/Statement.yaml");
            } else table1.createStatement(currentPath + "/Statement3.yaml");
            paragraph1.createConclusion(house1, currentPath + "/conclusion.txt");
        } else {
            paragraph1.createConclusion(house1, currentPath + "/conclusion.txt");
        }
        document.write(out);

    }

    public static void createConclutionFile() throws IOException {
        FileConclusion fileConclusion = new FileConclusion(document, houses, path);
        fileConclusion.createFileConclusion();
        Images imagesTable = new Images(document, houses);
        imagesTable.createImages();

        document.write(out);
        out.close();
    }


    public int getTableId() {
        tableId++;
        return tableId;
    }

    public static String getPath() {
        return path;
    }
}
