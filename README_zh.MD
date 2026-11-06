# Competitive Problem Receiver (竞赛题目接收器)

<p align="center">
  <b>一款为算法竞赛选手打造的 JetBrains IDE 插件。</b><br>
  在 IDE 中无缝获取、解析和管理算法竞赛题目。
</p>

> 🔗 **JetBrains 插件市场地址:** [Competitive Problem Receiver](https://plugins.jetbrains.com/plugin/35779-competitive-problem-receiver)

<p align="center">
  <a href="README.md"><img src="https://img.shields.io/badge/Language-English-blue.svg" alt="English"></a>
  <a href="README_zh.md"><img src="https://img.shields.io/badge/语言-简体中文-red.svg" alt="简体中文"></a>
</p>

## 📖 插件简介

**Competitive Problem Receiver** 是连接你的浏览器和 IDE 的桥梁。它可以监听由流行的 [Competitive Companion](https://github.com/jmerle/competitive-companion) 浏览器插件发送的题目数据，并将其自动解析并保存到你本地的项目工作区中。

告别手动复制粘贴测试用例的繁琐步骤，把精力集中在解题本身。

## ✨ 功能特性

* 🚀 **无缝集成**: 与 *Competitive Companion* 浏览器插件完美配合。
* 🌐 **本地且安全**: 严格仅在本地 (`localhost`) 监听，绝不会向任何外部服务器发送数据。
* 📁 **自动解析**: 自动解析题目描述、输入/输出样例以及测试数据。
* ⚙️ **高度可定制**: 提供设置选项以匹配你的工作流（配置安全地保存在本地用户目录中）。

## 🛠️ 快速上手

### 1. 安装插件
* 打开你的 JetBrains IDE（IntelliJ IDEA, PyCharm, CLion 等）。
* 进入 `设置/偏好设置` -> `插件` -> `Marketplace`。
* 搜索 **Competitive Problem Receiver** 并点击 **安装**。
* 重启 IDE。

### 2. 安装浏览器扩展
* 下载并安装适用于你浏览器的 [Competitive Companion](https://github.com/jmerle/competitive-companion) 扩展（支持 Chrome, Firefox, Edge）。

### 3. 如何使用
1. 在 IDE 的右侧边栏打开 **Competitive Problem Receiver** 工具窗口。
2. 点击 **Start Listening**（开始监听）按钮（如果它没有自动运行）。
3. 在浏览器中打开支持的比赛平台（如 Codeforces、AtCoder、洛谷等）。
4. 点击浏览器右上角的绿色 **Competitive Companion** 图标。
5. 题目将被瞬间解析并保存到你的项目文件夹中！

## 🔒 隐私与安全

我们非常重视您的隐私安全。
* **无遥测**: 本插件不会收集任何个人数据或使用统计信息。
* **仅限本地**: Socket 服务严格绑定在 `127.0.0.1` (localhost)，不连接互联网。
* **本地存储**: 配置文件安全地存储在您的本地用户目录中。

更多详情，请阅读我们的 [隐私政策](https://github.com/Azathorpe/Competitive-Problem-Receiver/blob/master/PRIVACY.md)。

## 🐛 问题与反馈

如果你发现了 Bug、有功能建议或想要贡献代码，欢迎在 [GitHub Issues](https://github.com/Azathorpe/Competitive-Problem-Receiver/issues) 页面提交。

## 📄 开源协议

本项目基于 MIT 协议开源 - 详情请参阅 [LICENSE](LICENSE) 文件。

---
*作者: Azathorpe♥️*