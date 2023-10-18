package com.iths.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    private Button buttonLeft;

    private int playerTurn = 1;

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
    private Button buttonRight;
    @FXML
    private Model model = new Model();

    public Model getModel() {
        return model;
    }

    public void initialize() {
        // welcomeText.textProperty().bind(model.messageProperty()); // bind field to Model.
        pane.disableProperty().bind(model.gameIsOverProperty());
        winnerName.visibleProperty().bind(model.gameIsOverProperty());
        winnerName.textProperty().bind(model.winnerNameProperty());
        model.addButtons(b1);
        model.addButtons(b2);
        model.addButtons(b3);
        model.addButtons(b4);
        model.addButtons(b5);
        model.addButtons(b6);
        model.addButtons(b7);
        model.addButtons(b8);
        model.addButtons(b9);
        model.setGameIsOver(false);
        model.setPlayerTurn(1);
        model.setCount(9);
    }

    public void onLabelClicked() {
        //       model.setMessage("");
    }

    public void setXandO(MouseEvent event) {
        model.XandO(event);

    }

    public void resetGame(MouseEvent event) {
        model.resetGame();
    }
}