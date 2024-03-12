package com.mycompany.ps;

import java.io.IOException;

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
        }
        String token = Auth.register(username, email, pwd1);
        System.out.println(token);
        App.setRoot("exito_registro");
    }
}
