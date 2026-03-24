// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Plugin versions are managed via buildSrc dependencies, no need to declare them here.

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
