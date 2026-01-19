import com.android.build.gradle.BaseExtension
import com.drz.plugin.ModulePlugin

apply<ModulePlugin>()

configure<BaseExtension> {
    namespace = "com.drz.base"
    resourcePrefix = "base_"
}

dependencies {
    add("api", libs.androidx.appcompat)
    add("api", libs.androidx.lifecycle.extensions)
    add("api", libs.androidx.lifecycle.viewmodel.ktx) // Updated
    add("api", libs.androidx.recyclerview)
    add("api", libs.androidx.constraintlayout)
    add("api", libs.androidx.cardview)
    add("api", libs.android.material)
    add("api", libs.androidx.navigation.fragment)
    add("api", libs.androidx.navigation.ui)
    add("api", libs.loadsir)
    add("api", libs.gson)
    add("api", libs.rxjava2)
    add("api", libs.rxpermissions)
    add("api", libs.mmkv)
    add("api", libs.brvah)
    add("api", libs.immersionbar)
    add("api", libs.glide)
}
