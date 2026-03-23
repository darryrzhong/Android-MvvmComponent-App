# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目简介

这是一款仿 Eyepetizer（开眼视频）的 Android 开源 App，采用 **MVVM + 组件化** 架构，基于 AndroidX + Jetpack 开发。

## 构建命令

```bash
# 构建整个项目（集成模式，所有模块作为 library）
./gradlew assembleDebug

# 构建 Release APK
./gradlew assembleRelease

# 清理构建
./gradlew clean

# 运行单元测试
./gradlew test

# 运行特定模块的测试
./gradlew :module-home:test
./gradlew :module-user:test

# 运行 Instrumented 测试（需要设备/模拟器）
./gradlew connectedAndroidTest
```

### 独立模块运行（组件化调试）

`gradle.properties` 中的 `isBuildModule` 控制模块构建模式：

- `isBuildModule=false`：所有模块作为 library 集成到 app（正常开发）
- `isBuildModule=true`：某个模块作为独立 App 运行（单模块调试）

每个模块在独立运行时使用 `src/main/alone/AndroidManifest.xml`，集成模式使用 `src/main/AndroidManifest.xml`。

## 架构总览

### 模块依赖层次

```
app（宿主 Application）
  └── module-main（底部导航 + 主框架）
       ├── module-home（首页：发现/日报/推荐）
       ├── module-community（社区：推荐/关注）
       ├── module-more（通知/更多）
       ├── module-player（视频详情页）
       └── module-user（用户/登录）

library-common（公共配置 + 模块生命周期管理）
library-base（BaseApplication + BaseViewModel 基类）
library-network（Retrofit + OkHttp + Hilt 网络层）
library-video（GSYVideoPlayer 视频播放封装）
library-servicemanager（跨模块服务通信框架）
```

### 关键架构机制

**1. 模块生命周期初始化（反射机制）**
`ModuleLifecycleReflexs.kt` 中注册各模块初始化类名，`ModuleLifecycleConfig` 通过反射调用每个模块的 `IModuleInit` 实现，分为 `onInitAhead`（优先初始化）和 `onInitLow`（延迟初始化）两个阶段。新增模块需在 `ModuleLifecycleReflexs.kt` 中注册。

**2. 跨模块路由（ARouter）**
使用阿里 ARouter 进行跨模块页面跳转和服务调用。每个模块的 `build.gradle` 中需配置 `AROUTER_MODULE_NAME` 注解处理参数（已在 `ModulePlugin` 中统一配置）。

**3. 跨模块服务通信（ServiceManager）**
`library-servicemanager` 提供类似 AIDL 的本地服务通信机制，各模块通过接口暴露服务，例如 `module-user` 的 `ILoginServiceImpl` 实现登录服务供其他模块调用。

**4. 依赖注入（Hilt）**
`ModulePlugin` 统一为所有模块添加 Hilt 依赖，每个模块通过自己的 `@Module` 类（如 `UserModule`、`NetworkModule`）提供依赖。

**5. 网络层**
`library-network` 提供统一的 Retrofit + OkHttp 客户端（`NetworkModule`），各业务模块通过 Hilt 注入 `Retrofit` 实例，再创建自己的 API 接口（如 `UserApi`）。

**6. ViewModel 基类**
`BaseViewModel`（`library-base`）封装了协程 `launchDataLoad`、统一 `isLoading`（StateFlow）和 `errorMessage`（StateFlow）。业务 ViewModel 继承此类。

### 新增业务模块流程

1. 在 `settings.gradle.kts` 的 `include` 中添加模块
2. 模块 `build.gradle.kts` 中应用 `id("com.drz.module")` 插件（`ModulePlugin`）
3. 实现 `IModuleInit` 接口并在 `ModuleLifecycleReflexs.kt` 中注册
4. 在 `src/main/alone/AndroidManifest.xml` 配置独立运行入口

## 技术栈版本

- compileSdk 34 / minSdk 21 / targetSdk 34
- Kotlin 1.9.22 + Coroutines 1.7.3
- Hilt 2.50 + KSP 1.9.22-1.0.17
- Retrofit 2.9.0 + OkHttp 4.12.0
- ARouter 1.5.2（路由）
- GSYVideoPlayer 11.3.0（视频播放）
- BRVAH 3.0.11（RecyclerView Adapter）
- SmartRefreshLayout 2.0.0（下拉刷新）
- MMKV 1.3.3（替代 SharedPreferences）
- Glide 4.16.0（图片加载）

## API 接口

项目抓包自开眼 App，Base URL：`http://baobab.kaiyanapp.com/api/`，仅供学习使用。

## 签名配置

Release 签名读取根目录 `keystore.properties`（未提交到 git），格式：
```
keyAlias=xxx
keyPassword=xxx
storeFile=xxx
storePassword=xxx
```
