package com.drz.mvvmcomponent

import android.util.Log
import com.drz.base.base.BaseApplication
import com.drz.common.config.ModuleLifecycleConfig
import dagger.hilt.android.HiltAndroidApp

/**
 * 应用模块: 宿主app
 * 
 * 类描述: 宿主app的 application
 *
 * @author darryrzhoong
 * @since 2020-02-26
 */
@HiltAndroidApp
class AppApplication : BaseApplication() {
    
    override fun onCreate() {
        super.onCreate()
        Log.d("AppApplication", "onCreate: Hilt should be initialized")
        setDebug(BuildConfig.DEBUG)
        // 初始化需要初始化的组件
        ModuleLifecycleConfig.instance.initModuleAhead(this)
    }
}
