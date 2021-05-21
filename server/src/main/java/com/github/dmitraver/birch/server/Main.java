package com.github.dmitraver.birch.server;

import com.github.dmitraver.birch.protocol.RequestParser;
import com.github.dmitraver.birch.protocol.requests.ReadRequest;
import com.github.dmitraver.birch.protocol.requests.WriteRequest;
import com.github.dmitraver.birch.server.client.ClientHandler;
import com.github.dmitraver.birch.server.processors.ReadRequestProcessor;
import com.github.dmitraver.birch.server.processors.WriteRequestProcessor;
import com.github.dmitraver.birch.server.queue.ReadRequestsQueue;
import com.github.dmitraver.birch.server.queue.RequestDispatcher;
import com.github.dmitraver.birch.server.queue.WriteRequestsQueue;
import com.github.dmitraver.birch.server.storage.InMemoryStorage;
import com.github.dmitraver.birch.server.storage.Storage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static Storage<String, String> storage = new InMemoryStorage<String, String>();
    private static ReadRequestProcessor readRequestProcessor = new ReadRequestProcessor(storage);
    private static WriteRequestProcessor writeRequestProcessor = new WriteRequestProcessor(storage);

    private static ReadRequestsQueue<ReadRequest, Optional<String>> readRequestsQueue = new ReadRequestsQueue<>(readRequestProcessor);
    private static WriteRequestsQueue<WriteRequest, Optional<String>> writeRequestsQueue = new WriteRequestsQueue<>(writeRequestProcessor);
    private static RequestDispatcher dispatcher = new RequestDispatcher(readRequestsQueue, writeRequestsQueue);

    public static void main(String[] args) {
        int port = 9999;

        // TODO: check which threadpool is better for long running connections
        ExecutorService executor = Executors.newCachedThreadPool();
        RequestParser requestParser = new RequestParser();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                System.out.println("Waiting for the clients to connect...");
                Socket clientSocket = serverSocket.accept();
                executor.submit(new ClientHandler(clientSocket, requestParser, dispatcher));
            }
        } catch (IOException e) {
            System.err.println("Opps, exception " + e.getMessage());
        } finally {
            executor.shutdownNow();
        }
    }
}
