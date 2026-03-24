import com.android.build.gradle.BaseExtension
import com.drz.plugin.ModulePlugin

apply<ModulePlugin>()

configure<BaseExtension> {
    namespace = "com.drz.common"
    resourcePrefix = "common_"
}

dependencies {
    add("api", project(":library-base"))
    add("api", libs.utilcodex)
    add("api", libs.mmkv)
}
