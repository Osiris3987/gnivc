package com.example.dwh_service.service;

import com.example.dwh_service.model.Task;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ClickHouseJDBCDemo implements AutoCloseable{

    private static final String DB_URL = "jdbc:clickhouse://localhost:8123/default";

    private final Connection conn;

    /**
     * Creates new instance
     * @throws SQLException in case of connection issue
     */
    public ClickHouseJDBCDemo() throws SQLException {
        conn = DriverManager.getConnection(DB_URL);
    }
    public List<Task> popularYearRoutes() throws SQLException {
        String query = "SELECT " +
                "* " +
                "FROM tasks_view";
        long time = System.currentTimeMillis();
        List<Task> tasks = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task();
                    task.setId(UUID.fromString(rs.getString(1)));
                    task.setDescription(rs.getString(6));
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    public Integer recentTasks(String companyId) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startOfCurrentDay = LocalDate.now().atStartOfDay().plusDays(1);
        LocalDateTime endOfPreviousDay = startOfCurrentDay.minusDays(1);
        String startOfCurrentDayToString = dtf.format(startOfCurrentDay);
        String endOfPreviousDayToString = dtf.format(endOfPreviousDay);
        String query = "SELECT " +
                "count() " +
                "FROM tasks_view " +
                "WHERE company_id LIKE ? AND created_at >= ? AND created_at <= ?";
        Integer taskCount = 0;
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, companyId);
            statement.setString(2, endOfPreviousDayToString);
            statement.setString(3, startOfCurrentDayToString);

            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                taskCount = rs.getInt(1);
            }
        }
        return taskCount;
    }



    @Override
    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }
    public static void main(String[] args) throws Exception {
        try (ClickHouseJDBCDemo demo = new ClickHouseJDBCDemo()) {
            //System.out.println(demo.recentTasks());
        }
    }
}
