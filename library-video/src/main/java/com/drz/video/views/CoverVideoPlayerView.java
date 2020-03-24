package com.drz.video.views;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.drz.video.R;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.utils.NetInfoModule;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * 应用模块: video
 * <p>
 * 类描述: 自定义的带封面的video
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-20
 */
public class CoverVideoPlayerView extends StandardGSYVideoPlayer
{
    ImageView mCoverImage;
    
    String mCoverOriginUrl;
    
    public RelativeLayout rvContent;
    
    int mDefaultRes;
    
    public CoverVideoPlayerView(Context context, Boolean fullFlag)
    {
        super(context, fullFlag);
    }
    
    public CoverVideoPlayerView(Context context)
    {
        super(context);
    }
    
    public CoverVideoPlayerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    @Override
    public int getLayoutId()
    {
        return R.layout.video_layout_cover;
    }
    
    @Override
    protected void init(Context context)
    {
        
        super.init(context);
        mCoverImage = (ImageView)findViewById(R.id.thumbImage);
        rvContent = findViewById(R.id.surface_container);
        if (mThumbImageViewLayout != null
            && (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL
                || mCurrentState == CURRENT_STATE_ERROR))
        {
            mThumbImageViewLayout.setVisibility(VISIBLE);
        }
    }
    
    /**
     * 设置视频url
     */
    public void setVideoUrl(String url)
    {
        mUrl = url;
        mOriginUrl = url;
    }
    
    /**
     * 是否边缓存
     */
    public void setVideoCache(boolean cache)
    {
        mCache = cache;
    }
    
    /**
     * 设置title
     */
    public void setVideoTitle(String title)
    {
        mTitle = title;
    }
    
    /**
     * 保存播放状态
     */
    public CoverVideoPlayerView saveState()
    {
        CoverVideoPlayerView switchVideo =
            new CoverVideoPlayerView(getContext());
        cloneParams(this, switchVideo);
        return switchVideo;
    }
    
    public void cloneState(CoverVideoPlayerView coverVideoPlayerView)
    {
        cloneParams(coverVideoPlayerView, this);
    }
    
