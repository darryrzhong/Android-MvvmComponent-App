package com.drz.video.helper;

import com.drz.video.views.CoverVideoPlayerView;
import com.shuyu.gsyvideoplayer.listener.GSYMediaPlayerListener;

import android.view.View;

/**
 * 应用模块:
 * <p>
 * 类描述: 播放帮助类
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-21
 */
public class VideoPlayerHelper {

    private static CoverVideoPlayerView mVideoView;
    private static GSYMediaPlayerListener sMediaPlayerListener;

    /**
     * 播放前初始化配置
     * */
    public static void optionPlayer(final CoverVideoPlayerView gsyVideoPlayer, String url, boolean cache, String title) {
        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gsyVideoPlayer.startWindowFullscreen(gsyVideoPlayer.getContext(), false, true);
            }
        });
        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
        gsyVideoPlayer.setAutoFullWithSize(true);
        //音频焦点冲突时是否释放
        gsyVideoPlayer.setReleaseWhenLossAudio(true);
        //全屏动画
        gsyVideoPlayer.setShowFullAnimation(false);
        //小屏时不触摸滑动
        gsyVideoPlayer.setIsTouchWiget(false);

        gsyVideoPlayer.setVideoUrl(url);

        gsyVideoPlayer.setVideoCache(cache);

        gsyVideoPlayer.setVideoTitle(title);

    }

    public static void savePlayState(CoverVideoPlayerView videoPlayerView) {
        mVideoView = videoPlayerView.saveState();
        sMediaPlayerListener = videoPlayerView;
    }

    public static void clonePlayState(CoverVideoPlayerView videoPlayerView) {
        videoPlayerView.cloneState(mVideoView);
    }

    public static void release() {
        if (sMediaPlayerListener != null) {
            sMediaPlayerListener.onAutoCompletion();
        }
        mVideoView = null;
        sMediaPlayerListener = null;
    }
}
