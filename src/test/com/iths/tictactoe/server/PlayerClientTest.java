package com.iths.tictactoe.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerClientTest {

    @Test
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException {
        PlayerClient client = new PlayerClient();
        client.startConnection("127.0.0.1", 5555);
        String response = client.sendMessage("hello server");
        assertEquals("hello server", response);
    }
}