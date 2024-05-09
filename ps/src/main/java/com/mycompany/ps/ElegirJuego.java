package com.mycompany.ps;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class ElegirJuego {

    @FXML
    private void jugarLocal() throws IOException {
        App.setRoot("juego_local");
    }

    @FXML
    private void jugarOnline() throws IOException {
        App.setRoot("juego");
    }
}