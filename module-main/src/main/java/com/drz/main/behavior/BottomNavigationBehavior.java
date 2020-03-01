
package com.drz.main.behavior;

import com.google.android.material.snackbar.Snackbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

/**
 *
 * @author Vishal Patolia
 * @date 18-Feb-18
 */

public final class BottomNavigationBehavior<V extends View>
    extends VerticalScrollingBehavior<V>
{
    private static final Interpolator INTERPOLATOR =
        new LinearOutSlowInInterpolator();
    
    private final BottomNavigationWithSnackbar mWithSnackBarImpl =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            ? new LollipopBottomNavWithSnackBarImpl()
            : new PreLollipopBottomNavWithSnackBarImpl();
    
    private boolean isTablet;
    
    private int mTabLayoutId;
    
    private boolean hidden = false;
    
    private ViewPropertyAnimatorCompat mOffsetValueAnimator;
    
    private ViewGroup mTabLayout;
    
    private View mTabsHolder;
    
    private int mSnackbarHeight = -1;
    
    private boolean scrollingEnabled = true;
    
    private boolean hideAlongSnackbar = false;
    
    int[] attrsArray = new int[] {android.R.attr.id};
    
    public BottomNavigationBehavior()
    {
        super();
    }
    
    public BottomNavigationBehavior(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, attrsArray);
        mTabLayoutId = a.getResourceId(0, View.NO_ID);
        a.recycle();
    }
    
    public static <V extends View> BottomNavigationBehavior<V> from(
        @NonNull V view)
    {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams))
        {
            throw new IllegalArgumentException(
                "The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior =
            ((CoordinatorLayout.LayoutParams)params).getBehavior();
        if (!(behavior instanceof BottomNavigationBehavior))
        {
            throw new IllegalArgumentException(
                "The view is not associated with BottomNavigationBehavior");
        }
        return (BottomNavigationBehavior<V>)behavior;
    }
    
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child,
        View dependency)
    {
        mWithSnackBarImpl.updateSnackbar(parent, dependency, child);
        return dependency instanceof Snackbar.SnackbarLayout;
    }
    
    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, V child,
        View dependency)
    {
        updateScrollingForSnackbar(dependency, child, true);
        super.onDependentViewRemoved(parent, child, dependency);
    }
    
    private void updateScrollingForSnackbar(View dependency, V child,
        boolean enabled)
    {
        if (!isTablet && dependency instanceof Snackbar.SnackbarLayout)
        {
            scrollingEnabled = enabled;
            if (!hideAlongSnackbar && ViewCompat.getTranslationY(child) != 0)
            {
                ViewCompat.setTranslationY(child, 0);
                hidden = false;
                hideAlongSnackbar = true;
            }
            else if (hideAlongSnackbar)
            {
                hidden = true;
                animateOffset(child, -child.getHeight());
            }
        }
    }
    
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child,
        View dependency)
    {
        updateScrollingForSnackbar(dependency, child, false);
        return super.onDependentViewChanged(parent, child, dependency);
    }
    
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child,
        int layoutDirection)
    {
        boolean layoutChild =
            super.onLayoutChild(parent, child, layoutDirection);
        if (mTabLayout == null && mTabLayoutId != View.NO_ID)
        {
            mTabLayout = findTabLayout(child);
            getTabsHolder();
        }
        
        return layoutChild;
    }
    
    @Nullable
    private ViewGroup findTabLayout(@NonNull View child)
    {
        if (mTabLayoutId == 0)
            return null;
        return (ViewGroup)child.findViewById(mTabLayoutId);
    }
    
    @Override
    public void onNestedVerticalOverScroll(CoordinatorLayout coordinatorLayout,
        V child, @ScrollDirection int direction, int currentOverScroll,
        int totalOverScroll)
    {
    }
    
    @Override
    public void onDirectionNestedPreScroll(CoordinatorLayout coordinatorLayout,
        V child, View target, int dx, int dy, int[] consumed,
        @ScrollDirection int scrollDirection)
    {
        handleDirection(child, scrollDirection);
    }
    
    private void handleDirection(V child, @ScrollDirection int scrollDirection)
    {
        if (!scrollingEnabled)
            return;
        if (scrollDirection == ScrollDirection.SCROLL_DIRECTION_DOWN && hidden)
        {
            hidden = false;
            animateOffset(child, 0);
        }
        else if (scrollDirection == ScrollDirection.SCROLL_DIRECTION_UP
            && !hidden)
        {
            hidden = true;
            animateOffset(child, child.getHeight());
        }
    }
    
    @Override
    protected boolean onNestedDirectionFling(
        CoordinatorLayout coordinatorLayout, V child, View target,
        float velocityX, float velocityY, @ScrollDirection int scrollDirection)
    {
        handleDirection(child, scrollDirection);
        return true;
    }
    
    private void animateOffset(final V child, final int offset)
    {
        ensureOrCancelAnimator(child);
        mOffsetValueAnimator.translationY(offset).start();
        animateTabsHolder(offset);
    }
    
    private void animateTabsHolder(int offset)
    {
        if (mTabsHolder != null)
        {
            offset = offset > 0 ? 0 : 1;
            ViewCompat.animate(mTabsHolder)
                .alpha(offset)
                .setDuration(200)
                .start();
        }
    }
    
    private void ensureOrCancelAnimator(V child)
    {
        if (mOffsetValueAnimator == null)
        {
            mOffsetValueAnimator = ViewCompat.animate(child);
            mOffsetValueAnimator.setDuration(100);
            mOffsetValueAnimator.setInterpolator(INTERPOLATOR);
        }
        else
        {
            mOffsetValueAnimator.cancel();
        }
    }
    
    private void getTabsHolder()
    {
        if (mTabLayout != null)
        {
            mTabsHolder = mTabLayout.getChildAt(0);
        }
    }
    
    public boolean isScrollingEnabled()
    {
        return scrollingEnabled;
    }
    
    public void setScrollingEnabled(boolean scrollingEnabled)
    {
        this.scrollingEnabled = scrollingEnabled;
    }
    
    public void setHidden(V view, boolean bottomLayoutHidden)
    {
        if (!bottomLayoutHidden && hidden)
        {
            animateOffset(view, 0);
        }
        else if (bottomLayoutHidden && !hidden)
        {
            animateOffset(view, -view.getHeight());
        }
        hidden = bottomLayoutHidden;
    }
    
    private interface BottomNavigationWithSnackbar
    {
        void updateSnackbar(CoordinatorLayout parent, View dependency,
                            View child);
    }
    
    private class PreLollipopBottomNavWithSnackBarImpl
        implements BottomNavigationWithSnackbar
    {
        
        @Override
        public void updateSnackbar(CoordinatorLayout parent, View dependency,
            View child)
        {
            if (!isTablet && dependency instanceof Snackbar.SnackbarLayout)
            {
                if (mSnackbarHeight == -1)
                {
                    mSnackbarHeight = dependency.getHeight();
                }
                
                int targetPadding = child.getMeasuredHeight();
                
                int shadow = (int)ViewCompat.getElevation(child);
                ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams)dependency.getLayoutParams();
                layoutParams.bottomMargin = targetPadding - shadow;
                child.bringToFront();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
                {
                    child.getParent().requestLayout();
                    ((View)child.getParent()).invalidate();
                }
                
            }
        }
    }
    
    private class LollipopBottomNavWithSnackBarImpl
        implements BottomNavigationWithSnackbar
    {
        
        @Override
        public void updateSnackbar(CoordinatorLayout parent, View dependency,
            View child)
        {
            if (!isTablet && dependency instanceof Snackbar.SnackbarLayout)
            {
                if (mSnackbarHeight == -1)
                {
                    mSnackbarHeight = dependency.getHeight();
                }
                int targetPadding =
                    (mSnackbarHeight + child.getMeasuredHeight());
                dependency.setPadding(dependency.getPaddingLeft(),
                    dependency.getPaddingTop(),
                    dependency.getPaddingRight(),
                    targetPadding);
            }
        }
    }
}