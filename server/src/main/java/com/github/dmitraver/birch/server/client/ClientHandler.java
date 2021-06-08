package com.github.dmitraver.birch.server.client;

import com.github.dmitraver.birch.server.requests.Request;
import com.github.dmitraver.birch.server.requests.RequestParser;
import com.github.dmitraver.birch.server.requests.RequestParsingException;
import com.github.dmitraver.birch.server.requests.Requests;
import com.github.dmitraver.birch.server.queue.RequestDispatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public final class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final RequestParser requestParser;
    private final RequestDispatcher<Optional<String>> dispatcher;

    public ClientHandler(Socket clientSocket, RequestParser parser, RequestDispatcher<Optional<String>> dispatcher) {
        this.clientSocket = clientSocket;
        this.requestParser = parser;
        this.dispatcher = dispatcher;
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
                    if (line.startsWith(Requests.QUIT)) {
                        writer.println("Cya soon...");
                        break;
                    }

                    Request request = requestParser.parse(line);
                    Optional<CompletableFuture<Optional<String>>> resultOpt = dispatcher.dispatchRequest(request);
                    if(resultOpt.isPresent()) {
                        Optional<String> result = resultOpt.get().get();
                        writer.println(result.orElse("null"));
                    } else {
                        writer.println("-ERR request was interrupted on the server");
                    }
                } catch (RequestParsingException e) {
                    writer.println("-ERR unknown request type");
                } catch (ExecutionException | InterruptedException e) {
                    writer.println("-ERR request processing interrupted");
                }
            }
        } catch (IOException e) {
            System.err.println("Opps, exception in client handler" + e.getMessage());
        }
    }
}
