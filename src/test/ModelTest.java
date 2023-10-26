import com.iths.tictactoe.Model;
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
    }

    @Test
    @DisplayName("playerWins")
    void thePlayerWins() {
        model.setGameState(Model.GameState.WINNER);
        model.setPlayerTurn(1);
        model.theWinnerIs(model.getPlayerTurn());
        assertEquals("PLAYER WINS", model.getTheWinnerIs());
    }

    @Test
    @DisplayName("computerWins")
    void theComputerWins() {
        model.setGameState(Model.GameState.WINNER);
        model.setPlayerTurn(0);
        model.theWinnerIs(model.getPlayerTurn());
        assertEquals("COMPUTER WINS", model.getTheWinnerIs());
    }

    @Test
    @DisplayName("no-one wins")
    void noWinner() {
        model.setGameState(Model.GameState.NO_WINNER);
        model.setEmptySpaces(0);
        model.thereIsNoWinner();
        assertNotEquals("COMPUTER WINS", model.getTheWinnerIs());
        assertNotEquals("PLAYER WINS", model.getTheWinnerIs());
        assertEquals("NO WINNER", model.getTheWinnerIs());
    }

    @Test
    @DisplayName("check if round is over when there is no winner")
    void roundIsOverWhenThereIsNoWinner() {
        model.implementGamestate(Model.GameState.NO_WINNER);
        assertTrue(model.getGameOver());
    }

    @Test
    @DisplayName("check if round is over when there is a winner")
    void roundIsOverWhenThereIsAWinner() {
        model.implementGamestate(Model.GameState.WINNER);
        assertTrue(model.getGameOver());
    }

    @Test
    @DisplayName("check if round is not over when game is running")
    void roundIsNotOverWhenGameIsRunning() {
        model.implementGamestate(Model.GameState.RUNNING);
        assertFalse(model.getGameOver());
    }

}