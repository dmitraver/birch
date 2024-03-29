package com.github.dmitraver.birch.server.processors;

import com.github.dmitraver.birch.server.requests.PutRequest;
import com.github.dmitraver.birch.server.requests.WriteRequest;
import com.github.dmitraver.birch.server.storage.Storage;

import java.util.Optional;

/**
 * Processor that takes a write request and executes it against the underlying storage and returns result.
 */
public final class WriteRequestProcessor implements RequestProcessor<WriteRequest, Optional<String>> {

    private Storage<String, String> storage;

    public WriteRequestProcessor(Storage<String, String> storage) {
        this.storage = storage;
    }

    @Override
    public Optional<String> process(WriteRequest writeRequest) throws RequestProcessingException {
        if (writeRequest instanceof PutRequest) {
            PutRequest request = (PutRequest) writeRequest;
            return storage.put(request.getKey(), request.getValue());
        } else {
            return Optional.empty();
        }
    }
}
