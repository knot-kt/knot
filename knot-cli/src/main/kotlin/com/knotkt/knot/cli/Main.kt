package com.knotkt.knot.cli

import java.nio.file.Path
import kotlin.io.path.absolute

fun main(args: Array<String>) {
    try {
        val options = InitOptions.parse(args.toList())
        val generated = TemplateGenerator().generate(options)
        println("Generated ${options.template} project '${options.projectName}' at ${generated.absolute()}.")
        println("Next: cd ${generated.absolute()} && ./gradlew check :androidApp:assembleDebug")
    } catch (error: CliException) {
        System.err.println("knot: ${error.message}")
        System.err.println(InitOptions.usage)
        kotlin.system.exitProcess(2)
    }
}

data class InitOptions(
    val projectName: String,
    val destination: Path,
    val template: String,
) {
    companion object {
        const val usage = "Usage: knot init [project-name] [--template android-ktor] [--dir <parent>]"

        fun parse(args: List<String>): InitOptions {
            if (args.firstOrNull() != "init") throw CliException(usage)
            val hasExplicitName = args.getOrNull(1)?.let { !it.startsWith("--") } == true
            val projectName = if (hasExplicitName) args[1] else "knot-app"
            if (!projectName.matches(Regex("[A-Za-z][A-Za-z0-9-]{0,63}"))) {
                throw CliException("project name must start with a letter and contain only letters, digits, or hyphens")
            }

            var template = "android-ktor"
            var parent = Path.of(".")
            var index = if (hasExplicitName) 2 else 1
            while (index < args.size) {
                when (args[index]) {
                    "--template" -> {
                        template = args.getOrNull(++index) ?: throw CliException("--template requires a value")
                    }
                    "--dir" -> {
                        parent = Path.of(args.getOrNull(++index) ?: throw CliException("--dir requires a value"))
                    }
                    else -> throw CliException("unknown option: ${args[index]}")
                }
                index += 1
            }
            if (template != "android-ktor") throw CliException("unsupported template: $template")
            return InitOptions(projectName, parent.resolve(projectName), template)
        }
    }
}

class CliException(message: String) : IllegalArgumentException(message)