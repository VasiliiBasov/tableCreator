package org.example;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
        this.out = new FileOutputStream(new File(path));
        this.path = path;
    }

    public static void test1() throws IOException, House.DocumentNotCreated {
        App app = new App(currentPath + "/Tables.docx");
        House house1 = new House();
        houses.add(house1);


        CreateParagraph paragraph1 = new CreateParagraph(document, path);
        paragraph1.createParagraph("Таблица 3." + app.getTableId() + " – Конструктивные данные по результатам обследования сооружения по адресу: " + house1.getAddress() + " (ID объекта " + house1.getId() + ")", false, true);

        CreateTable table1 = new CreateTable(document, path, house1);
        table1.createTable(currentPath + house1.getType());

        paragraph1.createParagraph("Паспорт здания: " + house1.getAddress() + " (ID объекта " + house1.getId() + ")", true, false);

        table1.createTable(currentPath + "/Passport_" + house1.getType(1) + ".yaml", 12);

        if (!house1.isWorking()) {

            paragraph1.createParagraph("Ведомость дефектов:", true, false);

            table1.createStatement(currentPath + "/Statement.yaml");
            paragraph1.createConclusion(house1, currentPath + "/conclusion.txt");

//            paragraph1.createParagraph("По результатам проведенного обследования несущих и " +
//                    "ограждающих конструкций здания по адресу: " + house1.getAddress()
//                    + " (ID объекта " + house1.getId() + ")"
//                    + ", техническое состояние сооружения – " + house1.getCondition()
//                    + ", обнаружены дефекты, влияющие на несущую способность конструкций, " +
//                    "а также на их долговечность и на эксплуатационную надежность."
//                    + " Обнаруженные дефекты возникли по причине отсутствия своевременных " +
//                    "ремонтно-восстановительных работ конструкций здания и дальнейшего воздействия " +
//                    "знакопеременных температур.", false, false);
        } else {
            paragraph1.createConclusion(house1, currentPath + "/conclusion.txt");
//            paragraph1.createParagraph("По результатам проведенного обследования несущих и ограждающих " +
//                    "конструкций здания по адресу: " + house1.getAddress()
//                    + " (ID объекта " + house1.getId() + ")"
//                    + ", техническое состояние сооружения – " + house1.getCondition()
//                    + ", дефекты, влияющие на несущую способность конструкций, " +
//                    "а также на их долговечность и на эксплуатационную надежность не обнаружены.", false, false);
        }
        document.write(out);
        out.close();

    }

    public static void createImages() throws IOException {
        App app1 = new App(currentPath + "/Tables.docx");
        Images imagesTable = new Images(document, houses);
        imagesTable.createImages();

        document.write(out);
        out.close();
    }


    public int getTableId() {
        tableId++;
        return tableId;
    }
}
