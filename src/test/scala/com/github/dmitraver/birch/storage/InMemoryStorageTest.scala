package com.github.dmitraver.birch.storage

import org.scalatest.{Matchers, FunSuite}

class InMemoryStorageTest extends FunSuite with Matchers {

	test("Empty storage should have size 1 after adding element to it") {
		val storage = new InMemoryStorage[Int, String]
		storage put (1, "aaa")
		storage.size shouldBe 1
	}

	test("1 element storage should have size 0 after removing an element from it") {
		val storage = new InMemoryStorage[Int, String]
		storage put (1, "aaa")
		storage remove 1
		storage.size shouldBe 0
	}

	test("Storage should return element which was added before") {
		val storage = new InMemoryStorage[Int, String]
		storage put (1, "aaa")
		storage get 1 shouldBe Some("aaa")
	}

	test("Storage should contain an element that was added before") {
		val storage = new InMemoryStorage[Int, String]
		storage put (1, "aaa")
		storage contains 1 shouldBe true
	}

	test("Storage shouldn't have an element after it was removed") {
		val storage = new InMemoryStorage[Int, String]
		storage put (1, "aaa")
		storage remove 1
		storage get 1 shouldBe None
	}


}
