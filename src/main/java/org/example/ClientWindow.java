package org.example;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.example.App.currentPath;

public class ClientWindow extends JFrame implements ActionListener {

    private static final String IP_ADDR = "localhost";
    private static final int PORT = 8189;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    //private final BufferedReader in;
    private final BufferedWriter out;
    private static ArrayList<String> question = new ArrayList();
    public static ArrayList<String> answer = new ArrayList();
    private static int i = 1;
    private static boolean isNew = false;

    public static void main(String[] args) {

        try {
            readFile(currentPath + "/question.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }

    public static final JTextArea log = new JTextArea();
    private final JTextField fieldInput = new JTextField();


    public ClientWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        log.setEditable(false);
        log.setLineWrap(true);
        add(new JScrollPane(log), BorderLayout.CENTER);

        fieldInput.addActionListener(this);
        add(fieldInput, BorderLayout.SOUTH);

        out = new BufferedWriter(new OutputStreamWriter(System.out, Charset.forName("UTF-8")));


        setVisible(true);
        log.append(question.get(0) + "\r\n");


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            write();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidFormatException ex) {
            throw new RuntimeException(ex);
        }

    }

    public String read() {
        String msg = fieldInput.getText();
        if (msg.equals("")) {
            log.append("Строка пуста");
            read();
        }
        fieldInput.setText(null);
        return msg;
    }

    public synchronized void write() throws IOException, InvalidFormatException {
        if (isNew) {
            if (fieldInput.getText().equals("")) {
                i = 1;
                isNew = false;
                answer = new ArrayList<>();
                log.append(question.get(0) + "\r\n");
                write();
            } else {
                return;
            }
        }
        String msg = fieldInput.getText();
        if (msg.equals("")) return;
        fieldInput.setText(null);
        try {
            if (searchEr(i, msg)) return;
            log.append("Ответ: " + msg + "\r\n");
            answer.add(msg);
            if (i < question.size()) {
                log.append(question.get(i) + "\r\n");
                i++;
            } else {
//                log.selectAll();
//                log.replaceSelection("");
                App.test1();
                ClientWindow.log.append("Здание добавлено!\n" +
                        "Чтобы добавить новое здание нажмите enter\n");
                isNew = true;

            }
        }
         catch (House.DocumentNotCreated documentNotCreated) {
            restart(documentNotCreated.toString());
        }
         catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void readFile(String path) throws IOException {
        Files.lines(Paths.get(path)).forEach(question::add);
        question.add("Выберите тип крыши: \n" +
                "1 - Односкатная\n" +
                "2 - Двускатная");
        question.add("Выберите техническое состояние: \n" +
                "1 - работоспособное\n" +
                "2 - ограниченно-работоспособное");
        question.add("Выберите тип: \n" +
                "1 - деревянны каркас\n" +
                "2 - деревянный брус\n" +
                "3 - Металл каркас\n" +
                "4 - пеноблок\n" +
                "5 - кирпич");
    }

    public static void restart(String er) {
        ClientWindow.log.append("Ошибка " + er + "\n" +
                "Чтобы добавить новое здание нажмите enter\n");
        isNew = true;
    }
    public static boolean isNumeric(String str) {
        try {
            str = str.replaceAll(",", ".");
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private boolean searchEr(int i, String msg) {
        if (i > 2 && !isNumeric(msg) && i != 6) {
            log.append("Ошибка! " + msg + " - не числовое значение.\n");
            log.append(question.get(i-1) + "\r\n");
            return true;
        }
        if (i > 6 && !isInteger(msg)) {
            log.append("Ошибка! " + msg + " - не целое число.\n");
            log.append(question.get(i-1) + "\r\n");
            return true;
        }
        if ((i == 7 || i == 8) && (Integer.parseInt(msg) < 1 || Integer.parseInt(msg) > 2)) {
            System.out.println(Integer.parseInt(msg));
            log.append("Ошибка! " + "Не верно выбран параметр - " + msg + ".\n");
            log.append(question.get(i-1) + "\r\n");
            return true;
        }
        if (i == 9 && (Integer.parseInt(msg) < 1 || Integer.parseInt(msg) > 5)) {
            log.append("Ошибка! " + "Не верно выбран параметр - " + msg + ".\n");
            log.append(question.get(i-1) + "\r\n");
            return true;
        }

        return false;

    }


}
