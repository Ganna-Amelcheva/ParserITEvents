
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Event {
    private String name;
    private int month;
    private int day;
    public static List<Event> events = new ArrayList<Event>();
    private int finishDay;
    private int finishMonth;
    private String allDate;
    private long id;
    private static int count=0;

    public Event(){

    }
    public Event(String name, int month, int day) {
        this.id=count;
        count++;
        this.name = name;
        this.month = month;
        this.day = day;

    }

    public Event(String name, int month, int day, int finishMonth, int finishDay) {
        this.id=count;
        count++;
        this.name = name;
        this.month = month;
        this.day = day;
        this.finishMonth = finishMonth;
        this.finishDay = finishDay;
        this.id = id;
    }

    public String getAllDate() {
        if (finishDay == 0) {
            return day + "." + month;
        }
        return day + "." + month + "-" + finishDay + "." + finishMonth;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", month=" + month +
                ", day=" + day +
                ", finishDay=" + finishDay +
                ", finishMonth=" + finishMonth +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Event event = (Event) object;
        return month == event.month && day == event.day && finishDay == event.finishDay && finishMonth == event.finishMonth && Objects.equals(name, event.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, month, day, finishDay, finishMonth);
    }
}


