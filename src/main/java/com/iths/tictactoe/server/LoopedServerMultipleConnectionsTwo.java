package com.iths.tictactoe.server;

import com.iths.tictactoe.HelloController;
import com.iths.tictactoe.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LoopedServerMultipleConnectionsTwo {
    private final HelloController helloController;
    private ServerSocket serverSocket;

    Model model = new Model();

    private static int playerCount = 0;
    private static int playerTurn = 0;

    public LoopedServerMultipleConnectionsTwo(HelloController helloController) {
        this.helloController = helloController;
    }


    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true)
            new MultiClientHandler(serverSocket.accept(), helloController).start();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class MultiClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private HelloController helloController;

        public MultiClientHandler(Socket socket, HelloController helloController) {
            this.clientSocket = socket;
            this.helloController = helloController;
            System.out.println("player" + (++playerCount) + " Connected");
        }

        public void run() {

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            String inputLine;
            while (true) {
                try {
                    if ((inputLine = in.readLine()) == null) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (".".equals(inputLine)) {
                    out.println("bye");
                    break;
                }
                if (playerTurn == 1)
                    out.println("i got you!");
                out.println("i got me!");
                swapPlayerTurn();
            }

            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.close();
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void swapPlayerTurn() {
        if (playerTurn == 0)
            playerTurn = 1;
        else
            playerTurn = 0;
    }
}