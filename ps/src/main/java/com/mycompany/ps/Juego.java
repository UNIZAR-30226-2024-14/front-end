package com.mycompany.ps;

import com.mycompany.ps.api.Auth;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import org.java_websocket.client.WebSocketClient;

public class Juego {

    private static final String ws_uri = "ws://64.225.78.184:8000";
    private static final String blackjack_uri = "/ws/blackjack/";
    private static final String chat_uri = "/ws/chat/";

    @FXML
    private VBox vbox; // Referencia al VBox

    @FXML
    private Button comienzo; // Referencia al botón
    
    @FXML
    private Button enviar;

    @FXML
    private HBox dealerCardBox; // Donde se mostrarán las cartas del crupier

    @FXML
    private HBox playerCardBox1; // Donde se mostrarán las cartas del jugador 1

    @FXML
    private HBox playerCardBox2; // Donde se mostrarán las cartas del jugador 2

    @FXML
    private HBox playerCardBox3; // Donde se mostrarán las cartas del jugador 3

    @FXML
    private HBox playerCardBox4; // Donde se mostrarán las cartas del jugador 4

    @FXML
    private Label puntosJugador1; // Etiqueta para mostrar puntos del jugador 1

    @FXML
    private Label puntosJugador2; // Etiqueta para mostrar puntos del jugador 2

    @FXML
    private Label puntosJugador3; // Etiqueta para mostrar puntos del jugador 3

    @FXML
    private Label puntosJugador4; // Etiqueta para mostrar puntos del jugador 4

    @FXML
    private Label puntosCrupier;

    @FXML
    private Button hitButton; // Botón "Hit"

    @FXML
    private Button standButton; // Botón "Stand"

    private int puntosDealer = 0; // Para almacenar los puntos del crupier
    private int puntosJugador = 0; // Puedes obtenerlo del jugador activo (por ejemplo, jugador 1)

    private Random random = new Random();

    private int[] puntos = {0, 0, 0, 0}; // Para almacenar los puntos de cada jugador

    private static int id;
    private static String token;

    WebSocketClient client;
    
    @FXML
    private Label chatMessage1;

    @FXML
    private Label chatMessage2;
    
    @FXML
    private TextField chatInput;

    // Método para mostrar un nuevo mensaje del chat
    void mostrarMensajeChat(String mensaje) {
        // Encuentra la primera etiqueta de mensaje de chat vacía y establece el mensaje
        if (chatMessage1.getText().isEmpty()) {
            chatMessage1.setText(mensaje);
        } else if (chatMessage2.getText().isEmpty()) {
            chatMessage2.setText(mensaje);
        } else {
            // Si ambas etiquetas están ocupadas, mueve el mensaje anterior hacia arriba y muestra el nuevo mensaje
            chatMessage1.setText(chatMessage2.getText());
            chatMessage2.setText(mensaje);
        }
    }
    
    @FXML
    private void enviarMensaje() {
        // Obtener el mensaje del campo de texto
        String mensaje = chatInput.getText();

        // Verificar que el mensaje no esté vacío antes de enviarlo
        if (!mensaje.isEmpty()) {
            // Enviar el mensaje al servidor de chat a través del WebSocket
            if (client != null && client.isOpen()) {
                String msg = "{\"message\": \"" + mensaje + "\"}";
                client.send(msg);
                //Platform.runLater(() -> mostrarMensajeChat(mensaje));

                // Limpiar el campo de texto después de enviar el mensaje
                chatInput.clear();
            }
        }
    }

    // Método estático para configurar el ID y el token
    public static void configurarJuego(int id, String token) {
        Juego.id = id;
        Juego.token = token;
    }

    private ImageView createCardImageView(Image cardImage) {
        // Crea un ImageView con tamaño uniforme y preserva la proporción
        ImageView imageView = new ImageView(cardImage);
        imageView.setFitWidth(120);  // Ajusta el ancho
        imageView.setFitHeight(180); // Ajusta el alto
        imageView.setPreserveRatio(true); // Mantiene la proporción
        return imageView;
    }

