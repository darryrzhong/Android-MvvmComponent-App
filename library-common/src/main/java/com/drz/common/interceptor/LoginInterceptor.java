package com.drz.common.interceptor;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.drz.common.router.RouterActivityPath;
import com.drz.common.services.ILoginService;
import com.orhanobut.logger.Logger;

/**
 * 应用模块:
 * <p>
 * 类描述: 登录状态拦截器
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-29
 */
@Interceptor(priority = 8)
public class LoginInterceptor implements IInterceptor {
    private Context context;
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (postcard.getPath().equals(RouterActivityPath.User.PAGER_ATTENTION)){
            ILoginService iLoginService =ARouter.getInstance().navigation(ILoginService.class);
            if (iLoginService.isLogin()){
                // 处理完成，交还控制权
                callback.onContinue(postcard);
            }else {
             ARouter.getInstance().build(RouterActivityPath.User.PAGER_LOGIN).greenChannel().navigation(context);
              callback.onInterrupt(null);
            }
        }else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
      this.context = context;
        Logger.d("登录拦截器被初始化了");
    }
}
