package org.example.prepared_statement.action;

import org.example.prepared_statement.dao.CourseDao;
import org.example.prepared_statement.model.Course;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CourseAction {

    private final CourseDao courseDao = new CourseDao();
    private final Scanner scanner = new Scanner(System.in);

    public void addCourse() {
        try {
            System.out.print("Введите название курса: ");
            String title = scanner.nextLine();
            System.out.print("Введите количество часов: ");
            int hours = Integer.parseInt(scanner.nextLine());
            System.out.print("Введите описание: ");
            String description = scanner.nextLine();
            System.out.print("Введите уровень сложности: ");
            String difficulty = scanner.nextLine();

            Course course = new Course(title, hours, description, difficulty);
            courseDao.insert(course);
            System.out.println("Курс успешно добавлен!");
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Ошибка при добавлении курса: " + e.getMessage());
        }
    }

    public void updateCourse() {
        try {
            System.out.print("Введите ID курса для обновления: ");
            Long id = Long.parseLong(scanner.nextLine());

            Course course = courseDao.getById(id);
            if (course == null) {
                System.out.println("Курс с таким ID не найден!");
                return;
            }

            System.out.print("Введите новое название курса: ");
            course.setTitle(scanner.nextLine());
            System.out.print("Введите новое количество часов: ");
            course.setHours(Integer.parseInt(scanner.nextLine()));
            System.out.print("Введите новое описание: ");
            course.setDescription(scanner.nextLine());
            System.out.print("Введите новый уровень сложности: ");
            course.setDifficultyLevel(scanner.nextLine());

            courseDao.update(course);
            System.out.println("Курс успешно обновлен!");
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Ошибка при обновлении курса: " + e.getMessage());
        }
    }

    public void deleteCourse() {
        try {
            System.out.print("Введите ID курса для удаления: ");
            Long id = Long.parseLong(scanner.nextLine());

            Course course = courseDao.getById(id);
            if (course == null) {
                System.out.println("Курс с таким ID не найден!");
                return;
            }

            courseDao.delete(id);
            System.out.println("Курс успешно удален!");
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Ошибка при удалении курса: " + e.getMessage());
        }
    }

    public void getAllCourses() {
        try {
            List<Course> courses = courseDao.getAll();
            if (courses.isEmpty()) {
                System.out.println("Список курсов пуст.");
                return;
            }
            System.out.println("\n--- СПИСОК КУРСОВ ---");
            for (Course course : courses) {
                System.out.println(course);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка курсов: " + e.getMessage());
        }
    }
}
