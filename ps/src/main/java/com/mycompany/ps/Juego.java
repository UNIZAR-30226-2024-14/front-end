package com.mycompany.ps;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class Juego {

    @FXML
    private void exitApplication() {
        Platform.exit();
    }
}