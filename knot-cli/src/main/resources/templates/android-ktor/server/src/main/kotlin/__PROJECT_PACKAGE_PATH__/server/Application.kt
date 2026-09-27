package __PROJECT_PACKAGE__.server

import __PROJECT_PACKAGE__.contracts.HealthResponse
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.module() {
    install(ContentNegotiation) { json() }
    routing {
        get("/health") { call.respond(HealthResponse("ok")) }
    }
}

fun main() = embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)