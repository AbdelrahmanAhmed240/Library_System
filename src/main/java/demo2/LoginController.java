package com.example.demo2;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button loginButton;
    @FXML
    private Button signupButton;
    @FXML
    private ChoiceBox<String> roleChoiceBox;
    @FXML
    private TextField TxtFieldUsername;
    @FXML
    private PasswordField TxtFieldPassword;

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @FXML
    private void initialize() {
        this.roleChoiceBox.getItems().addAll("Student", "Librarian");

        this.loginButton.setOnMouseEntered(event -> ButtonStyleHelper.applyButtonHoverStyle(loginButton));
        this.loginButton.setOnMouseExited(event -> ButtonStyleHelper.resetButtonStyle(loginButton));

        this.signupButton.setOnMouseEntered(event -> ButtonStyleHelper.applyButtonHoverStyle(signupButton));
        this.signupButton.setOnMouseExited(event -> ButtonStyleHelper.resetButtonStyle(signupButton));

        this.loginButton.setOnAction(this::handleLoginButton);
        this.signupButton.setOnAction(this::handleSignupButton);
    }

    private void handleLoginButton(ActionEvent event) {
        String selectedRole = this.roleChoiceBox.getValue();
        String username = this.TxtFieldUsername.getText().trim();
        String password = this.TxtFieldPassword.getText().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            try (Connection connection = DatabaseConnection.connect()) {
                String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String role = rs.getString("role");
                        String studentName = rs.getString("username");

                        if (role.equals("Student")) {
                            navigateToStudentPage(studentName);
                        } else if (role.equals("Librarian")) {
                            navigateToLibrarianPage();
                        } else {
                            showError("Invalid role selected!");
                            logger.warning("Invalid role for username: " + username);
                        }
                    } else {
                        showError("Invalid username or password!");
                        logger.warning("Failed login attempt for username: " + username);
                    }
                }
            } catch (SQLException e) {
                showError("Database connection error: " + e.getMessage());
                logger.severe("SQL error: " + e.getMessage());
            }
        } else {
            showError("Username and password cannot be empty!");
        }
    }

    private void handleSignupButton(ActionEvent event) {
        String username = this.TxtFieldUsername.getText().trim();
        String password = this.TxtFieldPassword.getText().trim();
        String selectedRole = this.roleChoiceBox.getValue();

        if (!username.isEmpty() && !password.isEmpty() && selectedRole != null) {
            try (Connection connection = DatabaseConnection.connect()) {
                String checkUserQuery = "SELECT * FROM Users WHERE username = ?";
                try (PreparedStatement checkStmt = connection.prepareStatement(checkUserQuery)) {
                    checkStmt.setString(1, username);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next()) {
                        showError("Username already exists!");
                        logger.warning("Username already exists during signup: " + username);
                    } else {
                        String insertUserQuery = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertUserQuery)) {
                            insertStmt.setString(1, username);
                            insertStmt.setString(2, password);
                            insertStmt.setString(3, selectedRole);
                            insertStmt.executeUpdate();
                            showSuccessMessage("Sign Up successful! You can now log in with your credentials.");
                            logger.info("User registered: " + username);
                            clearFields();
                        }
                    }
                }
            } catch (SQLException e) {
                showError("Database connection error: " + e.getMessage());
                logger.severe("SQL error during signup: " + e.getMessage());
            }
        } else {
            showError("Username, password, and role cannot be empty!");
        }
    }

    private void navigateToStudentPage(String studentName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/studentfxml.fxml"));
            Parent root = loader.load();


            StudentPageController studentController = loader.getController();

            studentController.setLoggedInStudentName(studentName);

            Stage stage = (Stage) this.loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Student Page");
        } catch (IOException e) {
            showError("Failed to load the Student page: " + e.getMessage());
            logger.severe("Error loading student page: " + e.getMessage());
        }
    }

    private void navigateToLibrarianPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/librarianfxml.fxml"));
            Stage stage = (Stage) this.loginButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Librarian Page");
        } catch (IOException e) {
            showError("Failed to load the Librarian page: " + e.getMessage());
            logger.severe("Error loading librarian page: " + e.getMessage());
        }
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-font-size: 12px; -fx-background-color: #0ec239;");
        alert.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-font-size: 12px; -fx-background-color: #f8d7da;");
        alert.show();
    }

    private void clearFields() {
        TxtFieldUsername.clear();
        TxtFieldPassword.clear();
    }
}
