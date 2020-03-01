package com.drz.base.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.drz.base.R;

/**
 * 应用模块:
 * <p>
 * 类描述: 错误页面
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-01-27
 */
public class ErrorCallback extends Callback
{
    @Override
    protected int onCreateView()
    {
        return R.layout.base_layout_error;
    }
}
