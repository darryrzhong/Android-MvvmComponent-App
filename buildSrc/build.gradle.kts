plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    maven(url = "https://maven.aliyun.com/repository/google")
    maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
    maven(url = "https://maven.aliyun.com/repository/public")
    maven(url = "https://maven.aliyun.com/repository/central")
}

dependencies {
    implementation("com.android.tools.build:gradle:8.2.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.50")
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.22-1.0.17")
}
