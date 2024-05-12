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
        int id = Auth.crearMesa();
        Auth.joinMesa(id, Auth.devolverToken());
        while(!Auth.mesaEstaLlena(id, Auth.devolverToken())){
            System.out.println("espero");
        }
        App.setRoot("juego");
    }

    @FXML
    private void unirSala() throws IOException {
        int mesas = Auth.getMesaCount();
        if (mesas == 0) {
            App.setRoot("sin_mesas");
        } else {
            int id = Auth.buscarMesa();
            Auth.joinMesa(id, Auth.devolverToken());
            if(Auth.mesaEstaLlena(id, Auth.devolverToken())){
                System.out.println("esta llena");
            }
            while(!Auth.mesaEstaLlena(id, Auth.devolverToken())){
                System.out.println("espero");
            }
            App.setRoot("juego");
        }

    }
}
