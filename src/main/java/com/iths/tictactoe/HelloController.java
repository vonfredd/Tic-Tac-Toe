package com.iths.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
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
        buttons = new ArrayList<>();
        addButtons(buttons);
        setOpacityOnButtons(buttons);

        model.setGameIsOver(false);
        model.setEmptySpotsLeft(9);
        model.setPlayerTurn(1);
    }

    private void addButtons(List<Button> buttons) {
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

    private void setOpacityOnButtons(List<Button> buttons) {
        buttons.stream().forEach((e) -> e.setOpacity(1));
    }

    public void setXandO(MouseEvent event) {
        model.setPlayerTurn(1);
        Button clickedButton = (Button) event.getSource();
        clickedButton.setText(model.getPlayerOne());
        clickedButton.setDisable(true);
        model.setEmptySpotsLeft(model.getEmptySpotsLeft() - 1);

        winner(model.getWinConditions(), buttons);

        if (!model.isGameIsOver()) {
            model.setPlayerTurn(0);
            computerPick();
            winner(model.getWinConditions(), buttons);
        }
    }

    public void computerPick() {
        if (model.getEmptySpotsLeft() == 0) {
            model.setWinnerName("NO WINNER!");
            model.setGameIsOver(true);
            return;
        }
        int index = model.computerPickTwo(buttons.size() - 1);
        while (buttons.get(index).isDisabled())
            index = model.computerPickTwo(buttons.size() - 1);
        buttons.get(index).setText(model.getPlayerTwo());
        buttons.get(index).setDisable(true);
        model.setEmptySpotsLeft(model.getEmptySpotsLeft() - 1);
    }

    public void resetGame() {
        resetRound();
        model.resetScore();
    }

    public void resetRound() {
        resetButtons(buttons);
        model.resetRound();
    }

    private static void resetButtons(List<Button> buttons) {
        buttons.stream().forEach((e) -> {
            e.setBorder(null);
            e.setDisable(false);
            e.setText("");
        });
    }

    private void winner(int[][] winCondition, List<Button> buttons) {
        StringBuilder possibleWinner;
        List<Button> colorWinners;
        for (int i = 0; i < winCondition.length; i++) {
            colorWinners = new ArrayList<>();
            possibleWinner = new StringBuilder();
            for (int j = 0; j < winCondition[i].length; j++) {
                possibleWinner.append(buttons.get(winCondition[i][j]).getText());
                colorWinners.add(buttons.get(winCondition[i][j]));
            }
            if (possibleWinner.toString().equals("XXX") || possibleWinner.toString().equals("OOO")) {
                paintWinningButtons(colorWinners);
                model.theWinner(model.getPlayerTurn());
                break;
            }
        }
    }

    private void paintWinningButtons(List<Button> colorWinners) {
        colorWinners.stream().forEach((e) -> e.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(5)))));
        colorWinners.stream().forEach((e) -> e.setOpacity(1));
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