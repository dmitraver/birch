package com.github.dmitraver.birch.server.processors;

/**
 * Generic interface that defines processor that processes request and return response.
 * @param <Request> type of the request
 * @param <Response> type of the response
 */
@FunctionalInterface
public interface RequestProcessor<Request, Response> {
    Response process(Request request) throws RequestProcessingException;
}
