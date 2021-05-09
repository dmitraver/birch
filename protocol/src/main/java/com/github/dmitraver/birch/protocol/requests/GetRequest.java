package com.github.dmitraver.birch.protocol.requests;

public final class GetRequest implements Request {

    private final String key;

    public GetRequest(String key) {
        this.key = key;
    }
}
