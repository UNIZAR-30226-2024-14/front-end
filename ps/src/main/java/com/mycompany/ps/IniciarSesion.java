package com.mycompany.ps;

import com.mycompany.ps.api.Auth;
import javafx.fxml.FXML;
import java.io.IOException;
import java.util.Map;

import javafx.scene.control.TextField;

public class IniciarSesion {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField contrasenaField;


    @FXML
    private void irAMenu() throws IOException {
        String username = usernameField.getText();
        String pwd = contrasenaField.getText();
        Map response = Auth.login(username, pwd);
        if ((int) response.get("code") != 200) {
            System.out.println("Error al iniciar sesion");
            System.out.println("Razon: " + response.get("detail"));
            return;
        } else {
            System.out.println("inicio exitoso");
            System.out.println("Token: " + response.get("access_token"));
        }
        App.setRoot("exito_ir");
    }
}
