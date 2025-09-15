import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParserEvents {
    public static void main(String[] args) throws IOException {
        fillEventsFromGorodZovet();
        fillEventsFromAllEvent();
        sortedEvents();
        Event.printAllEvent();

}
public static void fillEventsFromGorodZovet() throws IOException {
    String url = "https://gorodzovet.ru/spb/it/";
    Document document = fileCheck("Gorod1", url);
    Elements elements = document.getElementsByClass("event-block");
    for (int i = 0; i < elements.size(); i++) {
        String name = elements.get(i).getElementsByTag("h3").text();
        String month = elements.get(i).getElementsByClass("event-month").text();
        String day = elements.get(i).getElementsByClass("event-day").text();
        Event event = new Event(name, getFormateDateMonth(month), Integer.parseInt(day));
        Event.events.add(event);
    }
}
    public static void fillEventsFromAllEvent() throws IOException {
        String url = "https://all-events.ru/events/calendar/type-is-conferencia/theme-is-informatsionnye_tekhnologii/";
        Document document = fileCheck("GlobalTechForum", url);
        Elements elements = document.getElementsByClass("event_order_1");
        for (int i = 0; i < elements.size(); i++) {
            String name = elements.get(i).getElementsByClass("event_name_new").text();
//
            String date = elements.get(i).getElementsByClass("event-date").text();
//
            date = parseDateTwo(date);
            String[] date2 = date.split("-");

            String[] dateDayAndMonth = date2[0].split("\\.");
           int day=Integer.parseInt(dateDayAndMonth[0]);
            int month=Integer.parseInt(dateDayAndMonth[1]);
           if(date2.length>1) {
               String[] dateDayAndMonthFinish = date2[1].split("\\.");
               int day2=Integer.parseInt(dateDayAndMonthFinish[0]);
               int month2=Integer.parseInt(dateDayAndMonthFinish[1]);
               Event event = new Event(name, month, day, month2, day2);
               Event.events.add(event);
           }
           else{
               Event event = new Event(name, month, day);
               Event.events.add(event);
           }
        }
    }
    public static int[] parseDate(String date) {
        String firstDateStr = date.split(" - ")[0].trim();
        firstDateStr = firstDateStr.split(" ")[0].trim();
        String[] parts = firstDateStr.split("\\.");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);

        return new int[]{day, month};
    }
    public static String parseDateTwo(String date){
        date = date.replaceAll("[^0-9.-]", "");
        date = date.replaceAll("-+$", "");
        return date;
    }
public static void sortedEvents(){
    Event.events = Event.events.stream()
            .sorted(Comparator.comparing(Event::getMonth)
                    .thenComparing(Event::getDay)
                    .thenComparing(Event:: getName))
            .collect(Collectors.toList());
}

public static int getFormateDateMonth(String month) {
    int numberMonth = switch (month.toLowerCase()) {
        case "января", "янв" -> 1;
        case "февраля", "фев" -> 2;
        case "марта", "мар" -> 3;
        case "апреля", "апр" -> 4;
        case "мая", "май" -> 5;
        case "июня", "июн" -> 6;
        case "июля", "июл" -> 7;
        case "августа", "авг" -> 8;
        case "сентября", "сен" -> 9;
        case "октября", "окт" -> 10;
        case "ноября", "ноя" -> 11;
        case "декабря", "дек" -> 12;
        default -> throw new IllegalStateException("Unexpected value: " + month.toLowerCase());
    };
    return numberMonth;
}

public static Document fileCheck(String filename, String url) throws IOException {
    File file = new File(filename + ".html");
    FileWriter fileWriter = new FileWriter(file);
    Document doc = null;

    if (file.exists()) {
        doc = (Document) Jsoup.connect(url).get();
        fileWriter.write(String.valueOf(doc));
    }
    doc = (Document) Jsoup.parse(file, "UTF-8", url);
    return doc;
}
}
//event-block