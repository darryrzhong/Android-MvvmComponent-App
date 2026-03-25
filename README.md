> 一款模仿 Eyepetizer | 开眼视频的开源 Android App

基于 **MVVM + 组件化** 架构，采用 Jetpack Compose + Hilt + Navigation Compose 全面重构，UI 层完全使用 Compose 实现。

## 效果图

| ![app_02.png](https://upload-images.jianshu.io/upload_images/5549640-9380bb706c59e3af.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/260) | ![app_03.png](https://upload-images.jianshu.io/upload_images/5549640-0fd473371888d8ac.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/260) | ![app_04.png](https://upload-images.jianshu.io/upload_images/5549640-2e2421bce1e93e26.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/260) |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![app_05.png](https://upload-images.jianshu.io/upload_images/5549640-5d56b2b27f5e942e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/260) | ![app_06.png](https://upload-images.jianshu.io/upload_images/5549640-de207f28f6b3bdf7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/260) | ![app_07.png](https://upload-images.jianshu.io/upload_images/5549640-7e5261c0c5a6f7cd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/260) |
| ![app_08.png](https://upload-images.jianshu.io/upload_images/5549640-2b5e338fb31a0c25.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/260) | ![app_09.png](https://upload-images.jianshu.io/upload_images/5549640-6950f3f3ed412191.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/260) | ![app_10.png](https://upload-images.jianshu.io/upload_images/5549640-58b719bb47a3dbfc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/260) |


## 项目结构

采用组件化开发，`gradle.properties` 中的 `isBuildModule` 控制构建模式：

- `isBuildModule=false`：所有模块作为 library 集成到 app（正常开发）
- `isBuildModule=true`：某个模块作为独立 App 运行（单模块调试）

```
app（宿主 Application，组装 AppNavHost）
  └── module-main（底部导航 + 主框架）
       ├── module-home（首页：发现 / 日报 / 推荐）
       ├── module-community（社区：推荐 / 关注）
       └── module-player（视频详情页）

library-common（公共配置 + AppRoutes 路由常量）
library-base（BaseApplication + BaseViewModel + 通用 Compose 组件）
library-network（Retrofit + OkHttp + Hilt 网络层）
```

## 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 语言 | Kotlin | 2.0.21 |
| UI | Jetpack Compose + Material3 | BOM 2025.02.00 |
| 导航 | Navigation Compose | 2.8.5 |
| 依赖注入 | Hilt | 2.52 |
| 异步 | Kotlin Coroutines | 1.9.0 |
| 网络 | Retrofit2 + OkHttp4 | 2.11.0 / 4.12.0 |
| 图片加载 | Coil | 2.7.0 |
| 视频播放 | GSYVideoPlayer | 12.0.0 |
| 本地存储 | MMKV | 2.4.0 |
| compileSdk / minSdk | - | 35 / 24 |

## 架构设计

**UI 状态（UiState + sealed class）**

每个 Screen 对应一个 `sealed class UiState`（`Loading / Success / Error`），ViewModel 持有 `MutableStateFlow<XxxUiState>`，Screen 通过 `collectAsState()` 消费。

**网络层（NetworkResult + safeApiCall）**

`library-network` 提供 `NetworkResult<T>（Success / Error）` 密封类和 `safeApiCall {}` 挂起函数，统一处理 HttpException / IOException。

**导航（Navigation Compose）**

跨模块导航路由常量统一定义在 `library-common` 的 `AppRoutes`，各业务模块暴露 `NavGraphBuilder.xxxNavGraph()` 扩展函数，由 `app` 模块的 `AppNavHost` 统一组装。

**依赖注入（Hilt）**

`ModulePlugin` 统一为所有模块添加 Hilt 依赖，Screen 通过 `hiltViewModel()` 获取 ViewModel。

## 构建

```bash
# Debug 构建
./gradlew assembleDebug

# Release 构建
./gradlew assembleRelease

# 清理
./gradlew clean
```

## 项目 API

> 声明：项目中所有 API 接口均抓取自开眼 App，所有版权归属开眼 App 所有，仅供学习借鉴使用，请勿用于商业用途，若有侵权，请联系作者删除。

Base URL：`http://baobab.kaiyanapp.com/api/`

### 首页

1. 发现更多
   - `GET /v7/index/tab/discovery`

2. 每日推荐
   - `GET /v5/index/tab/allRec`

3. 日报精选
   - `GET /v5/index/tab/feed`

### 社区

1. 推荐
   - `GET /v7/community/tab/rec`

2. 关注
   - `GET /v6/community/tab/follow`

### 视频详情页

1. 相关推荐
   - `GET /v4/video/related?id={videoId}`

2. 评论
   - `GET /v2/replies/video?videoId={videoId}`

## 项目地址

**[Android-MvvmComponent-App](https://github.com/darryrzhong/Android-MvvmComponent-App)**

欢迎关注作者 [darryrzhong](https://darryrzhong.github.io/)，更多干货等你来拿。
