package com.iths.tictactoe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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


    }

    public void setXandO(MouseEvent event) {
        //while(index is not 0)
            Button clickedButton = (Button) event.getSource();
            addPlayersMarkAndDisable(clickedButton);
            model.removeFromValidList(clickedButton);
            addPlayersMarkAndDisable(computerChoise());

            model.checkWinner();

    }


    private void addPlayersMarkAndDisable(Button clickedButton) {
        clickedButton.setText(model.setPlayerMark());
        model.setNextTurn();
        clickedButton.setDisable(true);
    }

    private Button computerChoise() {
        return model.randomButton();
    }

    public void resetRound(MouseEvent event) {
    }

    public void resetGame(MouseEvent event) {
    }
}