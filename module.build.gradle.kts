buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.aliyun.com/repository/google")
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
        maven(url = "https://maven.aliyun.com/repository/public")
        maven(url = "https://maven.aliyun.com/repository/central")
    }
    dependencies {
        classpath(libs.plugin.android.gradle)
        classpath(libs.plugin.kotlin.gradle)
    }
}

import com.android.build.gradle.BaseExtension

val isBuildModule: String by project

if (isBuildModule.toBoolean()) {
    //作为独立App应用运行
    apply(plugin = "com.android.application")
} else {
    //作为组件运行
    apply(plugin = "com.android.library")
}

apply(plugin = "org.jetbrains.kotlin.android")

// configure<BaseExtension> fails if looked up by type. Use getByName("android") instead.
(extensions.getByName("android") as BaseExtension).apply {
    val libs = rootProject.extensions.getByType<VersionCatalogsExtension>().named("libs")
    
    compileSdkVersion(libs.findVersion("android-compileSdk").get().requiredVersion.toInt())
    buildToolsVersion(libs.findVersion("android-buildTools").get().requiredVersion)

    defaultConfig {
        minSdkVersion(libs.findVersion("android-minSdk").get().requiredVersion.toInt())
        targetSdkVersion(libs.findVersion("android-targetSdk").get().requiredVersion.toInt())
        
        if (isBuildModule.toBoolean()) {
             versionCode(libs.findVersion("android-versionCode").get().requiredVersion.toInt())
             versionName(libs.findVersion("android-versionName").get().requiredVersion)
        }

        consumerProguardFiles("consumer-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["AROUTER_MODULE_NAME"] = project.name
            }
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        getByName("main") {
            if (isBuildModule.toBoolean()) {
                manifest.srcFile("src/main/alone/AndroidManifest.xml")
            } else {
                manifest.srcFile("src/main/AndroidManifest.xml")
            }
        }
    }

    dataBinding {
        isEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
