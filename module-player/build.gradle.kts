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
    add("api", libs.androidx.recyclerview)
    add("api", libs.glide)
    add("ksp", libs.glide.compiler)
    add("api", libs.gsyVideoPlayer.java)
    add("api", libs.gsyVideoPlayer.exo)
    add("testImplementation", libs.junit)
    add("androidTestImplementation", libs.androidx.test.ext.junit)
    add("androidTestImplementation", libs.androidx.test.espresso.core)
}