    /**
     * 加载封面
     * 
     * @param url url
     * @param res 默认封面
     */
    public void loadCoverImage(String url, int res)
    {
        mCoverOriginUrl = url;
        mDefaultRes = res;
        Glide.with(getContext().getApplicationContext())
            .load(url)
            .apply(RequestOptions.bitmapTransform(new RoundedCorners(40)))
            .into(mCoverImage);
    }
    
    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context,
        boolean actionBar, boolean statusBar)
    {
        GSYBaseVideoPlayer gsyBaseVideoPlayer =
            super.startWindowFullscreen(context, actionBar, statusBar);
        CoverVideoPlayerView sampleCoverVideo =
            (CoverVideoPlayerView)gsyBaseVideoPlayer;
        // sampleCoverVideo.loadCoverImage(mCoverOriginUrl, mDefaultRes);
        return gsyBaseVideoPlayer;
    }
    
    @Override
    public GSYBaseVideoPlayer showSmallVideo(Point size, boolean actionBar,
        boolean statusBar)
    {
        // 下面这里替换成你自己的强制转化
        CoverVideoPlayerView sampleCoverVideo =
            (CoverVideoPlayerView)super.showSmallVideo(size,
                actionBar,
                statusBar);
        sampleCoverVideo.mStartButton.setVisibility(GONE);
        sampleCoverVideo.mStartButton = null;
        return sampleCoverVideo;
    }
    
    @Override
    protected void cloneParams(GSYBaseVideoPlayer from, GSYBaseVideoPlayer to)
    {
        super.cloneParams(from, to);
        CoverVideoPlayerView sf = (CoverVideoPlayerView)from;
        CoverVideoPlayerView st = (CoverVideoPlayerView)to;
        st.mShowFullAnimation = sf.mShowFullAnimation;
    }
    
    /**
     * 退出window层播放全屏效果
     */
    @SuppressWarnings("ResourceType")
    @Override
    protected void clearFullscreenLayout()
    {
        if (!mFullAnimEnd)
        {
            return;
        }
        mIfCurrentIsFullscreen = false;
        int delay = 0;
        if (mOrientationUtils != null)
        {
            delay = mOrientationUtils.backToProtVideo();
            mOrientationUtils.setEnable(false);
            if (mOrientationUtils != null)
            {
                mOrientationUtils.releaseListener();
                mOrientationUtils = null;
            }
        }
        
        if (!mShowFullAnimation)
        {
            delay = 0;
        }
        
        final ViewGroup vp = (CommonUtil.scanForActivity(getContext()))
            .findViewById(Window.ID_ANDROID_CONTENT);
        final View oldF = vp.findViewById(getFullId());
        if (oldF != null)
        {
            // 此处fix bug#265，推出全屏的时候，虚拟按键问题
            CoverVideoPlayerView gsyVideoPlayer = (CoverVideoPlayerView)oldF;
            gsyVideoPlayer.mIfCurrentIsFullscreen = false;
        }
        
        if (delay == 0)
        {
            backToNormal();
        }
        else
        {
            postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    backToNormal();
                }
            }, delay);
        }
        
    }
    
    /******************* 下方两个重载方法，在播放开始前不屏蔽封面，不需要可屏蔽 ********************/
    @Override
    public void onSurfaceUpdated(Surface surface)
    {
        super.onSurfaceUpdated(surface);
        if (mThumbImageViewLayout != null
            && mThumbImageViewLayout.getVisibility() == VISIBLE)
        {
            mThumbImageViewLayout.setVisibility(INVISIBLE);
        }
    }
    
    @Override
    protected void setViewShowState(View view, int visibility)
    {
        if (view == mThumbImageViewLayout && visibility != VISIBLE)
        {
            return;
        }
        super.setViewShowState(view, visibility);
    }
    
    @Override
    public void onSurfaceAvailable(Surface surface)
    {
        super.onSurfaceAvailable(surface);
        if (GSYVideoType.getRenderType() != GSYVideoType.TEXTURE)
        {
            if (mThumbImageViewLayout != null
                && mThumbImageViewLayout.getVisibility() == VISIBLE)
            {
                mThumbImageViewLayout.setVisibility(INVISIBLE);
            }
        }
    }
    
    /******************* 下方重载方法，在播放开始不显示底部进度和按键，不需要可屏蔽 ********************/
    
    protected boolean byStartedClick;
    
    @Override
    protected void onClickUiToggle()
    {
        if (mIfCurrentIsFullscreen && mLockCurScreen && mNeedLockFull)
        {
            setViewShowState(mLockScreen, VISIBLE);
            return;
        }
        byStartedClick = true;
        super.onClickUiToggle();
        
    }
    
    @Override
    protected void changeUiToNormal()
    {
        super.changeUiToNormal();
        byStartedClick = false;
    }
    
    @Override
    protected void changeUiToPreparingShow()
    {
        super.changeUiToPreparingShow();
        Debuger.printfLog("Sample changeUiToPreparingShow");
        setViewShowState(mBottomContainer, INVISIBLE);
        setViewShowState(mStartButton, INVISIBLE);
    }
    
    @Override
    protected void changeUiToPlayingBufferingShow()
    {
        super.changeUiToPlayingBufferingShow();
        Debuger.printfLog("Sample changeUiToPlayingBufferingShow");
        if (!byStartedClick)
        {
            setViewShowState(mBottomContainer, INVISIBLE);
            setViewShowState(mStartButton, INVISIBLE);
        }
    }
    
    @Override
    protected void changeUiToPlayingShow()
    {
        super.changeUiToPlayingShow();
        Debuger.printfLog("Sample changeUiToPlayingShow");
        if (!byStartedClick)
        {
            setViewShowState(mBottomContainer, INVISIBLE);
            setViewShowState(mStartButton, INVISIBLE);
        }
    }
    
    @Override
    public void startAfterPrepared()
    {
        super.startAfterPrepared();
        Debuger.printfLog("Sample startAfterPrepared");
        setViewShowState(mBottomContainer, INVISIBLE);
        setViewShowState(mStartButton, INVISIBLE);
        setViewShowState(mBottomProgressBar, VISIBLE);
    }
    
    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {
        byStartedClick = true;
        super.onStartTrackingTouch(seekBar);
    }
    
    /**
     * 利用反射 解决gsy库中导致的内存泄漏
     */
    public void cancel()
    {
        mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
        try
        {
            if (mNetInfoModule != null){
                // 拿到NetInfoModule对象中 mConnectivityBroadcastReceiver字段.
                Field mConnectivityBroadcastReceiver = NetInfoModule.class
                        .getDeclaredField("mConnectivityBroadcastReceiver");
                // 由于是私有字段,所以需要调用setAccessible(true),否则会报错
                mConnectivityBroadcastReceiver.setAccessible(true);
                // 根据当前mNetInfoModule对象的 mConnectivityBroadcastReceiver字段值为null
                mConnectivityBroadcastReceiver.set(mNetInfoModule, null);
                Field mNetChangeListener =
                        NetInfoModule.class.getDeclaredField("mNetChangeListener");
                mNetChangeListener.setAccessible(true);
                mNetChangeListener.set(mNetInfoModule, null);
            }

            
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        mAudioManager = null;
        mContext = null;
    }
    
}
