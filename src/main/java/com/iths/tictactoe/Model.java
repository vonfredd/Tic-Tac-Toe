package com.iths.tictactoe;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {
    private StringProperty message = new SimpleStringProperty();
    private ReadOnlyStringProperty playerOne = new ReadOnlyStringWrapper("X");
    private ReadOnlyStringProperty playerTwo = new ReadOnlyStringWrapper("O");

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
}