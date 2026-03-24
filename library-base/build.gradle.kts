import com.android.build.gradle.BaseExtension
import com.drz.plugin.ModulePlugin

apply<ModulePlugin>()

configure<BaseExtension> {
    namespace = "com.drz.base"
    resourcePrefix = "base_"
}

dependencies {
    add("api", project(":library-network"))

    add("api", libs.androidx.appcompat)
    add("api", libs.androidx.lifecycle.runtime.ktx)
    add("api", libs.androidx.lifecycle.viewmodel.ktx)
    add("api", libs.androidx.lifecycle.runtime.compose)
    add("api", libs.androidx.lifecycle.viewmodel.compose)
    add("api", libs.androidx.activity.compose)
    add("api", libs.core.ktx)
    add("api", libs.android.material)

    // Compose
    add("api", platform(libs.androidx.compose.bom))
    add("api", libs.androidx.compose.ui)
    add("api", libs.androidx.compose.ui.tooling.preview)
    add("api", libs.androidx.compose.material3)
    add("api", libs.androidx.compose.foundation)
    add("api", libs.androidx.compose.runtime)
    add("debugImplementation", libs.androidx.compose.ui.tooling)

    // Navigation
    add("api", libs.androidx.navigation.compose)
    add("api", libs.hilt.navigation.compose)

    // Utilities
    add("api", libs.mmkv)
    add("api", libs.logger)
    add("api", libs.coil.compose)
}
