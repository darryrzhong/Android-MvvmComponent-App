package com.drz.more.topic;

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
public class TopicFragmentViewModel
    extends MvmBaseViewModel<ITopicView, TopicModel>
    implements IPagingModelListener<List<BaseCustomViewModel>>
{
    @Override
    protected void initModel()
    {
        model = new TopicModel();
        model.register(this);
        model.getCacheDataAndLoad();
    }
    
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
    public void detachUi()
    {
        super.detachUi();
        if (model != null)
        {
            model.unRegister(this);
        }
    }

    public void tryRefresh() {
        model.refresh();
    }

    public void loadMore() {
        model.loadMore();
    }
}
