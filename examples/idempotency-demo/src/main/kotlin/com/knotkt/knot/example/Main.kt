package com.knotkt.knot.example

import com.knotkt.knot.core.IdempotencyCache

fun main() {
    val cache = IdempotencyCache<String, String>()
    val first = cache.getOrPut("client-1") { "stored-result" }
    val duplicate = cache.getOrPut("client-1") { "should-not-run" }
    check(first == duplicate)
    println("duplicate key replayed: $duplicate")
}
