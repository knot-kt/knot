# 双仓库协同与持续集成 / Cross-Repository Coordination and CI

## 目标 / Purpose

Knot 是可复用的 Kotlin 工程能力与参考实现；CS26 是校园社交应用和毕业设计交付物。先在 CS26 的真实功能中验证，再把职责稳定、可独立测试的部分提取到 Knot。

Knot contains reusable Kotlin engineering capabilities and reference implementations. CS26 owns the campus social product and graduation deliverables. Validate behavior in CS26 first, then extract only stable, independently testable responsibilities into Knot.

## 依赖关系 / Dependency Policy

- CS26 不依赖 Knot 的浮动分支。每次使用都记录 Knot 的版本和提交号。
- 本地联调可以临时使用 Gradle composite build：`includeBuild("../knot")`。
- Knot 发布 `v0.x.y` 后，CS26 通过独立 PR 升级依赖并运行兼容性验证。
- 不把校园业务实体、权限规则或外部服务凭证提取到 Knot。

- CS26 must not depend on a floating Knot branch. Record the Knot version and commit for every integration.
- Local development may temporarily use a Gradle composite build with `includeBuild("../knot")`.
- After Knot publishes `v0.x.y`, update CS26 in a separate PR with compatibility validation.
- Do not extract campus domain entities, permission rules, or external-service credentials into Knot.

## 分支与发布 / Branches and Releases

使用 `feat/area-short-name`、`fix/area-short-name`、`docs/topic` 和 `chore/topic`。`main` 只通过 PR 更新；推荐 squash merge。提交主题使用祈使句，例如 `feat(cs26): add health endpoint`。

Use `feat/area-short-name`, `fix/area-short-name`, `docs/topic`, and `chore/topic`. Update `main` through pull requests only and prefer squash merges. Use imperative commit subjects such as `feat(cs26): add health endpoint`.

Knot 的 `v0.x.y` 标签代表可复现的包版本；CS26 的应用版本单独管理。跨仓库变更必须互相链接 PR、ADR 或 issue。

Knot `v0.x.y` tags identify reproducible package versions; CS26 manages application versions separately. Cross-repository changes must link the related PRs, ADRs, or issues.

## Actions 预算 / Actions Budget

两个仓库都使用 Ubuntu 和单一 JDK 版本。PR 运行快速共享/服务端检查；`main` 运行完整 JVM 检查和 Android debug assemble；模拟器 UI 测试只在手动、每周回归或发布前运行。每个 workflow 设置 `timeout-minutes` 和按 PR 分组的 `concurrency`，新提交取消旧运行。

Both repositories use Ubuntu and one JDK version. PRs run fast shared/server checks; `main` runs the full JVM checks and Android debug assemble; emulator UI tests run only manually, weekly, or before a release. Set `timeout-minutes` and PR-scoped `concurrency` on every workflow so newer commits cancel obsolete runs.

把 2000 分钟视为硬上限，目标使用 1000-1200 分钟，保留重跑空间。避免多平台矩阵、macOS runner、每个 commit 的重复 push/PR 构建和无必要的跨仓库 dispatch。Gradle 使用官方 setup-gradle 缓存。

Treat 2000 minutes as a hard limit and target 1000-1200 minutes to leave room for retries. Avoid platform matrices, macOS runners, duplicate push/PR builds, and unnecessary cross-repository dispatches. Use the official Gradle setup action for caching.

## 密钥边界 / Secret Boundaries

当前 CI 不需要新增 key；GitHub Actions 的 `GITHUB_TOKEN` 足够完成 checkout、测试和普通 artifact 操作。暂不启用跨仓库自动 dispatch 或生产发布。

The current CI needs no new key; the built-in `GITHUB_TOKEN` is sufficient for checkout, tests, and ordinary artifact operations. Do not enable cross-repository dispatch or production deployment yet.

进入对应功能后再配置最小范围的 secrets：GitHub Packages、短信、OSS、ntfy、数据库、Android signing。开发环境使用 `.env.example` 字段说明和 fake/local adapter；任何 token、密码、验证码或签名文件不得入库。

Add narrowly scoped secrets only when the corresponding feature is implemented: GitHub Packages, SMS, OSS, ntfy, databases, and Android signing. Development uses `.env.example` field descriptions and fake/local adapters; never commit tokens, passwords, verification codes, or signing files.

## 决策记录 / Decision Records

重要版本、依赖发布、CI 预算变化和跨仓库 API 变化记录在 `docs/adr/NNNN-title.md`，中英文同时维护。没有真实运行证据时，不在 README 中宣称支持或稳定。

Record important version choices, dependency releases, CI budget changes, and cross-repository API changes in `docs/adr/NNNN-title.md` in both languages. Do not claim support or stability in README files without runtime evidence.
