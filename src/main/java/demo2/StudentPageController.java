package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;
import java.time.LocalDate;

public class StudentPageController {
    @FXML
    private Button buyButton;
    @FXML
    private ListView<String> bookListView;

    private String loggedInStudentName;

    public void setLoggedInStudentName(String studentName) {
        this.loggedInStudentName = studentName;
    }

    @FXML
    private void initialize() {
        loadAvailableBooks();
    }

    private void loadAvailableBooks() {
        String query = "SELECT title FROM books WHERE number_of_copies > 0";

        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            bookListView.getItems().clear();

            while (resultSet.next()) {
                String bookTitle = resultSet.getString("title");
                bookListView.getItems().add(bookTitle);
            }

        } catch (SQLException e) {
            showError("Error loading books: " + e.getMessage());
        }
    }

    @FXML
    private void handleBuyBook(ActionEvent event) {
        String selectedBook = bookListView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            showSuccessMessage("You have successfully borrowed " + selectedBook + "!");
            updateBookStatus(selectedBook);
        } else {
            showError("Please select a book to borrow.");
        }
    }

    private void updateBookStatus(String bookTitle) {
        String selectBookQuery = "SELECT id, price FROM books WHERE title = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(selectBookQuery)) {

            preparedStatement.setString(1, bookTitle);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                double bookPrice = resultSet.getDouble("price");

                LocalDate dueDate = LocalDate.now().plusDays(7);

                String insertBorrowedBookQuery = "INSERT INTO books_borrowed (title, student_name, borrowed_date, due_date, price) " +
                        "VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement insertStatement = connection.prepareStatement(insertBorrowedBookQuery)) {
                    insertStatement.setString(1, bookTitle);
                    insertStatement.setString(2, loggedInStudentName);
                    insertStatement.setTimestamp(3, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
                    insertStatement.setDate(4, Date.valueOf(dueDate));
                    insertStatement.setDouble(5, bookPrice);

                    int rowsAffected = insertStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        updateBookCopies(bookId);
                    }
                }

            } else {
                showError("The selected book does not exist or has no available copies.");
            }

        } catch (SQLException e) {
            showError("Error borrowing book: " + e.getMessage());
        }
    }

    private void updateBookCopies(int bookId) {
        String updateQuery = "UPDATE books SET number_of_copies = number_of_copies - 1 WHERE id = ? AND number_of_copies > 0";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, bookId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                showError("Book could not be borrowed. It may no longer be available.");
            }
        } catch (SQLException e) {
            showError("Error updating book copies: " + e.getMessage());
        }
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-font-size: 12px; -fx-background-color: #0ec239;");
        alert.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-font-size: 12px; -fx-background-color: #f8d7da;");
        alert.show();
    }
}
