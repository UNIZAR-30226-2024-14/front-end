package com.mycompany.ps;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class ISoR {
    
    @FXML
    private VBox vboxMenu;
    
    public void initialize() {
        // Crear una animación de fundido (fade-in) para el VBox
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), vboxMenu);
        fadeIn.setFromValue(0.5); // Comienza desde invisible
        fadeIn.setToValue(1);   // Hasta completamente visible
        fadeIn.play(); // Inicia la animación
    }
    
    /*public void initialize() {
        // Crear una transición de traducción para mover el VBox
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(5), vboxMenu);
        translateTransition.setFromY(-vboxMenu.getHeight()); // Comienza desde arriba de la pantalla
        translateTransition.setToY(0);   // Hasta su posición original
        translateTransition.play(); // Inicia la animación
    }*/
    
    /*public void initialize() {
        // Crear una animación de escala para el VBox
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), vboxMenu);
        scaleTransition.setFromX(0); // Comienza desde invisible
        scaleTransition.setToX(1);   // Hasta su escala original en el eje X
        scaleTransition.setFromY(0); // Comienza desde invisible
        scaleTransition.setToY(1);   // Hasta su escala original en el eje Y
        scaleTransition.play(); // Inicia la animación
    }*/
    
    /*public void initialize() {
        // Crear una animación de rotación para el VBox
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), vboxMenu);
        rotateTransition.setFromAngle(0); // Comienza desde una rotación de 0 grados
        rotateTransition.setToAngle(360); // Gira 360 grados
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE); // Repetir indefinidamente
        rotateTransition.setAutoReverse(true); // Revertir automáticamente
        rotateTransition.play(); // Inicia la animación
    }*/

    /*public void initialize() {
        // Crear una animación de transición de relleno para el VBox
        FillTransition fillTransition = new FillTransition(Duration.seconds(2), vboxMenu, Color.TRANSPARENT, Color.WHITE);
        fillTransition.play(); // Inicia la animación
    }*/
    
    /*public void initialize() {
        // Crear el camino de la transición
        Path path = new Path();
        path.getElements().add(new MoveTo(0, 0)); // Mover desde el origen
        path.getElements().add(new LineTo(200, 200)); // Hasta (100, 100)

        // Crear una transición de trayectoria para el VBox
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(0.5)); // Duración de la transición
        pathTransition.setPath(path); // Establecer el camino
        pathTransition.setNode(vboxMenu); // Establecer el nodo a animar
        pathTransition.setCycleCount(1); // Número de ciclos de la transición
        pathTransition.setAutoReverse(false); // No revertir automáticamente

        // Iniciar la animación
        pathTransition.play();
    }*/
    
    @FXML
    private void iniciarSesion() throws IOException {
        App.setRoot("iniciar_sesion");
    }

    @FXML
    private void registro() throws IOException {
        App.setRoot("registro");
    }
}
