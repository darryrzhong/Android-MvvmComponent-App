import com.android.build.gradle.BaseExtension
import com.drz.plugin.ModulePlugin

apply<ModulePlugin>()

configure<BaseExtension> {
    namespace = "com.drz.user"
    defaultConfig {
        val isBuildModule = project.properties["isBuildModule"]?.toString()?.toBoolean() ?: false
        if (isBuildModule) applicationId = "com.drz.user"
    }
    resourcePrefix = "user_"
}

dependencies {
    add("api", project(":library-common"))
    add("implementation", libs.androidx.activity.compose)
    add("implementation", libs.androidx.compose.ui)
    add("implementation", libs.androidx.compose.material3)
    add("implementation", libs.androidx.lifecycle.runtime.compose)
    add("testImplementation", libs.junit)
    add("androidTestImplementation", libs.androidx.test.ext.junit)
    add("androidTestImplementation", libs.androidx.test.espresso.core)
}