    @FXML
    private void comenzarPartida() {

        // Crear un hilo para manejar la funcionalidad del chat simultáneamente
        Thread chatThread = new Thread(() -> {
            try {
                client = new ChatClient(String.valueOf(id), token, ws_uri + chat_uri, this);
                client.connect();

                System.out.println("Connecting to websocket server...");
                while (!client.isOpen() && !client.isClosed()) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace(); // Manejo de la excepción
                    }
                }

                if (client.isClosed()) {
                    System.out.println("Connection failed.");
                    System.exit(1);
                }

                System.out.println("Connected to chat server.");

                // No necesitas leer desde la consola, simplemente enviar mensajes utilizando el método enviarMensaje()

            } catch (Exception e) {
                e.printStackTrace(); // Manejo de la excepción
            }
        });
        chatThread.start();


        // Cambiar el fondo a verde para indicar el inicio de la partida
        vbox.setStyle("-fx-background-image: url('images/Tapete3.png');");

        // Ocultar el botón de comienzo
        comienzo.setVisible(false);
        hitButton.setVisible(true);
        standButton.setVisible(true);
        puntosJugador1.setVisible(true);
        puntosJugador2.setVisible(true);
        puntosJugador3.setVisible(true);
        puntosJugador4.setVisible(true);
        puntosCrupier.setVisible(true);
        chatMessage1.setVisible(true);
        chatMessage2.setVisible(true);
        chatInput.setVisible(true);
        enviar.setVisible(true);

        // Asignar dos cartas para cada jugador y calcular los puntos
        HBox[] playerCardBoxes = {playerCardBox1, playerCardBox2, playerCardBox3, playerCardBox4};
        Label[] puntosLabels = {puntosJugador1, puntosJugador2, puntosJugador3, puntosJugador4};

        Image cartaCrupier = getRandomCardImage();
        dealerCardBox.getChildren().add(createCardImageView(cartaCrupier));
        puntosDealer += getCardValue(cartaCrupier);
        puntosCrupier.setText("Puntos: " + puntosDealer);

        for (int i = 0; i < playerCardBoxes.length; i++) {
            HBox playerCardBox = playerCardBoxes[i];
            Label puntosLabel = puntosLabels[i];

            Image playerCard1 = getRandomCardImage();
            Image playerCard2 = getRandomCardImage();

            playerCardBox.getChildren().add(createCardImageView(playerCard1));
            playerCardBox.getChildren().add(createCardImageView(playerCard2));

            puntos[i] = getCardValue(playerCard1) + getCardValue(playerCard2);
            puntosLabel.setText("Puntos: " + puntos[i]);
        }
    }

    @FXML
    private void hitAction() {
        HBox playerCardBox = playerCardBox1; // Modificar para el jugador adecuado
        Label puntosLabel = puntosJugador1; // Modificar para el jugador adecuado

        // Añadir una carta y actualizar puntos
        Image nuevaCarta = getRandomCardImage();
        playerCardBox.getChildren().add(createCardImageView(nuevaCarta));

        int valorCarta = getCardValue(nuevaCarta);
        puntos[0] += valorCarta;
        puntosLabel.setText("Puntos: " + puntos[0]);

        // Verificar si el jugador pierde o gana
        if (puntos[0] > 21) {
            // Mostrar mensaje de derrota
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Derrota");
            alert.setHeaderText(null);
            alert.setContentText("Te has pasado de 21 puntos. Has perdido.");
            alert.showAndWait();
        } else if (puntos[0] == 21) {
            // Mostrar mensaje de victoria
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Victoria");
            alert.setHeaderText(null);
            alert.setContentText("¡Has alcanzado 21 puntos! Has ganado.");
            alert.showAndWait();
        }
    }

    @FXML
    private void standAction() {
        // Lógica para manejar el botón "Stand"

        // Sacar cartas para el crupier hasta llegar a un mínimo de 17 puntos
        while (puntosDealer < 17) {
            Image nuevaCarta = getRandomCardImage();
            dealerCardBox.getChildren().add(createCardImageView(nuevaCarta));
            puntosDealer += getCardValue(nuevaCarta);
        }

        // Actualizar la etiqueta de puntos del crupier
        puntosCrupier.setText("Puntos: " + puntosDealer);

        // Determinar el resultado de la partida
        Alert alert;
        if (puntosDealer > 21) {
            // Crupier se pasa de 21, jugador gana
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Victoria");
            alert.setHeaderText(null);
            alert.setContentText("¡El crupier se pasó de 21! Has ganado.");
        } else if (puntosDealer == puntosJugador) {
            // Empate entre crupier y jugador
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empate");
            alert.setHeaderText(null);
            alert.setContentText("¡Es un empate!");
        } else if (puntosDealer > puntosJugador) {
            // Crupier tiene más puntos que el jugador
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Derrota");
            alert.setHeaderText(null);
            alert.setContentText("El crupier tiene más puntos. Has perdido.");
        } else {
            // Crupier tiene menos puntos que el jugador
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Victoria");
            alert.setHeaderText(null);
            alert.setContentText("¡Has ganado!");
        }

        alert.showAndWait();
    }

    @FXML
    private void exitApplication() {
        Platform.exit();
    }

    private Image getRandomCardImage() {
        // Generar un número aleatorio entre 1 y 13
        int cardNumber = random.nextInt(13) + 1;

        // Generar un palo aleatorio (corazones, tréboles, diamantes, picas)
        String[] suits = {"hearts", "clubs", "diamonds", "spades"};
        String suit = suits[random.nextInt(4)];

        // Construir el nombre de la imagen (por ejemplo, "1_of_hearts.jpg")
        String cardImageName = cardNumber + "_of_" + suit + ".jpg";

        // Cargar y devolver la imagen
        return new Image(getClass().getResource("/images/cards/" + cardImageName).toExternalForm());
    }

    private int getCardValue(Image cardImage) {
        // Extraer el nombre del archivo de imagen y determinar el valor de la carta
        String imageUrl = cardImage.getUrl();
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

        String cardName = fileName.split("_")[0];
        int cardNumber = Integer.parseInt(cardName);

        return cardNumber > 10 ? 10 : cardNumber;
    }
}
