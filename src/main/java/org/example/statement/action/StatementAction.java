package org.example.statement.action;

import org.example.statement.dao.StatementDao;

import java.sql.SQLException;

public class StatementAction {

    private final StatementDao statementDao = new StatementDao();

    public void getAllInfo() {
        execute(() -> statementDao.getAllInfo());
    }

    public void getAuthorsSorted() {
        execute(() -> statementDao.getAuthorsSorted());
    }

    public void getBooksAfterYear(int year) {
        execute(() -> statementDao.getBooksAfterYear(year));
    }

    public void updateRandomBookTitleInShop(
            String shopName, String newTitle) {
        execute(() ->
                statementDao.updateRandomBookTitleInShop(shopName, newTitle)
        );
    }

    public void getBooksByAuthorLastNameInitial(char letter) {
        execute(() ->
                statementDao.getBooksByAuthorLastNameInitial(letter)
        );
    }

    public void updateAuthorsFirstNameByShop(
            String shopName, String newFirstName) {
        execute(() ->
                statementDao.updateAuthorsFirstNameByShop(
                        shopName, newFirstName
                )
        );
    }

    public void getBookCountByAuthor() {
        execute(() -> statementDao.getBookCountByAuthor());
    }

    private void execute(SqlOperation operation) {
        try {
            operation.run();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL: " + e.getMessage());
        }
    }

    @FunctionalInterface
    private interface SqlOperation {
        void run() throws SQLException;
    }
}
