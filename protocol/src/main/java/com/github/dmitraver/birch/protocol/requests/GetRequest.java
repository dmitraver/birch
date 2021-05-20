package com.github.dmitraver.birch.protocol.requests;

public final class GetRequest implements ReadRequest {

    private final String key;

    public GetRequest(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
