package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllBooksController {
    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableColumn<Book, String> bookNameColumn;
    @FXML
    private TableColumn<Book, Double> priceColumn;
    @FXML
    private TableColumn<Book, Integer> copiesColumn;

    @FXML
    private Button backButton;

    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<>("numCopies"));

        backButton.setOnAction(this::handleBack);

        loadBooksFromDatabase();

        bookTableView.setItems(books);
    }

    private void loadBooksFromDatabase() {
        String sql = "SELECT title, price, number_of_copies FROM books";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                double price = resultSet.getDouble("price");
                int numCopies = resultSet.getInt("number_of_copies");

                Book book = BookFactory.createBook(title, price, numCopies);
                books.add(book);
            }
        } catch (SQLException ex) {
            showError("Database error: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.show();
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
}
