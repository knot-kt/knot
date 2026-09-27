# Knot Init / 脚手架初始化

## Product contract / 产品契约

Knot 的长期交付形态是一个可复现的初始化命令：

```text
knot init campus-app --template android-ktor
cd campus-app
./gradlew check :androidApp:assembleDebug
```

该命令生成一个能立即编译的最小 Kotlin 全栈应用，而不是生成空目录或只展示示例代码。生成结果至少包含 `contracts`、`shared`、`server`、`androidApp`、Gradle Wrapper 和双语开发说明。

Knot's long-term delivery is a reproducible initialization command. It creates a minimal Kotlin full-stack application that builds immediately, with `contracts`, `shared`, `server`, `androidApp`, the Gradle Wrapper, and bilingual development notes.

## Generated baseline / 生成基线

The first template uses the verified CS26 boundaries without campus entities:

- Compose startup screen and a health check through Ktor Client.
- Ktor Server with `/health`, JSON serialization, and a fake local adapter.
- Shared state, Repository, and contract examples with one JVM test.
- No real provider credentials, production database, push service, or Koog model key.

首个模板只复制已经验证的工程边界，不复制校园业务实体。生成项目必须能在本机使用 JDK 21 和 Wrapper 完成检查；外部供应商和密钥通过配置模板保留为空。

## Delivery stages / 交付阶段

1. **Template proof**: store the template in Knot and build it in a dedicated CI job.
2. **Local init**: implement `knot init` with deterministic name substitution and a clean destination check.
3. **Example rebuild**: create a second small example from the template and keep CS26 independent.
4. **Versioned release**: publish a `v0.x.y` tag only after the template and generated example pass checks.

当前仓库已经有可构建的核心模块和第二个示例，但 `knot init` CLI 仍未实现。README 不声称该命令已可用。CLI 实现完成前，可以使用临时的 Gradle composite build 验证提取边界，但不能把 composite build 当作脚手架发布。

## Acceptance / 验收

Every template change must record the generated tree, the exact command, and build output. CI runs template verification only on relevant Knot pull requests, while generated projects use the same pinned toolchain. A failed optional provider must not prevent the generated baseline from starting.

每次模板变更都记录生成目录、完整命令和构建结果。只有相关 Knot PR 执行模板检查；生成项目使用同一套固定工具链。可选供应商失败不能阻塞基础项目启动。
