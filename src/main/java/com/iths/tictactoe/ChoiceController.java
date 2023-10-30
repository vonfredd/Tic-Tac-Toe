package com.iths.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ChoiceController {
    @FXML
    private Button vsComputer;
    @FXML
    private Button multiplayer;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void multiPlayer(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("multiplayer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("TicTacToe!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void playerVsComputer(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("TicTacToe!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
