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

    @BeforeEach
    void setUp() {
        model = new Model(); // Initialize your model before each test
    }

    @Test
    @DisplayName("Test game over property")
    void testGameOverProperty() {
        // You can write test cases here to check the behavior of your game over property
        model.setGameOver(true);
        Assertions.assertTrue(model.getGameOver());
        model.setGameOver(false);
        Assertions.assertFalse(model.getGameOver());
    }
}