package com.iths.tictactoe;

import com.iths.tictactoe.server.PlayerClient;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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
    private Label labelTwo;
    @FXML
    private Circle oneConnected = new Circle();
    @FXML
    private Circle twoConnected;

    @FXML
    private Label labelTurn;

    @FXML
    private final MultiplayerModel model = new MultiplayerModel();

    private List<Button> buttons;
    private volatile boolean stopListening = false;

    private static int playerTurn;
    private PlayerClient thisPlayerClient;

    public static void setPlayerTurn(int playerT) {
        playerTurn = playerT;
    }

    public static int getPlayerTurn() {
        return playerTurn;
    }

    public void initialize() throws IOException {
        thisPlayerClient = new PlayerClient();
        thisPlayerClient.startConnection("localhost", 5555);
        addBoardGameButtonsFromFXML();
        bindProperties();
        buttons.forEach((e) -> e.setOpacity(1));
        model.setEmptySpaces(buttons.size());
        model.setGameState(MultiplayerModel.GameState.RUNNING);
        model.setMyTurn(getPlayerTurn());

        Thread responseListenerThread = new Thread(this::listenForResponses);
        responseListenerThread.setDaemon(true);
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
            buttons.get(i).disableProperty().bind(Bindings.isNotEmpty(model.getMarkingOfButtons().get(i)));
        }
    }

    public void pressedAButton(MouseEvent event) {
        model.gameLogicStarter(buttons.indexOf((Button) event.getSource()));

        sendMoveToServer(String.valueOf(buttons.indexOf((Button) event.getSource())));

    }

    public void resetRound() {
        sendMoveToServer("resetRound");
        model.resetRound();
    }

    public void resetGame() {
        sendMoveToServer("resetGame");
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
            labelTwo.setText("Player has connected!");
        });
    }

    public void sendMoveToServer(String moveMessage) {
        Thread sendMoveThread = new Thread(() -> {
            try {
                thisPlayerClient.sendMessage(moveMessage);
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
                String response = thisPlayerClient.receiveMessage();
                Platform.runLater(() -> handleReceivedResponse(response));
            } catch (IOException e) {
                System.out.println("Error in listenForResponses");
            }
        }
    }

    private void handleReceivedResponse(String response) {
        if (response.equals("oneConnect")) {
            playerOneConnected();
            return;
        } else if (response.equals("twoConnect")) {
            playerTwoConnected();
            return;
        }

        if (response.equals("playerOne")) {
            labelTurn.setText(playerTurn == 0 ? "Your turn" : "Not your turn");
            return;
        }
        if (response.equals("playerTwo")) {
            labelTurn.setText(playerTurn == 1 ? "Your turn" : "Not your turn");
            return;
        }

        if (response.equals("resetRound")) {
            model.resetRound();
            return;
        }

        if (response.equals("resetGame")) {
            model.resetGame();
            return;
        }
        model.gameLogicStarter(Integer.parseInt(response));
    }

    public void stopListening() {
        stopListening = true;
    }
}