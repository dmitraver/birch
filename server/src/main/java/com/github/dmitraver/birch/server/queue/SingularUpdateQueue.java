package com.github.dmitraver.birch.server.queue;

import com.github.dmitraver.birch.server.processors.RequestProcessingException;
import com.github.dmitraver.birch.server.processors.RequestProcessor;
import com.github.dmitraver.birch.server.utils.Tuple;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class SingularUpdateQueue<Request, Response> implements Runnable {

    private static final int MAX_QUEUE_CAPACITY = 100;

    private ArrayBlockingQueue<Tuple<Request, CompletableFuture<Response>>> queue = new ArrayBlockingQueue<>(MAX_QUEUE_CAPACITY);
    private RequestProcessor<Request, Response> requestProcessor;

    public SingularUpdateQueue(RequestProcessor<Request, Response> requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    public CompletableFuture<Response> submit(Request request) {
        try {
            CompletableFuture<Response> future = new CompletableFuture<>();
            Tuple<Request, CompletableFuture<Response>> requestAndResponse = new Tuple<>(request, future);
            queue.put(requestAndResponse);
            return future;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            Optional<Tuple<Request, CompletableFuture<Response>>> requestAndResponse = take();
            requestAndResponse.ifPresent(tuple -> {
                CompletableFuture<Response> responseFuture = tuple.getRight();

                try {
                    Response response = requestProcessor.process(tuple.getLeft());
                    responseFuture.complete(response);
                } catch (RequestProcessingException e) {
                    responseFuture.completeExceptionally(e);
                }
            });
        }
    }

    private Optional<Tuple<Request, CompletableFuture<Response>>> take() {
        try {
            return Optional.ofNullable(queue.poll(300, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            return Optional.empty();
        }
    }
}
