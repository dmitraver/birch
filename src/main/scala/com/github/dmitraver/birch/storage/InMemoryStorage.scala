package com.github.dmitraver.birch.storage

class InMemoryStorage[Key, Value] extends Storage[Key, Value] {

	private val map = scala.collection.mutable.HashMap[Key, Value]()

	override def put(kv: (Key, Value)): Unit = map += kv

	override def contains(k: Key): Boolean = map contains k

	override def get(k: Key): Option[Value] = map get k

	override def remove(k: Key): Boolean = {
		val presented = map contains k
		if (presented) map -= k
		presented
	}

	override def size: Int = map.size
}
