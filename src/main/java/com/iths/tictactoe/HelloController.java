package com.iths.tictactoe;

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
        pane.disableProperty().bind(model.gameIsOverProperty());
        ObservableList<Button> modelList = model.getButtons();
        buttons = new ArrayList<>();
        addButtons(buttons);
        setOpacityOnButtons(buttons);
        modelList.addAll(buttons);

        winnerName.visibleProperty().bind(model.gameIsOverProperty());
        winnerName.textProperty().bind(model.winnerNameProperty());
        playerScore.textProperty().bind(model.playerScoreProperty().asString());
        computerScore.textProperty().bind(model.computerScoreProperty().asString());

        model.setGameIsOver(false);
        model.setEmptySpotsLeft(9);
        model.setPlayerTurn(1);
        model.setSizeOfButtonList(model.getButtons().size()-1);
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

        //Välj bricka och markera den,
        markButton(event);

        //kolla om spelaren vunnit
        checkRound(model.getWinConditions(), buttons);

        //låt datorn välja bricka om spelaren ej har vunnit, kolla därefter ifall datorn har vunnit.
        if (!model.isGameIsOver()) {
            computerPick();
            checkRound(model.getWinConditions(), buttons);
        }
    }


    /*
         markButton läser av ett event och baserat på det, fångar upp knappen som tryckts.
         Skickar sedan vidare knappen till modifiering.
    */
    private void markButton(MouseEvent event) {
        var buttonClicked = model.getButtons().stream().filter((e) -> e.equals(event.getSource())).findFirst().get();
        modifyGrabbedButton(buttonClicked, model.playerMark());
    }


    /*
        modifyGrabbedButton takes a button and a playermark (X,O) as a parameter.
        based on that the button's fields changes based on the input parameters
        and the number of available spots are decresed.
     */
    private void modifyGrabbedButton(Button button, String playerMark) {
        button.setText(playerMark);
        button.setDisable(true);
        model.setEmptySpotsLeft(model.getEmptySpotsLeft() - 1);
    }


    /*
        computerPick grabs a button and checks if the button is disabled. While the button that is grabbed is disabled,
        a new button will be grabbed and checked. If the grabbed button has not been disabled, it will be modified.
     */
    public void computerPick() {
        var button = model.grabAButton(model.getSizeOfButtonList());
        while (button.isDisabled())
            button = model.grabAButton(model.getSizeOfButtonList());
        modifyGrabbedButton(button, model.playerMark());
    }


    /*
        resets the entire game (Scores removed!) and resets the conditions
     */
    public void resetGame() {
        resetRound();
        model.resetScore();
    }

    /*
        resets the buttons and conditions
     */
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

    /*
        check if there could be a winner. If there is a winner, the 3-in-row boxes will be highlighted and the winner
        will be presented.
        If there is no more empty spaces left and the game is not over, "NO WINNER" will be presented and the round will
        stop.
     */
    private void checkRound(int[][] winCondition, List<Button> buttons) {
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
        if (model.getEmptySpotsLeft() == 0 && !model.isGameIsOver()) {
            model.setWinnerName("NO WINNER!");
            model.setGameIsOver(true);
        }
        model.setPlayerTurn(model.getPlayerTurn() == 1 ? 0 : 1);
    }


    /*
      the winning buttons will get a border that highlights them.
     */
    private void paintWinningButtons(List<Button> colorWinners) {
        colorWinners.stream().forEach((e) -> e.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(5)))));
        colorWinners.stream().forEach((e) -> e.setOpacity(1));
    }
}