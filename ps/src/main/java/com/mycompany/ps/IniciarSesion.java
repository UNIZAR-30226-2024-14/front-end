package com.mycompany.ps;

import javafx.fxml.FXML;
import java.io.IOException;

public class IniciarSesion {

    @FXML
    private void irAMenu() throws IOException {
        App.setRoot("secondary");
    }
}
