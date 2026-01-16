plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.zhouyou.http"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    // resources exclusion removed

    resourcePrefix = "net_"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }
}

dependencies {
    api(libs.okhttp.logging)
    api(libs.okhttp)
    api(libs.disklrucache)
    api(libs.rxjava2)
    api(libs.rxandroid)
    api(libs.retrofit)
    api(libs.retrofit.converter.gson)
    api(libs.retrofit.adapter.rxjava2)
}
