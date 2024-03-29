package com.github.dmitraver.birch.server.queue;

import com.github.dmitraver.birch.server.processors.RequestProcessingException;
import com.github.dmitraver.birch.server.processors.RequestProcessor;
import com.github.dmitraver.birch.server.utils.Tuple;

import java.util.Optional;
import java.util.concurrent.*;

/**
 * Generic implementation of the requests queue that uses thread pool to process requests and return results
 * to the caller as future.
 * @param <Request> type of the request to process
 * @param <Response> type of the response to return to the caller
 */
abstract class RequestsQueue<Request, Response> {

    private final BlockingQueue<Tuple<Request, CompletableFuture<Response>>> queue;
    private RequestProcessor<Request, Response> requestProcessor;
    private ExecutorService executor;

    public RequestsQueue(BlockingQueue<Tuple<Request, CompletableFuture<Response>>> queue, RequestProcessor<Request, Response> requestProcessor, ExecutorService executor) {
        this.queue = queue;
        this.requestProcessor = requestProcessor;
        this.executor = executor;
    }

    public CompletableFuture<Response> submit(Request request) {
        try {
            CompletableFuture<Response> future = new CompletableFuture<>();
            Tuple<Request, CompletableFuture<Response>> requestAndResponse = new Tuple<>(request, future);
            queue.put(requestAndResponse);
            executor.submit(() -> {
                Optional<Tuple<Request, CompletableFuture<Response>>> result = take();
                result.ifPresent(tuple -> {
                    CompletableFuture<Response> responseFuture = tuple.getRight();

                    try {
                        Response response = requestProcessor.process(tuple.getLeft());
                        responseFuture.complete(response);
                    } catch (RequestProcessingException e) {
                        responseFuture.completeExceptionally(e);
                    }
                });
            });
            return future;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Tuple<Request, CompletableFuture<Response>>> take() {
        try {
            return Optional.of(queue.take());
        } catch (InterruptedException e) {
            return Optional.empty();
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}
