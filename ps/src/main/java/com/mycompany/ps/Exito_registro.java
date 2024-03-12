package com.mycompany.ps;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class Exito_registro {

    @FXML
    private void irAMenu() throws IOException {
        App.setRoot("secondary");
    }
}