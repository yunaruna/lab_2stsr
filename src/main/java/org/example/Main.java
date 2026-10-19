package org.example;

import org.example.connection.ConnectionManager;
import org.example.prepared_statement.action.CourseAction;
import org.example.statement.action.StatementAction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Connection connection = ConnectionManager.getConnection()) {
            System.out.println("Подключение к PostgreSQL успешно!");
            System.out.println("База данных: " + connection.getCatalog());
        } catch (SQLException e) {
            System.out.println("Не удалось подключиться к PostgreSQL.");
            System.out.println("Причина: " + e.getMessage());
            return;
        }

        StatementAction statementAction = new StatementAction();
        CourseAction courseAction = new CourseAction();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n===== ГЛАВНОЕ МЕНЮ ЛАБОРАТОРНОЙ JDBC =====");
                System.out.println("1. Меню Statement (Задания с 1 по 7)");
                System.out.println("2. Меню PreparedStatement (Курсы / Сущность 2-й части)");
                System.out.println("0. Выход");
                System.out.print("Выбери раздел: ");

                String mainChoice = scanner.nextLine().trim();

                switch (mainChoice) {
                    case "1":
                        runStatementMenu(statementAction, scanner);
                        break;

                    case "2":
                        runPreparedStatementMenu(courseAction, scanner);
                        break;

                    case "0":
                        System.out.println("Программа завершена.");
                        return;

                    default:
                        System.out.println("Нет такого пункта. Выбери 1, 2 или 0.");
                }
            }
        }
    }

    private static void runStatementMenu(StatementAction action, Scanner scanner) {
        while (true) {
            System.out.println("\n===== ЛАБОРАТОРНАЯ JDBC: STATEMENT =====");
            System.out.println("1. Вывести данные всех таблиц");
            System.out.println("2. Вывести авторов по имени и фамилии");
            System.out.println("3. Найти книги после заданного года");
            System.out.println("4. Изменить название случайной книги в магазине");
            System.out.println("5. Найти книги по первой букве фамилии автора");
            System.out.println("6. Изменить имена авторов книг выбранного магазина");
            System.out.println("7. Посчитать количество книг каждого автора");
            System.out.println("0. Назад в главное меню");
            System.out.print("Выбери действие: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        action.getAllInfo();
                        break;
                    case "2":
                        action.getAuthorsSorted();
                        break;
                    case "3":
                        System.out.print("Введи год: ");
                        int year = Integer.parseInt(scanner.nextLine().trim());
                        action.getBooksAfterYear(year);
                        break;
                    case "4":
                        System.out.print("Название магазина: ");
                        String shop = scanner.nextLine().trim();
                        System.out.print("Новое название книги: ");
                        String title = scanner.nextLine().trim();
                        action.updateRandomBookTitleInShop(shop, title);
                        break;
                    case "5":
                        System.out.print("Первая буква фамилии автора: ");
                        String letter = scanner.nextLine().trim();
                        if (letter.length() != 1) {
                            System.out.println("Введи ровно одну букву.");
                            break;
                        }
                        action.getBooksByAuthorLastNameInitial(letter.charAt(0));
                        break;
                    case "6":
                        System.out.print("Название магазина: ");
                        String shopName = scanner.nextLine().trim();
                        System.out.print("Новое имя авторов: ");
                        String firstName = scanner.nextLine().trim();
                        action.updateAuthorsFirstNameByShop(shopName, firstName);
                        break;
                    case "7":
                        action.getBookCountByAuthor();
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Нет такого пункта.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Нужно ввести корректное число.");
            }
        }
    }

    private static void runPreparedStatementMenu(CourseAction courseAction, Scanner scanner) {
        while (true) {
            System.out.println("\n===== МЕНЮ PREPARED STATEMENT (Курсы) =====");
            System.out.println("1. Добавить курс");
            System.out.println("2. Обновить курс");
            System.out.println("3. Удалить курс");
            System.out.println("4. Показать все курсы");
            System.out.println("0. Назад в главное меню");
            System.out.print("Выбери действие: ");

            String choice = scanner.nextLine().trim();

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
                    System.out.println("Нет такого пункта.");
            }
        }
    }
}
