package com.github.dmitraver.birch.server.storage;

import java.util.Optional;

/**
 * Storage interface defines main operations that key value storage supports.
 * @param <K> type of the key
 * @param <V> type of the value
 */
public interface Storage<K, V> {

    /**
     * Stores a new key-value pair if no key exists or updates a value for existing key.
     * @param key key to associate value with
     * @param value value associated with a key
     * @return previous value associated with a given key if exists
     */
    Optional<V> put(K key, V value);

    /**
     * Gets a value associated with a given key if exists.
     * @param key key that is used to search for a value
     * @return value associated with a given key if exists.
     */
    Optional<V> get(K key);
}
