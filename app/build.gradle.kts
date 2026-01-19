import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "com.drz.mvvmcomponent"
    
    signingConfigs {
        create("release") {
            if (keystoreProperties["keyAlias"] != null) {
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
            }
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
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["AROUTER_MODULE_NAME"] = project.name
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            if (keystoreProperties["keyAlias"] != null) {
                signingConfig = signingConfigs.getByName("release")
            }
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
        dataBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    
    // Processors
    ksp(libs.hilt.compiler)
    annotationProcessor(libs.arouter.compiler)
    annotationProcessor(libs.glide.compiler)
    
    // Hilt
    implementation(libs.hilt.android)
    
    val isBuildModule: String by project
    if (isBuildModule.toBoolean()) {
        implementation(project(":library-base"))
    } else {
        implementation(project(":module-main"))
        implementation(project(":module-home"))
        implementation(project(":module-community"))
        implementation(project(":module-more"))
        implementation(project(":module-player"))
        implementation(project(":module-user"))
    }
}
