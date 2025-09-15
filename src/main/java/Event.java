import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Event {
    private String name;
    private int month;
    private int day;
    public static List<Event> events = new ArrayList<Event>();
private int finishDay;
private int finishMonth;
    public Event(String name, int month, int day) {
        this.name = name;
        this.month = month;
        this.day = day;

    }
    public Event(String name, int month, int day, int finishMonth, int finishDay) {
        this.name = name;
        this.month = month;
        this.day = day;
        this.finishMonth=finishMonth;
        this.finishDay=finishDay;

    }

    public int getFinishDay() {
        return finishDay;
    }

    public void setFinishDay(int finishDay) {
        this.finishDay = finishDay;
    }

    public int getFinishMonth() {
        return finishMonth;
    }

    public void setFinishMonth(int finishMonth) {
        this.finishMonth = finishMonth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void printEvent() {
        if (finishDay != 0 || finishMonth != 0) {
            System.out.println(day + "." + month + "-" + finishDay + "." + finishMonth + " " + name);
        } else {
            System.out.println(day + "." + month + " " + name);
        }
    }
    public static void printAllEvent() {
        for (int i = 0; i < events.size(); i++) {
            events.get(i).printEvent();
        }
    }
}


