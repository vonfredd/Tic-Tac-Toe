import com.iths.tictactoe.Model;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
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
        model.implementGamestate(model.getGameState());

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

    @Test
    @DisplayName("A disabled button is not a valid choice")
    void cannotUseADisabledButton(){

     Button button = new Button();



      // public Button randomButton(ObservableList<Button> buttons) {
      //     var randomedButton = buttons.get((int) (Math.random() * v5Buttons.size() - 1));
      //     while (randomedButton.isDisabled()) {
      //         randomedButton = buttons.get((int) (Math.random() * v5Buttons.size() - 1));
      //     }
      //     return randomedButton;
      // }
    }
}