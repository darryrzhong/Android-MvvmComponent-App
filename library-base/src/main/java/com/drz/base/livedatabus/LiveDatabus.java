package com.drz.base.livedatabus;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用模块: liveData
 * <p>
 * 类描述: 基于liveData的事件总线 1.关联activity / fragment 的生命周期 自动识别活动状态触发更新 2.可以发送两种事件
 * 普通事件 & 粘性事件
 *
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-07
 */
public class LiveDatabus
{
    
    /**
     * 粘性事件集合
     */
    private final Map<String, MutableLiveData> stickyBus;
    
    /**
     * 普通事件结合
     */
    private final Map<String, BusMutableLiveData> bus;
    
    private LiveDatabus()
    {
        stickyBus = new HashMap<>();
        bus = new HashMap<>();
    }
    
    private static class singleHolder
    {
        private static final LiveDatabus SINGLE_BUS = new LiveDatabus();
    }

    public static LiveDatabus getInstance(){
        return singleHolder.SINGLE_BUS;
    }

    public MutableLiveData<Object> with(String key) {
        return with(key, Object.class);
    }

    public <T> MutableLiveData<T> with(String key, Class<T> type) {
        if (!bus.containsKey(key)) {
            bus.put(key, new BusMutableLiveData<T>());
        }
        return  bus.get(key);
    }

    public MutableLiveData<Object> withSticky(String key) {
        return withSticky(key, Object.class);
    }

    public <T> MutableLiveData<T> withSticky(String key, Class<T> type) {
        if (!stickyBus.containsKey(key)) {
            stickyBus.put(key, new MutableLiveData<T>());
        }
        return  stickyBus.get(key);
    }


}
