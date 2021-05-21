package com.github.dmitraver.birch.server.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class InMemoryStorage<K, V> implements Storage<String, String> {

    private Map<String, String> map = new HashMap<>();

    @Override
    public Optional<String> put(String key, String value) {
        String oldValue = map.put(key, value);
        return Optional.ofNullable(oldValue);
    }

    @Override
    public Optional<String> get(String key) {
        String value = map.get(key);
        return Optional.ofNullable(value);
    }
}
