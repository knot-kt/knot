# Knot

面向社交与实时业务的 Kotlin-first 脚手架和参考架构。

组织：[knot-kt](https://github.com/knot-kt)。首个完整应用：[CS26](https://github.com/knot-kt/cs26)。

## 当前定位

Knot 当前处于早期实验阶段，没有已发布的框架、稳定 API 或开箱即用的 `init` 命令。仓库已经包含第一批可构建的纯 Kotlin 核心代码，目标是把真实应用中验证过的工程结构、基础组件和开发方法沉淀为可复用脚手架。

CS26 是首个验证场景，也是毕业设计的实际交付物。先让 CS26 完成登录、动态、聊天、校园通知，再逐步提取可复用能力。最佳实践是需要通过测试、运行记录和文档证明的目标，不是当前已获得的结论。

## 技术方向

- Kotlin Multiplatform 共享业务逻辑，Compose Multiplatform 共享可复用 UI。
- Ktor Client / Server 提供 HTTP 和 WebSocket 通信。
- PostgreSQL 保存服务端权威数据，SQLite 保存客户端缓存和待发送记录。
- 业务通知由 Ktor 通知中心管理，PostgreSQL 持久化，WebSocket 实时下发。
- 后台推送采用自托管 ntfy 作为推送网关；Ktor 负责调用 ntfy，客户端负责对应接收适配。
- Koog 运行在 Ktor 服务端，作为独立 Agent 模块，不成为核心业务的运行前提。
- `kotlin.test + Kotest` 测试共享业务和服务端，Compose UI Test + AndroidX Test 验证 Android UI。
- Android 内存和性能使用 Android Studio Profiler、Heap Dump、Perfetto、StrictMode；不把 LeakCanary 作为必选依赖。
- 所有手写业务、服务端和测试代码使用 Kotlin；平台 SDK 的底层实现不作为项目源码语言约束。

完整选型见 [技术选型](docs/STACK.md)。版本与数据库访问库在最小原型完成后锁定，不同时维护多套实现。

## 文档

- [设计边界](docs/DESIGN.md)
- [演进与开发约定](docs/DEVELOPMENT.md)
- [双仓库协同与 CI / Cross-Repository Coordination and CI](docs/COORDINATION.md)
- [能力提取 / Reuse Extraction](docs/EXTRACTION.md)
- [脚手架初始化 / Init Scaffold](docs/INIT.md)
- [贡献指南 / Contributing](CONTRIBUTING.md)

## 项目关系

Knot 提供工程约定和通用能力；CS26 定义校园场景、产品交互、业务权限和部署配置。CS26 可以使用 Kotlin 编写的平台代码，也可以调用已有平台 SDK；Kotlin-first 不意味着消除 JVM、Android SDK 或 Xcode。

## 本地运行

使用 JDK 21 和提交的 Gradle Wrapper 构建核心模块与第二个示例：

```bash
./gradlew check :examples:idempotency-demo:run
```

当前命令验证 `knot-core` 的幂等缓存和 `idempotency-demo` 示例；`knot init` 仍处于后续阶段。

## 开源发布

许可证尚未确定。正式发布脚手架或库之前，应添加 LICENSE、贡献说明和第三方依赖许可证清单。
