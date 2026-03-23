import com.android.build.gradle.BaseExtension
import com.drz.plugin.ModulePlugin

apply<ModulePlugin>()

configure<BaseExtension> {
    namespace = "com.drz.community"
defaultConfig {
        val isBuildModule = project.properties["isBuildModule"]?.toString()?.toBoolean() ?: false
        if (isBuildModule) applicationId = "com.drz.community"
    }
    resourcePrefix = "community_"
}

dependencies {
    add("api", project(":library-common"))
    add("api", project(":module-home"))
    add("testImplementation", libs.junit)
    add("androidTestImplementation", libs.androidx.test.ext.junit)
    add("androidTestImplementation", libs.androidx.test.espresso.core)
}
