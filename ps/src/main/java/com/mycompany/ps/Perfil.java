package com.mycompany.ps;

import com.mycompany.ps.api.Auth;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Perfil {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    public void initialize() {
        try {
            // Obtener el nombre de usuario y correo electrónico utilizando las funciones de Auth.java
            // String token = "tu_token_de_autenticacion"; // Reemplaza esto con tu token de autenticación real
            String username = Auth.obtenerNombreUsuario();
            String email = Auth.obtenerCorreoUsuario();

            // Establecer el nombre de usuario y correo electrónico en las etiquetas correspondientes
            usernameLabel.setText(username);
            emailLabel.setText(email);
        } catch (IOException e) {
            // Manejar cualquier error de IO
            e.printStackTrace();
        }
    }
}
