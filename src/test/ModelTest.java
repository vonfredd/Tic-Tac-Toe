import com.iths.tictactoe.Model;
import javafx.scene.control.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;


class ModelTest {
    private Model model;

    @BeforeEach
    void setUp() throws IOException {
        model = new Model();
        model.addEmptyButtonMark();
    }

    @Test
    @DisplayName("playerWins")
    void PlayerWinsIfGamestateIsWinnerAndPlayerTurnIs1() {
        model.setGameState(Model.GameState.WINNER);
        model.setPlayerTurn(1);
        model.theWinnerIs(model.getPlayerTurn());
        assertEquals("PLAYER WINS", model.getTheWinnerIs());
    }

    @Test
    @DisplayName("computerWins")
    void computerWinsIfGamestateIsWinnerAndPlayerTurnIs0() {
        model.setGameState(Model.GameState.WINNER);
        model.setPlayerTurn(0);
        model.theWinnerIs(model.getPlayerTurn());
        assertEquals("COMPUTER WINS", model.getTheWinnerIs());
    }

    @Test
    @DisplayName("No winner")
    void checkIfThereIsNoWinner() {
        model.setGameState(Model.GameState.NO_WINNER);
        model.implementStateOfGame(model.getGameState());

        assertNotEquals("COMPUTER WINS", model.getTheWinnerIs());
        assertNotEquals("PLAYER WINS", model.getTheWinnerIs());
        assertEquals("NO WINNER", model.getTheWinnerIs());
    }

    @Test
    @DisplayName("check if round is over when there is no winner")
    void roundIsOverWhenThereIsNoWinner() {
        model.implementStateOfGame(Model.GameState.NO_WINNER);
        assertTrue(model.getGameOver());
    }

    @Test
    @DisplayName("check if round is over when there is a winner")
    void roundIsOverWhenThereIsAWinner() {
        model.implementStateOfGame(Model.GameState.WINNER);
        assertTrue(model.getGameOver());
    }

    @Test
    @DisplayName("check if round is not over when game is running")
    void roundIsNotOverWhenGameIsRunning() {
        model.implementStateOfGame(Model.GameState.RUNNING);
        assertFalse(model.getGameOver());
    }

    @Test
    @DisplayName("A button with no player mark is ok")
    void testRandomButton() {
        int index = model.randomButton();

        assertTrue(index >= 0 && index < model.getMarkingOfButtons().size());
        assertTrue(model.getMarkingOfButtons().get(index).getValue().isEmpty());
        
    }

    //todo: test för model constructor, lägga till random markings och testa weHaveAWinner

}