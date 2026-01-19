import com.android.build.gradle.BaseExtension
import com.drz.plugin.ModulePlugin

apply<ModulePlugin>()

configure<BaseExtension> {
    namespace = "com.drz.common"
    resourcePrefix = "common_"
}

dependencies {
    add("api", project(":library-base"))
    add("api", project(":library-network"))
    add("api", project(":library-servicemanager"))
    add("api", libs.smart.refresh.kernel)
    add("api", libs.smart.refresh.header)
    add("api", libs.smart.refresh.footer)
    add("api", libs.arouter.api)
    add("api", libs.logger)
    add("api", libs.annotations)
    add("api", libs.utilcodex)
    add("api", libs.banner.viewpager)
    add("api", libs.viewpagerindicator)
}
