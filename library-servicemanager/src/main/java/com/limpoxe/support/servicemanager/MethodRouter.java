package com.limpoxe.support.servicemanager;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.limpoxe.support.servicemanager.compat.BundleCompat;
import com.limpoxe.support.servicemanager.compat.ContentProviderCompat;
import com.limpoxe.support.servicemanager.local.ServicePool;
import com.limpoxe.support.servicemanager.util.ParamUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by cailiming on 16/6/4.
 */
public class MethodRouter {

    public static Object routerToBinder(String desciptpr, IBinder iBinder, Bundle argsBundle) {
        try {
            Bundle bundle = transact(desciptpr, iBinder, argsBundle);
            return ParamUtil.getResult(bundle);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object routerToProvider(String name, Bundle argsBundle) {
        Bundle bundle = ContentProviderCompat.call(ServiceProvider.buildUri(), ServiceProvider.CALL_SERVICE, name, argsBundle);
        return ParamUtil.getResult(bundle);
    }

    public static Bundle routerToInstance(Bundle extras) {

        String name = extras.getString(ParamUtil.service_name);
        String methodName = extras.getString(ParamUtil.method_name);;

        Object service = ServicePool.getService(name);
        Object result = null;
        if (service != null && !Proxy.isProxyClass(service.getClass())) {
            Method[] methods = service.getClass().getInterfaces()[0].getDeclaredMethods();
            if (methods != null) {
                for (Method m : methods) {
                    if (m.toGenericString().equals(methodName)) {
                        try {
                            if (!m.isAccessible()) {
                                m.setAccessible(true);
                            }
                            result = m.invoke(service, ParamUtil.unwrapperParams(extras));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }

        Bundle bundle = new Bundle();
        BundleCompat.putObject(bundle, ParamUtil.result, result);
        return bundle;
    }

    private static Bundle transact(String descriptor, IBinder remote, Bundle param) throws RemoteException {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        Bundle _result;
        try {
            _data.writeInterfaceToken(descriptor);
            if ((param != null)) {
                _data.writeInt(1);
                param.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            remote.transact(ProcessBinder.FIRST_CODE, _data, _reply, 0);
            _reply.readException();
            if ((0 != _reply.readInt())) {
                _result = Bundle.CREATOR.createFromParcel(_reply);
            } else {
                _result = null;
            }
        } finally {
            _reply.recycle();
            _data.recycle();
        }
        return _result;
    }
}
