# Reuse Extraction / 能力提取

Knot receives only behavior that has been demonstrated in CS26 with tests, a documented boundary, and a second usage example. CS26 remains the owner of campus entities, product policy, deployment values, and provider credentials.

Knot 只接收已经在 CS26 中通过测试、具有明确边界并有第二个使用示例的能力。校园实体、产品策略、部署值和供应商凭证继续由 CS26 负责。

## Candidate layers / 候选层

| Candidate | Evidence required in CS26 | Extraction shape |
| --- | --- | --- |
| HTTP/WebSocket client conventions | contract tests and reconnect evidence | KMP networking module |
| Auth session boundary | expiry, revocation, and adapter tests | server interface and test fake |
| Media storage boundary | local adapter, object ownership, size/type tests | provider-neutral storage API |
| Message idempotency | duplicate client ID and history tests | server persistence utility |
| Notification sync | unread/read and offline replay evidence | notification repository contract |
| CI and evidence templates | successful PR, main, and package runs | repository templates and workflows |

## Promotion gate / 提取门槛

1. The CS26 behavior has a named requirement and a reproducible evidence record.
2. The boundary has no CS26 package, entity, or permission dependency.
3. A fake adapter and at least one integration test exist.
4. A small second example can be rebuilt from the proposed Knot API.
5. CS26 upgrades to a tagged Knot version in a separate PR and keeps its checks green.

1. CS26 行为有明确需求编号和可复现实验证据。
2. 边界不依赖 CS26 包名、实体或权限规则。
3. 存在 fake adapter 和至少一个集成测试。
4. 能用拟定的 Knot API 重建一个小型第二示例。
5. CS26 在独立 PR 中升级 Knot 标签版本，并保持检查通过。

## Current status / 当前状态

`knot-core` now contains an experimental `IdempotencyCache` with unit tests and a separate `idempotency-demo` rebuild. It is a candidate implementation, not a promoted release: CS26 has not yet upgraded to a tagged Knot version, and the cache still needs a CS26 integration PR plus cross-repository compatibility evidence.

`knot-core` 现在包含带单元测试的实验性 `IdempotencyCache`，并有独立的 `idempotency-demo` 可重建示例。这仍是候选实现，不是正式发布能力：CS26 尚未升级到 Knot 标签版本，还需要 CS26 集成 PR 和跨仓库兼容性证据。
