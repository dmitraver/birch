package com.github.dmitraver.birch.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int port = 9999;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                System.out.println("Waiting for the clients to connect...");
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected...");
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.equals("QUIT")) {
                            writer.println("Cya soon...");
                            break;
                        } else {
                            System.out.println("Client: " + line);
                            writer.println("Got it!");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Opps, exception " + e.getMessage());
        }
    }
}
