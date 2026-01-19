package com.drz.common.config

import com.drz.base.base.BaseApplication
import com.drz.common.IModuleInit

/**
 * 应用模块: common
 *
 * 类描述: 作为组件生命周期初始化的配置类,通过反射机制,动态调用每个组件初始化逻辑
 *
 *
 * @author darryrzhoong
 * @since 2020-02-25
 */
class ModuleLifecycleConfig private constructor() {

    fun initModuleAhead(application: BaseApplication?) {
        for (moduleName in ModuleLifecycleReflexs.initModuleNames) {
            try {
                val clazz = Class.forName(moduleName)
                val init = clazz.newInstance() as IModuleInit
                init.onInitAhead(application)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    fun initModuleLow(application: BaseApplication?) {
        for (moduleName in ModuleLifecycleReflexs.initModuleNames) {
            try {
                val clazz = Class.forName(moduleName)
                val init = clazz.newInstance() as IModuleInit
                init.onInitLow(application)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    private object SingleHolder {
        val instance = ModuleLifecycleConfig()
    }

    companion object {
        @JvmStatic
        val instance: ModuleLifecycleConfig
            get() = SingleHolder.instance
    }
}
