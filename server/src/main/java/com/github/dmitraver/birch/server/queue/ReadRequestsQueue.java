package com.github.dmitraver.birch.server.queue;

import com.github.dmitraver.birch.server.processors.RequestProcessor;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public final class ReadRequestsQueue<Request, Response> extends RequestsQueue<Request, Response> {

    public ReadRequestsQueue(RequestProcessor<Request, Response> requestProcessor) {
        super(new LinkedBlockingQueue<>(), requestProcessor, Executors.newCachedThreadPool());
    }
}
