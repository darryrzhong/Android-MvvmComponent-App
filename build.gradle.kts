// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
   // Plugin management is handled in settings.gradle.kts and buildSrc dependencies.
   // We declare them here only if we want to apply them to root or lock versions.
   // Since buildSrc forces versions, we can comment these out or use apply false BUT 
   // if buildSrc is present, it takes precedence.
   // Let's try commenting them out to avoid the "already on classpath" error.
   // alias(libs.plugins.android.application) apply false
   // alias(libs.plugins.android.library) apply false
   // alias(libs.plugins.kotlin.android) apply false
   // alias(libs.plugins.kotlin.parcelize) apply false
   
   // Hilt and KSP might still need definition if not in buildSrc classpath (but they are)
   // alias(libs.plugins.hilt) apply false
   // alias(libs.plugins.ksp) apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
