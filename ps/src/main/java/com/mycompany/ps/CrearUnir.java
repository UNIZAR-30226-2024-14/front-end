package com.mycompany.ps;

import java.io.IOException;

import com.mycompany.ps.api.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CrearUnir {
    
    @FXML
    private Label mesasDisponibles;

    @FXML
    private void crearSala() throws IOException {
        App.setRoot("elegir_juego");
    }

    @FXML
    private void unirSala() throws IOException {
        int mesas = Auth.getMesaCount();
        if (mesas == 0){
            App.setRoot("sin_mesas");
        }
        else{
            App.setRoot("elegir_juego");
        }
        
    }
}