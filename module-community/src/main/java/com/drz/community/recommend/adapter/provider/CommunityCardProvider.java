package com.drz.community.recommend.adapter.provider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.community.R;
import com.drz.community.databinding.CommunityItemCommunityViewBinding;
import com.drz.community.recommend.bean.viewmodel.CloumnsCardViewModel;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-17
 */
public class CommunityCardProvider extends BaseItemProvider<BaseCustomViewModel> {
    @Override
    public int getItemViewType() {
        return IRecommendItemType.COMMUNITY_CARD_VIEW;
    }

    @Override
    public void onViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.community_item_community_view;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, @Nullable BaseCustomViewModel baseCustomViewModel) {
          if (baseCustomViewModel == null){
              return;
          }
        CommunityItemCommunityViewBinding binding = baseViewHolder.getBinding();
          if (binding != null){
              ViewGroup.LayoutParams layoutParams = binding.ivCoverBg.getLayoutParams();
              CloumnsCardViewModel viewModel = (CloumnsCardViewModel) baseCustomViewModel;
              int itemWidth = ScreenUtils.getScreenWidth() / 2;
             float scale = (itemWidth+0f)/viewModel.imgWidth;
             layoutParams.height = (int) (viewModel.imgHeight*scale);
             binding.ivCoverBg.setLayoutParams(layoutParams);
              binding.setViewModel(viewModel);
              binding.executePendingBindings();
          }
    }
}
