package com.drz.common.services.config;

/**
 * 应用模块:
 * <p>
 * 类描述: 各个组件需要暴露给外部的service名称 配置
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-29
 */
public class ServicesConfig
{
    private static final String SERVICE = "/service";
    
    /** 用户模块 */
    public static class User
    {
        /** 用户登录状态 */
        public static final String LONGING_SERVICE = SERVICE + "/login";
    }
    
}
