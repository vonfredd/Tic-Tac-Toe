package com.iths.tictactoe;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Model {
    private int playerTurn;

    private StringProperty winnerName = new SimpleStringProperty();
    private StringProperty message = new SimpleStringProperty();
    private final ReadOnlyStringProperty playerOne = new ReadOnlyStringWrapper("X");
    private final ReadOnlyStringProperty playerTwo = new ReadOnlyStringWrapper("O");
    private BooleanProperty gameIsOver = new SimpleBooleanProperty();

    private ObservableList<Button> buttons = FXCollections.observableList(new ArrayList<>());

    private BooleanProperty resetGame = new SimpleBooleanProperty();
    private IntegerProperty playerScore = new SimpleIntegerProperty();
    private IntegerProperty computerScore = new SimpleIntegerProperty();

    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public void addButtons(Button button) {
        buttons.add(button);
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public String getPlayerOne() {
        return playerOne.get();
    }

    public ReadOnlyStringProperty playerOneProperty() {
        return playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo.get();
    }

    public ReadOnlyStringProperty playerTwoProperty() {
        return playerTwo;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void XandO(MouseEvent event) {
        Button clickedButton = (Button) event.getSource();
        clickedButton.setText(getPlayerOne());
        clickedButton.setDisable(true);
        --count;
        checkWinner();
        if(!isGameIsOver()) {
            computerPick();
            checkWinner();
        }

        //Player 2
        //            clickedButton.setText(getPlayerTwo());
        //            clickedButton.setDisable(true);
        //            playerTurn = 1;
    }

    public void computerPick() {
        if (count == 0 || isGameIsOver()) {
            setWinnerName("NO WINNER!");
            setGameIsOver(true);
            return;
        }
        int choise = (int) (Math.random() * buttons.size() - 1);
        calculateMove(choise);

    }

    private void calculateMove(int choise) {
        while (buttons.get(choise).isDisabled()) {
            choise = (int) (Math.random() * buttons.size());
        }
        buttons.get(choise).setText(getPlayerTwo());
        buttons.get(choise).setDisable(true);
        --count;
    }

    public void checkWinner() {

        StringBuilder horizontal;
        for (int i = 0; i <= 6; i += 3) {
            horizontal = new StringBuilder();
            horizontal.append(buttons.get(i).getText());
            horizontal.append(buttons.get(i + 1).getText());
            horizontal.append(buttons.get(i + 2).getText());
            if (!whoIsTheWinner(horizontal).equals("")) {
                setGameIsOver(true);
                return;
            }
        }

        StringBuilder verticalStr;
        for (int i = 0; i < 3; i++) {
            verticalStr = new StringBuilder();
            verticalStr.append(buttons.get(i).getText());
            verticalStr.append(buttons.get(i + 3).getText());
            verticalStr.append(buttons.get(i + 6).getText());
            if (!whoIsTheWinner(verticalStr).equals("")) {
                setGameIsOver(true);
                return;
            }
        }

        StringBuilder diagonal = new StringBuilder();
        for (int i = 0; i < 9; i += 4) {
            diagonal.append(buttons.get(i).getText());
        }
        if (!whoIsTheWinner(diagonal).equals("")) {
            setGameIsOver(true);
            return;
        }

        diagonal = new StringBuilder();
        for (int i = 2; i < 7; i += 2) {
            diagonal.append(buttons.get(i).getText());
        }
        if (!whoIsTheWinner(diagonal).equals("")) {
            setGameIsOver(true);
        }
    }

    private String whoIsTheWinner(StringBuilder str) {
        if (str.toString().equals("XXX")) {
            setWinnerName("PLAYER WINS");
            setPlayerScore(getPlayerScore()+1);
            return "end";
        } else if (str.toString().equals("OOO")) {
            setWinnerName("COMPUTER WINS");
            setComputerScore(getComputerScore()+1);
            return "end";
        }
        return "";
    }

    public String getWinnerName() {
        return winnerName.get();
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

    public void resetGame() {
        resetRound();
        setPlayerScore(0);
        setComputerScore(0);
    }

    private static void resetButton(List<Button> b) {
        b.stream().forEach((e) -> {
            e.setDisable(false);
            e.setText("");
        });
    }

    public void saveToFile(Path path) {

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(20000);
                Files.writeString(path, "HEHEHEHE");
                System.out.println("File saved");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread.start();
    }


    public void resetRound() {
        resetButton(buttons);
        setGameIsOver(false);
        setWinnerName("");
        count = 9;
    }
}