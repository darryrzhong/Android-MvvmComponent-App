package com.limpoxe.support.servicemanager.local;

/**
 * Created by cailiming on 16/1/1.
 */
public abstract class ServiceFetcher {
    int mServiceId;
    String mGroupId;
    private Object mCachedInstance;

    public final Object getService() {
        synchronized (ServiceFetcher.this) {
            Object service = mCachedInstance;
            if (service != null) {
                return service;
            }
            return mCachedInstance = createService(mServiceId);
        }
    }

    public abstract Object createService(int serviceId);

}
