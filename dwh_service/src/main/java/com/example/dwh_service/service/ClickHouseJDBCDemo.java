package com.example.dwh_service.service;

import com.example.dwh_service.model.Task;
import org.springframework.stereotype.Repository;

import java.sql.*;
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



    @Override
    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }
    public static void main(String[] args) throws Exception {
        try (ClickHouseJDBCDemo demo = new ClickHouseJDBCDemo()) {
            List<Task> tasks = demo.popularYearRoutes();
            System.out.println(tasks);
        }
    }
}
