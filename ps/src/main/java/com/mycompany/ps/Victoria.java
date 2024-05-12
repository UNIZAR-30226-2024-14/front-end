package com.mycompany.ps;

import java.io.IOException;
import javafx.animation.TranslateTransition;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Victoria {

    @FXML
    private ImageView animationImageView;

    @FXML
    private void initialize() {
        // Cargar la imagen de la animación
        Image animationImage = new Image(getClass().getResourceAsStream("/images/conf.gif"));
        
        // Establecer la imagen en el ImageView
        animationImageView.setImage(animationImage);
        
        // Crear una transición de traducción para mover la imagen horizontalmente
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(5), animationImageView);
        translateTransition.setFromX(0); // Posición inicial en x
        translateTransition.setFromY(0); // Posición inicial en x
        translateTransition.setToX(0); // Posición final en x
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE); // Repetir indefinidamente
        translateTransition.setAutoReverse(true); // Revertir automáticamente
        translateTransition.play(); // Iniciar la animación
    }
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("elegir_juego");
    }
}