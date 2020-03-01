package com.drz.community.attention.adapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.drz.base.livedatabus.LiveDatabus;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.common.contract.VideoHeaderBean;
import com.drz.common.router.RouterActivityPath;
import com.drz.common.utils.DateTimeUtils;
import com.drz.community.attention.bean.AttentionCardViewModel;
import com.drz.community.databinding.CommunityItemAttentionCardViewBinding;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.databinding.DataBindingUtil;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-19
 */
public class AttentionRecyclerAdapter
    extends BaseQuickAdapter<BaseCustomViewModel, BaseViewHolder>
{
    
    public AttentionRecyclerAdapter(int layoutResId)
    {
        super(layoutResId);
    }
    
    @Override
    protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder,
        int viewType)
    {
        CommunityItemAttentionCardViewBinding binding =
            DataBindingUtil.bind(viewHolder.itemView);
        
    }
    
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder,
        @Nullable BaseCustomViewModel baseCustomViewModel)
    {
        if (baseCustomViewModel == null)
        {
            return;
        }
        CommunityItemAttentionCardViewBinding binding =
            baseViewHolder.getBinding();
        if (binding != null)
        {
            AttentionCardViewModel cardViewModel =
                (AttentionCardViewModel)baseCustomViewModel;
            GSYVideoOptionBuilder gsyVideoOptionBuilder =
                new GSYVideoOptionBuilder();
            binding.videoItemPlayer.loadCoverImage(cardViewModel.coverUrl, 0);
            binding.videoItemPlayer.rvContent.setOnClickListener(v -> {
                VideoHeaderBean headerBean =
                    new VideoHeaderBean(cardViewModel.title,
                        cardViewModel.category, cardViewModel.description,
                        cardViewModel.collectionCount, cardViewModel.shareCount,
                        cardViewModel.avatarUrl, cardViewModel.issuerName,
                        cardViewModel.authorDescription, cardViewModel.playUrl,
                        cardViewModel.blurredUrl, cardViewModel.videoId);
                ARouter.getInstance()
                    .build(RouterActivityPath.Video.PAGER_VIDEO)
                    .withParcelable("videoInfo", headerBean)
                    .navigation();
            });
            gsyVideoOptionBuilder.setIsTouchWiget(false)
                .setUrl(cardViewModel.playUrl)
                .setVideoTitle(cardViewModel.title)
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag("2")
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(baseViewHolder.getAdapterPosition())
                .setVideoAllCallBack(new GSYSampleCallBack()
                {
                    @Override
                    public void onPrepared(String url, Object... objects)
                    {
                        super.onPrepared(url, objects);
                        if (!binding.videoItemPlayer.isIfCurrentIsFullscreen())
                        {
                            // 静音
                            GSYVideoManager.instance().setNeedMute(true);
                        }
                        
                    }
                    
                    @Override
                    public void onQuitFullscreen(String url, Object... objects)
                    {
                        super.onQuitFullscreen(url, objects);
                        // 全屏不静音
                        GSYVideoManager.instance().setNeedMute(true);
                    }
                    
                    @Override
                    public void onEnterFullscreen(String url, Object... objects)
                    {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        binding.videoItemPlayer.getCurrentPlayer()
                            .getTitleTextView()
                            .setText((String)objects[0]);
                    }
                })
                .build(binding.videoItemPlayer);
            
            // 增加title
            binding.videoItemPlayer.getTitleTextView().setVisibility(View.GONE);
            
            // 设置返回键
            binding.videoItemPlayer.getBackButton().setVisibility(View.GONE);
            
            binding.tvReleaseTime.setText(DateTimeUtils
                .getDate(String.valueOf(cardViewModel.releaseTime), "HH:mm"));
            binding.setViewModel(cardViewModel);
        }
    }
}
