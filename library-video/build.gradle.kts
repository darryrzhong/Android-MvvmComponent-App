import com.android.build.gradle.BaseExtension
import com.drz.plugin.ModulePlugin

apply<ModulePlugin>()

configure<BaseExtension> {
    namespace = "com.drz.video"
    resourcePrefix = "video_"
}

dependencies {
    add("api", libs.androidx.recyclerview)
    add("api", libs.glide)
    add("annotationProcessor", libs.glide.compiler)
    add("api", libs.gsyVideoPlayer.java)
    add("api", libs.gsyVideoPlayer.armv7a)
    add("api", libs.gsyVideoPlayer.arm64)
}
