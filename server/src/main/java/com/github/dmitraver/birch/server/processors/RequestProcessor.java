package com.github.dmitraver.birch.server.processors;

@FunctionalInterface
public interface RequestProcessor<Request, Response> {
    Response process(Request request) throws RequestProcessingException;
}
