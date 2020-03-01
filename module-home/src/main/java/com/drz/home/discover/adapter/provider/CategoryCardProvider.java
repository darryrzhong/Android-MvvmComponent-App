package com.drz.home.discover.adapter.provider;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.common.recyclerview.RecyclerItemDecoration;
import com.drz.common.utils.DensityUtils;
import com.drz.home.R;
import com.drz.home.databinding.HomeItemCategoryCardViewBinding;
import com.drz.home.discover.adapter.CategoryItemAdapter;
import com.drz.home.discover.bean.CategoryCardBean;
import com.drz.home.discover.bean.SquareCard;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-15
 */
public class CategoryCardProvider extends BaseItemProvider<BaseCustomViewModel>
{
    @Override
    public int getItemViewType()
    {
        return IDisCoverItemType.CATEGORY_CARD_VIEW;
    }
    
    @Override
    public int getLayoutId()
    {
        return R.layout.home_item_category_card_view;
    }
    
    @Override
    public void onViewHolderCreated(@NotNull BaseViewHolder viewHolder,
        int viewType)
    {
        HomeItemCategoryCardViewBinding binding =
            DataBindingUtil.bind(viewHolder.itemView);
        
        binding.rvCategoryView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager =
            new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvCategoryView.setLayoutManager(gridLayoutManager);
        binding.rvCategoryView.addItemDecoration(new RecyclerItemDecoration(0,
            0, DensityUtils.dip2px(getContext(), 5), 0));
        
    }
    
    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder,
        @Nullable BaseCustomViewModel baseCustomViewModel)
    {
        if (baseCustomViewModel == null)
        {
            return;
        }
        HomeItemCategoryCardViewBinding binding = baseViewHolder.getBinding();
        if (binding != null)
        {
            CategoryCardBean cardBean = (CategoryCardBean)baseCustomViewModel;
            binding.tvTitle.setText(cardBean.getData().getHeader().getTitle());
            binding.tvActionTitle
                .setText(cardBean.getData().getHeader().getRightText());
            ArrayList<SquareCard> dataList = new ArrayList<>();
            for (int i = 0; i < cardBean.getData().getItemList().size(); i++)
            {
                dataList.add(cardBean.getData().getItemList().get(i).getData());
            }
            CategoryItemAdapter adapter =
                new CategoryItemAdapter(R.layout.home_item_category_item_card_view);
            binding.rvCategoryView.setAdapter(adapter);
            adapter.setNewData(dataList);
        }
    }
}
