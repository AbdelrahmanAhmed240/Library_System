package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.*;
import java.util.Date;

public class BorrowedBooksController {

    @FXML
    private TableView<BorrowedBook> bookTableView;
    @FXML
    private TableColumn<BorrowedBook, String> titleColumn;
    @FXML
    private TableColumn<BorrowedBook, String> studentColumn;
    @FXML
    private TableColumn<BorrowedBook, Double> priceColumn;
    @FXML
    private TableColumn<BorrowedBook, Date> borrowedDateColumn;
    @FXML
    private TableColumn<BorrowedBook, Date> dueDateColumn;
    @FXML
    private Button backButton;

    private ObservableList<BorrowedBook> borrowedBooks = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        borrowedDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        backButton.setOnAction(this::handleBack);

        loadBorrowedBooksFromDatabase();

        bookTableView.setItems(borrowedBooks);
    }

    private void loadBorrowedBooksFromDatabase() {
        String sql = "SELECT title, student_name, price, borrowed_date, due_date FROM books_borrowed";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String studentName = resultSet.getString("student_name");
                double price = resultSet.getDouble("price");
                Date borrowedDate = resultSet.getDate("borrowed_date");
                Date dueDate = resultSet.getDate("due_date");

                BorrowedBook borrowedBook = new BorrowedBook(title, studentName, price, borrowedDate, null, dueDate);
                borrowedBooks.add(borrowedBook);
            }
        } catch (SQLException ex) {
            showError("Database error: " + ex.getMessage());
        }
    }

    public void addBorrowedBook(String title, String studentName, double price, Date dueDate, Date returnDate, Date borrowedDate) {
        String sql = "INSERT INTO books_borrowed (title, student_name, price, borrowed_date, due_date, return_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, studentName);
            preparedStatement.setDouble(3, price);
            preparedStatement.setDate(4, new java.sql.Date(borrowedDate.getTime()));
            preparedStatement.setDate(5, new java.sql.Date(dueDate.getTime()));
            preparedStatement.setDate(6, returnDate != null ? new java.sql.Date(returnDate.getTime()) : null);

            preparedStatement.executeUpdate();

            borrowedBooks.add(new BorrowedBook(title, studentName, price, borrowedDate, returnDate, dueDate));
            bookTableView.refresh();
        } catch (SQLException ex) {
            showError("Failed to add borrowed book: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        showAlert(Alert.AlertType.WARNING, "Error", message, null);
    }

    private void showSuccess(String message) {
        showAlert(Alert.AlertType.INFORMATION, "Success", message, "#0ec239");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message, String backgroundColor) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        if (backgroundColor != null) {
            alert.getDialogPane().setStyle("-fx-font-size: 12px; -fx-background-color: " + backgroundColor + ";");
        }
        alert.show();
    }

    public void handleBack(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/librarianfxml.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Librarian Page");
        } catch (IOException ex) {
            showError("Failed to load the page: " + ex.getMessage());
        }
    }
}
