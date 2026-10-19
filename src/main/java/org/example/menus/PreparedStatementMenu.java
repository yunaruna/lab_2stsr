package org.example.menus;

import org.example.prepared_statement.action.CourseAction;

import java.util.Scanner;

public class PreparedStatementMenu {

    private final CourseAction courseAction = new CourseAction();
    private final Scanner scanner = new Scanner(System.in);

    public void print() {
        while (true) {
            System.out.println("\n=== МЕНЮ PREPARED STATEMENT (Курсы) ===");
            System.out.println("1) Добавить курс");
            System.out.println("2) Обновить курс");
            System.out.println("3) Удалить курс");
            System.out.println("4) Показать все курсы");
            System.out.println("0) Выход в главное меню");
            System.out.print("Выберите пункт: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    courseAction.addCourse();
                    break;
                case "2":
                    courseAction.updateCourse();
                    break;
                case "3":
                    courseAction.deleteCourse();
                    break;
                case "4":
                    courseAction.getAllCourses();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Неверный ввод! Попробуйте снова.");
            }
        }
    }
}