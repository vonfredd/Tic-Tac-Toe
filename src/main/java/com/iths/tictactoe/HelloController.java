package com.iths.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class HelloController {

    private int playerTurn = 1;

   @FXML
   private Button b1;
   @FXML
   private Button b2;
   @FXML
   private Button b3;
   @FXML
   private Button b4;
   @FXML
   private Button b5;
   @FXML
   private Button b6;
   @FXML
   private Button b7;
   @FXML
   private Button b8;
   @FXML
   private Button b9;


    @FXML
    private Button buttonRight;
    @FXML
    private Model model = new Model();

    public Model getModel(){
        return model;
    }

    public void initialize() {
        // welcomeText.textProperty().bind(model.messageProperty()); // bind field to Model.
    }

    public void onLabelClicked() {
 //       model.setMessage("");
    }

    public void changeText(MouseEvent event){
        //Button clickedButton = (Button) event.getSource();
        Button clickedButton = (Button) event.getSource();

        if (playerTurn == 1) {
            clickedButton.setText(model.getPlayerOne());
            playerTurn = 2;
        } else {
            clickedButton.setText(model.getPlayerTwo());
            playerTurn = 1;
        }

    }

}