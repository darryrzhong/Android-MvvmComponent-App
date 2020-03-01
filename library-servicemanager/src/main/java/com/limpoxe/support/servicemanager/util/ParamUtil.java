package com.limpoxe.support.servicemanager.util;

import android.os.Bundle;

import com.limpoxe.support.servicemanager.compat.BundleCompat;

/**
 * Created by cailiming on 16/6/3.
 */
public class ParamUtil {

    public static final String service_name = "service_name";
    public static final String method_name = "method_name";
    public static final String method_args_count = "method_args_count";
    public static final String result = "result";

    public static Bundle wrapperParams(String name, String methodName, Object[] args) {
        Bundle params = new Bundle();
        params.putString(service_name, name);
        params.putString(method_name, methodName);
        if (args != null && args.length >0) {
            params.putInt(method_args_count, args.length);
            for (int i = 0; i< args.length; i++) {
                BundleCompat.putObject(params, String.valueOf(i), args[i]);
            }
        }
        return params;
    }

    public static Object[] unwrapperParams(Bundle extras) {
        Object[] params = null;
        int maxKey = extras.getInt(method_args_count);
        if (maxKey > 0) {
            params = new Object[maxKey];
            for(int i = 0; i< maxKey; i++) {
                params[i] = extras.get(String.valueOf(i));
            }
        }
        return params;
    }

    public static Object getResult(Bundle bundle) {
        if (bundle != null) {
            return bundle.get(result);
        }
        return null;
    }

}
