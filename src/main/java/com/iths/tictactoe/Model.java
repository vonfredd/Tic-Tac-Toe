package com.iths.tictactoe;

import javafx.beans.property.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private int playerTurn;
    private StringBuilder playerPoints = new StringBuilder();
    private StringBuilder computerPoints = new StringBuilder();
    private StringProperty message = new SimpleStringProperty();
    private ReadOnlyStringProperty playerOne = new ReadOnlyStringWrapper("X");
    private ReadOnlyStringProperty playerTwo = new ReadOnlyStringWrapper("O");
    private List<Button> buttons = new ArrayList<>();

    Map<List<Button>, String> winnerMap = new HashMap<>();
    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public void addButtons(Button button) {
        buttons.add(button);
    }

    public List<Button> getButtonsList() {
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
        //buttons.remove(clickedButton);
        System.out.println(count);
        if (count > 0)
            computerPick();
        else
            hideAllButtons();

        //Player 2
        //            clickedButton.setText(getPlayerTwo());
        //            clickedButton.setDisable(true);
        //            playerTurn = 1;

    }

    public void computerPick() {
        int choise = (int) (Math.random() * buttons.size() - 1);
        calculateMove(choise);

        // buttons.remove(buttons.get(choise));
    }

    public void hideAllButtons() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setOpacity(0);
        }
    }


    private void calculateMove(int choise) {
        while (buttons.get(choise).isDisabled()) {
            choise = (int) (Math.random() * buttons.size() - 1);
        }
        buttons.get(choise).setText(getPlayerTwo());
        buttons.get(choise).setDisable(true);
        --count;
    }
}