package com.mycompany.ps;

import java.io.IOException;

import javafx.fxml.FXML;

public class ISoR {
    
    @FXML
    private void iniciarSesion() throws IOException {
        App.setRoot("iniciar_sesion");
    }

    @FXML
    private void registro() throws IOException {
        App.setRoot("registro");
    }
}
