import com.iths.tictactoe.HelloApplication;
import com.iths.tictactoe.HelloController;
import com.iths.tictactoe.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ModelTest {
    private Model model;
    private HelloApplication helloApplication;
    private HelloController helloController;

    @BeforeEach
    void setUp() throws IOException {
        helloApplication = new HelloApplication();
        model = new Model();
        helloController = new HelloController();

        // Perform any other common setup tasks
    }

    @Test
    @DisplayName("NO_WINNER")
    void checker() {
        model.setEmptySpotsLeft(0);
        model.setGameIsOver(true);


        Assertions.assertFalse(model.weHaveAWinner("123"));
        Assertions.assertEquals("NO_WINNER",model.getWinnerName());
    }
}