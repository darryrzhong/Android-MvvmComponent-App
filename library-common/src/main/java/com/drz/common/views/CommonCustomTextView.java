package com.drz.common.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-26
 */
@SuppressLint("AppCompatCustomView")
public class CommonCustomTextView extends TextView
{
    public CommonCustomTextView(Context context)
    {
        super(context);
        init(context);
    }
    
    public CommonCustomTextView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }
    
    public CommonCustomTextView(Context context, @Nullable AttributeSet attrs,
        int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    public CommonCustomTextView(Context context, @Nullable AttributeSet attrs,
        int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    
    /**
     * 定制字体
     */
    private void init(Context context)
    {
        // 获取资源文件
        AssetManager assets = context.getAssets();
        Typeface font =
            Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf");
        setTypeface(font);
    }
}
