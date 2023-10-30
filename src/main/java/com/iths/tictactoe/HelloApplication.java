package com.iths.tictactoe;

import com.iths.tictactoe.server.LoopedServerMultipleConnections;
import com.iths.tictactoe.server.PlayerClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    static HelloController helloController;
    static LoopedServerMultipleConnections server;
    static PlayerClient client1;
    static PlayerClient client2;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("TicTacToe!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        helloController = new HelloController();
        server = new LoopedServerMultipleConnections(helloController);
        client1 = new PlayerClient();
        client2 = new PlayerClient();

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