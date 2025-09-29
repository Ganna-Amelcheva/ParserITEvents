import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;

import java.util.stream.Collectors;

public class ParserEvents {
    public static boolean flag = true;
    public static JScrollPane scrollPane;
    public static JTextField jTextField;
    public static JTable table;
    public static JButton button1;
    public static JButton searchButton;
    public static JPanel searchPanel;
    public static JFrame jframe;
    public static DefaultTableModel defaultTableModel;

    public static void main(String[] args) throws IOException {
//        fillEventsFromGorodZovet();
        fillEventsFromAllEvent();
//        fillEventsFromExpomap();
        sortedEvents();
//        вызываем метод ля создания окна
        applicationEvent();
        Event.printAllEvent();

    }

    public static void applicationEvent() {
        createWindow();

        searchButton = new JButton("Найти");


        Object[][] events = new Object[Event.events.size()][2];
        String[] columsName = {"Дата", "Мероприятие"};
        fillEvents(events);
        defaultTableModel = new DefaultTableModel(events, columsName);
        table = new JTable(defaultTableModel);
        scrollPane = new JScrollPane(table);
        jTextField = new JTextField("Text", 20);

        createSeachPanel();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultTableModel.setRowCount(0);
                String text = jTextField.getText().toLowerCase();
                for (int i = 0; i < Event.events.size(); i++) {
                    if (Event.events.get(i).getAllDate().toString().contains(text) || Event.events.get(i).getName().toLowerCase().contains(text))
                        defaultTableModel.addRow(new Object[]{Event.events.get(i).getAllDate(), Event.events.get(i).getName()});
                }
                if (flag) {
                    searchButton.setBackground(Color.RED);
                } else {
                    searchButton.setBackground(Color.BLUE);
                }
                flag = !flag;
            }
        });

        jframe.setVisible(true);

    }

    public static void createWindow() {
        jframe = new JFrame("Календарь событий");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(500, 400);
        jframe.setLocationRelativeTo(null);

        jframe.setLayout(new BorderLayout());
    }

    public static void createSeachPanel() {
        // Создаем панель для компонентов
        searchPanel = new JPanel();
        searchPanel.add(new JLabel("Поиск событий:"));
        searchPanel.add(jTextField);
        searchPanel.add(searchButton);
        searchPanel.add(scrollPane);
        jframe.add(searchPanel, BorderLayout.CENTER);
    }

    public static void fillEvents(Object[][] events) {
        for (int i = 0; i < events.length; i++) {
            events[i][0] = Event.events.get(i).getAllDate();
            events[i][1] = Event.events.get(i).getName();
        }
        System.out.println("ТАБЛИЦА");
    }


    public static void fillEventsFromGorodZovet() throws IOException {
        String url = "https://gorodzovet.ru/spb/it/";
        Document document = fileCheck("Gorod1", url);
        Elements elements = document.getElementsByClass("event-block");
        for (int i = 0; i < elements.size(); i++) {
            String name = elements.get(i).getElementsByTag("h3").text();
            String month = elements.get(i).getElementsByClass("event-month").text();
            String day = elements.get(i).getElementsByClass("event-day").text();
            System.out.println(day);
            checkEventOneDayOrMore(day, name, month);
        }
    }

    public static void fillEventsFromAllEvent() throws IOException {
        String url = "https://all-events.ru/events/calendar/type-is-conferencia/theme-is-informatsionnye_tekhnologii/";
        Document document = fileCheck("GlobalTechForum", url);
        Elements elements = document.getElementsByClass("event_order_1");
        for (int i = 0; i < elements.size(); i++) {
            String name = elements.get(i).getElementsByClass("event_name_new").text();
            String date = elements.get(i).getElementsByClass("event-date").text();
            checkDateLength(date, name);
        }
    }

    public static void fillEventsFromExpomap() throws IOException {
        String url = "https://expomap.ru/conference/theme/it-kommunikatsii-svyaz/";
        Document document = fileCheck("Expo", url);
        Elements elements = document.getElementsByClass("cl-item");
        for (int i = 0; i < elements.size(); i++) {
            String name = elements.get(i).getElementsByClass("header-cli-title-pc").text();
            String date = elements.get(i).getElementsByClass("cli-date").text();
            checkNumberOrText(date, name);
        }
    }

    public static void checkEventOneDayOrMore(String day, String name, String month) {
        String[] dates = day.split(" ");
        if (dates.length == 1) {
            Event event = new Event(name, getFormateDateMonth(month), Integer.parseInt(day));
            Event.events.add(event);
        } else {
            Event event = new Event(name, getFormateDateMonth(month), Integer.parseInt(dates[0]), getFormateDateMonth(month), Integer.parseInt(dates[dates.length - 1]));
            Event.events.add(event);
        }
    }

    public static void checkDateLength(String date, String name) {
        date = parseDateTwo(date);
        String[] date2 = date.split("-");

        String[] dateDayAndMonth = date2[0].split("\\.");
        int day = Integer.parseInt(dateDayAndMonth[0]);
        int month = Integer.parseInt(dateDayAndMonth[1]);
        if (date2.length > 1) {
            String[] dateDayAndMonthFinish = date2[1].split("\\.");
            int day2 = Integer.parseInt(dateDayAndMonthFinish[0]);
            int month2 = Integer.parseInt(dateDayAndMonthFinish[1]);
            Event event = new Event(name, month, day, month2, day2);
            Event.events.add(event);
        } else {
            Event event = new Event(name, month, day);
            Event.events.add(event);
        }
    }

    public static void checkNumberOrText(String date, String name) {
        date = parseDateThree(date);
        String[] date2 = date.split("по");
        String[] dateDayAndMonth = date2[0].split(" ");
        int day = Integer.parseInt(dateDayAndMonth[0]);
        int monthNumber = getFormateDateMonthTwo(date);
        if (dateDayAndMonth[1].matches("\\d+")) {
            int dayFinish = Integer.parseInt(dateDayAndMonth[1]);
            Event event = new Event(name, monthNumber, day, monthNumber, dayFinish);
            Event.events.add(event);
        } else {
            Event event = new Event(name, monthNumber, day);
            Event.events.add(event);
        }
    }


    public static String parseDateTwo(String date) {
        date = date.replaceAll("[^0-9.-]", "");
        date = date.replaceAll("-+$", "");
        return date;
    }

    public static String parseDateThree(String date) {
        date = date.replace("с ", "").replace("по ", "");
        date = date.replaceAll("[^0-9а-яё ]", "").trim();
        return date;
    }

    //протестировать sortedEvents()
    public static void sortedEvents() {
        Event.events = Event.events.stream()
                .sorted(Comparator.comparing(Event::getMonth)
                        .thenComparing(Event::getDay)
                        .thenComparing(Event::getName))
                .collect(Collectors.toList());
    }

    public static int getFormateDateMonth(String month) {
        String[] month2 = month.split(" ");
        int numberMonth = switch (month2[0].toLowerCase()) {
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

    public static int getFormateDateMonthTwo(String month) {
        if (month.contains("января"))
            return 1;

        if (month.contains("февраля"))
            return 2;

        if (month.contains("марта"))
            return 3;

        if (month.contains("апреля"))
            return 4;

        if (month.contains("мая"))
            return 5;

        if (month.contains("июня"))
            return 6;

        if (month.contains("июля"))
            return 7;

        if (month.contains("августа"))
            return 8;

        if (month.contains("сентября"))
            return 9;

        if (month.contains("октября"))
            return 10;

        if (month.contains("ноября"))
            return 11;

        if (month.contains("декабря"))
            return 12;

        return 0;
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
