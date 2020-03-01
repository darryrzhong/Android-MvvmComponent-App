package com.drz.player.adapter;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.player.adapter.provider.IVideoItemType;
import com.drz.player.adapter.provider.NominateViewProvider;
import com.drz.player.adapter.provider.ReplyViewProvider;
import com.drz.player.adapter.provider.TitleViewProvider;
import com.drz.player.bean.viewmodel.ReplyViewModel;
import com.drz.player.bean.viewmodel.VideoCardViewModel;
import com.drz.player.bean.viewmodel.VideoTextViewModel;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-20
 */
public class ProviderVideoPagerAdapter
    extends BaseProviderMultiAdapter<BaseCustomViewModel>
{
    
    public ProviderVideoPagerAdapter()
    {
        super();
        addItemProvider(new TitleViewProvider());
        addItemProvider(new NominateViewProvider());
        addItemProvider(new ReplyViewProvider());
    }
    
    @Override
    protected int getItemType(@NotNull List<? extends BaseCustomViewModel> data,
        int position)
    {

        if (data.get(position) instanceof VideoTextViewModel)
        {

            return IVideoItemType.TITLE_VIEW;
        }
        else if (data.get(position) instanceof VideoCardViewModel)
        {

            return IVideoItemType.NOMINATE_VIEW;

        }
        else if (data.get(position) instanceof ReplyViewModel)
        {

            return IVideoItemType.REPLY_VIEW;
        }
        return 0;
    }
}
