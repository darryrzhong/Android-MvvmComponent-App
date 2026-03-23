package com.drz.base.base

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        sInstance = this
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
