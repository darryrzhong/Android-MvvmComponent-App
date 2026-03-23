package com.drz.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class ModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val isBuildModule = project.properties["isBuildModule"]?.toString()?.toBoolean() ?: false

        if (isBuildModule) {
            project.plugins.apply("com.android.application")
        } else {
            project.plugins.apply("com.android.library")
        }
        project.plugins.apply("org.jetbrains.kotlin.android")
        project.plugins.apply("org.jetbrains.kotlin.plugin.compose")
        project.plugins.apply("kotlin-parcelize")
        project.plugins.apply("com.google.devtools.ksp")
        project.plugins.apply("com.google.dagger.hilt.android")

        val libs = project.rootProject.extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
        project.dependencies.add("implementation", libs.findLibrary("hilt-android").get())
        project.dependencies.add("ksp", libs.findLibrary("hilt-compiler").get())
        project.dependencies.add("implementation", libs.findLibrary("kotlinx-coroutines-android").get())
        project.dependencies.add("implementation", libs.findLibrary("kotlinx-coroutines-core").get())

        // Compose BOM + runtime（所有模块都启用了 compose buildFeature，必须有 runtime）
        val composeBom = project.dependencies.platform(libs.findLibrary("androidx-compose-bom").get())
        project.dependencies.add("implementation", composeBom)
        project.dependencies.add("implementation", libs.findLibrary("androidx-compose-runtime").get())

        project.extensions.configure<BaseExtension> {
            compileSdkVersion(libs.findVersion("android-compileSdk").get().requiredVersion.toInt())
            buildToolsVersion(libs.findVersion("android-buildTools").get().requiredVersion)

            defaultConfig {
                minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
                targetSdk = libs.findVersion("android-targetSdk").get().requiredVersion.toInt()

                if (isBuildModule) {
                    val vCode = libs.findVersion("android-versionCode").get().requiredVersion.toInt()
                    val vName = libs.findVersion("android-versionName").get().requiredVersion
                    try {
                        val method = this.javaClass.getMethod("setVersionCode", Integer::class.java)
                        method.invoke(this, vCode)
                    } catch (e: Exception) { }
                    try {
                        val method = this.javaClass.getMethod("setVersionName", String::class.java)
                        method.invoke(this, vName)
                    } catch (e: Exception) { }
                }

                consumerProguardFiles("consumer-rules.pro")
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
                    if (isBuildModule) {
                        manifest.srcFile("src/main/alone/AndroidManifest.xml")
                    } else {
                        manifest.srcFile("src/main/AndroidManifest.xml")
                    }
                }
            }

            // 启用 ViewBinding 和 Compose，禁用 DataBinding
            try {
                val buildFeatures = this::class.java.getMethod("getBuildFeatures").invoke(this)
                buildFeatures::class.java.getMethod("setViewBinding", java.lang.Boolean::class.java)
                    .invoke(buildFeatures, true)
                buildFeatures::class.java.getMethod("setCompose", java.lang.Boolean::class.java)
                    .invoke(buildFeatures, true)
                // 确保 DataBinding 关闭
                try {
                    buildFeatures::class.java.getMethod("setDataBinding", java.lang.Boolean::class.java)
                        .invoke(buildFeatures, false)
                } catch (e: Exception) { }
            } catch (e: Exception) { }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }

        project.tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
}
