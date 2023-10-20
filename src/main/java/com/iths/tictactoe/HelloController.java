package com.iths.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
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
        pane.disableProperty().bind(model.gameIsOverProperty());
        winnerName.visibleProperty().bind(model.gameIsOverProperty());
        winnerName.textProperty().bind(model.winnerNameProperty());
        playerScore.textProperty().bind(model.playerScoreProperty().asString());
        computerScore.textProperty().bind(model.computerScoreProperty().asString());
        addButtons(buttons);
        setOpacityOnButtons(buttons);
        model.setGameIsOver(false);
        model.setCount(9);
    }

    private void addButtons(List<Button> buttons){
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
    private void setOpacityOnButtons(List<Button> buttons){
        buttons.stream().forEach((e)-> e.setOpacity(1));
    }

    public void setXandO(MouseEvent event) {
        model.XandO(event);
    }

    public void resetGame(MouseEvent event) {
        model.resetGame();
    }

    public void resetRound(MouseEvent event) {
        model.resetRound();
    }

    public void onSaveButtonAction(ActionEvent event) {
        Window window = ((Node) event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(window);
        if (selectedFile != null) {
          //  model.saveToFile(selectedFile);
        }
    }
}