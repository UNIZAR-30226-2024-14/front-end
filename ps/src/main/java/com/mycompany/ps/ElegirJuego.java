package com.mycompany.ps;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ElegirJuego {
    
    @FXML
    private ImageView animationImageView;

    @FXML
    private void initialize() {
    // Cargar la imagen de la animación
    Image animationImage = new Image(getClass().getResourceAsStream("/images/car.gif"));
    
    // Establecer la imagen en el ImageView
    animationImageView.setImage(animationImage);
    
    // Crear una transición de traducción para mover la imagen horizontalmente
    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(20), animationImageView); // Ajusta la duración a 20 segundos
    translateTransition.setFromX(0); // Posición inicial en x
    translateTransition.setFromY(0); // Posición inicial en y
    translateTransition.setToX(0); // Posición final en x
    translateTransition.setToY(0); // Posición final en y
    translateTransition.setCycleCount(TranslateTransition.INDEFINITE); // Repetir indefinidamente
    translateTransition.setAutoReverse(true); // Revertir automáticamente
    
    // Ajusta la opacidad de la imagen
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(10), animationImageView); // Ajusta la duración de la transición de opacidad a 10 segundos
    fadeTransition.setFromValue(0.5); // Opacidad inicial
    fadeTransition.setToValue(0.5); // Opacidad final
    fadeTransition.setCycleCount(FadeTransition.INDEFINITE); // Repetir indefinidamente
    fadeTransition.setAutoReverse(true); // Revertir automáticamente
    
    // Iniciar las animaciones
    translateTransition.play(); 
    fadeTransition.play(); 
}
    
    @FXML
    private void jugarLocal() throws IOException {
        App.setRoot("juego_local");
    }

    @FXML
    private void jugarOnline() throws IOException {
        App.setRoot("crear_unir");
    }
    
    @FXML
    private void volver() throws IOException {
        App.setRoot("secondary");
    }
}