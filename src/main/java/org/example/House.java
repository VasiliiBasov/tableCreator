package org.example;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class House {
    private String address;
    private String id;
    private double length;
    private double width;
    private double height;
    private String appointment;
    private String condition;
    private int type;

    private String date;


    public House() throws IOException, DocumentNotCreated {
        setDate();
        ArrayList<String> ans = ClientWindow.answer;
        for (int i = 0; i < ClientWindow.answer.size(); i++) {
            if (String.valueOf(ans.get(i)).contains(",")) {
                ans.set(i, ans.get(i).replace(",", "."));
            }
        }
        try {
            address = ClientWindow.answer.get(0);
            id = ClientWindow.answer.get(1);
            length = Double.parseDouble(ClientWindow.answer.get(2));
            width = Double.parseDouble(ClientWindow.answer.get(3));
            height = Double.parseDouble(ClientWindow.answer.get(4));
            appointment = ClientWindow.answer.get(5);
            condition = ClientWindow.answer.get(6);
            if (Integer.parseInt(ClientWindow.answer.get(6)) == 1) {
                condition = "работоспособное";
            } else if (Integer.parseInt(ClientWindow.answer.get(6)) == 2) {
                condition = "ограниченно-работоспособное";
            }
            type = Integer.parseInt(ClientWindow.answer.get(7));
        }
        catch (Exception e) {
            System.out.println(e);
            throw new DocumentNotCreated("hjgfhgf");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        DateFormat df = new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();
        String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        String month = monthNames[calendar.get(Calendar.MONTH)];
        date = month + " " + df.format(calendar.getTime()) + " г.";
    }

    public String getType() {
        return "\\" + String.valueOf(type) + ".yaml";
    }
    public class DocumentNotCreated extends Exception {

        public DocumentNotCreated (String message) {
            super(message);
        }
    }
}
