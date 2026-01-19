import com.android.build.gradle.BaseExtension
import com.drz.plugin.ModulePlugin

apply<ModulePlugin>()

configure<BaseExtension> {
    namespace = "com.zhouyou.http"
    resourcePrefix = "net_"
    
    defaultConfig {
        versionCode = 4
        versionName = "2.1.6"
    }
    
    lintOptions {
        isAbortOnError = false
        isCheckReleaseBuilds = false
    }
}

dependencies {
    add("api", libs.okhttp.logging)
    add("api", libs.okhttp)
    add("api", libs.disklrucache)
    add("api", libs.rxjava2)
    add("api", libs.rxandroid)
    add("api", libs.retrofit)
    add("api", libs.retrofit.converter.gson)
    add("api", libs.retrofit.adapter.rxjava2)
}
