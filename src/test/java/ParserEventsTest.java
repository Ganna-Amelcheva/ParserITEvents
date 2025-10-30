
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;


public class ParserEventsTest {
    @BeforeEach
    public void setUp() {
        Event.events = new ArrayList<>();
    }

    @Test
    public void sortedEventsTest() {
        Event.events = Arrays.asList(
                new Event("January Conference", 1, 15),
                new Event("April Conference", 4, 5),
                new Event("April Seminar", 4, 5),
                new Event("April ConferenceTwo", 4, 29),
                new Event("January ConferenceTwo", 1, 9, 1, 12),
                new Event("December Conference", 12, 26)
        );
        ParserEvents.sortedEvents();
        Assertions.assertEquals("January ConferenceTwo", Event.events.get(0).getName());
        Assertions.assertEquals("January Conference", Event.events.get(1).getName());
        Assertions.assertEquals("April Conference", Event.events.get(2).getName());
        Assertions.assertEquals("April Seminar", Event.events.get(3).getName());
        Assertions.assertEquals("April ConferenceTwo", Event.events.get(4).getName());
        Assertions.assertEquals("December Conference", Event.events.get(5).getName());
    }

    @Test
    public void sortedEventsIsEmptyTest() {
        Event.events = new ArrayList<>();
        ParserEvents.sortedEvents();
        Assertions.assertTrue(Event.events.isEmpty());
    }

    @Test

    public void getFormateDateMonthTest() {
        Assertions.assertEquals(1, ParserEvents.getFormateDateMonth("января"));
        Assertions.assertEquals(1, ParserEvents.getFormateDateMonth("янв"));
        Assertions.assertEquals(1, ParserEvents.getFormateDateMonth("ЯНВАРЯ"));
        Assertions.assertEquals(1, ParserEvents.getFormateDateMonth("ЯНВ"));
        Assertions.assertEquals("Unexpected value: " + "привет", ParserEvents.getFormateDateMonth("привет"));
        Assertions.assertEquals("Unexpected value: " + "1", ParserEvents.getFormateDateMonth("1"));
        Assertions.assertEquals("Unexpected value: " + null, ParserEvents.getFormateDateMonth(null));
    }


    //января  янв ЯНВАРЯ ЯНВ
    @Test
    public void checkEventOneDayOrMoreTest() {
        int size = Event.events.size();
        ParserEvents.checkEventOneDayOrMore("15", "name", "января");
        Event.events.get(Event.events.size() - 1);
        Assertions.assertEquals(size + 1, Event.events.size());
        Assertions.assertEquals(1, Event.events.get(Event.events.size() - 1).getMonth());

    }

    @Test
    public void checkEventOneDayOrMoreTwoTest() {
        int size2 = Event.events.size();
        ParserEvents.checkEventOneDayOrMore("15 17", "name", "января");
        Assertions.assertEquals(15, Event.events.get(Event.events.size() - 1).getDay());
        Assertions.assertEquals(17, Event.events.get(Event.events.size() - 1).getFinishDay());
        Event.events.get(Event.events.size() - 1);
        Assertions.assertEquals(size2 + 1, Event.events.size());
    }

    @Test
    public void checkEventOneDayOrMore3Test() {
        int size2 = Event.events.size();
        ParserEvents.checkEventOneDayOrMore("15 17 20", "name", "января");
        Assertions.assertEquals(15, Event.events.get(Event.events.size() - 1).getDay());
        Assertions.assertEquals(20, Event.events.get(Event.events.size() - 1).getFinishDay());
        Assertions.assertEquals(size2 + 1, Event.events.size());
    }

    @Test
    public void parseDateTwoTest() {
        Assertions.assertEquals("18", ParserEvents.parseDateTwo("$%18as!"));
        Assertions.assertEquals("15-18", ParserEvents.parseDateTwo("15-18 eventOne января"));
        Assertions.assertEquals("15.09-16.09", ParserEvents.parseDateTwo("asd 15.09-16.09 eventOne января"));
    }

    @Test
    public void parseDateThreeTest() {
        Assertions.assertEquals("18", ParserEvents.parseDateThree("//'18hj"));
        Assertions.assertEquals("15 18 ", ParserEvents.parseDateThree("с 15 по 18 fghh"));
    }
//15 17  event1 января
//    15  event1 января
}
