import com.android.build.gradle.BaseExtension
import com.drz.plugin.ModulePlugin

apply<ModulePlugin>()

configure<BaseExtension> {
    namespace = "com.drz.player"
    defaultConfig {
        val isBuildModule = project.properties["isBuildModule"]?.toString()?.toBoolean() ?: false
        if (isBuildModule) {
            applicationId = "com.drz.player"
        }
    }

    resourcePrefix = "player_"
}

dependencies {
    add("api", project(":library-common"))
    add("api", project(":library-video"))
    add("testImplementation", libs.junit)
    add("androidTestImplementation", libs.androidx.test.ext.junit)
    add("androidTestImplementation", libs.androidx.test.espresso.core)
}
