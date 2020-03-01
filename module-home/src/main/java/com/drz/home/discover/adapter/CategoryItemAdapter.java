package com.drz.home.discover.adapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.drz.home.databinding.HomeItemCategoryItemCardViewBinding;
import com.drz.home.discover.bean.SquareCard;


import androidx.databinding.DataBindingUtil;

/**
 * 应用模块:
 * <p>
 * 类描述: 热门分类 adapter
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-15
 */
public class CategoryItemAdapter
    extends BaseQuickAdapter<SquareCard, BaseViewHolder>
{
    
    public CategoryItemAdapter(int layoutResId)
    {
        super(layoutResId);
    }
    
    @Override
    protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder,
        int viewType)
    {
        DataBindingUtil.bind(viewHolder.itemView);
    }
    
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder,
        @Nullable SquareCard squareCard)
    {
        if (squareCard == null)
        {
            return;
        }
        HomeItemCategoryItemCardViewBinding binding = baseViewHolder.getBinding();
        if (binding != null)
        {
            binding.setViewModel(squareCard);
            binding.executePendingBindings();
        }
    }
}
