package com.drz.common.config;

/**
 * 应用模块: common
 * <p>
 * 类描述: 组件生命周期初始化类的类目管理者,在这里注册需要初始化的组件,通过反射动态调用各个组件的初始化方法
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-25
 */
public class ModuleLifecycleReflexs
{
    /** 基础库 */
    private static final String BaseInit = "com.drz.common.CommonModuleInit";
    
    /** main组件库 */
    private static final String MainInit =
        "com.drz.main.application.MainModuleInit";

    /**用户组件初始化*/
    private static final String UserInit = "com.drz.user.UserModuleInit";
    
    public static String[] initModuleNames = {BaseInit, MainInit,UserInit};
}
