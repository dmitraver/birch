package com.github.dmitraver.birch.server.queue;

import com.github.dmitraver.birch.protocol.requests.ReadRequest;
import com.github.dmitraver.birch.protocol.requests.Request;
import com.github.dmitraver.birch.protocol.requests.WriteRequest;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class RequestDispatcher {

    private ReadRequestsQueue<ReadRequest, Optional<String>> readRequestsQueue;
    private WriteRequestsQueue<WriteRequest, Optional<String>> writeRequestsQueue;

    public RequestDispatcher(ReadRequestsQueue<ReadRequest, Optional<String>> readRequestsQueue, WriteRequestsQueue<WriteRequest, Optional<String>> writeRequestsQueue) {
        this.readRequestsQueue = readRequestsQueue;
        this.writeRequestsQueue = writeRequestsQueue;
    }

    public Optional<CompletableFuture<Optional<String>>> dispatchRequest(Request request) {
        if (request instanceof ReadRequest) {
            return Optional.of(readRequestsQueue.submit((ReadRequest) request));
        } else if (request instanceof WriteRequest) {
            return Optional.of(writeRequestsQueue.submit((WriteRequest) request));
        } else {
            return Optional.empty();
        }
    }
}
