//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo2;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LibrarianController {
    @FXML
    private Button addnewbookButton;
    @FXML
    private Button x;
    @FXML
    private Button Allbooks;

    public LibrarianController() {
    }

    @FXML
    public void initialize() {
        this.addnewbookButton.setOnMouseEntered(event -> ButtonStyleHelper.applyButtonHoverStyle(addnewbookButton));
        this.addnewbookButton.setOnMouseExited(event -> ButtonStyleHelper.resetButtonStyle(addnewbookButton));


        this.x.setOnMouseEntered(event -> ButtonStyleHelper.applyButtonHoverStyle(x));
        this.x.setOnMouseExited(event -> ButtonStyleHelper.resetButtonStyle(x));

        this.Allbooks.setOnMouseEntered(event -> ButtonStyleHelper.applyButtonHoverStyle(Allbooks));
        this.Allbooks.setOnMouseExited(event -> ButtonStyleHelper.resetButtonStyle(Allbooks));

        this.addnewbookButton.setOnAction((event) -> {
            this.handleAddBook();
        });
        this.x.setOnAction((event) -> {
            this.handleBorrowedBooks();
        });
        this.Allbooks.setOnAction((event) -> {
            this.handleBooks();
        });


    }

    @FXML
    public void handleAddBook() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/demo2/NewBooksFXML.fxml"));
            Stage stage = (Stage)this.addnewbookButton.getScene().getWindow();
            stage.setScene(new Scene((Parent)loader.load()));
            stage.setTitle("Add Book");
        } catch (IOException var3) {
            IOException ex = var3;
            this.showError("Failed to load the page: " + ex.getMessage());
        }

    }

    @FXML
    public void handleBorrowedBooks() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/demo2/Borrow.fxml"));
            Stage stage = (Stage)this.x.getScene().getWindow();
            stage.setScene(new Scene((Parent)loader.load()));
            stage.setTitle("Borrowed Books List");
        } catch (IOException var3) {
            IOException ex = var3;
            this.showError("Failed to load the page: " + ex.getMessage());
        }

    }

    @FXML
    public void handleBooks() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/demo2/AllBooks.fxml"));
            Stage stage = (Stage)this.Allbooks.getScene().getWindow();
            stage.setScene(new Scene((Parent)loader.load()));
            stage.setTitle("Students List");
        } catch (IOException var3) {
            IOException ex = var3;
            this.showError("Failed to load the Students List page: " + ex.getMessage());
        }

    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
