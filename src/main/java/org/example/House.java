package org.example;


import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Entity
@Table(name="housess", catalog = "houses")
public class House {
    private String address;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String idd;
    @Column
    private double length;
    @Column
    private double width;

    private double height;

    private String appointment;

    private String conditionn;

    private boolean isWorking;

    private final int type;

    private int windows;

    private String roof;


    private String date;


    public House() throws DocumentNotCreated {
        setDate();
        ArrayList<String> ans = Window.answer;
        for (int i = 0; i < Window.answer.size(); i++) {
            if (String.valueOf(ans.get(i)).contains(",")) {
                ans.set(i, ans.get(i).replace(",", "."));
            }
        }
        try {
            address = Window.answer.get(0);
            idd = Window.answer.get(1);
            length = Double.parseDouble(Window.answer.get(2));
            width = Double.parseDouble(Window.answer.get(3));
            height = Double.parseDouble(Window.answer.get(4));
            appointment = Window.answer.get(5);
            roof = Window.answer.get(6);
            if (Integer.parseInt(Window.answer.get(7)) == 1) {
                conditionn = "работоспособное";
                isWorking = true;
            } else if (Integer.parseInt(Window.answer.get(7)) == 2) {
                conditionn = "ограниченно-работоспособное";
                isWorking = false;
            }
            type = Integer.parseInt(Window.answer.get(8));
            windows = Integer.parseInt(Window.answer.get(9));
        }
        catch (Exception e) {
            throw new DocumentNotCreated(e.getMessage());
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getConditionn() {
        return conditionn;
    }

    public void setConditionn(String conditionn) {
        this.conditionn = conditionn;
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
        if (isWorking) {
            return "/" + type + ".yaml";
        }
        else return "/" + type + "D.yaml";
    }
    public int getType(int i) {
        return type;
    }

    public String getRoof() throws DocumentNotCreated {
        if (roof.equals("1")) {
            return "Односкатная";
        }
        if (roof.equals("2")) {
            return "Двускатная";
        }
        else throw new DocumentNotCreated("Не правильный тип крыши");
    }

    public void setRoof(String roof) {
        this.roof = roof;
    }

    public String getWindows() throws DocumentNotCreated {
        if (windows == 1) {
            return "Отсутствует";
        }
        if (windows == 2) {
            return "Пвх двухкамерные окна, с стеклянным заполнением";
        }
        if (windows == 3) {
            return "Деревянные стеклопакеты";
        }
        else throw new DocumentNotCreated("Не правильный тип проема");
    }

    public void setWindows(int windows) {
        this.windows = windows;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public static class DocumentNotCreated extends Exception {

        public DocumentNotCreated (String message) {
            super(message);
        }
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }
}
