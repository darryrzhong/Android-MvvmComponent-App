package com.drz.community.recommend.adapter.provider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.common.recyclerview.RecyclerItemDecoration;
import com.drz.common.utils.DensityUtils;
import com.drz.community.R;
import com.drz.community.databinding.CommunityItemSquareCardViewBinding;
import com.drz.community.recommend.adapter.SquareCardAdapter;
import com.drz.community.recommend.bean.HorizontalScrollCard;

import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-17
 */
public class SquareCardProvider extends BaseItemProvider<BaseCustomViewModel> {
    @Override
    public int getItemViewType() {
        return IRecommendItemType.SQUARE_CARD_VIEW;
    }

    @Override
    public void onViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        CommunityItemSquareCardViewBinding binding = DataBindingUtil.bind(viewHolder.itemView);
        StaggeredGridLayoutManager.LayoutParams layoutParams =
                new StaggeredGridLayoutManager.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        DensityUtils.dp2px(getContext(),80));
        layoutParams.setFullSpan(true);
        viewHolder.itemView.setLayoutParams(layoutParams);
        binding.rvSquareView.setHasFixedSize(true);
       GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
       layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.rvSquareView.setLayoutManager(layoutManager);
        binding.rvSquareView.addItemDecoration(new RecyclerItemDecoration(0,0, DensityUtils.dp2px(getContext(),5),0));
    }

    @Override
    public int getLayoutId() {
        return R.layout.community_item_square_card_view;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, @Nullable BaseCustomViewModel baseCustomViewModel) {
          if (baseCustomViewModel == null){
              return;
          }
          CommunityItemSquareCardViewBinding binding = baseViewHolder.getBinding();
          if (binding != null){
              SquareCardAdapter adapter = new SquareCardAdapter(R.layout.community_item_square_item_card_view);
              binding.rvSquareView.setAdapter(adapter);
              HorizontalScrollCard scrollCard = (HorizontalScrollCard) baseCustomViewModel;
              adapter.setNewData(scrollCard.getData().getItemList());
          }
    }
}
