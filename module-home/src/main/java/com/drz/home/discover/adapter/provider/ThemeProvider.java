package com.drz.home.discover.adapter.provider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.common.router.RouterActivityPath;
import com.drz.home.R;
import com.drz.home.databinding.HomeItemBriefCardViewBinding;
import com.drz.home.discover.bean.viewmodel.BriefCardViewModel;

import androidx.databinding.DataBindingUtil;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-15
 */
public class ThemeProvider extends BaseItemProvider<BaseCustomViewModel>
{
    @Override
    public int getItemViewType()
    {
        return IDisCoverItemType.THEME_CARD_VIEW;
    }
    
    @Override
    public int getLayoutId()
    {
        return R.layout.home_item_brief_card_view;
    }
    
    @Override
    public void onViewHolderCreated(@NotNull BaseViewHolder viewHolder,
        int viewType)
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
        HomeItemBriefCardViewBinding binding = baseViewHolder.getBinding();
        if (binding != null)
        {
            binding.btnAction.setOnClickListener(v -> {
                ARouter.getInstance()
                    .build(RouterActivityPath.User.PAGER_ATTENTION)
                    .navigation();
            });
            binding.setViewModel((BriefCardViewModel)baseCustomViewModel);
            binding.executePendingBindings();
        }
    }
}
