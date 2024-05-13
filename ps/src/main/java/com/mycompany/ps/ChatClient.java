/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ps;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import javafx.application.Platform;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatClient extends WebSocketClient {

    private Juego juegoController; // Referencia al controlador Juego

    public ChatClient(String room, String access_token, String base_uri, Juego juego) throws Exception {
        super(new URI(base_uri + room + "?access_token=" + access_token));
        this.juegoController = juego;
    }

    public ChatClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        System.out.print("\r");
        System.out.println("received message: " + message);
        System.out.print(">>> ");
        
        try {
            // Crear un ObjectMapper para analizar el JSON
            ObjectMapper mapper = new ObjectMapper();
            // Leer el JSON y convertirlo en un árbol de nodos
            JsonNode rootNode = mapper.readTree(message);
            // Obtener el valor asociado con la clave "message"
            String mensaje = rootNode.get("message").asText();
            // Mostrar el mensaje
            Platform.runLater(() -> juegoController.mostrarMensajeChat(mensaje));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }
}
