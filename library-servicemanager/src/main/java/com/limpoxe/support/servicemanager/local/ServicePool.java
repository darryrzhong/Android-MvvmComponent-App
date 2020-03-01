package com.limpoxe.support.servicemanager.local;

import android.os.Process;
import android.util.Log;

import java.util.Hashtable;

/**
 * Created by cailiming on 16/1/1.
 */
public class ServicePool {

    private static final String TAG = ServicePool.class.getSimpleName();

    private static final Hashtable<String, ServiceFetcher> SYSTEM_SERVICE_MAP =
            new Hashtable<String, ServiceFetcher>();

    private ServicePool() {
    }

    public static synchronized void registerClass(final String name, final ClassProvider provider) {
        Log.d(TAG, "registerClass service " + name);
        if (!SYSTEM_SERVICE_MAP.containsKey(name)) {
            ServiceFetcher fetcher = new ServiceFetcher() {
                @Override
                public Object createService(int serviceId) {

                    Object object = provider.getServiceInstance();
                    mGroupId = String.valueOf(Process.myPid());

                    Log.d(TAG, "create service instance @ pid " + Process.myPid());
                    return object;
                }
            };
            fetcher.mServiceId ++;
            SYSTEM_SERVICE_MAP.put(name, fetcher);
        }
    }

    public static synchronized void registerInstance(final String name, final Object service) {
        Log.d(TAG, "registerInstance service " + name + " @ " + service);

        Class[] faces = service.getClass().getInterfaces();
        if (faces == null || faces.length == 0) {
            return;
        }
        if (!SYSTEM_SERVICE_MAP.containsKey(name)) {
            ServiceFetcher fetcher = new ServiceFetcher() {
                @Override
                public Object createService(int serviceId) {

                    Object object = service;
                    mGroupId = String.valueOf(Process.myPid());

                    Log.d(TAG, "create service instance @ pid " + Process.myPid());

                    return object;
                }
            };
            fetcher.mServiceId ++;
            SYSTEM_SERVICE_MAP.put(name, fetcher);
        }
    }


    public static Object getService(String name) {
        ServiceFetcher fetcher = SYSTEM_SERVICE_MAP.get(name);
        return fetcher == null ? null : fetcher.getService();
    }

    public static void unRegister(String name){
        Log.d(TAG, "unRegister service " + name);
        SYSTEM_SERVICE_MAP.remove(name);
    }

    public static abstract class ClassProvider {

        public abstract Object getServiceInstance();

        public abstract String getInterfaceName();
    }
}
