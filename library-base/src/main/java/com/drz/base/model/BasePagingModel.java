package com.drz.base.model;

import java.lang.ref.WeakReference;

/**
 * 应用模块: model
 * <p>
 * 类描述: 适用于需要加载分页的model
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-01-27
 */
public abstract class BasePagingModel<T> extends SuperBaseModel<T>
{
    /**
     * 是否还有下一页
     */
    protected boolean hasNextPage = false;
    
    /**
     * 下一页的url
     */
    protected String nextPageUrl = "";
    
    /**
     * 上拉刷新 true or 下拉加载 false
     */
    protected boolean isRefresh = true;
    
    /**
     * 数据加载成功
     * 
     * @param data 数据
     * @param isEmpty 数据是否为空
     * @param isFirstPage 当前是否是第一页
     */
    public void loadSuccess(T data, boolean isEmpty,boolean isFirstPage)
    {
        synchronized (this)
        {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakReferenceDeque)
                {
                    if (weakListener.get() instanceof IPagingModelListener)
                    {
                        IPagingModelListener listenerItem =
                            (IPagingModelListener)weakListener.get();
                        if (null != listenerItem)
                        {
                            listenerItem.onLoadFinish(BasePagingModel.this,
                                data,
                                isEmpty,isFirstPage);
                        }
                    }
                }
//                if (null != getCachekey() && isFirstPage)
//                {
//                    saveDataToLocal(data);
//                }
            }, 0);
        }
    }

    /**
     * @param prompt msg
     * @param isFirstPage 是否是第一页
     * */
    public void loadFail(String prompt,boolean isFirstPage)
    {
        synchronized (this)
        {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakReferenceDeque)
                {
                    if (weakListener.get() instanceof IPagingModelListener)
                    {
                        IPagingModelListener listenerItem =
                            (IPagingModelListener)weakListener.get();
                        if (null != listenerItem)
                        {
                            listenerItem.onLoadFail(BasePagingModel.this,
                                prompt,
                                isRefresh);
                        }
                    }
                }
            }, 0);
        }
    }
    
    @Override
    protected void notifyCacheData(T data)
    {
        loadSuccess(data, false,true);
    }
}
