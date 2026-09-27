package __PROJECT_PACKAGE__.contracts

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.serialization.json.Json

class HealthContractTest {
    @Test
    fun healthResponseRoundTrips() {
        val encoded = Json.encodeToString(HealthResponse.serializer(), HealthResponse("ok"))
        assertEquals(HealthResponse("ok"), Json.decodeFromString(HealthResponse.serializer(), encoded))
    }
}