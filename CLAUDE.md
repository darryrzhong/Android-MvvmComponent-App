# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目简介

这是一款仿 Eyepetizer（开眼视频）的 Android 开源 App，采用 **MVVM + 组件化** 架构，基于 Jetpack Compose + Hilt + Navigation Compose 开发。UI 层已全面迁移至 Compose，**禁止使用 dataBinding**。

## 构建命令

```bash
# 构建整个项目（集成模式）
./gradlew assembleDebug

# 构建 Release APK
./gradlew assembleRelease

# 清理构建
./gradlew clean

# 运行特定模块测试
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
app（宿主 Application，组装 AppNavHost）
  └── module-main（底部导航 + 主框架）
       ├── module-home（首页：发现/日报/推荐）
       ├── module-community（社区：推荐/关注）
       ├── module-more（通知/更多）
       ├── module-player（视频详情页）
       └── module-user（用户/登录）

library-common（公共配置 + 模块生命周期管理 + AppRoutes 路由常量）
library-base（BaseApplication + BaseViewModel 基类 + 通用 Compose 屏幕）
library-network（Retrofit + OkHttp + Hilt 网络层）
library-video（GSYVideoPlayer 视频播放封装）
library-servicemanager（跨模块服务通信框架）
```

### 关键架构机制

**1. 导航（Navigation Compose）**
跨模块导航使用 Navigation Compose，路由常量统一定义在 `library-common` 的 `AppRoutes`。每个业务模块暴露扩展函数（如 `NavGraphBuilder.homeNavGraph()`），由 `app` 模块的 `AppNavHost` 统一组装。传参通过 URL path/query 编码，字符串需 URLEncoder 编码。

**2. UI 状态模式（UiState + sealed class）**
每个 Screen 对应一个 sealed class UiState（`Loading / Success / Error`），ViewModel 持有 `MutableStateFlow<XxxUiState>`，Screen 通过 `collectAsState()` 消费。示例：`DiscoverUiState`、`DailyUiState`。

**3. 模块生命周期初始化（反射机制）**
`ModuleLifecycleReflexs.kt` 中注册各模块初始化类名，`ModuleLifecycleConfig` 通过反射调用每个模块的 `IModuleInit` 实现，分为 `onInitAhead`（优先初始化）和 `onInitLow`（延迟初始化）两个阶段。新增模块需在此注册。

**4. 网络层（NetworkResult + safeApiCall）**
`library-network` 提供 `NetworkResult<T>（Success / Error）` 密封类和 `safeApiCall {}` 挂起函数（统一捕获 HttpException / IOException）。Repository 调用 `safeApiCall { api.xxx() }` 返回 `NetworkResult`，ViewModel 对结果做 when 分支映射到 UiState。

**5. ViewModel 基类**
`BaseViewModel`（`library-base`）提供 `launchRequest {}` 和 `launchDataLoad {}`，统一管理 `isLoading`（StateFlow）和 `errorMessage`（StateFlow）。

**6. 依赖注入（Hilt）**
`ModulePlugin` 统一为所有模块添加 Hilt 依赖，每个模块通过自己的 `@Module` 类（如 `HomeModule`、`NetworkModule`）提供依赖。Screen 通过 `hiltViewModel()` 获取 ViewModel。

**7. 跨模块服务通信（ServiceManager）**
`library-servicemanager` 提供本地服务通信机制，各模块通过接口暴露服务，例如 `module-user` 的 `ILoginServiceImpl`。

### 通用 Compose 组件（library-base）

`BaseScreen.kt` 提供三个可复用组件，所有屏幕统一使用：
- `LoadingScreen()`：居中圆形进度条
- `ErrorScreen(message, onRetry)`：错误信息 + 重试按钮
- `EmptyScreen(message)`：空数据提示

### 新增业务模块流程

1. 在 `settings.gradle.kts` 的 `include` 中添加模块
2. 模块 `build.gradle.kts` 中应用 `id("com.drz.module")` 插件（`ModulePlugin`）
3. 实现 `IModuleInit` 接口并在 `ModuleLifecycleReflexs.kt` 中注册
4. 在 `src/main/alone/AndroidManifest.xml` 配置独立运行入口
5. 暴露 `NavGraphBuilder.xxxNavGraph()` 扩展函数，在 `app` 的 `AppNavHost.kt` 中注册

## 技术栈版本

- compileSdk 34 / minSdk 21 / targetSdk 34
- Kotlin 1.9.22 + Coroutines 1.7.3
- Jetpack Compose（Material3）+ Navigation Compose
- Hilt 2.50 + KSP 1.9.22-1.0.17
- Retrofit 2.9.0 + OkHttp 4.12.0
- GSYVideoPlayer 11.3.0（视频播放）
- Coil（Compose 图片加载，替代 Glide）
- MMKV 1.3.3（替代 SharedPreferences）

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
