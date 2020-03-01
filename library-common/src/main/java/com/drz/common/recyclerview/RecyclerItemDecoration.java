package com.drz.common.recyclerview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 应用模块:
 * <p>
 * 类描述: recyclerView item间距设置 & 分割线绘制 实现类
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-16
 */
public class RecyclerItemDecoration extends RecyclerView.ItemDecoration
{
    
    private int left;
    
    private int top;
    
    private int right;
    
    private int bottom;
    
    private int dividerColor;
    
    private int dividerHeight;
    
    private int dividerMarginHeight;
    
    private Paint mPaint;
    
    public RecyclerItemDecoration(int left, int top, int right, int bottom)
    {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
    
    public RecyclerItemDecoration(int left, int top, int right, int bottom,
                                  int dividerColor, int dividerHeight, int dividerMarginHeight)
    {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.dividerColor = dividerColor;
        this.dividerHeight = dividerHeight;
        this.dividerMarginHeight = dividerMarginHeight;
        mPaint = new Paint();
        mPaint.setColor(dividerColor);
    }
    
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
        @NonNull RecyclerView parent, @NonNull RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
        if (left == 0 && top == 0 && right == 0 && bottom == 0)
        {
            return;
        }
        
        outRect.set(left, top, right, bottom);
    }
    
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent,
        @NonNull RecyclerView.State state)
    {
        super.onDraw(c, parent, state);
        
    }
    
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent,
        @NonNull RecyclerView.State state)
    {
        super.onDrawOver(c, parent, state);
        if (mPaint == null)
        {
            return;
        }
        // 获取RecyclerView的child view的个数
        int childCount = parent.getChildCount();
        // 遍历每个Item，分别获取它们的位置信息，然后再绘制对应的分割线
        for (int i = 0; i < childCount; i++)
        {
            // 获取每个Item
            View child = parent.getChildAt(i);
            final int left = child.getLeft();
            // 需要加上 margin的高度
            final int top = child.getBottom() + dividerMarginHeight;
            final int right = child.getRight();
            final int bottom = top + dividerHeight;
            // 绘制分割线
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }
}
