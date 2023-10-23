package com.iths.tictactoe;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class Model {

    private BooleanProperty gameOver = new SimpleBooleanProperty();
    private ObservableList<Button> buttons = FXCollections.observableArrayList();
    private SimpleIntegerProperty playerTurn = new SimpleIntegerProperty();
    private SimpleStringProperty playerMark = new SimpleStringProperty();
    private SimpleStringProperty computerMark = new SimpleStringProperty();


    public enum state {
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

    private final int[][] winConditions = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}}; // if a player has its mark on all indexes in a row, the player will be presented as WINNER


    private void changeTurn() {
        if (getPlayerTurn() == 1) {
            setPlayerTurn(0);
        } else {
            setPlayerTurn(1);
        }
    }

    public void removeFromValidList(Button clickedButton) {
        buttons.remove(clickedButton);
    }

    public Button randomButton() {
        var randomedButton = getButtons().get((int) (Math.random() * getButtons().size()-1));
        while (!buttons.contains(randomedButton)) {
            randomedButton = getButtons().get((int) (Math.random() * getButtons().size()-1));
        }
        removeFromValidList(randomedButton);
        return randomedButton;
    }

    public void setNextTurn(){
        setPlayerTurn(getPlayerTurn() == 1 ? 0 : 1);
    }
    public String setPlayerMark() {
        return getPlayerTurn() == 1 ? "X" : "O";
    }


    public void checkWinner() {

    }

}