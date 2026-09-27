package com.knotkt.knot.cli

import java.nio.file.Files
import kotlin.io.path.readText
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TemplateGeneratorTest {
    @Test
    fun parsesInitOptionsAndNormalizesProjectPackage() {
        val options = InitOptions.parse(listOf("init", "Knot-App", "--template", "android-ktor"))

        assertEquals("Knot-App", options.projectName)
        assertTrue(options.destination.endsWith("Knot-App"))
        assertEquals("android-ktor", options.template)
    }

    @Test
    fun defaultsToKnotAppWhenNoProjectNameIsProvided() {
        val options = InitOptions.parse(listOf("init"))

        assertEquals("knot-app", options.projectName)
        assertTrue(options.destination.endsWith("knot-app"))
    }

    @Test
    fun rejectsUnsafeOrUnsupportedArguments() {
        assertFailsWith<CliException> { InitOptions.parse(listOf("init", "../escape")) }
        assertFailsWith<CliException> { InitOptions.parse(listOf("init", "demo", "--template", "unknown")) }
    }

    @Test
    fun generatesBuildableTemplateTreeWithSubstitutions() {
        val parent = Files.createTempDirectory("knot-init-test")
        val destination = TemplateGenerator().generate(
            InitOptions.parse(listOf("init", "knot-demo", "--dir", parent.toString())),
        )

        assertTrue(destination.resolve("contracts").toFile().isDirectory)
        assertTrue(destination.resolve("shared").toFile().isDirectory)
        assertTrue(destination.resolve("server").toFile().isDirectory)
        assertTrue(destination.resolve("androidApp").toFile().isDirectory)
        assertTrue(destination.resolve("gradlew").toFile().canExecute())
        assertTrue(destination.resolve("settings.gradle.kts").readText().contains("rootProject.name = \"knot-demo\""))
        assertTrue(destination.resolve("androidApp/src/main/kotlin/com/knotkt/knotdemo/android/MainActivity.kt").toFile().exists())
        assertFailsWith<CliException> {
            TemplateGenerator().generate(InitOptions.parse(listOf("init", "knot-demo", "--dir", parent.toString())))
        }
    }
}