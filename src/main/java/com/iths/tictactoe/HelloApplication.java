package com.iths.tictactoe;

import com.iths.tictactoe.server.LoopedServerMultipleConnectionsTwo;
import com.iths.tictactoe.server.PlayerClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    static ChoiceController choiceController;
    static SinglePlayerController singlePlayerController;
    static LoopedServerMultipleConnectionsTwo server;
    static PlayerClient client1;
    static PlayerClient client2;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("choice.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        choiceController = fxmlLoader.getController();
        choiceController.setStage(stage);
        stage.setTitle("TicTacToe!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        singlePlayerController = new SinglePlayerController();
        server = new LoopedServerMultipleConnectionsTwo(singlePlayerController);

        Thread serverThread = new Thread(() -> {
            try {
                server.start(5555);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

        launch();

    }
}