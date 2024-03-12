package com.mycompany.ps;

import com.mycompany.ps.api.Auth;
import javafx.fxml.FXML;
import java.io.IOException;
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
        String token = Auth.iniciarSesion(username, pwd);
        System.out.println(token);
        App.setRoot("exito_ir");
    }
}
