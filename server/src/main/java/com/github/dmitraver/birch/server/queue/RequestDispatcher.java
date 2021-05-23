package com.github.dmitraver.birch.server.queue;

import com.github.dmitraver.birch.protocol.requests.ReadRequest;
import com.github.dmitraver.birch.protocol.requests.Request;
import com.github.dmitraver.birch.protocol.requests.WriteRequest;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class RequestDispatcher<Response> {

    private ReadRequestsQueue<ReadRequest, Response> readRequestsQueue;
    private WriteRequestsQueue<WriteRequest, Response> writeRequestsQueue;

    public RequestDispatcher(ReadRequestsQueue<ReadRequest, Response> readRequestsQueue, WriteRequestsQueue<WriteRequest, Response> writeRequestsQueue) {
        this.readRequestsQueue = readRequestsQueue;
        this.writeRequestsQueue = writeRequestsQueue;
    }

    public Optional<CompletableFuture<Response>> dispatchRequest(Request request) {
        if (request instanceof ReadRequest) {
            return Optional.of(readRequestsQueue.submit((ReadRequest) request));
        } else if (request instanceof WriteRequest) {
            return Optional.of(writeRequestsQueue.submit((WriteRequest) request));
        } else {
            return Optional.empty();
        }
    }
}
