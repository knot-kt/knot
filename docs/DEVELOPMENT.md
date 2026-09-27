# Knot 演进与开发约定

## 实施顺序

1. CS26 完成 Android、Ktor、PostgreSQL 的最小纵向流程。
2. 在真实功能中验证网络、缓存、状态和平台接口。
3. 提取已有复用需求、职责稳定且可以独立测试的能力。
4. 让 CS26 消费提取结果，验证不会破坏产品功能。
5. 提供脚手架初始化说明、一个小型示例和运行记录。

毕设交付优先于基座扩张；提取不应成为功能开发的前置阻塞。

## 两个仓库如何联动

初期 CS26 可保留其内部实现，Knot 记录约定和最小样例。开始提取代码后，开发阶段可以使用 Gradle composite build 连接两个本地仓库；需要跨仓库或 CI 使用时发布有明确版本的包。

CS26 不依赖 Knot 浮动分支。记录使用的版本或提交，保证答辩环境可重建。脚手架复制的源文件必须说明后续由应用自行维护；发布库则要定义 API 和升级策略。

## 编码与配置

- 应用和测试源码使用 Kotlin，构建配置使用 Gradle Kotlin DSL。
- 使用 Gradle Wrapper、版本目录和兼容的工具链版本；稳定版本优先。
- 密钥不入库，提供仅含字段说明的 `.env.example`。
- 共用错误协议包含稳定错误码、用户可理解的信息及请求标识。
- 取消协程时传播取消异常；资源用明确关闭流程释放。
- 日志不记录验证码、Token、密钥和私聊正文。
- 平台代码通过接口与公共业务隔离，不强制所有接口都使用 expect/actual。

## 测试与质量

| 层次 | 建议工具 |
| --- | --- |
| 共享业务 | kotlin.test、kotlinx-coroutines-test；Kotest 断言与属性测试 |
| JVM 服务端 | Kotest、Ktor testApplication、真实 PostgreSQL 集成测试 |
| Android UI | AndroidX Test、Compose Testing APIs |
| 启动和交互性能 | Macrobenchmark、Profiler、Perfetto |
| Android 内存 | Memory Profiler、Heap Dump、Perfetto、StrictMode |

Kotest 的完整跨平台运行方式需按选定版本验证，先用 commonTest 小样例跑通各目标。Android UI 测试保留官方运行基础设施，测试源码仍是 Kotlin。

StrictMode 用于检查主线程违规及部分资源使用问题，Perfetto 用于追踪性能；Profiler 和 Heap Dump 用于分析分配、堆引用链和内存趋势。它们需要结合可复现实验，不宣称自动检测全部内存泄漏。LeakCanary 不作为 CS26 的基线依赖。

## 发布条件

首次可复用发布需要：实际运行命令、测试报告、支持矩阵、示例、许可证、配置说明。没有真实兼容性证据时不承诺稳定 API。毕业设计完成后，再通过第二个业务示例验证抽象是否适用。

## 官方参考

- [Android 架构](https://developer.android.com/topic/architecture)
- [Compose 测试](https://developer.android.com/develop/ui/compose/testing)
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform/kmp-overview.html)
- [Kotest](https://kotest.io/docs/)
- [Ktor](https://ktor.io/docs/)
