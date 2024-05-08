package com.mycompany.ps;

import java.io.IOException;
import java.util.Map;

import com.mycompany.ps.api.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class Registro {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField correoField;

    @FXML
    private TextField contrasena1Field;

    @FXML
    private TextField contrasena2Field;

    @FXML
    private void irAMenu() throws IOException {
        String username = usernameField.getText();
        String email = correoField.getText();
        String pwd1 = contrasena1Field.getText();
        String pwd2 = contrasena2Field.getText();

        // Si las contraseñas no coinciden, muestra un mensaje popup
        if (!pwd1.equals(pwd2)) {
            Alert alert = new Alert(AlertType.ERROR); // Tipo de alerta de error
            alert.setTitle("Error");
            alert.setHeaderText("Las contraseñas no coinciden");
            alert.showAndWait(); // Muestra el popup y espera a que se cierre
            return;
        }

        // Código de registro y verificación
        Map<String, Object> response = Auth.register(username, email, pwd1);
        if ((int) response.get("code") != 200) {
            System.out.println("Error al registrar");
            System.out.println("Razón: " + response.get("detail"));
            App.setRoot("fallo_registro");
        } else {
            System.out.println("Registro exitoso");
            System.out.println("Token: " + response.get("access_token"));
            App.setRoot("exito_registro");
        }
    }

    @FXML
    private void irABienvenida() throws IOException {
        App.setRoot("isor");
    }
}
