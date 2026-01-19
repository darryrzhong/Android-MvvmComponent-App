package com.drz.mvvmcomponent

import com.drz.base.base.BaseApplication
import com.drz.common.config.ModuleLifecycleConfig
import dagger.hilt.android.HiltAndroidApp

/**
 * 应用模块: 宿主app
 * 
 * 类描述: 宿主app的 application ,在这里通过common组件中定义的组件生命周期配置类进行相应初始化(通过反射调用各个组件的初始化器)
 * 
 *
 * @author darryrzhoong
 * @since 2020-02-26
 */
@HiltAndroidApp
class AppApplication : BaseApplication() {
    
    override fun onCreate() {
        super.onCreate()
        setDebug(BuildConfig.DEBUG)
        // 初始化需要初始化的组件
        ModuleLifecycleConfig.getInstance().initModuleAhead(this)
    }
}
