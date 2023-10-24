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
    private Model model = new Model();

    private List<Button> buttons;


    public Model getModel() {
        return model;
    }

    public void initialize() {
        pane.disableProperty().bind(model.gameOverProperty());
        model.setPlayerTurn(1);
        ObservableList<Button> modelList = model.getButtons();
        winnerName.visibleProperty().bind(model.gameOverProperty());
        winnerName.textProperty().bind(model.theWinnerIsProperty());
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

        modelList.addAll(buttons);
        model.addButtons();
        Model.State GameState = Model.State.RUNNING;

    }

    public void pressedAButton(MouseEvent event) {

        Button clickedButton = (Button) event.getSource();
        addPlayersMarkAndDisable(clickedButton);
        model.removeFromValidList(clickedButton);
        model.checkEnding();
        model.setNextTurn();

        if (!model.isGameOver()) {
            var computerButtonClicked = computerChoice();
            addPlayersMarkAndDisable(computerButtonClicked);
            model.removeFromValidList(computerButtonClicked);
            model.checkEnding();
            model.setNextTurn();
        }


    }


    private void addPlayersMarkAndDisable(Button clickedButton) {
        clickedButton.setText(model.setPlayerMark());
        clickedButton.setDisable(true);
    }

    private Button computerChoice() {
        return model.randomButton();
    }

    public void resetRound(MouseEvent event) {
        buttons.stream().forEach((e) -> {
            e.setDisable(false);
            e.setText("");
        });
        model.resetRound();

    }

    public void resetGame(MouseEvent event) {

    }
}