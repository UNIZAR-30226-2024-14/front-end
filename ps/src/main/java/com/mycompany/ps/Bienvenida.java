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
import javafx.util.Duration;


public class Bienvenida {

    @FXML
    private Button primaryButton;
    
    @FXML
    private ImageView animationImageView;

    /*@FXML
    private void initialize() {
        // Cargar la imagen de la animación
        Image animationImage = new Image(getClass().getResourceAsStream("/images/start.gif"));
        
        // Establecer la imagen en el ImageView
        animationImageView.setImage(animationImage);
        
        // Crear una transición de traducción para mover la imagen horizontalmente
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(5), animationImageView);
        translateTransition.setFromX(0); // Posición inicial en x
        translateTransition.setToX(400); // Posición final en x
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE); // Repetir indefinidamente
        translateTransition.setAutoReverse(true); // Revertir automáticamente
        translateTransition.play(); // Iniciar la animación
    }*/
    
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
		System.out.println("error haciendo get (no pasa nada solo son pruebgas)");
	}
        App.setRoot("isor");
    }
}