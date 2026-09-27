# Repository Guidelines

## Project Structure & Module Organization

Knot is a design-stage repository. `README.md` states project scope and status. The `docs/` directory contains the design boundary (`DESIGN.md`), development and evolution rules (`DEVELOPMENT.md`), and technology baseline (`STACK.md`). Future reusable code should be organized into Kotlin Multiplatform modules, with shared business code and tests separated from platform implementations. Put architectural decision records in `docs/adr/NNNN-title.md` and include context, decision, alternatives, costs, and verification.

## Build, Test, and Development Commands

There is no runnable application, Gradle project, or checked-in build command yet. Do not document a command as supported until it has been run successfully. Once Gradle modules are added, use the checked-in wrapper, for example `./gradlew test` for JVM/shared tests and the module-specific Android or server task documented with the implementation. Use `rg` to search documentation and source files, and review changes with `git diff`.

## Coding Style & Naming Conventions

Application, server, and test code should be Kotlin; build configuration should use Gradle Kotlin DSL. Follow standard Kotlin formatting with four-space indentation, trailing commas where the formatter permits them, `PascalCase` types, `camelCase` members, and descriptive names for state and repository boundaries. Keep UI unidirectional: composables render state and send events to a ViewModel or state holder. Keep platform code behind interfaces or adapters. Run the repository’s configured formatter and linter once those tools are introduced.

## Testing Guidelines

Use `kotlin.test` and Kotest for shared and server logic, Ktor’s test application support for HTTP behavior, and Compose UI Test with AndroidX Test for Android UI behavior. Name tests after the behavior they verify. Keep tests near their module (`commonTest`, server tests, or `androidTest`). Add real PostgreSQL integration coverage for persistence boundaries; do not treat shared tests as a substitute for device UI tests.

## Commit & Pull Request Guidelines

No Git history exists yet, so no project-specific commit convention has been established. Use short, imperative subjects such as `Add notification contract`, and keep each commit focused. Pull requests should explain the change, link the relevant issue or ADR, describe validation commands and results, and include screenshots or recordings for UI changes. Update the affected documentation when behavior or support claims change.

## Security & Configuration

Never commit tokens, passwords, verification codes, or private message content. Provide `.env.example` with field names only. Logs must omit secrets and message bodies. Keep model keys server-side; treat notification delivery and external Agent services as failure-prone integrations.
