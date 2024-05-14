package com.mycompany.ps;

import com.mycompany.ps.api.Auth;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.mycompany.ps.JuegoLocal;
<<<<<<< Updated upstream
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
=======
import com.mycompany.ps.Juego;
>>>>>>> Stashed changes

public class Perfil {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label emailLabel;
    
    @FXML
    private ImageView animationImageView;

    @FXML
    public void initialize() {
        // Cargar la imagen de la animación
        Image animationImage = new Image(getClass().getResourceAsStream("/images/coin3.gif"));
        
        // Establecer la imagen en el ImageView
        animationImageView.setImage(animationImage);
        
        // Crear una transición de traducción para mover la imagen horizontalmente
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(5), animationImageView);
        translateTransition.setFromX(-60); // Posición inicial en x
        translateTransition.setFromY(0); // Posición inicial en x
        //translateTransition.setToX(0); // Posición final en x
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE); // Repetir indefinidamente
        translateTransition.setAutoReverse(true); // Revertir automáticamente
        translateTransition.play(); // Iniciar la animación
    
        try {
            // Obtener el nombre de usuario y correo electrónico utilizando las funciones de Auth.java
            // String token = "tu_token_de_autenticacion"; // Reemplaza esto con tu token de autenticación real
            String username = Auth.devolverNombre();
            String email = Auth.obtenerCorreoUsuario(username);

            // Establecer el nombre de usuario y correo electrónico en las etiquetas correspondientes
            usernameLabel.setText(username);
            emailLabel.setText(email);
        } catch (IOException e) {
            // Manejar cualquier error de IO
            e.printStackTrace();
        }
    }
    
    @FXML
    public void cambiar1(){
        JuegoLocal.cambiarSkin("cards");
        Juego.cambiarSkin("cards");
    }
    
    @FXML
    public void cambiar2(){
        JuegoLocal.cambiarSkin("cards2");
        Juego.cambiarSkin("cards2");
    }
    
    @FXML
    public void cambiar3(){
        JuegoLocal.cambiarSkin("cards3");
    }
    
    @FXML
    private void volver() throws IOException {
        App.setRoot("secondary");
    }
}
