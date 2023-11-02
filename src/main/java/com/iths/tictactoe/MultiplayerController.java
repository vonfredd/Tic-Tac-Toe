package com.iths.tictactoe;

import com.iths.tictactoe.server.PlayerClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MultiplayerController {

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
    private final MultiplayerModel model = new MultiplayerModel();

    private static PlayerClient playerClient;

    private List<Button> buttons;
    private volatile boolean stopListening = false;

    public static void setPlayerClient(PlayerClient player) {
        playerClient = player;
    }

    private static int playerTurn;

    public static void setPlayerTurn(int playerT) {
        playerTurn = playerT;
    }


    public void initialize() {
        addBoardGameButtonsFromFXML();
        bindProperties();
        buttons.forEach((e) -> e.setOpacity(1));
        model.setEmptySpaces(buttons.size());
        model.setGameState(MultiplayerModel.GameState.RUNNING);

        Thread responseListenerThread = new Thread(this::listenForResponses);
        responseListenerThread.setDaemon(true); // Make the thread a daemon so it doesn't prevent the application from exiting
        responseListenerThread.start();
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
        synchronized (this) {
            model.gameLogicStarter(buttons.indexOf((Button) event.getSource()));
            sendMoveToServer(String.valueOf(buttons.indexOf((Button) event.getSource())));
        }
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

    public void sendMoveToServer(String moveMessage) {
        Thread sendMoveThread = new Thread(() -> {
            try {
                String a = playerClient.sendMessage(moveMessage);
                System.out.println("Sent to server , INDEX " + a);
            } catch (IOException e) {
                System.out.println("Error in sendMoveToServer");
            }
        });
        sendMoveThread.setDaemon(false);
        sendMoveThread.start();
    }

    private void listenForResponses() {
        while (!stopListening) {
            try {
                System.out.println("I am now calling from listenForResponses");
                String response = playerClient.receiveMessage();
                Platform.runLater(() -> handleReceivedResponse(response));

            } catch (IOException e) {
                System.out.println("Error in listenForResponses");
            }
        }
    }

    private void handleReceivedResponse(String response) {
        System.out.println("I am now calling from handleReceivedResponse");
        model.gameLogicStarter(Integer.parseInt(response));
    }

    public void stopListening() {
        stopListening = true;
    }
}

//varannan gång funkar sendMoveToServer
//varannan gång tar emot


/*
public void sendMoveToServer(String moveMessage)
this method accidentaly gets the other servers index to its string, why? Its like the other clients thread became this instances thread
 */
