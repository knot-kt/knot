# Development / 开发说明

Use JDK 21 and the committed Gradle Wrapper. Keep shared contracts and state in `contracts` and `shared`; keep Ktor routes and provider adapters in `server`; keep Android permissions and platform code in `androidApp`.

使用 JDK 21 和提交的 Gradle Wrapper。契约和共享状态放在 `contracts`、`shared`；Ktor 路由和供应商适配放在 `server`；Android 权限和平台代码放在 `androidApp`。

Run `./gradlew check :androidApp:assembleDebug` before opening a pull request. Secrets belong in local environment configuration and must never be committed.

提交 PR 前运行 `./gradlew check :androidApp:assembleDebug`。密钥只放在本地环境配置中，不能提交到仓库。