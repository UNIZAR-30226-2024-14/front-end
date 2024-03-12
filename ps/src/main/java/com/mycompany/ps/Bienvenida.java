package com.mycompany.ps;

import java.io.IOException;

import com.mycompany.ps.api.http.HttpRequest;
import com.mycompany.ps.api.http.HttpResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Bienvenida {

    @FXML
    private Button primaryButton;

    @FXML
    private void switchToSecondary() throws IOException {
        // Ejemplo petición GET
        // curl -X 'GET' \
        // 'http://localhost:8000/users/' \
        // -H 'accept: application/json'
	try {
        	HttpResponse response = HttpRequest.GET("http://64.225.78.184:8000/users/", new String[] { "accept: application/json" });
        	System.out.println(response.getBody());
	} catch (IOException e) {
		System.out.println("error haciendo get (no pasa nada solo son pruebgas)");
	}
        App.setRoot("isor");
    }
}
