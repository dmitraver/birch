package com.github.dmitraver.birch.server.processors;

import com.github.dmitraver.birch.protocol.requests.GetRequest;
import com.github.dmitraver.birch.protocol.requests.ReadRequest;
import com.github.dmitraver.birch.server.storage.Storage;

import java.util.Optional;

/**
 * Processor that takes a read request and executes it against the underlying storage and returns result.
 */
public final class ReadRequestProcessor implements RequestProcessor<ReadRequest, Optional<String>> {

    private Storage<String, String> storage;

    public ReadRequestProcessor(Storage<String, String> storage) {
        this.storage = storage;
    }

    @Override
    public Optional<String> process(ReadRequest readRequest) throws RequestProcessingException {
        if (readRequest instanceof GetRequest) {
            GetRequest request = (GetRequest) readRequest;
            return storage.get(request.getKey());
        } else {
            return Optional.empty();
        }
    }
}
