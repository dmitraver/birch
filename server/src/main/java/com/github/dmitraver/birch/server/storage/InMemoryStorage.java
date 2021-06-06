package com.github.dmitraver.birch.server.storage;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryStorage<K, V> implements Storage<K, V> {

    private final Map<K, V> map = new ConcurrentHashMap<>();

    @Override
    public Optional<V> put(K key, V value) {
        V oldValue = map.put(key, value);
        return Optional.ofNullable(oldValue);
    }

    @Override
    public Optional<V> get(K key) {
        V value = map.get(key);
        return Optional.ofNullable(value);
    }
}
