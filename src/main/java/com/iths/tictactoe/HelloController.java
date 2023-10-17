package com.iths.tictactoe;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class HelloController {
 //  @FXML
 //  private Label welcomeText;
    @FXML
    private Button buttonRight;
    @FXML
    private Model model = new Model();

    public Model getModel(){
        return model;
    }

    public void initialize() {
     //   welcomeText.textProperty().bind(model.messageProperty()); // bind field to Model.
    }

    @FXML
    protected void onHelloButtonClick() {
  //      model.setMessage("Welcome to JavaFX Application!");
    }

    public void onLabelClicked() {
 //       model.setMessage("");
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        buttonRight.setText("X" + mouseEvent.getX() + "    Y" + mouseEvent.getY());
    }
}