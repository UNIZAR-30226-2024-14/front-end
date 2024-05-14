package com.mycompany.ps;

import static com.mycompany.ps.BlackjackClient.Action.BET;
import static com.mycompany.ps.BlackjackClient.Action.DRAW;
import static com.mycompany.ps.BlackjackClient.Action.END;
import static com.mycompany.ps.BlackjackClient.Action.INFO;
import static com.mycompany.ps.BlackjackClient.Action.NONE;
import static com.mycompany.ps.BlackjackClient.Action.TURN;
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
import java.util.Map;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Juego {

    private static final String ws_uri = "ws://64.225.78.184:8000";
    private static final String blackjack_uri = "/ws/blackjack/";
    private static final String chat_uri = "/ws/chat/";
    private static String nombre;

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

    private static String skin = "cards";

    private int puntosDealer = 0; // Para almacenar los puntos del crupier
    private int puntosJugador = 0; // Puedes obtenerlo del jugador activo (por ejemplo, jugador 1)

    private Random random = new Random();

    private int[] puntos = {0, 0, 0, 0}; // Para almacenar los puntos de cada jugador

    private static int id;
    private static String token;

    WebSocketClient client;
    WebSocketClient clientB;

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

    static void cambiarSkin(String carpeta) {
        skin = carpeta;
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
    public static void configurarJuego(int id, String token, String nombre) {
        Juego.id = id;
        Juego.token = token;
        Juego.nombre = nombre;
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

        iniciarJuego();

//        Thread esperaMesaLlenaThread = new Thread(() -> {
//            try {
//                while(!Auth.mesaEstaLlena(id, token)){
//                    JOptionPane.showMessageDialog(null, "La mesa aún no está llena. Esperando...");
//                    Thread.sleep(1000); // Esperar un segundo antes de volver a verificar
//                }
//                iniciarJuego();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        esperaMesaLlenaThread.start();
    }

    private void iniciarJuego() {
        Scanner scanner = new Scanner(System.in);
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

        Thread bjThread = new Thread(() 
            -> {
            try {

                clientB = new BlackjackClient(String.valueOf(id), token, nombre, ws_uri + blackjack_uri);
                clientB.connect();

                System.out.println("Connecting to websocket server...");
                while (!clientB.isOpen() && !clientB.isClosed()) {
                    Thread.sleep(100);
                }

                if (clientB.isClosed()) {
                    System.out.println("Connection failed.");
                    System.exit(1);
                }

                System.out.println("Connected to BJ server.");

                while (clientB.isOpen()) {

                    BlackjackClient.Pair pair = ((BlackjackClient) clientB).parseMessage();
                    BlackjackClient.Action action = pair.action;
                    var message = pair.map;
                    //System.out.println(message);

                    // Pretty print
                    if (message != null && !message.isEmpty()) {
                        System.out.println("Game state:");
                        for (Map.Entry<?, ?> entry : message.entrySet()) {
                            System.out.println("\t" + entry.getKey() + ": " + entry.getValue());
                        }
                        System.out.println(action);
                    }

                    switch (action) {
                        case BET:
                            System.out.print("Enter bet amount: ");
                            String bet = scanner.nextLine();
                            ;
                            if (bet.equals("pause")) {
                                clientB.send("{\"action\": \"pause\"}");
                                break;
                            }
                            clientB.send("{\"action\": \"bet\", \"value\": \"" + bet + "\"}");
                            break;
                        case TURN:
                            //            System.out.println(message);
                            System.out.print("Enter action (hit/stand): ");
                            String turn = scanner.nextLine();
                            if (turn.equals("pause")) {
                                clientB.send("{\"action\": \"pause\"}");
                                break;
                            }
                            clientB.send("{\"action\": \"" + turn + "\"}");
                            break;
                        case DRAW:
                            System.out.println("Drawing card...");
                            System.out.println(message);
                            break;
                        case END:
                            System.out.println("Game ended.");
                            System.out.println(message);
                            break;
                        case INFO:
                            Platform.runLater(() -> iniciarJuego(message));
                            break;
                        case NONE:
                        default:
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        bjThread.start();
    }

    private void iniciarJuego(Map<String, String> message) {
        // Obtener las cartas de cada jugador del mensaje recibido
        Map<String, List<Map<String, String>>> cards = new HashMap<>();

        for (Map.Entry<String, String> entry : message.entrySet()) {
            String jugador = entry.getKey();
            List<Map<String, String>> playerCards = new ArrayList<>();
            String[] cardStrings = entry.getValue().split(";");

            for (String cardString : cardStrings) {
                String[] cardParts = cardString.split(",");
                Map<String, String> cardMap = new HashMap<>();
                cardMap.put("suit", cardParts[0]);
                cardMap.put("value", cardParts[1]);
                playerCards.add(cardMap);
            }

            cards.put(jugador, playerCards);
        }

        // Iterar sobre las cartas de cada jugador y mostrarlas en las HBox respectivas
        for (Map.Entry<String, List<Map<String, String>>> entry : cards.entrySet()) {
            String jugador = entry.getKey();
            HBox playerCardBox = getPlayerCardBox(jugador);
            playerCardBox.getChildren().clear(); // Limpiar las cartas anteriores

            // Obtener las cartas del jugador actual
            List<Map<String, String>> playerCards = entry.getValue();

            // Mostrar las cartas del jugador actual en su HBox
            for (Map<String, String> card : playerCards) {
                String suit = card.get("suit").toLowerCase();
                String imageName = card.get("value") + "_of_" + suit + ".jpg";
                Image cardImage = new Image(getClass().getResource("/images/" + skin + "/" + imageName).toExternalForm());
                ImageView cardImageView = createCardImageView(cardImage);
                playerCardBox.getChildren().add(cardImageView);
            }
        }
    }


    private HBox getPlayerCardBox(String jugador) {
        switch (jugador) {
            case "a":
                return playerCardBox1;
            case "b":
                return playerCardBox2;
            // Agrega más casos según la cantidad de jugadores que tengas
            default:
                return null; // Manejar el caso por defecto según tu lógica
        }
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
        return new Image(getClass().getResource("/images/" + skin + "/" + cardImageName).toExternalForm());
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
