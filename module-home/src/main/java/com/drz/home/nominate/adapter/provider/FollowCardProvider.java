package com.drz.home.nominate.adapter.provider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.common.contract.VideoHeaderBean;
import com.drz.common.router.RouterActivityPath;
import com.drz.home.R;
import com.drz.home.databinding.HomeItemFollowCardViewBinding;
import com.drz.home.nominate.bean.viewmodel.FollowCardViewModel;

import androidx.databinding.DataBindingUtil;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-14
 */
public class FollowCardProvider extends BaseItemProvider<BaseCustomViewModel>
{
    
    @Override
    public int getItemViewType()
    {
        return NominateItemType.FOLLOW_CARD_VIEW;
    }
    
    @Override
    public int getLayoutId()
    {
        return R.layout.home_item_follow_card_view;
    }
    
    @Override
    public void onViewHolderCreated(BaseViewHolder viewHolder, int viewType)
    {
        DataBindingUtil.bind(viewHolder.itemView);
    }
    
    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder,
        @Nullable BaseCustomViewModel baseCustomViewModel)
    {
        if (baseCustomViewModel == null)
        {
            return;
        }
        
        HomeItemFollowCardViewBinding binding = baseViewHolder.getBinding();
        if (binding != null)
        {
            FollowCardViewModel cardViewModel =
                (FollowCardViewModel)baseCustomViewModel;
            binding.ivVideoCover.setOnClickListener(v -> {
                //TODO
                VideoHeaderBean headerBean = new VideoHeaderBean(
                    cardViewModel.title, cardViewModel.description,
                    cardViewModel.video_description,
                    cardViewModel.collectionCount, cardViewModel.shareCount,
                    cardViewModel.authorUrl, cardViewModel.nickName,
                    cardViewModel.userDescription, cardViewModel.playerUrl,
                    cardViewModel.blurredUrl, cardViewModel.videoId);
                ARouter.getInstance()
                        .build(RouterActivityPath.Video.PAGER_VIDEO)
                        .withParcelable("videoInfo", headerBean)
                        .navigation();
            });
            binding.setViewModel(cardViewModel);
            binding.executePendingBindings();
        }
    }
}
