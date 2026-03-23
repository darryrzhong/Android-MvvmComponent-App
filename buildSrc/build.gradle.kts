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
    implementation("com.android.tools.build:gradle:8.7.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
    implementation("org.jetbrains.kotlin:compose-compiler-gradle-plugin:2.0.21")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.52")
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.0.21-1.0.27")
}
