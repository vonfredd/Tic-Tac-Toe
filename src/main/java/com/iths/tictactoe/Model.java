package com.iths.tictactoe;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void XandO(MouseEvent event) {
        Button clickedButton = (Button) event.getSource();
        clickedButton.setText(getPlayerOne());
        clickedButton.setDisable(true);
        --count;
        checkWinner();
        if (!isGameIsOver()) {
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

        /* win if
         *Horisontellt
         * 0,1,2
         * 3,4,5
         * 6,7,8
         *
         *Vertikalt
         * 0,3,6
         * 1,4,7
         * 2,5,8
         *
         *Diagonalt
         * 0,4,8
         * 2,4,6
         *
         * */


        StringBuilder horizontal;
        for (int i = 0; i <= 6; i += 3) {
            horizontal = new StringBuilder();
            horizontal.append(buttons.get(i).getText());
            horizontal.append(buttons.get(i + 1).getText());
            horizontal.append(buttons.get(i + 2).getText());
            if (weHaveAWinner(horizontal, i, 1))
                return;
        }

        StringBuilder verticalStr;
        for (int i = 0; i < 3; i++) {
            verticalStr = new StringBuilder();
            verticalStr.append(buttons.get(i).getText());
            verticalStr.append(buttons.get(i + 3).getText());
            verticalStr.append(buttons.get(i + 6).getText());
            if (weHaveAWinner(verticalStr, i, 3))
                return;
        }

        StringBuilder diagonal = new StringBuilder();
        diagonal.append(buttons.get(0).getText());
        diagonal.append(buttons.get(4).getText());
        diagonal.append(buttons.get(8).getText());
        if (weHaveADiagonalWinner(diagonal)) {
            List<Button> list = Arrays.asList(buttons.get(0), buttons.get(4), buttons.get(8));
            changeColor(list);
            return;
        }

        diagonal = new StringBuilder();
        diagonal.append(buttons.get(2).getText());
        diagonal.append(buttons.get(4).getText());
        diagonal.append(buttons.get(6).getText());
        if (weHaveADiagonalWinner(diagonal)) {
            List<Button> list = Arrays.asList(buttons.get(2), buttons.get(4), buttons.get(6));
            changeColor(list);
        }
    }

    private boolean weHaveADiagonalWinner(StringBuilder str) {
        if (str.toString().equals("XXX") || str.toString().equals("OOO")) {
            SetWinner(str);
            setGameIsOver(true);
            return true;
        }
        return false;
    }

    private boolean weHaveAWinner(StringBuilder str, int i, int d) {
        if (str.toString().equals("XXX") || str.toString().equals("OOO")) {
            SetWinner(str);
            List<Button> list = Arrays.asList(buttons.get(i), buttons.get(i + d), buttons.get(i + (d + d)));
            changeColor(list);
            setGameIsOver(true);
            return true;
        }
        return false;
    }

    private void SetWinner(StringBuilder horizontal) {
        switch (horizontal.toString()) {
            case "XXX" -> {
                setWinnerName("PLAYER WINS");
                setPlayerScore(getPlayerScore() + 1);
            }
            case "OOO" -> {
                setWinnerName("COMPUTER WINS");
                setComputerScore(getComputerScore() + 1);
            }
        }
    }

    public void resetGame() {
        resetRound();
        setPlayerScore(0);
        setComputerScore(0);
    }

    private static void resetButton(List<Button> b) {
        b.stream().forEach((e) -> {
            e.setBorder(null);
            e.setDisable(false);
            e.setText("");
        });
    }

    public void resetRound() {
        resetButton(buttons);
        setGameIsOver(false);
        setWinnerName("");
        count = 9;
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

    public void changeColor(List<Button> buttonsThatWon) {
        for (Button b : buttonsThatWon) {
            b.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
        }
    }
}