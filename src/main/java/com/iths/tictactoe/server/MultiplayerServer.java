package com.iths.tictactoe.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiplayerServer {
    private ServerSocket serverSocket;  //waits for request
    private Socket clientSocket; //endpoint for communications between machines
    private PrintWriter out; //formatted representations of objects to a text-output stream. kind of like printstream but flush???
    private BufferedReader in; //reds from input streams. Use to wrap example FileReaders

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();

    }

    public static void main(String[] args) {

    }
}
