plugins {
    kotlin("jvm")
    application
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "com.knotkt.knot.example.MainKt"
}

dependencies {
    implementation(project(":knot-core"))
}
