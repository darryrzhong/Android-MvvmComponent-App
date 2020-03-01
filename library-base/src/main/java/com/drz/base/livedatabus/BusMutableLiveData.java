package com.drz.base.livedatabus;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 应用模块: liveData
 * <p>
 * 类描述: 扩展liveData hook源码拦截实现非粘性事件
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-07
 */
public class BusMutableLiveData<T> extends MutableLiveData<T> {

    private Map<Observer,Observer> observerMap = new HashMap<>();
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, observer);
        try {
            hook(observer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void observeForever(@NonNull Observer<? super T> observer) {
        if (!observerMap.containsKey(observer)){
            observerMap.put(observer,new ObserverWrapper(observer));
        }
        super.observeForever(observerMap.get(observer));
    }

    @Override
    public void removeObserver(@NonNull Observer<? super T> observer) {
        Observer realObserver = null;
        if (observerMap.containsKey(observer)){
            realObserver = observerMap.remove(observer);
        }else {
            realObserver = observer;
        }
        super.removeObserver(realObserver);
    }

    /**
     * hook源码实现,拦截订阅之前的事件
     * @param observer observer
     * */
    private void hook(Observer<? super T> observer) throws Exception {
        //get wrapper's version
        Class<LiveData> classLiveData = LiveData.class;
        Field fieldObservers = classLiveData.getDeclaredField("mObservers");
        fieldObservers.setAccessible(true);
        Object objectObservers = fieldObservers.get(this);
        Class<?> classObservers = objectObservers.getClass();
        Method methodGet = classObservers.getDeclaredMethod("get", Object.class);
        methodGet.setAccessible(true);
        Object objectWrapperEntry = methodGet.invoke(objectObservers, observer);
        Object objectWrapper = null;
        if (objectWrapperEntry instanceof Map.Entry) {
            objectWrapper = ((Map.Entry) objectWrapperEntry).getValue();
        }
        if (objectWrapper == null) {
            throw new NullPointerException("Wrapper can not be bull!");
        }
        Class<?> classObserverWrapper = objectWrapper.getClass().getSuperclass();
        Field fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion");
        fieldLastVersion.setAccessible(true);
        //get livedata's version
        Field fieldVersion = classLiveData.getDeclaredField("mVersion");
        fieldVersion.setAccessible(true);
        Object objectVersion = fieldVersion.get(this);
        //set wrapper's version
        fieldLastVersion.set(objectWrapper, objectVersion);
    }
}
