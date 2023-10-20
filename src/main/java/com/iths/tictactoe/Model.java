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
    private int count;
    private StringProperty winnerName = new SimpleStringProperty();
    private int[][] arr = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private StringProperty message = new SimpleStringProperty();
    private final ReadOnlyStringProperty playerOne = new ReadOnlyStringWrapper("X");
    private final ReadOnlyStringProperty playerTwo = new ReadOnlyStringWrapper("O");
    private BooleanProperty gameIsOver = new SimpleBooleanProperty();
    private ObservableList<Button> buttons = FXCollections.observableList(new ArrayList<>());

    private IntegerProperty playerScore = new SimpleIntegerProperty();
    private IntegerProperty computerScore = new SimpleIntegerProperty();


    public void setCount(int count) {
        this.count = count;
    }

    public void addButtons(Button button) {
        buttons.add(button);
        button.setOpacity(1);
    }


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

    public void XandO(MouseEvent event) {
        Button clickedButton = (Button) event.getSource();
        clickedButton.setText(getPlayerOne());
        clickedButton.setDisable(true);
        --count;
        winner(arr, buttons);
        if (!isGameIsOver()) {
            computerPick();
            winner(arr, buttons);
        }

        //Player 2
        //            clickedButton.setText(getPlayerTwo());
        //            clickedButton.setDisable(true);
        //            playerTurn = 1;
    }

    public void computerPick() {
        if (count == 0) {
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
       // if (playerOneTurn)
         //   buttons.get(choise).setText(getPlayerOne());
        buttons.get(choise).setText(getPlayerTwo());
        buttons.get(choise).setDisable(true);
        --count;
    }


    private void winner(int[][] winCondition, ObservableList<Button> buttons) {
        StringBuilder possibleWinner;
        List<Button> tempList;
        for (int i = 0; i < winCondition.length; i++) {
            tempList = new ArrayList<>();
            possibleWinner = new StringBuilder();
            for (int j = 0; j < winCondition[i].length; j++) {
                possibleWinner.append(buttons.get(winCondition[i][j]).getText());
                tempList.add(buttons.get(winCondition[i][j]));
            }
            if (possibleWinner.toString().equals("XXX")) {
                paintWinningButtons(tempList);
                setWinnerName("Player Wins");
                setGameIsOver(true);
                setPlayerScore(getPlayerScore() + 1);

            } else if (possibleWinner.toString().equals("OOO")) {
                paintWinningButtons(tempList);
                setWinnerName("Computer Wins");
                setGameIsOver(true);
                setComputerScore(getComputerScore() + 1);
            }
        }
    }

    public void resetGame() {
        resetRound();
        setPlayerScore(0);
        setComputerScore(0);
    }

    private static void resetButtons(List<Button> b) {
        b.stream().forEach((e) -> {
            e.setBorder(null);
            e.setDisable(false);
            e.setText("");
        });
    }

    public void resetRound() {
        resetButtons(buttons);
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
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    private static void paintWinningButtons(List<Button> tempList) {
        tempList.stream().forEach((e) -> e.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(5)))));
        tempList.stream().forEach((e) -> e.setOpacity(1));
    }

    public void difficultyHard() {
        if (count == 0) {
            setWinnerName("NO WINNER!");
            setGameIsOver(true);
            return;
        }
        int choise = (int) (Math.random() * buttons.size() - 1);
        calculateMove(choise);

        //If playerOneturn == true. calculatemove should have condition to set playermark.
        // boolean playerOneTurn = true;
    }



}