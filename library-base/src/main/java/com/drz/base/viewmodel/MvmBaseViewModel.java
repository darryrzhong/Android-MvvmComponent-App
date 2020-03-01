package com.drz.base.viewmodel;

import androidx.lifecycle.ViewModel;

import com.drz.base.model.SuperBaseModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 应用模块: viewModel
 * <p>
 * 类描述: 管理 v M
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-01-27
 */
public  abstract class MvmBaseViewModel<V, M extends SuperBaseModel> extends ViewModel
    implements IMvvmBaseViewModel<V>
{
    
    private Reference<V> mUiRef;
    
    protected M model;
    
    @Override
    public void attachUi(V view)
    {
        mUiRef = new WeakReference<>(view);
    }
    
    @Override
    public V getPageView()
    {
        if (null == mUiRef)
        {
            return null;
        }
        if (null != mUiRef.get())
        {
            return mUiRef.get();
        }
        return null;
    }
    
    @Override
    public boolean isUiAttach()
    {
        return null != mUiRef && null != mUiRef.get();
    }
    
    @Override
    public void detachUi()
    {
        if (null != mUiRef)
        {
            mUiRef.clear();
            mUiRef = null;
        }
        if (null != model)
        {
            model.cancel();
        }
    }


    protected void loadData(){

    }

    protected  abstract void initModel();
}
