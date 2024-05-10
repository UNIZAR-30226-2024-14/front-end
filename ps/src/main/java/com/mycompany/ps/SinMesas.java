package com.mycompany.ps;

import java.io.IOException;

import javafx.fxml.FXML;

public class SinMesas {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("crear_unir");
    }
}