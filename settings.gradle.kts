pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.aliyun.com/repository/google")
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
        maven(url = "https://maven.aliyun.com/repository/public")
        maven(url = "https://maven.aliyun.com/repository/central")
        maven(url = "https://plugins.gradle.org/m2/com/gradle")
        maven(url = "https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.aliyun.com/repository/google")
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
        maven(url = "https://maven.aliyun.com/repository/public")
        maven(url = "https://maven.aliyun.com/repository/central")
        maven(url = "https://plugins.gradle.org/m2/com/gradle")
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "MvvmComponent"
include(
    ":app",
    ":library-base",
    ":library-common",
    ":library-servicemanager",
    ":library-network",
    ":module-main",
    ":module-home",
    ":module-community",
    ":library-video",
    ":module-more",
    ":module-player",
    ":module-user"
)
