package com.drz.common.config

/**
 * 应用模块: common
 *
 * 类描述: 组件生命周期初始化类的类目管理者,在这里注册需要初始化的组件,通过反射动态调用各个组件的初始化方法
 *
 *
 * @author darryrzhoong
 * @since 2020-02-25
 */
object ModuleLifecycleReflexs {
    /**
     * 基础库
     */
    private const val BaseInit = "com.drz.common.CommonModuleInit"

    /**
     * main组件库
     */
    private const val MainInit = "com.drz.main.application.MainModuleInit"

    /**
     * 用户组件初始化
     */
    private const val UserInit = "com.drz.user.UserModuleInit"

    val initModuleNames = arrayOf(BaseInit, MainInit, UserInit)
}
