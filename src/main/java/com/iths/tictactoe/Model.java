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
    ObservableList<Button> v5Buttons = FXCollections.observableArrayList();
    private final BooleanProperty gameOver = new SimpleBooleanProperty();
    private final SimpleStringProperty theWinnerIs = new SimpleStringProperty();
    private final SimpleIntegerProperty playerTurn = new SimpleIntegerProperty();
    private final SimpleIntegerProperty playerScore = new SimpleIntegerProperty();
    private final SimpleIntegerProperty computerScore = new SimpleIntegerProperty();
    private GameState gameState;

    public ObservableList<Button> getV5Buttons() {
        return v5Buttons;
    }

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
        var randomedButton = buttons.get((int) (Math.random() * v5Buttons.size() - 1));
        while (randomedButton.isDisabled()) {
            randomedButton = buttons.get((int) (Math.random() * v5Buttons.size() - 1));
        }
        return randomedButton;
    }

    public void setNextTurn() {
        setPlayerTurn(getPlayerTurn() == 1 ? 0 : 1);
    }

    public String setPlayerMark() {
        return getPlayerTurn() == 1 ? "X" : "O";
    }


    public void theWinnerIs(int playerTurn) {
        String winner = playerTurn == 1 ? "PLAYER WINS" : "COMPUTER WINS";
        setTheWinnerIs(winner);
        addToWinnerScore(playerTurn);
    }

    public void addToWinnerScore(int playerTurn) {
        if (playerTurn == 1) {
            setPlayerScore(getPlayerScore() + 1);
        } else
            setComputerScore(getComputerScore() + 1);
    }

    public void resetRound() {
        setPlayerTurn(1);
        setGameOver(false);
        setEmptySpaces(v5Buttons.size());
        setGameState(GameState.RUNNING);
        resetButtons();
    }

    public void resetGame() {
        resetRound();
        setPlayerScore(0);
        setComputerScore(0);
    }

    public void addButtonsToV5List(){
        Button button1 = new Button();
        Button button2 = new Button();
        Button button3 = new Button();
        Button button4 = new Button();
        Button button5 = new Button();
        Button button6 = new Button();
        Button button7 = new Button();
        Button button8 = new Button();
        Button button9 = new Button();

        v5Buttons.add(button1);
        v5Buttons.add(button2);
        v5Buttons.add(button3);
        v5Buttons.add(button4);
        v5Buttons.add(button5);
        v5Buttons.add(button6);
        v5Buttons.add(button7);
        v5Buttons.add(button8);
        v5Buttons.add(button9);

    }

    public void addPlayerMarkAndDisable(Button clickedButton) {
        clickedButton.setText(setPlayerMark());
        clickedButton.setDisable(true);
    }
    public void gameLogicStarter(Button buttonToModify) {

        addPlayerMarkAndDisable(buttonToModify);
        setGameState();

        if (getGameState() == Model.GameState.RUNNING) {
            setNextTurn();
            addPlayerMarkAndDisable(randomButton(getV5Buttons()));
            setGameState();
        }

        implementGamestate(getGameState());
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
                str.append(getV5Buttons().get(winConditions[i][j]).getText());
            }
            if (str.toString().equals("XXX") || str.toString().equals("OOO"))
                return true;
        }
        return false;
    }

    public void implementGamestate(GameState gameState) {
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

    private void resetButtons() {
        getV5Buttons().stream().forEach((e) -> {
            e.setDisable(false);
            e.setText("");
        });
    }
}