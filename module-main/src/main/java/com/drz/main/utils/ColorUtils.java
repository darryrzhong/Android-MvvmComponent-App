package com.drz.main.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-26
 */
public class ColorUtils {
    public static int getColor(Context context,int colorId){
       return ContextCompat.getColor(context,colorId);
    }
}
