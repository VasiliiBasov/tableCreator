package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class House {
    private String address;
    private String id;
    private double length;
    private double width;
    private double height;
    private String appointment;
    private String condition;

    private String date;


    public House() throws IOException {
        setDate();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите адрес объекта");
        address = reader.readLine();
        System.out.println("Введите id объекта");
        id = reader.readLine();
        System.out.println("Введите длину объекта:");
        length = Double.parseDouble(reader.readLine());
        System.out.println("Введите ширину объекта:");
        width = Double.parseDouble(reader.readLine());
        System.out.println("Введите высоту объекта:");
        height = Double.parseDouble(reader.readLine());
        System.out.println("Введите техн сост:");
        condition = reader.readLine();
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
        String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
        String month = monthNames[calendar.get(Calendar.MONTH)];
        date = month + " " + df.format(calendar.getTime()) + " г.";
    }
}
