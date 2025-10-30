import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoEvent {
    private static Connection connection = UtilEvent.getConnection();

    public DaoEvent() {
    }

    public static void createTable() {

        String sql = """
                            CREATE TABLE IF NOT EXISTS Events(
                id BIGSERIAL PRIMARY KEY,
                name varchar(100),
                                month INT,
                                day INT,
                                finishDay INT,
                                finishMonth INT
                            )
                            """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropTable() {
        String sql = "DROP TABLE IF EXISTS Events";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
        }
    }

    public static void deleteById(long id) {
        String sql = "DELETE FROM Events WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public static void deleteName(String name) {
        String sql = "DELETE FROM Events WHERE name LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public static List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM Events";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                events.add(new Event(
                        resultSet.getString("name"),
                        resultSet.getInt("month"),
                        resultSet.getInt("day"),
                        resultSet.getInt("finishDay"),
                        resultSet.getInt("finishMonth")));

            }
        } catch (SQLException e) {
        }
        return events;
    }

    public static Event getEventById(long id) {
        String sql = "SELECT * FROM Events WHERE id=?";
        Event event = new Event();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                event.setName(resultSet.getString("name"));
                event.setMonth(resultSet.getInt("month"));
                event.setDay(resultSet.getInt("day"));
                event.setFinishDay(resultSet.getInt("finishDay"));
                event.setFinishMonth(resultSet.getInt("finishMonth"));
            }
        } catch (SQLException e) {
        }
        return event;
    }

    public static void saveEvents(long id, String name, int month, int day, int finishDay, int finishMonth) {
        String sql = "INSERT INTO Events (id, name, month, day, finishDay, finishMonth) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, month);
            preparedStatement.setInt(4, day);
            preparedStatement.setInt(5, finishDay);
            preparedStatement.setInt(6, finishMonth);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public static List<Event> findThreeFirstEvents() {
        List<Event> threeEvents = new ArrayList<>();
        String sql = """
                    SELECT * FROM Events 
                ORDER BY  month, day
                     LIMIT 3""";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                threeEvents.add(new Event(
                        resultSet.getString("name"),
                        resultSet.getInt("month"),
                        resultSet.getInt("day"),
                        resultSet.getInt("finishMonth"),
                resultSet.getInt("finishDay")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return threeEvents;
    }
}


//        Прописать метод, который возвращает 3 ближайших события