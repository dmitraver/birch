package com.github.dmitraver.birch.server;

import com.github.dmitraver.birch.protocol.RequestParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        int port = 9999;

        // TODO: check which threadpool is better for long running connections
        ExecutorService executor = Executors.newCachedThreadPool();
        RequestParser requestParser = new RequestParser();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                System.out.println("Waiting for the clients to connect...");
                Socket clientSocket = serverSocket.accept();
                executor.submit(new ClientHandler(clientSocket, requestParser));
            }
        } catch (IOException e) {
            System.err.println("Opps, exception " + e.getMessage());
        } finally {
            executor.shutdownNow();
        }
    }
}
