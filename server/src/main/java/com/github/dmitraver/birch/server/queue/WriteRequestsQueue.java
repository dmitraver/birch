package com.github.dmitraver.birch.server.queue;

import com.github.dmitraver.birch.server.processors.RequestProcessor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;

public class WriteRequestsQueue<Request, Response> extends RequestsQueue<Request, Response> {

    private static final int MAX_QUEUE_CAPACITY = 100;

    public WriteRequestsQueue(RequestProcessor<Request, Response> requestProcessor) {
        super(new ArrayBlockingQueue<>(MAX_QUEUE_CAPACITY), requestProcessor, Executors.newSingleThreadExecutor());
    }
}
