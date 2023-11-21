package com.iths.tictactoe;

import com.iths.tictactoe.server.MultiplayerServer;
import com.iths.tictactoe.server.PlayerClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private Button vsComputer;
    @FXML
    private Button multiplayer;

    private Stage stage;

    private MultiplayerServer server;
    private PlayerClient player;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        player = new PlayerClient();
    }

    public void multiPlayer() throws IOException {
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

    public void hostGame() throws IOException {
        server = new MultiplayerServer();

        Thread serverThread = new Thread(() -> {
            try {
                server.start(5555);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

        MultiplayerController.setPlayerTurn(0);
        multiPlayer();

    }

    public void joinGame() throws IOException {

        MultiplayerController.setPlayerTurn(1);
        multiPlayer();
    }
}
