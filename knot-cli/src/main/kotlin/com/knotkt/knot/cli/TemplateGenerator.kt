package com.knotkt.knot.cli

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.readText
import kotlin.io.path.writeText

class TemplateGenerator(
    private val classLoader: ClassLoader = TemplateGenerator::class.java.classLoader,
) {
    fun generate(options: InitOptions): Path {
        val destination = options.destination
        if (destination.exists()) {
            if (!destination.isDirectory() || Files.list(destination).use { it.findAny().isPresent }) {
                throw CliException("destination already exists and is not empty: $destination")
            }
        } else {
            destination.createDirectories()
        }

        val projectName = options.projectName
        val packageName = "com.knotkt.${projectName
            .lowercase()
            .replace(Regex("[^a-z0-9]+"), "")
            .ifBlank { "app" }}"
        val packagePath = packageName.replace('.', '/')
        val tokens = mapOf(
            "__PROJECT_NAME__" to projectName,
            "__PROJECT_PACKAGE__" to packageName,
            "__PROJECT_PACKAGE_PATH__" to packagePath,
        )
        val manifest = resource("templates/${options.template}/template-files.txt")
            .lineSequence()
            .map(String::trim)
            .filter { it.isNotEmpty() && !it.startsWith("#") }
            .toList()

        manifest.forEach { templatePath ->
            val renderedPath = render(templatePath, tokens)
            val outputPath = if (renderedPath == "gitignore") ".gitignore" else renderedPath
            val target = destination.resolve(outputPath)
            target.parent?.createDirectories()
            val bytes = classLoader.getResourceAsStream("templates/${options.template}/$templatePath")
                ?.use { it.readBytes() }
                ?: throw CliException("template resource is missing: $templatePath")
            if (templatePath.endsWith(".jar")) {
                target.toFile().writeBytes(bytes)
            } else {
                target.writeText(render(bytes.toString(Charsets.UTF_8), tokens))
            }
            if (target.fileName.toString() == "gradlew") target.toFile().setExecutable(true)
        }
        return destination
    }

    private fun resource(path: String): String = classLoader.getResourceAsStream(path)
        ?.use { it.readBytes().toString(Charsets.UTF_8) }
        ?: throw CliException("template resource is missing: $path")

    private fun render(value: String, tokens: Map<String, String>): String =
        tokens.entries.fold(value) { rendered, (token, replacement) -> rendered.replace(token, replacement) }
}