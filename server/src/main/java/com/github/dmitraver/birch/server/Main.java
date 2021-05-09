package com.github.dmitraver.birch.server;

import com.github.dmitraver.birch.protocol.RequestParser;
import com.github.dmitraver.birch.protocol.requests.QuitRequest;
import com.github.dmitraver.birch.protocol.requests.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        int port = 9999;

        RequestParser requestParser = new RequestParser();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                System.out.println("Waiting for the clients to connect...");
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected...");
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        Optional<Request> requestOpt = requestParser.parse(line);
                        if (!requestOpt.isPresent()) {
                            writer.println("Unknown request...");
                        } else {
                            // boy this is ugly in java, look for a better way
                            Request request = requestOpt.get();
                            if (request instanceof QuitRequest) {
                                writer.println("Cya soon...");
                                break;
                            }

                            writer.println("Processing...");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Opps, exception " + e.getMessage());
        }
    }
}
