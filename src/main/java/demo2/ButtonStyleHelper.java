package com.example.demo2;

import javafx.scene.control.Button;

public class ButtonStyleHelper {

    public static void applyButtonHoverStyle(Button button) {
        button.setStyle("-fx-background-color: #6a11cb; -fx-background-radius: 15; -fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: white;");
        button.setScaleX(1.05);
        button.setScaleY(1.05);
    }

    public static void resetButtonStyle(Button button) {
        button.setStyle("-fx-background-color: #ff8c00; -fx-background-radius: 15; -fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: white;");
        button.setScaleX(1.0);
        button.setScaleY(1.0);
    }
}
