import com.android.build.gradle.BaseExtension
import com.drz.plugin.ModulePlugin

apply<ModulePlugin>()

configure<BaseExtension> {
    namespace = "com.drz.network"
    buildFeatures.buildConfig = true
}

dependencies {
    add("api", libs.okhttp)
    add("api", libs.okhttp.logging)
    add("api", libs.retrofit)
    add("api", libs.retrofit.converter.gson)
    add("api", libs.gson)
}
