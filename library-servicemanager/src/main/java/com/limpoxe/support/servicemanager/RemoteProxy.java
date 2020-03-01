package com.limpoxe.support.servicemanager;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.limpoxe.support.servicemanager.compat.BundleCompat;
import com.limpoxe.support.servicemanager.compat.ContentProviderCompat;
import com.limpoxe.support.servicemanager.util.ParamUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by cailiming on 16/1/1.
 */
public class RemoteProxy {

    public static Object getProxyService(final String name, String iFaceClassName, ClassLoader classloader) {
        try {
            //classloader
            Class clientClass = classloader.loadClass(iFaceClassName);
            return Proxy.newProxyInstance(classloader, new Class[]{clientClass},
                    new InvocationHandler() {

                        Boolean isInProviderProcess;

                        String desciptpr;
                        IBinder iBinder;

                        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                            Bundle argsBundle = ParamUtil.wrapperParams(name, method.toGenericString(), args);

                            if (isInProviderProcess == null) {

                                prepare(argsBundle);
                            }

                            if (Boolean.TRUE.equals(isInProviderProcess)) {

                                return MethodRouter.routerToProvider(name, argsBundle);

                            } else if (desciptpr != null && iBinder != null) {

                                return MethodRouter.routerToBinder(desciptpr, iBinder, argsBundle);
                            } else {
                                //服务所在进程已死,重启服务进程可自动恢复
                                Log.w("RemoteProxy", "not active，service May Died！");
                            }

                            if (!method.getReturnType().isPrimitive()) {
                                //是包装类，返回null
                                return null;
                            } else {
                                //不是包装类，默认返回值没法给，throws RemoteExecption
                                throw new IllegalStateException("Service not active! Remote process may died");
                            }
                        }

                        private void prepare(Bundle argsBundle) throws Throwable {
                            Bundle queryResult = ContentProviderCompat.call(ServiceProvider.buildUri(),
                                    ServiceProvider.QUERY_SERVICE, name, argsBundle);
                            if (queryResult != null) {
                                isInProviderProcess = queryResult.getBoolean(ServiceProvider.QUERY_SERVICE_RESULT_IS_IN_PROVIDIDER_PROCESS, false);
                                iBinder = BundleCompat.getBinder(queryResult, ServiceProvider.QUERY_SERVICE_RESULT_BINDER);
                                desciptpr = queryResult.getString(ServiceProvider.QUERY_SERVICE_RESULT_DESCRIPTOR);

                                if (iBinder != null) {
                                    iBinder.linkToDeath(new IBinder.DeathRecipient() {
                                        @Override
                                        public void binderDied() {
                                            isInProviderProcess = null;
                                            iBinder = null;
                                            desciptpr = null;
                                        }
                                    }, 0);
                                }
                            }
                        }

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
