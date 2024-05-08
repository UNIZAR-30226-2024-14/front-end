package com.mycompany.ps;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class Fallo_registro {

    @FXML
    private void irAMenu() throws IOException {
        App.setRoot("registro");
    }
}