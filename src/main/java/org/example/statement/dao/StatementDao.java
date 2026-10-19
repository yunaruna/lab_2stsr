package org.example.statement.dao;

import org.example.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementDao {

    public void getAllInfo() throws SQLException {
        String[] queries = {
                "SELECT * FROM authors",
                "SELECT * FROM books",
                "SELECT * FROM shops",
                "SELECT * FROM shops_books",
                "SELECT * FROM students"
        };

        String[] names = {
                "АВТОРЫ",
                "КНИГИ",
                "МАГАЗИНЫ",
                "КНИГИ В МАГАЗИНАХ",
                "СТУДЕНТЫ"
        };

        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            for (int i = 0; i < queries.length; i++) {
                System.out.println("\n--- " + names[i] + " ---");

                try (ResultSet resultSet =
                             statement.executeQuery(queries[i])) {
                    int columns = resultSet.getMetaData().getColumnCount();

                    for (int column = 1; column <= columns; column++) {
                        System.out.print(
                                resultSet.getMetaData().getColumnName(column)
                                        + "\t"
                        );
                    }
                    System.out.println();

                    while (resultSet.next()) {
                        for (int column = 1; column <= columns; column++) {
                            System.out.print(
                                    resultSet.getString(column) + "\t"
                            );
                        }
                        System.out.println();
                    }
                }
            }
        }
    }

    public void getAuthorsSorted() throws SQLException {
        String sql = """
                SELECT id, first_name, last_name
                FROM authors
                ORDER BY first_name ASC, last_name ASC
                """;

        executeSelect(sql);
    }

    public void getBooksAfterYear(int year) throws SQLException {
        String sql = """
                SELECT id, title, author_id, year_of_publication
                FROM books
                WHERE year_of_publication > %d
                ORDER BY year_of_publication ASC
                """.formatted(year);

        executeSelect(sql);
    }

    public void updateRandomBookTitleInShop(
            String shopName, String newTitle) throws SQLException {

        String sql = """
                UPDATE books
                SET title = '%s'
                WHERE id = (
                    SELECT sb.book_id
                    FROM shops_books sb
                    JOIN shops s ON s.id = sb.shop_id
                    WHERE s.name = '%s'
                    ORDER BY RANDOM()
                    LIMIT 1
                )
                """.formatted(escapeSql(newTitle), escapeSql(shopName));

        executeUpdate(sql);
    }

    public void getBooksByAuthorLastNameInitial(char letter)
            throws SQLException {

        String sql = """
                SELECT b.id, b.title, a.first_name, a.last_name,
                       b.year_of_publication
                FROM books b
                JOIN authors a ON a.id = b.author_id
                WHERE a.last_name LIKE '%s%%'
                ORDER BY a.last_name, b.title
                """.formatted(escapeSql(String.valueOf(letter)));

        executeSelect(sql);
    }

    public void updateAuthorsFirstNameByShop(
            String shopName, String newFirstName) throws SQLException {

        String sql = """
                UPDATE authors
                SET first_name = '%s'
                WHERE id IN (
                    SELECT DISTINCT b.author_id
                    FROM books b
                    JOIN shops_books sb ON sb.book_id = b.id
                    JOIN shops s ON s.id = sb.shop_id
                    WHERE s.name = '%s'
                )
                """.formatted(
                escapeSql(newFirstName),
                escapeSql(shopName)
        );

        executeUpdate(sql);
    }

    public void getBookCountByAuthor() throws SQLException {
        String sql = """
                SELECT a.id, a.first_name, a.last_name,
                       (
                           SELECT COUNT(*)
                           FROM books b
                           WHERE b.author_id = a.id
                       ) AS book_count
                FROM authors a
                ORDER BY a.last_name, a.first_name
                """;

        executeSelect(sql);
    }

    private void executeSelect(String sql) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            int columns = resultSet.getMetaData().getColumnCount();

            for (int column = 1; column <= columns; column++) {
                System.out.print(
                        resultSet.getMetaData().getColumnName(column) + "\t"
                );
            }
            System.out.println();

            while (resultSet.next()) {
                for (int column = 1; column <= columns; column++) {
                    System.out.print(resultSet.getString(column) + "\t");
                }
                System.out.println();
            }
        }
    }

    private void executeUpdate(String sql) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            int affectedRows = statement.executeUpdate(sql);
            System.out.println("Изменено строк: " + affectedRows);
        }
    }

    private String escapeSql(String value) {
        return value.replace("'", "''");
    }
}
