import com.iths.tictactoe.HelloController;
import com.iths.tictactoe.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ModelTest {
private Model model;
private HelloController helloController;

    @BeforeEach
    void setUp() {
        model = new Model();

    }

    @Test
    @DisplayName("Test playerWins")
    void thePlayerWins(){
        model.setGameState(Model.GameState.WINNER);
        model.setPlayerTurn(1);
        model.theWinnerIs(model.getPlayerTurn());
        Assertions.assertEquals("PLAYER WINS",model.getTheWinnerIs());
    }

    @Test
    @DisplayName("Test computerWins")
    void theComputerWins(){
        model.setGameState(Model.GameState.WINNER);
        model.setPlayerTurn(0);
        model.theWinnerIs(model.getPlayerTurn());
        Assertions.assertEquals("COMPUTER WINS",model.getTheWinnerIs());
    }
    @Test
    @DisplayName("Test no-one wins")
    void noWinner(){
        model.setGameState(Model.GameState.NO_WINNER);
        model.setEmptySpaces(0);
        model.thereIsNoWinner();
        Assertions.assertNotEquals("COMPUTER WINS",model.getTheWinnerIs());
        Assertions.assertNotEquals("PLAYER WINS",model.getTheWinnerIs());
        Assertions.assertEquals("NO WINNER",model.getTheWinnerIs());
    }


}