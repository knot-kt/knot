package com.knotkt.knot.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class IdempotencyCacheTest {
    @Test
    fun reusesResultForDuplicateKey() {
        val cache = IdempotencyCache<String, String>()
        var calls = 0

        val first = cache.getOrPut("client-1") { calls += 1; "message-1" }
        val duplicate = cache.getOrPut("client-1") { calls += 1; "message-2" }

        assertEquals("message-1", first)
        assertEquals(first, duplicate)
        assertEquals(1, calls)
    }

    @Test
    fun evictsLeastRecentlyUsedValueAtCapacity() {
        val cache = IdempotencyCache<String, Int>(capacity = 2)
        cache.getOrPut("a") { 1 }
        cache.getOrPut("b") { 2 }
        cache.getOrPut("a") { 10 }
        cache.getOrPut("c") { 3 }

        assertEquals(true, cache.contains("a"))
        assertEquals(false, cache.contains("b"))
        assertFailsWith<IllegalArgumentException> { IdempotencyCache<String, Int>(0) }
    }
}
