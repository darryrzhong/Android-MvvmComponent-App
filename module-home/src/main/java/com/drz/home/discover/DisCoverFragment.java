package com.drz.home.discover;

import java.util.ArrayList;

import com.drz.base.fragment.MvvmLazyFragment;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.home.R;
import com.drz.home.databinding.HomeFragmentFindMoreBinding;
import com.drz.home.discover.adapter.ProviderDisCoverAdapter;
import com.scwang.smart.refresh.header.ClassicsHeader;

import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 应用模块:
 * <p>
 * 类描述: 发现
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-09
 */
public class DisCoverFragment
    extends MvvmLazyFragment<HomeFragmentFindMoreBinding, DisCoverViewModel>
    implements IDisCoverView
{
    
    private ProviderDisCoverAdapter adapter;
    
    public static DisCoverFragment newInstance()
    {
        DisCoverFragment fragment = new DisCoverFragment();
        return fragment;
    }
    
    @Override
    protected void onFragmentFirstVisible()
    {
        super.onFragmentFirstVisible();
        initView();
    }
    
    private void initView()
    {
        // 确定Item的改变不会影响RecyclerView的宽高
        viewDataBinding.rvDiscoverView.setHasFixedSize(true);
        viewDataBinding.rvDiscoverView
            .setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProviderDisCoverAdapter();
        adapter.addFooterView(getFooterView());
        viewDataBinding.rvDiscoverView.setAdapter(adapter);
        viewDataBinding.refreshLayout
            .setRefreshHeader(new ClassicsHeader(getContext()));
        viewDataBinding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            viewModel.tryToRefresh();
        });
        setLoadSir(viewDataBinding.refreshLayout);
        showLoading();
        viewModel.initModel();
    }
    
    private View getFooterView()
    {
        return LayoutInflater.from(getContext())
            .inflate(R.layout.home_item_foote_view,
                viewDataBinding.rvDiscoverView,
                false);
    }
    
    @Override
    public int getLayoutId()
    {
        return R.layout.home_fragment_find_more;
    }
    
    @Override
    public int getBindingVariable()
    {
        return 0;
    }
    
    @Override
    protected DisCoverViewModel getViewModel()
    {
        return ViewModelProviders.of(this).get(DisCoverViewModel.class);
    }
    
    @Override
    protected void onRetryBtnClick()
    {
        
    }
    
    @Override
    public void onDataLoadFinish(ArrayList<BaseCustomViewModel> viewModels,
        boolean isEmpty)
    {
        if (isEmpty)
        {
            viewDataBinding.refreshLayout.finishRefresh(false);
        }
        else
        {
            viewDataBinding.refreshLayout.finishRefresh(true);
        }
        adapter.setNewData(viewModels);
        showContent();
    }
}
