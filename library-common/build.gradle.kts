plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.drz.common"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
    }

    // resources exclusion removed

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    resourcePrefix = "common_"
    buildFeatures {
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    api(project(":library-base"))
    api(project(":library-network"))
    api(project(":library-servicemanager"))
    api(libs.smart.refresh.kernel)
    api(libs.smart.refresh.header)
    api(libs.smart.refresh.footer)
    api(libs.arouter.api)
    api(libs.logger)
    api(libs.annotations)
    api(libs.utilcodex)
    api(libs.banner.viewpager)
    api(libs.viewpagerindicator)
}
