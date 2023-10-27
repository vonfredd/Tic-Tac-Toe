package com.iths.tictactoe.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SimpleClientTest {

    @Test
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException {
        SimpleClient client = new SimpleClient();
        client.startConnection("127.0.0.1", 8888);
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }
}