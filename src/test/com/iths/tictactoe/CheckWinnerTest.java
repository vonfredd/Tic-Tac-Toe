package com.iths.tictactoe;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckWinnerTest {

    private Model modelTwo;
    private final ObservableList<SimpleStringProperty> myButtons = FXCollections.observableArrayList();
    @BeforeEach
    void setUp() {
        modelTwo = new Model(myButtons);
    }

    @Test
    void testHorizontalWin() {
        modelTwo.getMarkingOfButtons().get(0).setValue("X");
        modelTwo.getMarkingOfButtons().get(1).setValue("X");
        modelTwo.getMarkingOfButtons().get(2).setValue("X");
        assertTrue(modelTwo.weHaveAWinner());
    }
    @Test
    void testVerticalWin() {
        modelTwo.getMarkingOfButtons().get(0).setValue("X");
        modelTwo.getMarkingOfButtons().get(3).setValue("X");
        modelTwo.getMarkingOfButtons().get(6).setValue("X");
        assertTrue(modelTwo.weHaveAWinner());
    }
    @Test
    void diagonalWin() {
        modelTwo.getMarkingOfButtons().get(2).setValue("O");
        modelTwo.getMarkingOfButtons().get(4).setValue("O");
        modelTwo.getMarkingOfButtons().get(6).setValue("O");
        assertTrue(modelTwo.weHaveAWinner());
    }
    @Test
    void testHorizontalFail() {
        modelTwo.getMarkingOfButtons().get(0).setValue("X");
        modelTwo.getMarkingOfButtons().get(1).setValue("O");
        modelTwo.getMarkingOfButtons().get(2).setValue("X");
        assertFalse(modelTwo.weHaveAWinner());
    }
}