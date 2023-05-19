package com.github.jfsql.demo;

import static com.github.jfsql.demo.Constants.JFSQL_CONNECTION_STRING;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    public void createTask(final Task task) {
        try (final Connection connection = DriverManager.getConnection(JFSQL_CONNECTION_STRING);
            final Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS tasks (id INTEGER, description TEXT, completed TEXT)");
            final PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO tasks VALUES (?, ?, ?)");
            preparedStatement.setString(1, "default");
            preparedStatement.setString(2, task.description());
            preparedStatement.setString(3, String.valueOf(task.completed()));
            preparedStatement.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(final Task task, final Long id) {
        try (final Connection connection = DriverManager.getConnection(JFSQL_CONNECTION_STRING)) {
            final PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE tasks SET description = ?, completed = ? WHERE id = ?");
            preparedStatement.setString(1, task.description());
            preparedStatement.setString(2, String.valueOf(task.completed()));
            preparedStatement.setLong(3, id);
            preparedStatement.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Task> selectAllTask() {
        final List<Task> allTasks = new ArrayList<>();
        try (final Connection connection = DriverManager.getConnection(JFSQL_CONNECTION_STRING);
            final Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS tasks (id INTEGER, description TEXT, completed TEXT)");
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks;");
            while (resultSet.next()) {
                final long id = resultSet.getLong("id");
                final String description = resultSet.getString("description");
                final String completed = resultSet.getString("completed");
                final Task task = new Task(id, description, Boolean.parseBoolean(completed));
                allTasks.add(task);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return allTasks;
    }

    public void deleteTaskById(final long selectedId) {
        try (final Connection connection = DriverManager.getConnection(JFSQL_CONNECTION_STRING)) {
            final String sql = "DELETE FROM tasks WHERE id = ?;";
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, selectedId);
            preparedStatement.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

}
