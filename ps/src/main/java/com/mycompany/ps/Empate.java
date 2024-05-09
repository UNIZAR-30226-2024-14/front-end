package com.mycompany.ps;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class Empate {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("elegir_juego");
    }
}