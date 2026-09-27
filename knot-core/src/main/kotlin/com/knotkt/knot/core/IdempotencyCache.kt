package com.knotkt.knot.core

/**
 * Bounded, thread-safe cache for replaying a result for a previously seen client key.
 * The producer is called at most once while a key remains in the cache.
 */
class IdempotencyCache<K, V>(
    private val capacity: Int = 1_024,
) {
    private val values = LinkedHashMap<K, V>(capacity, 0.75f, true)

    init {
        require(capacity > 0) { "capacity must be positive" }
    }

    @Synchronized
    fun getOrPut(key: K, producer: () -> V): V {
        values[key]?.let { return it }
        val value = producer()
        values[key] = value
        while (values.size > capacity) values.remove(values.entries.first().key)
        return value
    }

    @Synchronized
    fun contains(key: K): Boolean = key in values

    @Synchronized
    fun clear() = values.clear()
}
