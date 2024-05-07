package com.mycompany.ps;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.layout.VBox;

public class ISoR {
    
    @FXML
    private VBox vboxMenu;
    
    public void initialize() {
        // Crear una animación de fundido (fade-in) para el VBox
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), vboxMenu);
        fadeIn.setFromValue(0); // Comienza desde invisible
        fadeIn.setToValue(1);   // Hasta completamente visible
        fadeIn.play(); // Inicia la animación
    }
    
    @FXML
    private void iniciarSesion() throws IOException {
        App.setRoot("iniciar_sesion");
    }

    @FXML
    private void registro() throws IOException {
        App.setRoot("registro");
    }
}
