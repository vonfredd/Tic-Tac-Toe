package com.iths.tictactoe;

import com.iths.tictactoe.server.LoopedServerMultipleConnections;
import com.iths.tictactoe.server.PlayerClient;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    private Label playerScore;

    @FXML
    private Label computerScore;

    @FXML
    private TilePane pane;
    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b4;
    @FXML
    private Button b5;
    @FXML
    private Button b6;
    @FXML
    private Button b7;
    @FXML
    private Button b8;
    @FXML
    private Button b9;

    @FXML
    private Label winnerName;

    @FXML
    private Label labelOne;
    @FXML
    private Circle oneConnected = new Circle();
    @FXML
    private Circle twoConnected;

    @FXML
    private final Model model = new Model();

    private List<Button> buttons;

    public void initialize() {
        addBoardGameButtonsFromFXML();
        bindProperties();
        buttons.forEach((e) -> e.setOpacity(1));
        model.setEmptySpaces(buttons.size());
        model.setGameState(Model.GameState.RUNNING);
        model.setPlayerTurn(1);
    }

    private void addBoardGameButtonsFromFXML() {
        buttons = new ArrayList<>();
        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);
        buttons.add(b4);
        buttons.add(b5);
        buttons.add(b6);
        buttons.add(b7);
        buttons.add(b8);
        buttons.add(b9);
    }

    private void bindProperties() {
        model.setButtonsDisable();
        model.addEmptyButtonMark();
        pane.disableProperty().bind(model.gameOverProperty());
        winnerName.visibleProperty().bind(model.gameOverProperty());
        winnerName.textProperty().bind(model.theWinnerIsProperty());
        playerScore.textProperty().bind(model.playerScoreProperty().asString());
        computerScore.textProperty().bind(model.computerScoreProperty().asString());


        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).textProperty().bind(model.getMarkingOfButtons().get(i));
            buttons.get(i).disableProperty().bind(model.getDisabledButtons().get(i));
        }
    }

    public void pressedAButton(MouseEvent event) {
        model.gameLogicStarter(buttons.indexOf((Button) event.getSource()));
    }

    public void resetRound() {
        model.resetRound();
    }

    public void resetGame() {
        model.resetGame();
    }


    public void playerOneConnected() {
        Platform.runLater(() -> {
            oneConnected.setFill(Color.GREEN);
            labelOne.setText("Player has connected!");
        });
    }

    public void playerTwoConnected() {
        Platform.runLater(() -> {
            twoConnected.setFill(Color.GREEN);
        });
    }

    public void connectUserToServer() throws IOException {
        PlayerClient client = new PlayerClient();
        client.startConnection("localhost", 5555);
        playerOneConnected();

        String message = ".";
        String response = client.sendMessage(message);
        System.out.println("Server response: " + response);
    }
}