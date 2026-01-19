import com.android.build.gradle.BaseExtension
import com.drz.plugin.ModulePlugin

apply<ModulePlugin>()

configure<BaseExtension> {
    namespace = "com.drz.user"
    defaultConfig {
        val isBuildModule = project.properties["isBuildModule"]?.toString()?.toBoolean() ?: false
        if (isBuildModule) {
            applicationId = "com.drz.user"
        }
    }
    
    resourcePrefix = "user_"
    
    buildFeatures.compose = true
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
}

dependencies {
    add("testImplementation", libs.junit)
    add("androidTestImplementation", libs.androidx.test.ext.junit)
    add("androidTestImplementation", libs.androidx.test.espresso.core)
    
    add("annotationProcessor", libs.arouter.compiler)
    add("annotationProcessor", libs.glide.compiler)
    
    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    add("implementation", composeBom)
    add("androidTestImplementation", composeBom)
    
    // Explicitly add runtime to avoid version mismatch issues
    add("implementation", libs.androidx.compose.runtime)

    add("implementation", libs.androidx.compose.ui)
    add("implementation", libs.androidx.compose.ui.tooling.preview)
    add("implementation", libs.androidx.compose.material)
    add("implementation", libs.androidx.activity.compose)
    add("implementation", libs.androidx.lifecycle.runtime.compose)
    
    add("debugImplementation", libs.androidx.compose.ui.tooling)
    
    add("api", project(":library-common"))
}
