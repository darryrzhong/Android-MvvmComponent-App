package com.limpoxe.support.servicemanager.compat;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.limpoxe.support.servicemanager.ServiceManager;
import com.limpoxe.support.servicemanager.util.RefIectUtil;

/**
 * Created by cailiming on 16/4/14.
 */
public class ContentProviderCompat {

    public static Bundle call(Uri uri, String method, String arg, Bundle extras) {

        ContentResolver resolver = ServiceManager.sApplication.getContentResolver();

        if (Build.VERSION.SDK_INT >= 11) {
            return resolver.call(uri, method, arg, extras);
        } else {
            ContentProviderClient client = resolver.acquireContentProviderClient(uri);
            if (client == null) {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
            try {
                Object mContentProvider = RefIectUtil.getFieldObject(client, ContentProviderClient.class, "mContentProvider");
                if (mContentProvider != null) {
                    //public Bundle call(String method, String request, Bundle args)
                    Object result = null;
                    try {
                        result = RefIectUtil.invokeMethod(mContentProvider, Class.forName("android.content.IContentProvider"), "call",
                                new Class[]{String.class, String.class, Bundle.class},
                                new Object[]{method, arg, extras});
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return  (Bundle) result;
                }

            } finally {
                client.release();
            }
            return null;
        }
    }
}
