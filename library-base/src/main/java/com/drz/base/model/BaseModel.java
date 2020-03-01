package com.drz.base.model;

import java.lang.ref.WeakReference;

/**
 * 应用模块: model
 * <p>
 * 类描述: 通用的基类model
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-01-27
 */
public abstract class BaseModel<T> extends SuperBaseModel<T>
{
    
    /**
     * 网络数据加载成功,通知所有注册者加载数据
     * 
     * @param data 数据bean
     */
    public void loadSuccess(T data)
    {
        synchronized (this)
        {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakReferenceDeque)
                {
                    if (weakListener.get() instanceof IModelListener)
                    {
                        IModelListener listenerItem =
                            (IModelListener)weakListener.get();
                        if (null != listenerItem)
                        {
                            listenerItem.onLoadFinish(BaseModel.this, data);
                        }
                    }
                }
//                if (null != getCachekey())
//                {
//                    saveDataToLocal(data);
//                }
            }, 0);
        }
    }
    
    /**
     * 加载数据失败,通知所有注册者
     */
    protected void loadFail(String prompt)
    {
        synchronized (this)
        {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakReferenceDeque)
                {
                    if (weakListener.get() instanceof IModelListener)
                    {
                        IModelListener listenerItem =
                            (IModelListener)weakListener.get();
                        if (null != listenerItem)
                        {
                            listenerItem.onLoadFail(BaseModel.this, prompt);
                        }
                    }
                }
            }, 0);
        }
    }
    
    @Override
    protected void notifyCacheData(T data)
    {
        loadSuccess(data);
    }
    
}
