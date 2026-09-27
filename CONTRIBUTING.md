# 贡献指南 / Contributing

## 工作方式 / Workflow

从 `main` 创建短期分支，通过 PR 合并。分支名使用 `feat/...`、`fix/...`、`docs/...` 或 `chore/...`。PR 说明必须写清目标、关联 issue/ADR、本地验证命令和结果；UI 或流程变化附截图或录屏。

Create a short-lived branch from `main` and merge through a pull request. Use `feat/...`, `fix/...`, `docs/...`, or `chore/...`. A PR must state its goal, linked issue/ADR, local validation commands and results; attach screenshots or a recording for UI or flow changes.

## 提交 / Commits

使用祈使句和聚焦的提交，例如 `docs: define package release policy`。跨仓库改动分别提交，并在 PR 中互相链接。

Use imperative, focused commits such as `docs: define package release policy`. Commit cross-repository changes separately and link the PRs.

## 密钥 / Secrets

不要提交 token、密码、验证码、私聊正文或签名文件。新增配置时同步更新 `.env.example` 和双语文档。

Never commit tokens, passwords, verification codes, private message content, or signing files. Update `.env.example` and the bilingual documentation when adding configuration.
