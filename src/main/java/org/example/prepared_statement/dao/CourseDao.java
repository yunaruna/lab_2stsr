package org.example.prepared_statement.dao;

import org.example.connection.ConnectionManager;
import org.example.prepared_statement.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    public void insert(Course course) throws SQLException {
        String sql = "INSERT INTO courses (title, hours, description, difficulty_level) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getTitle());
            stmt.setInt(2, course.getHours());
            stmt.setString(3, course.getDescription());
            stmt.setString(4, course.getDifficultyLevel());
            stmt.executeUpdate();
        }
    }

    public void update(Course course) throws SQLException {
        String sql = "UPDATE courses SET title = ?, hours = ?, description = ?, difficulty_level = ? WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getTitle());
            stmt.setInt(2, course.getHours());
            stmt.setString(3, course.getDescription());
            stmt.setString(4, course.getDifficultyLevel());
            stmt.setLong(5, course.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Course getById(Long id) throws SQLException {
        String sql = "SELECT * FROM courses WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Course course = new Course();
                    course.setId(rs.getLong("id"));
                    course.setTitle(rs.getString("title"));
                    course.setHours(rs.getInt("hours"));
                    course.setDescription(rs.getString("description"));
                    course.setDifficultyLevel(rs.getString("difficulty_level"));
                    return course;
                }
            }
        }
        return null;
    }

    public List<Course> getAll() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getLong("id"));
                course.setTitle(rs.getString("title"));
                course.setHours(rs.getInt("hours"));
                course.setDescription(rs.getString("description"));
                course.setDifficultyLevel(rs.getString("difficulty_level"));
                courses.add(course);
            }
        }
        return courses;
    }
}
