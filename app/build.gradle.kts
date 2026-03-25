plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.drz.mvvmcomponent"

    signingConfigs {
        create("release") {
            keyAlias = "key0"
            keyPassword = "mvvm123"
            storeFile = file("sign/mvvm.jks")
            storePassword = "mvvm123"
        }
        getByName("debug") {
            keyAlias = "key0"
            keyPassword = "mvvm123"
            storeFile = file("sign/mvvm.jks")
            storePassword = "mvvm123"
        }
    }

    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    defaultConfig {
        applicationId = libs.versions.android.applicationId.get()
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.android.versionCode.get().toInt()
        versionName = libs.versions.android.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            abiFilters.add("arm64-v8a")
            abiFilters.add("armeabi-v7a")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    sourceSets {
        getByName("main") {
            val isBuildModule: String by project
            if (isBuildModule.toBoolean()) {
                manifest.srcFile("src/main/alone/AndroidManifest.xml")
            } else {
                manifest.srcFile("src/main/AndroidManifest.xml")
            }
        }
    }

    resourcePrefix = "app_"
    buildFeatures {
        compose = true
        buildConfig = true
        dataBinding = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    
    ksp(libs.hilt.compiler)

    implementation(libs.hilt.android)
    implementation(libs.glide)
    
    val isBuildModule: String by project
    if (isBuildModule.toBoolean()) {
        implementation(project(":library-base"))
    } else {
        implementation(project(":module-main"))
        implementation(project(":module-home"))
        implementation(project(":module-community"))
        implementation(project(":module-player"))
//        implementation(project(":module-user"))
    }
}
