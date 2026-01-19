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
        project.plugins.apply("kotlin-parcelize")
        project.plugins.apply("com.google.devtools.ksp") 
        project.plugins.apply("com.google.dagger.hilt.android")

        val libs = project.rootProject.extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
        project.dependencies.add("implementation", libs.findLibrary("hilt-android").get())
        project.dependencies.add("ksp", libs.findLibrary("hilt-compiler").get())
        project.dependencies.add("implementation", libs.findLibrary("kotlinx-coroutines-android").get())
        project.dependencies.add("implementation", libs.findLibrary("kotlinx-coroutines-core").get())

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
                    if (isBuildModule) {
                        manifest.srcFile("src/main/alone/AndroidManifest.xml")
                    } else {
                        manifest.srcFile("src/main/AndroidManifest.xml")
                    }
                }
            }
            
            try {
                val buildFeatures = this::class.java.getMethod("getBuildFeatures").invoke(this)
                val setMethod = buildFeatures::class.java.getMethod("setDataBinding", java.lang.Boolean::class.java)
                setMethod.invoke(buildFeatures, true)
                val setVBMethod = buildFeatures::class.java.getMethod("setViewBinding", java.lang.Boolean::class.java)
                setVBMethod.invoke(buildFeatures, true)
                
                // Do NOT enable compose globally here. Let specific modules enable it.
            } catch (e: Exception) { }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
        
        // Fix JVM target incompatibility
        project.tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
}
