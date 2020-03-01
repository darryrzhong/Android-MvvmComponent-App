package com.drz.common;

import com.drz.base.base.BaseApplication;

/**
 * 应用模块: base
 * <p>
 * 类描述: 动态配置组件Application,有需要初始化的组件实现该接口,统一在宿主app 的Application进行初始化
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-25
 */
public interface IModuleInit
{
    /** 需要优先初始化的 */
    boolean onInitAhead(BaseApplication application);
    
    /** 可以后初始化的 */
    boolean onInitLow(BaseApplication application);
    
}
