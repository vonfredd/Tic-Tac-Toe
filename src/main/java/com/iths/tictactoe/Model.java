package com.iths.tictactoe;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class Model {


    private IntegerProperty playerTurn = new SimpleIntegerProperty();
    private IntegerProperty emptySpotsLeft = new SimpleIntegerProperty();
    private StringProperty winnerName = new SimpleStringProperty();

    private SimpleIntegerProperty sizeOfButtonList = new SimpleIntegerProperty();

    private final int[][] winConditions = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}}; // if a player has its mark on all indexes in a row, the player will be presented as WINNER

    private ObservableList<Button> buttons = FXCollections.observableArrayList();

    public ObservableList<Button> getButtons() {
        return buttons;
    }

    private final ReadOnlyStringProperty playerMarkOne = new ReadOnlyStringWrapper("X");
    private final ReadOnlyStringProperty playerMarkTwo = new ReadOnlyStringWrapper("O");
    private BooleanProperty gameIsOver = new SimpleBooleanProperty(); // The condition for each round
    private IntegerProperty playerScore = new SimpleIntegerProperty();
    private IntegerProperty computerScore = new SimpleIntegerProperty();

    private ObservableList<Button> markedButtons = FXCollections.observableArrayList();

    public String getPlayerMarkOne() {
        return playerMarkOne.get();
    }

    public String getPlayerMarkTwo() {
        return playerMarkTwo.get();
    }

    public int getPlayerScore() {
        return playerScore.get();
    }

    public IntegerProperty playerScoreProperty() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore.set(playerScore);
    }

    public int getComputerScore() {
        return computerScore.get();
    }

    public IntegerProperty computerScoreProperty() {
        return computerScore;
    }

    public void setComputerScore(int computerScore) {
        this.computerScore.set(computerScore);
    }

    public StringProperty winnerNameProperty() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName.set(winnerName);
    }

    public boolean isGameIsOver() {
        return gameIsOver.get();
    }

    public BooleanProperty gameIsOverProperty() {
        return gameIsOver;
    }

    public void setGameIsOver(boolean gameIsOver) {
        this.gameIsOver.set(gameIsOver);
    }

    public int getEmptySpotsLeft() {
        return emptySpotsLeft.get();
    }

    public void setEmptySpotsLeft(int count) {
        this.emptySpotsLeft.set(count);
    }

    public int getPlayerTurn() {
        return playerTurn.get();
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn.set(playerTurn);
    }

    public int getSizeOfButtonList() {
        return sizeOfButtonList.get();
    }

    public void setSizeOfButtonList(int sizeOfButtonList) {
        this.sizeOfButtonList.set(sizeOfButtonList);
    }


    public Integer computerPickTwo(int numberOfGameSpots) {
        return (int) (Math.random() * numberOfGameSpots);
    }

    public ObservableList<Button> getMarkedButtons() {
        return markedButtons;
    }

    public Button grabAButton(int sizeOfList) {
        var indexOfButton = computerPickTwo(sizeOfList);
        return getButtons().get(indexOfButton);
    }

    public void theWinnerIs(int winnersTurn) {
        if (winnersTurn == 1 || winnersTurn == 0) {
            if (winnersTurn == 1) {
                setWinnerName("Player Wins");
                setPlayerScore(getPlayerScore() + 1);
            } else if (winnersTurn == 0) {
                setWinnerName("Computer Wins");
                setComputerScore(getComputerScore() + 1);
            }
            setGameIsOver(true);
        }
    }


    public void resetRound() {
        setGameIsOver(false);
        setWinnerName("");
        setEmptySpotsLeft(9);
        setPlayerTurn(1);
    }

    public void resetScore() {
        setPlayerScore(0);
        setComputerScore(0);
    }

    public String playerMark() {
        return getPlayerTurn() == 1 ? getPlayerMarkOne() : getPlayerMarkTwo();
    }

    public void checker() {
        if (weHaveAWinner()) {
            theWinnerIs(getPlayerTurn());
        } else if (!weHaveAWinner() && getEmptySpotsLeft() == 0) {
            setWinnerName("NO WINNER!");
            setGameIsOver(true);
        }
    }

    private boolean weHaveAWinner() {
        StringBuilder str;
        for (int i = 0; i < winConditions.length; i++) {
            str = new StringBuilder();
            str.append(getButtons().get(winConditions[i][0]).getText());
            str.append(getButtons().get(winConditions[i][1]).getText());
            str.append(getButtons().get(winConditions[i][2]).getText());

            if (str.toString().equals("XXX") || str.toString().equals("OOO")) {
                addButtonsToMarkedList(i);
                return true;
            }
        }
        return false;
    }

    private void addButtonsToMarkedList(int i) {
        markedButtons.add(getButtons().get(winConditions[i][0]));
        markedButtons.add(getButtons().get(winConditions[i][1]));
        markedButtons.add(getButtons().get(winConditions[i][2]));
    }
}