package com.mycompany.ps;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Juego {
    
    @FXML
    private VBox vbox; // Referencia al VBox

    @FXML
    private Button comienzo; // Referencia al botón

    @FXML
    private void comenzarPartida() {
        // Cambiar el fondo a verde
        vbox.setStyle("-fx-background-color: green;");

        // Ocultar el botón
        comienzo.setVisible(false);
    }
    
    @FXML
    private void exitApplication() {
        Platform.exit();
    }
}