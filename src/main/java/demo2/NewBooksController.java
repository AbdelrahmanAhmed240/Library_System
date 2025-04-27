package com.example.demo2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NewBooksController {
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField priceField;
    @FXML private TextField copiesField;
    @FXML private TextField isbnField;
    @FXML private Button addButton;
    @FXML private Button backButton;

    @FXML
    public void initialize() {
        this.addButton.setOnMouseEntered(event -> ButtonStyleHelper.applyButtonHoverStyle(addButton));
        this.addButton.setOnMouseExited(event -> ButtonStyleHelper.resetButtonStyle(addButton));
        addButton.setOnAction(this::handleAddBook);
        backButton.setOnAction(this::handleBack);

        titleField.setPromptText("Enter the title of the book");
        authorField.setPromptText("Enter the author of the book");
        priceField.setPromptText("Enter the price of the book");
        copiesField.setPromptText("Enter the number of copies");
        isbnField.setPromptText("Enter the ISBN of the book");
    }

    public void handleAddBook(ActionEvent e) {
        String title = titleField.getText();
        String author = authorField.getText();
        String price = priceField.getText();
        String copies = copiesField.getText();
        String isbn = isbnField.getText();

        if (isValidInput(title, author, price, copies, isbn)) {
            try {
                double priceValue = Double.parseDouble(price);
                int copiesValue = Integer.parseInt(copies);

                try (Connection connection = DatabaseConnection.connect()) {
                    String sql = "INSERT INTO books (id, title, author, price, isbn, number_of_copies) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    preparedStatement.setString(1, title);
                    preparedStatement.setString(2, author);
                    preparedStatement.setDouble(3, priceValue);
                    preparedStatement.setString(4, isbn);
                    preparedStatement.setInt(5, copiesValue);

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        showInfo("Book added successfully!");
                    } else {
                        showError("Failed to add the book to the database.");
                    }
                } catch (SQLException ex) {
                    showError("Database error: " + ex.getMessage());
                }
            } catch (NumberFormatException var11) {
                showError("Price and copies must be numbers.");
            }
        } else {
            showError("Please fill in all fields.");
        }
    }

    public void handleBack(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/librarianfxml.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Librarian page");
        } catch (IOException ex) {
            showError("Failed to load the page: " + ex.getMessage());
        }
    }

    private boolean isValidInput(String title, String author, String price, String copies, String isbn) {
        return !title.isEmpty() && !author.isEmpty() && !price.isEmpty() && !copies.isEmpty() && !isbn.isEmpty();
    }

    private void showError(String message) {
        showAlert(AlertType.WARNING, "Error", message);
    }

    private void showInfo(String message) {
        showAlert(AlertType.INFORMATION, "Information", message);
    }

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.getDialogPane().setStyle("-fx-font-size: 12px;");
        alert.show();
    }
}
