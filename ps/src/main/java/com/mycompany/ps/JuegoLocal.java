package com.mycompany.ps;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Random;

public class JuegoLocal {
    
    @FXML
    private VBox vbox; 
    
    @FXML
    private Button comienzo; 
    
    @FXML
    private HBox dealerCardBox; 
    
    @FXML
    private HBox playerCardBox1; // Único jugador
    
    @FXML
    private Label puntosJugador1; 
    
    @FXML
    private Label puntosCrupier;
    
    @FXML
    private Button hitButton; 
    
    @FXML
    private Button standButton; 
    
    private int puntosDealer = 0; 
    private int puntosJugador = 0; 
    
    private Random random = new Random();

    private ImageView createCardImageView(Image cardImage) {
        ImageView imageView = new ImageView(cardImage);
        imageView.setFitWidth(120);
        imageView.setFitHeight(180);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    
    @FXML
    private void comenzarPartida() {
        vbox.setStyle("-fx-background-color: green;");

        // Ocultar el botón de comienzo y mostrar "Hit" y "Stand"
        comienzo.setVisible(false);
        hitButton.setVisible(true);
        standButton.setVisible(true);
        puntosJugador1.setVisible(true);
        puntosCrupier.setVisible(true);

        // Asignar cartas para el jugador y el crupier
        Image playerCard1 = getRandomCardImage();
        Image playerCard2 = getRandomCardImage();
        Image dealerCard1 = getRandomCardImage();

        playerCardBox1.getChildren().add(createCardImageView(playerCard1));
        playerCardBox1.getChildren().add(createCardImageView(playerCard2));
        
        dealerCardBox.getChildren().add(createCardImageView(dealerCard1));
        
        puntosJugador = getCardValue(playerCard1) + getCardValue(playerCard2);
        puntosDealer = getCardValue(dealerCard1);
        
        puntosJugador1.setText("Puntos: " + puntosJugador);
        puntosCrupier.setText("Puntos: " + puntosDealer);
    }
    
    @FXML
    private void hitAction() throws IOException {
        // Obtener el contenedor y la etiqueta del jugador
        HBox playerCardBox = playerCardBox1;
        Label puntosLabel = puntosJugador1;

        // Añadir una carta al jugador
        Image nuevaCarta = getRandomCardImage();
        playerCardBox.getChildren().add(createCardImageView(nuevaCarta));

        int valorCarta = getCardValue(nuevaCarta);
        puntosJugador += valorCarta;
        puntosLabel.setText("Puntos: " + puntosJugador);

        if (puntosJugador > 21) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Derrota");
            alert.setHeaderText(null);
            alert.setContentText("Te has pasado de 21 puntos. Has perdido.");
            alert.showAndWait();
            App.setRoot("derrota");
            
            hitButton.setVisible(false);
            standButton.setVisible(false);
        } else if (puntosJugador == 21) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Victoria");
            alert.setHeaderText(null);
            alert.setContentText("¡Has alcanzado 21 puntos! Has ganado.");
            alert.showAndWait();
            App.setRoot("victoria");

            hitButton.setVisible(false);
            standButton.setVisible(false);
        }
    }
    
    @FXML
    private void standAction() throws IOException {
        while (puntosDealer < 17) {
            Image nuevaCarta = getRandomCardImage();
            dealerCardBox.getChildren().add(createCardImageView(nuevaCarta));
            puntosDealer += getCardValue(nuevaCarta);
        }

        puntosCrupier.setText("Puntos: " + puntosDealer); // Actualizar la etiqueta del crupier
        
        Alert alert;
        if (puntosDealer > 21) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Victoria");
            alert.setHeaderText(null);
            alert.setContentText("¡El crupier se pasó de 21! Has ganado.");
            App.setRoot("victoria");
        } else if (puntosDealer == puntosJugador) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empate");
            alert.setHeaderText(null);
            alert.setContentText("¡Es un empate!");
        } else if (puntosDealer > puntosJugador) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Derrota");
            alert.setHeaderText(null);
            alert.setContentText("El crupier tiene más puntos. Has perdido.");
            App.setRoot("derrota");
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Victoria");
            alert.setHeaderText(null);
            alert.setContentText("¡Has ganado!");
            App.setRoot("victoria");
        }

        alert.showAndWait();
        hitButton.setVisible(false);
        standButton.setVisible(false);
    }

    @FXML
    private void exitApplication() {
        Platform.exit();
    }
    
    private Image getRandomCardImage() {
        int cardNumber = random.nextInt(13) + 1;
        String[] suits = {"hearts", "clubs", "diamonds", "spades"};
        String suit = suits[random.nextInt(4)];
        String cardImageName = cardNumber + "_of_" + suit + ".jpg";
        return new Image(getClass().getResource("/images/cards/" + cardImageName).toExternalForm());
    }
    
    private int getCardValue(Image cardImage) {
        String imageUrl = cardImage.getUrl();
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        String cardName = fileName.split("_")[0];
        int cardNumber = Integer.parseInt(cardName);

        return cardNumber > 10 ? 10 : cardNumber;
    }
}
