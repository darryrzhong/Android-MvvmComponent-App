package com.drz.base.base

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import coil.Coil
import coil.ImageLoader
import okhttp3.OkHttpClient

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        initCoil()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                AppManager.instance.addActivity(activity)
            }
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {
                AppManager.instance.removeActivity(activity)
            }
        })
    }

    private fun initCoil() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                // img.kaiyanapp.com 返回 1 像素占位图，替换为可用的 ali-img 域名
                val original = chain.request()
                val url = original.url
                val newUrl = if (url.host == "img.kaiyanapp.com") {
                    url.newBuilder().host("ali-img.kaiyanapp.com").build()
                } else {
                    url
                }
                chain.proceed(original.newBuilder().url(newUrl).build())
            }
            .build()
        Coil.setImageLoader {
            ImageLoader.Builder(this)
                .okHttpClient(okHttpClient)
                .build()
        }
    }

    fun isDebug(): Boolean = sDebug

    fun setDebug(isDebug: Boolean) {
        sDebug = isDebug
    }

    companion object {
        private var sInstance: BaseApplication? = null
        private var sDebug = false

        @JvmStatic
        val instance: BaseApplication
            get() = sInstance ?: throw NullPointerException("BaseApplication not initialized.")

        @JvmStatic
        fun currentProcessName(): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Application.getProcessName()
        } else {
            try {
                val cls = Class.forName("android.app.ActivityThread")
                cls.getMethod("currentProcessName").invoke(null) as? String ?: ""
            } catch (e: Exception) {
                ""
            }
        }
    }
}
