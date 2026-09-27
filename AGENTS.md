# Repository Guidelines

## Project Structure & Module Organization

Knot contains the reusable `knot-core` module, the `knot-cli` generator, and the `examples/` rebuildable examples. CLI templates live under `knot-cli/src/main/resources/templates`; the current `android-ktor` template generates `contracts`, `shared`, `server`, and `androidApp`. `README.md` states project scope and status. The `docs/` directory contains the design boundary (`DESIGN.md`), development and evolution rules (`DEVELOPMENT.md`), and technology baseline (`STACK.md`). Put architectural decision records in `docs/adr/NNNN-title.md` and include context, decision, alternatives, costs, and verification.

## Build, Test, and Development Commands

Use the checked-in wrapper with JDK 21. `./gradlew check :knot-cli:test :examples:idempotency-demo:run` runs all Kotlin checks, CLI tests, and the reusable example. `./gradlew :knot-cli:installDist` builds the local `knot` executable; run `./knot-cli/build/install/knot/bin/knot init` to generate `knot-app`. The generated project uses `./gradlew check :androidApp:assembleDebug`. Use `rg` to search documentation and source files, and review changes with `git diff`.

## Coding Style & Naming Conventions

Application, server, and test code should be Kotlin; build configuration should use Gradle Kotlin DSL. Follow standard Kotlin formatting with four-space indentation, trailing commas where the formatter permits them, `PascalCase` types, `camelCase` members, and descriptive names for state and repository boundaries. Keep UI unidirectional: composables render state and send events to a ViewModel or state holder. Keep platform code behind interfaces or adapters. Run the repository’s configured formatter and linter once those tools are introduced.

## Testing Guidelines

Use `kotlin.test` for core and CLI behavior, Ktor’s test application support for generated server HTTP behavior, and Compose UI Test with AndroidX Test for Android UI behavior. Name tests after the behavior they verify. Keep tests near their module (`knot-core`, `knot-cli`, `commonTest`, server tests, or `androidTest`). Template changes must pass the CLI generator tests and the generated-project build. Add real PostgreSQL integration coverage for persistence boundaries; do not treat shared tests as a substitute for device UI tests.

## Commit & Pull Request Guidelines

Use short, imperative Conventional-style subjects such as `feat(cli): add android-ktor template`, and keep each commit focused. Pull requests should explain the change, link the relevant issue or ADR, describe validation commands and results, and include screenshots or recordings for UI changes. Update the affected documentation when behavior or support claims change.

## Security & Configuration

Never commit tokens, passwords, verification codes, or private message content. Provide `.env.example` with field names only. Logs must omit secrets and message bodies. Keep model keys server-side; treat notification delivery and external Agent services as failure-prone integrations.