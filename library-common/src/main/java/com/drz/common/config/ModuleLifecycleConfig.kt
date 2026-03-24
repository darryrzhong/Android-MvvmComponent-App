package com.drz.common.config

import com.drz.base.base.BaseApplication
import com.drz.common.IModuleInit

class ModuleLifecycleConfig private constructor() {

    fun initModuleAhead(application: BaseApplication?) {
        ModuleLifecycleReflexs.initModuleNames.forEach { moduleName ->
            runCatching {
                val clazz = Class.forName(moduleName)
                val init = clazz.getDeclaredConstructor().newInstance() as IModuleInit
                init.onInitAhead(application)
            }
        }
    }

    fun initModuleLow(application: BaseApplication?) {
        ModuleLifecycleReflexs.initModuleNames.forEach { moduleName ->
            runCatching {
                val clazz = Class.forName(moduleName)
                val init = clazz.getDeclaredConstructor().newInstance() as IModuleInit
                init.onInitLow(application)
            }
        }
    }

    companion object {
        @JvmStatic
        val instance: ModuleLifecycleConfig by lazy { ModuleLifecycleConfig() }
    }
}
