import com.android.build.gradle.BaseExtension
import com.drz.plugin.ModulePlugin

apply<ModulePlugin>()
apply(plugin = "kotlin-parcelize")

configure<BaseExtension> {
    namespace = "com.drz.main"
    defaultConfig {
        val isBuildModule = project.properties["isBuildModule"]?.toString()?.toBoolean() ?: false
        if (isBuildModule) {
            applicationId = "com.drz.main"
        }
    }
    
    resourcePrefix = "main_"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    add("testImplementation", libs.junit)
    add("androidTestImplementation", libs.androidx.test.ext.junit)
    add("androidTestImplementation", libs.androidx.test.espresso.core)
    
    add("annotationProcessor", libs.arouter.compiler)
    add("annotationProcessor", libs.glide.compiler)
    
    add("api", project(":library-common"))
    add("api", libs.pager.bottom.tab.strip)
    add("api", libs.androidx.core.ktx)
    add("api", libs.androidx.lifecycle.viewmodel.ktx)
    add("api", libs.kotlin.stdlib)
}
