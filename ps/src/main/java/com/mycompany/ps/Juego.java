package com.mycompany.ps;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Random;

public class Juego {
    
    @FXML
    private VBox vbox; // Referencia al VBox
    
    @FXML
    private Button comienzo; // Referencia al botón
    
    @FXML
    private HBox playerCardBox; // Donde se mostrarán las cartas del jugador
    
    @FXML
    private HBox dealerCardBox; // Donde se mostrarán las cartas del dealer
    
    private Random random = new Random();
    
    @FXML
    private void comenzarPartida() {
        
        // Cambiar el fondo a verde
        vbox.setStyle("-fx-background-color: green;");

        // Ocultar el botón
        comienzo.setVisible(false);

        // Asignar dos cartas, una para el jugador y otra para el dealer
        Image playerCardImage = getRandomCardImage();
        Image dealerCardImage = getRandomCardImage();

        // Crear los componentes ImageView para mostrar las cartas
        ImageView playerCard = new ImageView(playerCardImage);
        ImageView dealerCard = new ImageView(dealerCardImage);

        // Añadir las cartas a sus respectivas HBox
        playerCardBox.getChildren().add(playerCard);
        dealerCardBox.getChildren().add(dealerCard);
    }
    
    private Image getRandomCardImage() {
        // Generar un número aleatorio entre 1 y 13 (1 es As, 11 es Jack, 12 es Reina, 13 es Rey)
        int cardNumber = random.nextInt(13) + 1;

        // Generar un palo aleatorio (corazones, tréboles, diamantes, picas)
        String[] suits = {"hearts", "clubs", "diamonds", "spades"};
        String suit = suits[random.nextInt(4)];

        // Construir el nombre de la imagen (por ejemplo, "1_of_hearts.png")
        String cardImageName = cardNumber + "_of_" + suit + ".jpg";

        // Cargar y devolver la imagen
        return new Image(getClass().getResource("/images/cards/" + cardImageName).toExternalForm());
    }
    
    @FXML
    private void exitApplication() {
        Platform.exit();
    }
}
