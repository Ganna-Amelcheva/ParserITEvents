import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Event {
    private String name;
    private int month;
    private int day;
    public static List<Event> events = new ArrayList<Event>();

    public Event(String name, int month, int day) {
        this.name = name;
        this.month = month;
        this.day = day;

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
        System.out.println(day + "." + month+ " "+name);
    }

    public static void printAllEvent() {
        for (int i = 0; i < events.size(); i++) {
            events.get(i).printEvent();
        }
    }
}
