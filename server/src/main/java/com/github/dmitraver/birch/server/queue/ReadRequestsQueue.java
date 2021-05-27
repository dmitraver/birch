package com.github.dmitraver.birch.server.queue;

import com.github.dmitraver.birch.server.processors.RequestProcessor;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Queue that processes read requests using thread pool with multiple threads.
 * @param <Request> type of the request to process
 * @param <Response> type of the response to return
 */
public final class ReadRequestsQueue<Request, Response> extends RequestsQueue<Request, Response> {

    public ReadRequestsQueue(RequestProcessor<Request, Response> requestProcessor) {
        super(new LinkedBlockingQueue<>(), requestProcessor, Executors.newCachedThreadPool());
    }
}
