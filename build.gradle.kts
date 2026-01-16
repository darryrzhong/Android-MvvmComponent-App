// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Plugins are managed via buildSrc and classpath inheritance.
// @Suppress("DSL_SCOPE_VIOLATION")
// plugins {
//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.android.library) apply false
//    alias(libs.plugins.kotlin.android) apply false
//    alias(libs.plugins.kotlin.parcelize) apply false
// }

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
