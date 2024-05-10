package com.mycompany.ps;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class CrearUnir {

    @FXML
    private void crearSala() throws IOException {
        App.setRoot("elegir_juego");
    }

    @FXML
    private void unirSala() throws IOException {
        App.setRoot("elegir_juego");
    }
}