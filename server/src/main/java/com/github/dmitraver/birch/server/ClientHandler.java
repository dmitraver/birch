package com.github.dmitraver.birch.server;

import com.github.dmitraver.birch.protocol.RequestParser;
import com.github.dmitraver.birch.protocol.RequestParsingException;
import com.github.dmitraver.birch.protocol.requests.QuitRequest;
import com.github.dmitraver.birch.protocol.requests.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public final class ClientHandler implements Runnable {

    private Socket clientSocket;
    private RequestParser requestParser;

    public ClientHandler(Socket clientSocket, RequestParser parser) {
        this.clientSocket = clientSocket;
        this.requestParser = parser;
    }

    @Override
    public void run() {
        try (Socket socket = clientSocket) {
            System.out.println("Client connected...");
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Request request = requestParser.parse(line);
                    if (request instanceof QuitRequest) {
                        writer.println("Cya soon...");
                        break;
                    }

                    writer.println("Processing...");
                } catch (RequestParsingException e) {
                    writer.println("-ERR unknown request type");
                }
            }
        } catch (IOException e) {
            System.err.println("Opps, exception in client handler" + e.getMessage());
        }
    }
}
