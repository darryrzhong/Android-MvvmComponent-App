package com.drz.more.topic.adapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.more.databinding.MoreItemThemesViewBinding;
import com.drz.more.themes.childpager.bean.ThemesItemViewModel;

import androidx.databinding.DataBindingUtil;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public class TopicAdapter
    extends BaseQuickAdapter<BaseCustomViewModel, BaseViewHolder>
{
    
    public TopicAdapter(int layoutResId)
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
        @Nullable BaseCustomViewModel baseCustomViewModel)
    {
        if (baseCustomViewModel == null)
        {
            return;
        }
        MoreItemThemesViewBinding binding = baseViewHolder.getBinding();
        if (binding != null)
        {
            binding.setViewModel((ThemesItemViewModel)baseCustomViewModel);
            binding.executePendingBindings();
        }
    }
}
