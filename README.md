<p align="center">
  <img src="extras/logo/gpm_original_logo.png" width="300">
</p>

# gpm 📦

**gpm** is a package manager for Gradle projects. It is inspired from the famous **npm** package manager.
 **gpm** is not a replacement for the Gradle build system, but a helper to install the dependencies seamlessly to reduce latency.

 It consists of a command line client, also called **gpm**, and an online database of package directory, called the **gpm registry**.

**gpm** uses similar syntax as **npm**.


### Features

| Command                      | Description                                                  | Implemented | Status  | Milestone      |
|------------------------------|--------------------------------------------------------------|-------------|---------|----------------|
| `install`                    | To install the dependency as `implementation`                | ⏳           | Implementing | v1.0.0-alpha01 |
| `install --save-dev`         | To install the dependency as `testImplementation`            | ❌           | Pending | v1.0.0-alpha01 |
| `install --save-dev-android` | To install the dependency as `androidTestImplementation`     | ❌           | Pending | v1.0.0-alpha01 |
| `uninstall`                  | To uninstall the dependency from `implementation`            | ❌           | Pending | v1.0.0-alpha01 |
| `uninstall dev`              | To uninstall the dependency from `testImplementation`        | ❌           | Pending | v1.0.0-alpha01 |
| `uninstall dev-android`      | To uninstall the dependency from `androidTestImplementation` | ❌           | Pending | v1.0.0-alpha01 |
| `docs`                       | To open the documentation in default browser                 | ❌           | Pending | v1.0.0-alpha01 |
| `update`                     | To update the dependency version to latest                   | ❌           | Pending | -              |
| `list`                       | To list all the dependencies                                 | ❌           | Pending | -              |

### How can I add my repo to the registry?

Create an issue with below given JSON model as the comment body. 

```json
{
  "name": "YOUR REPO NAME", // Required : Depenedency Name
  "github": "GITHUB REPO", // Optional: In format, user/repo 
  "docs": "DOCUMENTATION-URL", // Optional : Can be full URL or file name. For eg. "README.md",
  "dependency_signature": "DEPENDENCY SIGNATURE", // Required : Dependency signature without version name
  "latest_version": "4.6.0", // Required: Latest version name
  "default_type": "implementation" // Required: Possible values are implementation, testImplementation, androidTestImplementation
}
```

**Example**

```json
{
  "name": "OkHttp",
  "github": "square/okhttp",
  "docs": "https://square.github.io/okhttp/",
  "dependency_signature": "com.squareup.okhttp3:okhttp",
  "latest_version": "4.6.0",
  "default_type": "implementation"
}
```

### Project Status 👷

This project is under active development. Tap the `👁️ Watch` button to get updates. 


## Author ✍️
- theapache64