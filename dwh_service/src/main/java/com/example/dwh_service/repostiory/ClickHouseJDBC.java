package com.example.dwh_service.repostiory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Repository
public class ClickHouseJDBC implements AutoCloseable{

    private static final String DB_URL = "jdbc:clickhouse://localhost:8123/default";

    private final Connection conn;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ClickHouseJDBC() throws SQLException {
        conn = DriverManager.getConnection(DB_URL);
    }

    public Integer recentTasks(String companyId) throws SQLException {
        LocalDateTime startOfCurrentDay = LocalDate.now().atStartOfDay().plusDays(1);
        LocalDateTime endOfPreviousDay = startOfCurrentDay.minusDays(1);
        String query = "SELECT " +
                "count() " +
                "FROM tasks_view " +
                "WHERE company_id LIKE ? AND created_at >= ? AND created_at <= ?";
        int taskCount;
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, companyId);
            statement.setString(2, dtf.format(endOfPreviousDay));
            statement.setString(3, dtf.format(startOfCurrentDay));

            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                taskCount = rs.getInt(1);
            }
        }
        return taskCount;
    }

    public Integer recentRaceEvents(String eventType, String companyId) throws SQLException {
        LocalDateTime startOfCurrentDay = LocalDate.now().atStartOfDay().plusDays(1);
        LocalDateTime endOfPreviousDay = startOfCurrentDay.minusDays(1);
        String query =
                """
                        SELECT COUNT(DISTINCT rv.id)
                        FROM races_view as rv
                                 INNER JOIN race_events_view as rev ON rv.id = rev.race_id
                                 INNER JOIN tasks_view as tv ON rv.task_id = tv.id
                        WHERE rev.event_type = ?
                          AND tv.company_id = ?
                          AND rev.created_at BETWEEN ? AND ?;""";
        int eventCount;
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, eventType);
            statement.setString(2, companyId);
            statement.setString(3, dtf.format(endOfPreviousDay));
            statement.setString(4, dtf.format(startOfCurrentDay));

            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                eventCount = rs.getInt(1);
            }
        }
        return eventCount;
    }

    @Override
    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

    public static void main(String[] args) throws Exception {
        try (ClickHouseJDBC demo = new ClickHouseJDBC()) {
            System.out.println(demo.recentRaceEvents("CANCELED", "b24842d4-91ae-463b-ac83-50179208d04e"));
        }
    }
}
