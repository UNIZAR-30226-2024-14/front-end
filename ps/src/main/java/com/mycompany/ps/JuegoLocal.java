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
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.util.Duration;

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
    
    private static String skin = "cards";

    @FXML
    private ImageView animationImageView;
    
    @FXML
    private void initialize() {
        // Cargar la imagen de la animación
        Image animationImage = new Image(getClass().getResourceAsStream("/images/start.gif"));
        
        // Establecer la imagen en el ImageView
        animationImageView.setImage(animationImage);
        
        // Crear una transición de traducción para mover la imagen horizontalmente
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(5), animationImageView);
        translateTransition.setFromX(0); // Posición inicial en x
        translateTransition.setFromY(70); // Posición inicial en x
        translateTransition.setToX(0); // Posición final en x
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE); // Repetir indefinidamente
        translateTransition.setAutoReverse(true); // Revertir automáticamente
        translateTransition.play(); // Iniciar la animación
    }
    
    static void cambiarSkin(String carpeta){
        skin = carpeta;
    }
    
    private ImageView createCardImageView(Image cardImage) {
        ImageView imageView = new ImageView(cardImage);
        imageView.setFitWidth(120);
        imageView.setFitHeight(180);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    
    @FXML
    private void comenzarPartida() throws IOException {
    // Cambia el fondo de la pantalla
    vbox.setStyle("-fx-background-image: url('images/Tapete3.png'); -fx-background-size: cover; -fx-background-repeat: no-repeat;");
    animationImageView.setImage(null);
    
    Image lgImage = new Image(getClass().getResourceAsStream("/images/Lg2.png"));
    animationImageView.setImage(lgImage);
    animationImageView.setLayoutX(50); // Cambia el valor a la posición X deseada
    animationImageView.setLayoutY(50); // Cambia el valor a la posición Y deseada
    animationImageView.setFitWidth(950); // Cambia el valor al ancho deseado
    animationImageView.setFitHeight(300); // Cambia el valor al alto deseado
    
    Insets padding = new Insets(-100.0, 20.0, 20.0, 20.0);
    vbox.setPadding(padding);
    
    // Ocultar el botón de comienzo y mostrar los botones "Hit" y "Stand"
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

    // Actualizar el texto de los labels y aplicar la clase de estilo "bj-field"
    puntosJugador1.setText("Puntos: " + puntosJugador);
    puntosJugador1.getStyleClass().add("bj-field");  // Aplica la clase de estilo "bj-field"
    
    puntosCrupier.setText("Puntos: " + puntosDealer);
    puntosCrupier.getStyleClass().add("bj-field");  // Aplica la clase de estilo "bj-field"

    if (puntosJugador == 21) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Victoria");
        alert.setHeaderText(null);
        alert.setContentText("¡Has alcanzado 21 puntos! Has ganado.");
        alert.showAndWait();
        App.setRoot("victoria");

        // Ocultar los botones "Hit" y "Stand" después de la victoria
        hitButton.setVisible(false);
        standButton.setVisible(false);
    }
}
    
    @FXML
    private void hitAction() throws IOException {
        HBox playerCardBox = playerCardBox1;
        Label puntosLabel = puntosJugador1;

        // Añadir una carta al jugador
        Image nuevaCarta = getRandomCardImage();
        playerCardBox.getChildren().add(createCardImageView(nuevaCarta));

        int valorCarta = getCardValue(nuevaCarta);
        puntosJugador += valorCarta; // Sumar el valor de la carta con el valor correcto del As
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
            puntosDealer += getCardValue(nuevaCarta); // Sumar el valor correcto, considerando el As como 11 o 1
        }

        puntosCrupier.setText("Puntos: " + puntosDealer); // Actualizar la etiqueta del crupier

        Alert alert;
        if (puntosDealer > 21) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Victoria");
            alert.setHeaderText(null);
            alert.setContentText("¡El crupier se pasó de 21! Has ganado.");
            alert.showAndWait();
            App.setRoot("victoria");
        } else if (puntosDealer == puntosJugador) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empate");
            alert.setHeaderText(null);
            alert.setContentText("¡Es un empate!");
            alert.showAndWait();
            App.setRoot("empate");
        } else if (puntosDealer > puntosJugador) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Derrota");
            alert.setHeaderText(null);
            alert.setContentText("El crupier tiene más puntos. Has perdido.");
            alert.showAndWait();
            App.setRoot("derrota");
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Victoria");
            alert.setHeaderText(null);
            alert.setContentText("¡Has ganado!");
            alert.showAndWait();
            App.setRoot("victoria");
        }

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
        return new Image(getClass().getResource("/images/" + skin + "/" + cardImageName).toExternalForm());
    }
    
    private int getCardValue(Image cardImage) {
    String imageUrl = cardImage.getUrl();
    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    String cardName = fileName.split("_")[0];
    int cardNumber = Integer.parseInt(cardName);

    if (cardNumber == 1) {
        // Es un As, determinar si debe valer 1 u 11
        return (puntosJugador + 11 <= 21) ? 11 : 1;
    }

    return cardNumber > 10 ? 10 : cardNumber;
}

}
