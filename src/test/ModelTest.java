import com.iths.tictactoe.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ModelTest {
    private Model model;

    @BeforeEach
    void setUp() {
        model = new Model();
    }

    @Test
    @DisplayName("playerWins")
    void thePlayerWins() {
        model.setGameState(Model.GameState.WINNER);
        model.setPlayerTurn(1);
        model.theWinnerIs(model.getPlayerTurn());
        Assertions.assertEquals("PLAYER WINS", model.getTheWinnerIs());
    }

    @Test
    @DisplayName("computerWins")
    void theComputerWins() {
        model.setGameState(Model.GameState.WINNER);
        model.setPlayerTurn(0);
        model.theWinnerIs(model.getPlayerTurn());
        Assertions.assertEquals("COMPUTER WINS", model.getTheWinnerIs());
    }

    @Test
    @DisplayName("no-one wins")
    void noWinner() {
        model.setGameState(Model.GameState.NO_WINNER);
        model.setEmptySpaces(0);
        model.thereIsNoWinner();
        Assertions.assertNotEquals("COMPUTER WINS", model.getTheWinnerIs());
        Assertions.assertNotEquals("PLAYER WINS", model.getTheWinnerIs());
        Assertions.assertEquals("NO WINNER", model.getTheWinnerIs());
    }

    @Test
    @DisplayName("check if round is over when there is no winner")
    void roundIsOverWhenThereIsNoWinner() {
        model.setGameState(Model.GameState.NO_WINNER);
        model.implementGamestate();
        Assertions.assertTrue(model.getGameOver());
    }

    @Test
    @DisplayName("check if round is over when there is a winner")
    void roundIsOverWhenThereIsAWinner() {
        model.setGameState(Model.GameState.WINNER);
        model.implementGamestate();
        Assertions.assertTrue(model.getGameOver());
    }

    @Test
    @DisplayName("check if round is not over when game is running")
    void roundIsNotOverWhenGameIsRunning() {
        model.setGameState(Model.GameState.RUNNING);
        model.implementGamestate();
        Assertions.assertFalse(model.getGameOver());
    }
}