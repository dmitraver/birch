package com.github.dmitraver.birch.storage

trait Storage[Key, Value] {

	/**
	 * Adds a key/value pair to the storage. If the mapping with such key
	 * already exists in the storage it will be overridden by the new value.
	 * @param kv key/value pair
	 */
	def put(kv: (Key, Value)): Unit

	/**
	 * Checks whether the storage contains a mapping with such key.
	 * @param k key
	 * @return true if the storage has a mapping with such key, false otherwise.
	 */
	def contains(k: Key): Boolean

	/**
	 * Removes a mapping with a provided key from the storage.
	 * @param k key
	 * @return true if the mapping was removed from the storage, false otherwise.
	 */
	def remove(k: Key): Boolean

	/**
	 * Optionally returns a value with a provided key.
	 * @param k key
	 * @return optional value associated with a key if it exists, None otherwise.
	 */
	def get(k: Key): Option[Value]

	/**
	 * Returns size of the storage.
	 * @return number of key/value pairs in the storage.
	 */
	def size: Int
}
