package com.drz.more.message;

import com.drz.base.model.BasePagingModel;
import com.drz.base.model.IPagingModelListener;
import com.drz.base.viewmodel.MvmBaseViewModel;
import com.drz.common.contract.BaseCustomViewModel;

import java.util.List;
/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public class MessageFragmentViewModel
    extends MvmBaseViewModel<IMessageView, MessageModel>
    implements IPagingModelListener<List<BaseCustomViewModel>>
{
    @Override
    public void onLoadFinish(BasePagingModel model,
                             List<BaseCustomViewModel> data, boolean isEmpty, boolean isFirstPage)
    {
        if (getPageView() != null)
        {
            if (isEmpty)
            {
                if (isFirstPage)
                {
                    getPageView().showEmpty();
                }
                else
                {
                    getPageView().onLoadMoreEmpty();
                }
            }
            else
            {
                getPageView().onDataLoaded(data, isFirstPage);
            }
        }
    }
    
    @Override
    public void onLoadFail(BasePagingModel model, String prompt,
        boolean isFirstPage)
    {
        if (getPageView() != null)
        {
            if (isFirstPage)
            {
                getPageView().showFailure(prompt);
            }
            else
            {
                getPageView().onLoadMoreFailure(prompt);
            }
        }
    }
    
    @Override
    protected void initModel()
    {
        model = new MessageModel();
        model.register(this);
        model.getCacheDataAndLoad();
    }
    
    @Override
    public void detachUi()
    {
        super.detachUi();
        if (model != null)
        {
            model.unRegister(this);
        }
    }
    
    public void tryRefresh()
    {
        model.refresh();
    }
    
    public void loadMore()
    {
        model.loadMore();
    }
    
}
