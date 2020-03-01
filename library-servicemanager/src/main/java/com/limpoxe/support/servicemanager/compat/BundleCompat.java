package com.limpoxe.support.servicemanager.compat;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.ArrayMap;

import com.limpoxe.support.servicemanager.util.RefIectUtil;

import java.util.Map;

/**
 * Created by cailiming on 16/6/3.
 */
public class BundleCompat {

    public static IBinder getBinder(Bundle bundle, String key) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return bundle.getBinder(key);
        } else {
            return (IBinder) RefIectUtil.invokeMethod(bundle, Bundle.class, "getIBinder", new Class[]{String.class}, new Object[]{key});
        }
    }

    public static void putBinder(Bundle bundle, String key, IBinder iBinder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bundle.putBinder(key, iBinder);
        } else {
            RefIectUtil.invokeMethod(bundle, Bundle.class, "putIBinder", new Class[]{String.class, IBinder.class}, new Object[]{key, iBinder});
        }
    }

    public static void putObject(Bundle bundle, String key, Object value) {
        if (Build.VERSION.SDK_INT < 19) {

            RefIectUtil.invokeMethod(bundle, Bundle.class, "unparcel", (Class[])null, (Object[])null);
            Map<String, Object> mMap = (Map<String, Object>) RefIectUtil.getFieldObject(bundle, Bundle.class, "mMap");
            mMap.put(key, value);

        } else if (Build.VERSION.SDK_INT == 19) {

            RefIectUtil.invokeMethod(bundle, Bundle.class, "unparcel", (Class[])null, (Object[])null);
            ArrayMap<String, Object> mMap = (ArrayMap<String, Object>) RefIectUtil.getFieldObject(bundle, Bundle.class, "mMap");
            mMap.put(key, value);

        } else if(Build.VERSION.SDK_INT > 19) {

            RefIectUtil.invokeMethod(bundle, android.os.BaseBundle.class, "unparcel", (Class[])null, (Object[])null);
            ArrayMap<String, Object> mMap = (ArrayMap<String, Object>) RefIectUtil.getFieldObject(bundle, android.os.BaseBundle.class, "mMap");
            mMap.put(key, value);

        }

    }

}
