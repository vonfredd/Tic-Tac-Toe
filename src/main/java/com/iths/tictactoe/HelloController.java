package com.iths.tictactoe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

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
    private final Model model = new Model();

    private List<Button> buttons;


    public void initialize() {
        pane.disableProperty().bind(model.gameOverProperty());
        model.setPlayerTurn(1);
        ObservableList<Button> modelList = model.getButtons();
        winnerName.visibleProperty().bind(model.gameOverProperty());
        winnerName.textProperty().bind(model.theWinnerIsProperty());
        playerScore.textProperty().bind(model.playerScoreProperty().asString());
        computerScore.textProperty().bind(model.computerScoreProperty().asString());
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
        buttons.stream().forEach((e) -> e.setOpacity(1));
        modelList.addAll(buttons);
        model.setEmptySpaces(buttons.size());

        model.setGameState(Model.GameState.RUNNING);

    }

    public void pressedAButton(MouseEvent event) {
        Button clickedButton = (Button) event.getSource();
        addPlayerMarkAndDisable(clickedButton);
        model.setGameState();

        if (model.getGameState() == Model.GameState.RUNNING) {
            model.setNextTurn();
            addPlayerMarkAndDisable(computerChoice());
            model.setGameState();
        }
        adjustGameBasedOfGamestate();
    }

    private void adjustGameBasedOfGamestate() {
        model.implementGamestate();
    }

    private void addPlayerMarkAndDisable(Button clickedButton) {
        clickedButton.setText(model.setPlayerMark());
        clickedButton.setDisable(true);
    }

    private Button computerChoice() {
        return model.randomButton(model.getButtons());
    }

    public void resetRound() {
        resetButtons();
        model.resetRound();
    }

    public void resetGame() {
        resetButtons();
        model.resetGame();
    }

    private void resetButtons() {
        buttons.stream().forEach((e) -> {
            e.setDisable(false);
            e.setText("");
        });
    }
}