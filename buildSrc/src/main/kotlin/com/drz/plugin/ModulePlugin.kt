package com.drz.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class ModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val isBuildModule = project.properties["isBuildModule"]?.toString()?.toBoolean() ?: false

        if (isBuildModule) {
            project.plugins.apply("com.android.application")
        } else {
            project.plugins.apply("com.android.library")
        }
        project.plugins.apply("org.jetbrains.kotlin.android")
        // project.plugins.apply("kotlin-parcelize") // Optional, added in modules manually?

        project.extensions.configure<BaseExtension> {
            val libs =
                project.rootProject.extensions.getByType<VersionCatalogsExtension>().named("libs")

            compileSdkVersion(libs.findVersion("android-compileSdk").get().requiredVersion.toInt())
            buildToolsVersion(libs.findVersion("android-buildTools").get().requiredVersion)

            defaultConfig {
                minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
                targetSdk = libs.findVersion("android-targetSdk").get().requiredVersion.toInt()

                if (isBuildModule) {
                    val vCode =
                        libs.findVersion("android-versionCode").get().requiredVersion.toInt()
                    val vName = libs.findVersion("android-versionName").get().requiredVersion
                    // Use reflection or dynamic assignment because BaseExtension type might not expose 
                    // application-specific properties directly in a clean way for both app/lib?
                    // But BaseExtension usually covers both.
                    // However, 'versionCode' property on DefaultConfig is available.
                    // But for Library, it is ignored or deprecated.
                    // Let's try setting it safely.
                    try {
                        val method = this.javaClass.getMethod("setVersionCode", Integer::class.java)
                        method.invoke(this, vCode)
                    } catch (e: Exception) {
                        // ignore
                    }
                    try {
                        val method = this.javaClass.getMethod("setVersionName", String::class.java)
                        method.invoke(this, vName)
                    } catch (e: Exception) {
                        // ignore
                    }
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
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            sourceSets {
                getByName("main") {
                    if (isBuildModule) {
                        manifest.srcFile("src/main/alone/AndroidManifest.xml")
                    } else {
                        manifest.srcFile("src/main/AndroidManifest.xml")
                    }
                }
            }

            // dataBinding { isEnabled = true } legacy
            try {
                // buildFeatures.dataBinding = true
                val buildFeatures = this::class.java.getMethod("getBuildFeatures").invoke(this)
                val setMethod = buildFeatures::class.java.getMethod(
                    "setDataBinding",
                    java.lang.Boolean::class.java
                )
                setMethod.invoke(buildFeatures, true)
            } catch (e: Exception) {
                // Try legacy
                try {
                    val dataBinding = this::class.java.getMethod("getDataBinding").invoke(this)
                    val setEnabled = dataBinding::class.java.getMethod(
                        "setEnabled",
                        Boolean::class.javaPrimitiveType
                    )
                    setEnabled.invoke(dataBinding, true)
                } catch (e2: Exception) {
                    println("Failed to enable dataBinding: $e2")
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }
}
