package com.mycompany.ps;

import java.io.IOException;
import com.mycompany.ps.api.http.HttpRequest;
import com.mycompany.ps.api.http.HttpResponse;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Bienvenida {

    @FXML
    private Button primaryButton;
    
    @FXML
    private ImageView animationImageView;
    
    @FXML
    private VBox vboxMenu;

    public void initialize() {
        // Crear una animación de fundido (fade-in) para el VBox
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), vboxMenu);
        fadeIn.setFromValue(0.5); // Comienza desde invisible
        fadeIn.setToValue(1);   // Hasta completamente visible
        fadeIn.play(); // Inicia la animación
    }
    
    @FXML
    private void switchToSecondary() throws IOException {
        // Ejemplo petición GET
        // curl -X 'GET' \
        // 'http://localhost:8000/users/' \
        // -H 'accept: application/json'
        try {
            HttpResponse response = HttpRequest.GET("http://64.225.78.184:8000/users/", new String[] { "accept: application/json" });
            System.out.println(response.getBody());
        } catch (IOException e) {
            System.out.println("error haciendo get (no pasa nada solo son pruebas)");
        }
        App.setRoot("isor");
    }
}