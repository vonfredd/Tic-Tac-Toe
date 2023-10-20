package com.iths.tictactoe;

import javafx.beans.property.*;
public class Model {
    public IntegerProperty playerTurn = new SimpleIntegerProperty();
    private IntegerProperty emptySpotsLeft = new SimpleIntegerProperty();
    private StringProperty winnerName = new SimpleStringProperty();
    private final int[][] winConditions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private final ReadOnlyStringProperty playerOne = new ReadOnlyStringWrapper("X");
    private final ReadOnlyStringProperty playerTwo = new ReadOnlyStringWrapper("O");
    private BooleanProperty gameIsOver = new SimpleBooleanProperty();
    private IntegerProperty playerScore = new SimpleIntegerProperty();
    private IntegerProperty computerScore = new SimpleIntegerProperty();

    public String getPlayerOne() {
        return playerOne.get();
    }

    public String getPlayerTwo() {
        return playerTwo.get();
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

    public int[][] getWinConditions() {
        return winConditions;
    }

    public int getPlayerTurn() {
        return playerTurn.get();
    }


    public void setPlayerTurn(int playerTurn) {
        this.playerTurn.set(playerTurn);
    }
    public Integer computerPickTwo(int numberOfGameSpots) {
        return (int) (Math.random() * numberOfGameSpots);
    }

    public void theWinner(int winnersTurn) {
        if (winnersTurn == 1) {
            setWinnerName("Player Wins");
            setPlayerScore(getPlayerScore() + 1);
            setGameIsOver(true);
        } else if (winnersTurn == 0) {
            setWinnerName("Computer Wins");
            setComputerScore(getComputerScore() + 1);
            setGameIsOver(true);
        }
    }


    public void resetRound() {
        setGameIsOver(false);
        setWinnerName("");
        setEmptySpotsLeft(9);
    }

    public void resetScore() {
        setPlayerScore(0);
        setComputerScore(0);
    }
}