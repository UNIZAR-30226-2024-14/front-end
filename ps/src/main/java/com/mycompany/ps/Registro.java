package com.mycompany.ps;

import java.io.IOException;
import java.util.Map;

import com.mycompany.ps.api.Auth;
import javafx.fxml.FXML;
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
        if (!pwd1.equals(pwd2)) {
            System.out.println("Las contraseñas no coinciden");
            return;
        }
        Map response = Auth.register(username, email, pwd1);
        if ((int) response.get("code") != 200) {
            System.out.println("Error al registrar");
            System.out.println("Razon: " + response.get("detail"));
            return;
        } else {
            System.out.println("Registro exitoso");
            System.out.println("Token: " + response.get("access_token"));
        }
        App.setRoot("exito_registro");
    }
    
    @FXML
    private void irABienvenida() throws IOException {
        App.setRoot("isor");
    }
}
