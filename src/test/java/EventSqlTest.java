import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;


public class EventSqlTest {
    public static long id = 999;
    public static Event beforeEvent;
    public static Event event1;
    public static Event event2;
    public static Event event3;

    @BeforeEach
    public void setUp() {
        beforeEvent = new Event("Anna", 11, 15, 11, 15);
        event1 = new Event("Event", 10, 21, 10, 30);
        event2 = new Event("Event2", 10, 21, 10, 30);
        event3 = new Event("Event3", 10, 21, 10, 30);
        DaoEvent.createTable();
    }

    @AfterEach
    public void after() {
        DaoEvent.deleteById(id);
        DaoEvent.deleteName("Event");
        DaoEvent.deleteName("Event2");
        DaoEvent.deleteName("Event3");
    }

    @Test
    public void createTableTest() {

        DaoEvent.saveEvents(999, beforeEvent.getName(), beforeEvent.getMonth(), beforeEvent.getDay(),
                beforeEvent.getFinishDay(), beforeEvent.getFinishMonth());
        Event event = DaoEvent.getEventById(999);
        Assertions.assertEquals(beforeEvent.getName(), event.getName());
        Assertions.assertEquals(beforeEvent.getMonth(), event.getMonth());
        Assertions.assertEquals(beforeEvent.getDay(), event.getDay());
        Assertions.assertEquals(beforeEvent.getFinishDay(), event.getFinishDay());
        Assertions.assertEquals(beforeEvent.getFinishMonth(), event.getFinishMonth());
    }

    @Test
    public void deleteNameTest() {
        DaoEvent.saveEvents(999, beforeEvent.getName(), beforeEvent.getMonth(), beforeEvent.getDay(),
                beforeEvent.getFinishDay(), beforeEvent.getFinishMonth());
        DaoEvent.deleteName("Anna");
        Event event = DaoEvent.getEventById(999);
        Assertions.assertNull(event.getName());
        Assertions.assertEquals(0, event.getMonth());
        Assertions.assertEquals(0, event.getDay());
        Assertions.assertEquals(0, event.getFinishDay());
        Assertions.assertEquals(0, event.getFinishMonth());
    }

    @Test
    public void deleteById() {
        DaoEvent.saveEvents(999, beforeEvent.getName(), beforeEvent.getMonth(), beforeEvent.getDay(),
                beforeEvent.getFinishDay(), beforeEvent.getFinishMonth());
        DaoEvent.deleteById(999);
        Event event = DaoEvent.getEventById(999);
        Assertions.assertNull(event.getName());
        Assertions.assertEquals(0, event.getMonth());
        Assertions.assertEquals(0, event.getDay());
        Assertions.assertEquals(0, event.getFinishDay());
        Assertions.assertEquals(0, event.getFinishMonth());
    }

    @Test
    public void findThreeFirstEventsTest() {
        DaoEvent.saveEvents(1000, event1.getName(), event1.getMonth(), event1.getDay(), event1.getFinishDay(), event1.getFinishMonth());
        DaoEvent.saveEvents(1001, event2.getName(), event2.getMonth(), event2.getDay(), event2.getFinishDay(), event2.getFinishMonth());
        DaoEvent.saveEvents(1002, event3.getName(), event3.getMonth(), event3.getDay(), event3.getFinishDay(), event3.getFinishMonth());
        List<Event> events = DaoEvent.findThreeFirstEvents();
      events = events.stream()
        .sorted(Comparator.comparing(Event::getName))
                .toList();
        Assertions.assertEquals(event1, events.get(0));
        Assertions.assertEquals(event2, events.get(1));
        Assertions.assertEquals(event3, events.get(2));

    }
}
