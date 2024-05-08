package com.mycompany.ps;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class Fallo_IR {

    @FXML
    private void irAMenu() throws IOException {
        App.setRoot("iniciar_sesion");
    }
}