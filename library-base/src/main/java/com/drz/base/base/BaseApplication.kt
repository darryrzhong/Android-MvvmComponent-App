package com.drz.base.base

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Process

/**
 * 应用模块:
 *
 * 类描述:
 *
 *
 * @author darryrzhoong
 * @since 2020-02-25
 */
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setApplication(this)
    }

    /**
     * 当宿主没有继承自该Application时,可以使用set方法进行初始化baseApplication
     */
    private fun setApplication(application: BaseApplication) {
        sInstance = application
        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
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

    fun isDebug(): Boolean {
        return sDebug
    }

    fun setDebug(isDebug: Boolean) {
        sDebug = isDebug
    }

    companion object {
        private var sInstance: BaseApplication? = null
        private var sDebug = false

        /**
         * 获得当前app运行的Application
         */
        @JvmStatic
        val instance: BaseApplication
            get() {
                if (sInstance == null) {
                    throw NullPointerException("please inherit BaseApplication or call setApplication.")
                }
                return sInstance!!
            }

        /**
         * 获取进程名
         *
         * @param context
         * @return
         */
        @JvmStatic
        fun getCurrentProcessName(context: Context): String? {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val runningApps = am.runningAppProcesses ?: return null
            for (proInfo in runningApps) {
                if (proInfo.pid == Process.myPid()) {
                    if (proInfo.processName != null) {
                        return proInfo.processName
                    }
                }
            }
            return null
        }
    }
}
