package com.drz.base.loadsir;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.drz.base.R;

/**
 * 应用模块:
 * <p>
 * 类描述: 骨架屏
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-01-27
 */
public class ShimmerCallback extends Callback
{
    @Override
    protected int onCreateView()
    {
        return R.layout.base_layout_placeholder;
    }
    
    @Override
    protected boolean onReloadEvent(Context context, View view)
    {
        return true;
    }
}
