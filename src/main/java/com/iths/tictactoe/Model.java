package com.iths.tictactoe;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private BooleanProperty gameOver = new SimpleBooleanProperty();
    private ObservableList<Button> buttons = FXCollections.observableArrayList();
    private SimpleStringProperty theWinnerIs = new SimpleStringProperty();
    private SimpleIntegerProperty playerTurn = new SimpleIntegerProperty();
    private SimpleStringProperty playerMark = new SimpleStringProperty();
    private SimpleStringProperty computerMark = new SimpleStringProperty();
    private List<Button> theButtons;

    public void addButtons(){
        theButtons = new ArrayList<>(buttons);
    }


    public enum State {
        RUNNING,
        WINNER,
        NO_WINNER;
    }

    public boolean getGameOver() {
        return gameOver.get();
    }

    public BooleanProperty gameOverProperty() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver.set(gameOver);
    }

    public int getPlayerTurn() {
        return playerTurn.get();
    }

    public SimpleIntegerProperty playerTurnProperty() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn.set(playerTurn);
    }

    public ObservableList<Button> getButtons() {
        return buttons;
    }

    public void setButtons(ObservableList<Button> buttons) {
        this.buttons = buttons;
    }

    public boolean isGameOver() {
        return gameOver.get();
    }

    public String getTheWinnerIs() {
        return theWinnerIs.get();
    }

    public SimpleStringProperty theWinnerIsProperty() {
        return theWinnerIs;
    }

    public void setTheWinnerIs(String theWinnerIs) {
        this.theWinnerIs.set(theWinnerIs);
    }

    private final int[][] winConditions = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}}; // if a player has its mark on all indexes in a row, the player will be presented as WINNER

    public void removeFromValidList(Button clickedButton) {
        buttons.remove(clickedButton);
    }

    public Button randomButton() {
        int spacesLeft = getButtons().size() - 1;
        var randomedButton = getButtons().get((int) (Math.random() * spacesLeft));
        while (!buttons.contains(randomedButton)) {
            randomedButton = getButtons().get((int) (Math.random() * spacesLeft));
        }
        removeFromValidList(randomedButton);
        return randomedButton;
    }

    public void setNextTurn() {
        setPlayerTurn(getPlayerTurn() == 1 ? 0 : 1);
    }

    public String setPlayerMark() {
        System.out.println("SIZE" + buttons.size());
        return getPlayerTurn() == 1 ? "X" : "O";
    }

    public boolean checkWinner() {
        StringBuilder str;
        for (int i = 0; i < winConditions.length; i++) {
            str = new StringBuilder();
            for (int j = 0; j < winConditions[i].length; j++) {
                str.append(theButtons.get(winConditions[i][j]).getText());
            }
            if(str.toString().equals("XXX") || str.toString().equals("OOO"))
                return true;
        }
        return false;
    }

    public void checkEnding() {
        var emptySpotsLeft = getButtons().size();

        if (checkWinner()){
            theWinnerIs();
        }
        if (emptySpotsLeft == 0 && !isGameOver()) {
            theWinnerIs();
        }
    }

    public void theWinnerIs(){
        if (getPlayerTurn() == 1) {
            setTheWinnerIs("PLAYER WINS");
        } else {
            setTheWinnerIs("COMPUTER WINS");
        }
        setGameOver(true);
    }

    public void resetRound() {
        setPlayerTurn(1);
        setGameOver(false);
        theButtons = new ArrayList<>(buttons);
    }
}