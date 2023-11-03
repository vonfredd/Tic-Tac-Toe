package com.iths.tictactoe;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MultiplayerModel {
    private final ObservableList<SimpleStringProperty> markingOfButtons = FXCollections.observableArrayList();
    private final ObservableList<SimpleBooleanProperty> disabledButtons = FXCollections.observableArrayList();

    public void addEmptyButtonMark() {
        markingOfButtons.add(new SimpleStringProperty(""));
        markingOfButtons.add(new SimpleStringProperty(""));
        markingOfButtons.add(new SimpleStringProperty(""));
        markingOfButtons.add(new SimpleStringProperty(""));
        markingOfButtons.add(new SimpleStringProperty(""));
        markingOfButtons.add(new SimpleStringProperty(""));
        markingOfButtons.add(new SimpleStringProperty(""));
        markingOfButtons.add(new SimpleStringProperty(""));
        markingOfButtons.add(new SimpleStringProperty(""));

    }

    public void setButtonsDisable() {
        disabledButtons.add(new SimpleBooleanProperty());
        disabledButtons.add(new SimpleBooleanProperty());
        disabledButtons.add(new SimpleBooleanProperty());
        disabledButtons.add(new SimpleBooleanProperty());
        disabledButtons.add(new SimpleBooleanProperty());
        disabledButtons.add(new SimpleBooleanProperty());
        disabledButtons.add(new SimpleBooleanProperty());
        disabledButtons.add(new SimpleBooleanProperty());
        disabledButtons.add(new SimpleBooleanProperty());
    }

    private final BooleanProperty gameOver = new SimpleBooleanProperty();
    private final SimpleStringProperty theWinnerIs = new SimpleStringProperty();
    private final SimpleIntegerProperty playerTurn = new SimpleIntegerProperty();
    private final SimpleIntegerProperty playerScore = new SimpleIntegerProperty();
    private final SimpleIntegerProperty computerScore = new SimpleIntegerProperty();
    private GameState gameState;

    private int myTurn;

    public int getMyTurn() {
        return myTurn;
    }

    public void setMyTurn(int myTurn) {
        this.myTurn = myTurn;
    }

    public enum GameState {
        RUNNING,
        WINNER,
        NO_WINNER
    }

    private final int[][] winConditions = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}};
    private int emptySpaces;

    public GameState getGameState() {
        return gameState;
    }

    public ObservableList<SimpleBooleanProperty> getDisabledButtons() {
        return disabledButtons;
    }

    public ObservableList<SimpleStringProperty> getMarkingOfButtons() {
        return markingOfButtons;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setEmptySpaces(int emptySpaces) {
        this.emptySpaces = emptySpaces;
    }

    public int getEmptySpaces() {
        return emptySpaces;
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

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn.set(playerTurn);
    }

    public SimpleStringProperty theWinnerIsProperty() {
        return theWinnerIs;
    }

    public void setTheWinnerIs(String theWinnerIs) {
        this.theWinnerIs.set(theWinnerIs);
    }

    public Integer getPlayerScore() {
        return playerScore.get();
    }

    public SimpleIntegerProperty playerScoreProperty() {
        return playerScore;
    }

    public void setPlayerScore(Integer playerScore) {
        this.playerScore.set(playerScore);
    }

    public Integer getComputerScore() {
        return computerScore.get();
    }

    public SimpleIntegerProperty computerScoreProperty() {
        return computerScore;
    }

    public void setComputerScore(Integer computerScore) {
        this.computerScore.set(computerScore);
    }


    public void setNextTurn() {
        setPlayerTurn(getPlayerTurn() == 1 ? 0 : 1);
    }

    public String setPlayerMark() {
        return getPlayerTurn() == 0 ? "X" : "O";
    }

    public void theWinnerIs(int playerTurn) {
        String winner = playerTurn == getMyTurn() ? "YOU WIN" : "YOU LOSE";
        setTheWinnerIs(winner);
        addToWinnerScore(playerTurn);
    }
    public void addToWinnerScore(int playerTurn) {
        if (playerTurn == getMyTurn()) {
            setPlayerScore(getPlayerScore() + 1);
        } else
            setComputerScore(getComputerScore() + 1);
    }

    public void resetRound() {
        setPlayerTurn(0);
        setGameOver(false);
        setEmptySpaces(getMarkingOfButtons().size());
        setGameState(GameState.RUNNING);
        markingOfButtons.forEach((e) -> e.setValue(""));
        disabledButtons.forEach((e) -> e.setValue(false));
    }

    public void resetGame() {
        resetRound();
        setPlayerScore(0);
        setComputerScore(0);
    }

    public void addPlayerMarkAndDisable(int index) {
        getMarkingOfButtons().get(index).setValue(setPlayerMark());
        getDisabledButtons().get(index).setValue(true);
    }

    public void gameLogicStarter(int index) {
        addPlayerMarkAndDisable(index);
        setGameState();

        implementStateOfGame(getGameState());
    }

    public void setGameState() {
        setEmptySpaces(getEmptySpaces() - 1);

        if (weHaveAWinner()) {
            setGameState(GameState.WINNER);
        } else if (!weHaveAWinner() && getEmptySpaces() == 0) {
            setGameState(GameState.NO_WINNER);
        } else
            setGameState(GameState.RUNNING);
    }

    public boolean weHaveAWinner() {
        StringBuilder str;
        for (int i = 0; i < winConditions.length; i++) {
            str = new StringBuilder();
            for (int j = 0; j < winConditions[i].length; j++) {
                str.append(getMarkingOfButtons().get(winConditions[i][j]).getValue());
            }
            if (str.toString().equals("XXX") || str.toString().equals("OOO"))
                return true;
        }
        return false;
    }

    public void implementStateOfGame(GameState gameState) {
        switch (gameState) {
            case RUNNING -> setNextTurn();
            case WINNER -> {
                theWinnerIs(getPlayerTurn());
                setGameOver(true);
            }
            case NO_WINNER -> {
                setTheWinnerIs("NO WINNER");
                setGameOver(true);
            }
        }
    }
}