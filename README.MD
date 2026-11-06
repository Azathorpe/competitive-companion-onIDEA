# Competitive Problem Receiver

<p align="center">
  <b>A powerful JetBrains IDE plugin for competitive programmers.</b><br>
  Seamlessly fetch, parse, and manage programming contest problems directly within your IDE.
</p>

> 🔗 **Available on JetBrains Marketplace:** [Competitive Problem Receiver](https://plugins.jetbrains.com/plugin/35779-competitive-problem-receiver)

## 📖 About the Plugin

**Competitive Problem Receiver** acts as a bridge between your browser and your IDE. It listens for incoming problem data sent by the popular [Competitive Companion](https://github.com/jmerle/competitive-companion) browser extension, parses it, and saves it directly into your local project workspace.

Stop copy-pasting test cases manually. Focus on solving the problem, not setting up the environment.

## ✨ Features

* 🚀 **Seamless Integration**: Works directly with the *Competitive Companion* browser extension.
* 🌐 **Local & Secure**: Listens strictly on `localhost`. No data is sent to external servers.
* 📁 **Automatic Parsing**: Parses problem statements, input/output examples, and test cases.
* ⚙️ **Customizable**: Configurable settings to match your workflow (saved locally in your user directory).

## 🛠️ Getting Started

### 1. Install the Plugin
* Open your JetBrains IDE (IntelliJ IDEA, PyCharm, CLion, etc.).
* Go to `Settings/Preferences` -> `Plugins` -> `Marketplace`.
* Search for **Competitive Problem Receiver** and click **Install**.
* Restart your IDE.

### 2. Install the Browser Extension
* Download and install [Competitive Companion](https://github.com/jmerle/competitive-companion) for your browser (Chrome, Firefox, Edge).

### 3. How to Use
1. Open the **Competitive Problem Receiver** tool window on the right side of your IDE.
2. Click the **Start Listening** button (if it's not running automatically).
3. Go to a supported contest platform (e.g., Codeforces, AtCoder, Luogu) in your browser.
4. Click the green **Competitive Companion** icon in your browser.
5. The problem will be instantly parsed and saved to your project folder!

## 🔒 Privacy & Security

We take your privacy seriously.
* **No Telemetry**: The plugin does not collect any personal data or usage statistics.
* **Local Only**: The socket server binds strictly to `127.0.0.1` (localhost). It does not connect to the internet.
* **Local Storage**: Configuration files are stored safely in your local user directory.

For more details, please read our [Privacy Policy](https://github.com/Azathorpe/Competitive-Problem-Receiver/blob/master/PRIVACY.md).

## 🐛 Issues & Feedback

If you find any bugs, have feature requests, or want to contribute, please feel free to open an issue on the [GitHub Issues](https://github.com/Azathorpe/Competitive-Problem-Receiver/issues) page.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
*Made with ❤️ by Azathorpe*