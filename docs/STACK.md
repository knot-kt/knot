# Knot 技术选型基线

本文档是 Knot 和 CS26 的技术基线。除非记录新的 ADR 并说明迁移成本，否则不替换下列核心方案。

## 总体选型

| 领域 | 选型 | 责任 |
| --- | --- | --- |
| 客户端 UI | Compose Multiplatform | Android、iOS、Desktop 的共享 UI；平台特有控件通过适配层接入 |
| 客户端网络 | Ktor Client | REST、JSON、认证、文件上传和 WebSocket 客户端 |
| 服务端 | Ktor Server | REST API、WebSocket、认证、业务服务和通知编排 |
| 服务端数据库 | PostgreSQL | 用户、动态、消息、通知和权限等权威数据 |
| 客户端数据库 | SQLite + SQLDelight | KMP 共享的缓存、草稿、离线消息和待发送队列 |
| 序列化 | kotlinx.serialization | API DTO、WebSocket 事件和本地数据编码 |
| 并发 | Kotlin Coroutines / Flow | 请求、状态流、重连、任务取消和生命周期管理 |
| 业务测试 | kotlin.test + Kotest | `commonTest`、服务端单元测试、属性测试和测试数据 |
| Android UI 测试 | Compose UI Test + AndroidX Test | 真机或模拟器上的页面行为、语义节点和交互测试 |
| 性能分析 | Android Studio Profiler + Perfetto | 启动、主线程、分配、网络和时序分析 |
| 内存分析 | Heap Dump + Profiler + StrictMode | 堆引用链、资源使用和线程违规分析；LeakCanary 不作为必选方案 |
| 在线通知 | Ktor + PostgreSQL + WebSocket | 通知持久化、在线下发、未读状态和补拉 |
| 后台推送 | 自托管 ntfy | Ktor 通过 HTTP 发布，客户端通过 ntfy / UnifiedPush 接收 |
| Android 通知展示 | Android Notification API | 创建通知渠道、申请通知权限、展示消息和跳转 |
| AI Agent | Koog | Ktor 服务端的可选话题推荐、校园问答或内容辅助 |
| 文件存储 | 阿里云 OSS | 图片和语音对象；Ktor 签发 STS 或预签名上传权限 |

## 通知链路

```text
业务事件 → Ktor Notification Service
              ├── PostgreSQL：权威通知记录、已读状态
              ├── WebSocket：在线客户端实时事件
              └── ntfy HTTP：后台推送网关
                                      ↓
                           ntfy / UnifiedPush 接收适配
                                      ↓
                              Android Notification API
```

ntfy 是外部基础设施，不是 Kotlin 服务端代码。通知内容、权限、未读状态和跳转目标仍由 Ktor 与 CS26 管理。Android 端必须验证自托管 ntfy 的认证、锁屏、后台、Doze、进程回收和强制停止行为，不能把服务端发布成功等同于系统通知送达。

## 测试分层

```text
commonTest  → kotlin.test + Kotest
server      → Kotest + Ktor testApplication
androidTest → Compose UI Test + AndroidX Test
性能        → Macrobenchmark / Profiler / Perfetto
内存        → Heap Dump / Profiler / StrictMode
```

Kotest 是 Kotlin-first 的主要测试框架。AndroidX Test 保留为 Android UI 测试运行基础设施；测试源码仍然使用 Kotlin。不能把 commonTest 的 Kotest 测试当作 Android 设备 UI 测试的替代。

## Agent 边界

Koog 只运行在 Ktor 服务端。客户端通过受鉴权的业务接口调用 Agent，不能携带模型密钥。Agent 功能失败、超时或供应商不可用时，登录、动态、聊天和通知必须继续工作。

## Kotlin 边界

项目不编写 Java 业务源码。Kotlin/JVM、Android SDK、AndroidX Test、PostgreSQL 驱动和部分平台 SDK 的底层实现仍可能包含 Java 或 Objective-C，这是运行平台依赖，不改变项目的 Kotlin-first 代码约定。
