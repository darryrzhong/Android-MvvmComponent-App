package com.drz.main.application;

import com.blankj.utilcode.util.Utils;
import com.drz.base.base.BaseApplication;
import com.drz.base.loadsir.EmptyCallback;
import com.drz.base.loadsir.ErrorCallback;
import com.drz.base.loadsir.LoadingCallback;
import com.drz.base.loadsir.TimeoutCallback;
import com.drz.common.IModuleInit;
import com.drz.common.adapter.ScreenAutoAdapter;
import com.kingja.loadsir.core.LoadSir;
import com.limpoxe.support.servicemanager.ServiceManager;
import com.orhanobut.logger.Logger;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.GsonDiskConverter;
import com.zhouyou.http.cache.model.CacheMode;

/**
 * 应用模块: main
 * <p>
 * 类描述: main组件的业务初始化
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-26
 */
public class MainModuleInit implements IModuleInit
{
    @Override
    public boolean onInitAhead(BaseApplication application)
    {
        ScreenAutoAdapter.setup(application);
        EasyHttp.init(application);
        if (application.issDebug())
        {
            EasyHttp.getInstance().debug("easyhttp", true);
        }
        EasyHttp.getInstance()
            .setBaseUrl("http://baobab.kaiyanapp.com")
            .setReadTimeOut(15 * 1000)
            .setWriteTimeOut(15 * 1000)
            .setConnectTimeout(15 * 1000)
            .setRetryCount(3)
            .setCacheDiskConverter(new GsonDiskConverter())
            .setCacheMode(CacheMode.FIRSTREMOTE);
        LoadSir.beginBuilder()
            .addCallback(new ErrorCallback())
            .addCallback(new LoadingCallback())
            .addCallback(new EmptyCallback())
            .addCallback(new TimeoutCallback())
            .setDefaultCallback(LoadingCallback.class)
            .commit();
         Utils.init(application);
        Logger.i("main组件初始化完成 -- onInitAhead");
        return false;
    }
    
    @Override
    public boolean onInitLow(BaseApplication application)
    {
        return false;
    }
}
