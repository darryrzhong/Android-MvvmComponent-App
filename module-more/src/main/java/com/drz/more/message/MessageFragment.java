package com.drz.more.message;

import java.util.List;

import com.drz.base.fragment.MvvmLazyFragment;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.common.recyclerview.RecyclerItemDecoration;
import com.drz.common.utils.DensityUtils;
import com.drz.more.R;
import com.drz.more.databinding.MoreFragmentMessageBinding;
import com.drz.more.message.adapter.MessageAdapter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;

import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public class MessageFragment extends
    MvvmLazyFragment<MoreFragmentMessageBinding, MessageFragmentViewModel>
    implements IMessageView
{
    
    private MessageAdapter adapter;
    
    public static MessageFragment newInstance()
    {
        return new MessageFragment();
    }
    
    @Override
    public int getLayoutId()
    {
        return R.layout.more_fragment_message;
    }
    
    @Override
    protected void onFragmentFirstVisible()
    {
        super.onFragmentFirstVisible();
        initView();
    }
    
    private void initView()
    {
        int margin = DensityUtils.dp2px(getContext(), 10);
        viewDataBinding.rvMessageView.setHasFixedSize(true);
        viewDataBinding.rvMessageView.addItemDecoration(
            new RecyclerItemDecoration(margin, margin, margin, 0));
        viewDataBinding.rvMessageView
            .setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.refreshLayout
            .setRefreshHeader(new ClassicsHeader(getContext()));
        viewDataBinding.refreshLayout
            .setRefreshFooter(new ClassicsFooter(getContext()));
        viewDataBinding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            viewModel.tryRefresh();
        });
        viewDataBinding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            viewModel.loadMore();
        });
        adapter = new MessageAdapter();
        viewDataBinding.rvMessageView.setAdapter(adapter);
        setLoadSir(viewDataBinding.refreshLayout);
        showLoading();
        viewModel.initModel();
        
    }
    
    @Override
    public int getBindingVariable()
    {
        return 0;
    }
    
    @Override
    protected MessageFragmentViewModel getViewModel()
    {
        return ViewModelProviders.of(this).get(MessageFragmentViewModel.class);
    }
    
    private View getFooterView()
    {
        return LayoutInflater.from(getContext())
            .inflate(R.layout.more_item_foote_view,
                viewDataBinding.rvMessageView,
                false);
    }
    
    @Override
    protected void onRetryBtnClick()
    {
        
    }
    
    @Override
    public void onLoadMoreFailure(String message)
    {
        viewDataBinding.refreshLayout.finishLoadMore(false);
    }
    
    @Override
    public void onLoadMoreEmpty()
    {
        adapter.addFooterView(getFooterView());
        viewDataBinding.refreshLayout.finishLoadMoreWithNoMoreData();
    }
    
    @Override
    public void onDataLoaded(List<BaseCustomViewModel> data,
        boolean isFirstPage)
    {
        if (data == null)
        {
            return;
        }
        if (isFirstPage)
        {
            adapter.setNewData(data);
            showContent();
            viewDataBinding.refreshLayout.finishRefresh(true);
        }
        else
        {
            adapter.addData(data);
            showContent();
            viewDataBinding.refreshLayout.finishLoadMore(true);
        }
    }
}
