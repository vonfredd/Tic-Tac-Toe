package com.iths.tictactoe;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class Model {

    public enum GameState {
        RUNNING,
        WINNER,
        NO_WINNER
    }

    private final BooleanProperty gameOver = new SimpleBooleanProperty();
    private final ObservableList<Button> buttons = FXCollections.observableArrayList();
    private final SimpleStringProperty theWinnerIs = new SimpleStringProperty();
    private final SimpleIntegerProperty playerTurn = new SimpleIntegerProperty();
    private final SimpleIntegerProperty playerScore = new SimpleIntegerProperty();
    private final SimpleIntegerProperty computerScore = new SimpleIntegerProperty();
    private GameState gameState;

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private int emptySpaces;

    public void setEmptySpaces(int emptySpaces) {
        this.emptySpaces = emptySpaces;
    }

    public int getEmptySpaces() {
        return emptySpaces;
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

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn.set(playerTurn);
    }

    public ObservableList<Button> getButtons() {
        return buttons;
    }

    public SimpleStringProperty theWinnerIsProperty() {
        return theWinnerIs;
    }

    public void setTheWinnerIs(String theWinnerIs) {
        this.theWinnerIs.set(theWinnerIs);
    }

    public String getTheWinnerIs() {
        return theWinnerIs.get();
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

    private final int[][] winConditions = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}}; // if a player has its mark on all indexes in a row, the player will be presented as WINNER

    public Button randomButton(ObservableList<Button> buttons) {
        var randomedButton = getButtons().get((int) (Math.random() * getButtons().size() - 1));
        while (randomedButton.isDisabled()) {
            randomedButton = getButtons().get((int) (Math.random() * getButtons().size() - 1));
        }
        return randomedButton;
    }

    public void setNextTurn() {
        setPlayerTurn(getPlayerTurn() == 1 ? 0 : 1);
    }

    public String setPlayerMark() {
        return getPlayerTurn() == 1 ? "X" : "O";
    }

    public boolean weHaveAWinner() {
        StringBuilder str;
        for (int i = 0; i < winConditions.length; i++) {
            str = new StringBuilder();
            for (int j = 0; j < winConditions[i].length; j++) {
                str.append(getButtons().get(winConditions[i][j]).getText());
            }
            if (str.toString().equals("XXX") || str.toString().equals("OOO"))
                return true;
        }
        return false;
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


    public void theWinnerIs(int playerTurn) {
        String winner = playerTurn == 1 ? "PLAYER WINS" : "COMPUTER WINS";
        setTheWinnerIs(winner);
        addToWinnerScore(winner);
        setGameOver(true);
    }

    public void thereIsNoWinner(){
        setTheWinnerIs("NO WINNER");
        setGameOver(true);
    }

    public void addToWinnerScore(String winnerName) {
        if (winnerName.equals("PLAYER WINS")) {
            setPlayerScore(getPlayerScore() + 1);
        } else if (winnerName.equals("COMPUTER WINS")) {
            setComputerScore(getComputerScore() + 1);
        }
    }

    public void resetRound() {
        setPlayerTurn(1);
        setGameOver(false);
        setEmptySpaces(buttons.size());
        setGameState(GameState.RUNNING);
    }
    public void newGameState() {
        switch (getGameState()) {
            case RUNNING -> setNextTurn();
            case WINNER -> theWinnerIs(getPlayerTurn());
            case NO_WINNER -> thereIsNoWinner();
        }
    }
}