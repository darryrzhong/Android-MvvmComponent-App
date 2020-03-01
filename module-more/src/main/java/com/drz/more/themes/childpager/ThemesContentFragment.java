package com.drz.more.themes.childpager;

import java.util.ArrayList;

import com.drz.base.fragment.MvvmLazyFragment;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.more.R;
import com.drz.more.databinding.MoreFragmentThemesContentBinding;
import com.drz.more.themes.childpager.adapter.ThemesContentAdapter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 应用模块:
 * <p>
 * 类描述: 主题内容 二级fragment
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public class ThemesContentFragment extends
        MvvmLazyFragment<MoreFragmentThemesContentBinding, ThemesContentViewModel>
    implements IThemeContentView
{
    
    private ThemesContentAdapter adapter;
    
    private String typeName = "";
    
    private String apiUrl = "";
    
    @Override
    public int getLayoutId()
    {
        return R.layout.more_fragment_themes_content;
    }
    
    public static ThemesContentFragment newInstance(String name, String url)
    {
        ThemesContentFragment fragment = new ThemesContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("url", url);
        fragment.setArguments(bundle);
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
        viewDataBinding.rvThemeView.setHasFixedSize(true);
        viewDataBinding.rvThemeView
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
        adapter = new ThemesContentAdapter(R.layout.more_item_themes_view);
        viewDataBinding.rvThemeView.setAdapter(adapter);
        setLoadSir(viewDataBinding.refreshLayout);
        showLoading();
        viewModel.initModel(typeName, apiUrl);
        
    }
    
    private View getFooterView()
    {
        return LayoutInflater.from(getContext())
            .inflate(R.layout.more_item_foote_view,
                viewDataBinding.rvThemeView,
                false);
    }
    
    @Override
    protected void initParameters()
    {
        if (getArguments() != null)
        {
            typeName = getArguments().getString("name");
            apiUrl = getArguments().getString("url");
        }
    }
    
    @Override
    public int getBindingVariable()
    {
        return 0;
    }
    
    @Override
    protected ThemesContentViewModel getViewModel()
    {
        return ViewModelProviders.of(this).get(ThemesContentViewModel.class);
    }
    
    @Override
    protected void onRetryBtnClick()
    {
        
    }
    
    @Override
    public void onDataLoaded(ArrayList<BaseCustomViewModel> viewModels,
        boolean isFirstPage)
    {
        if (viewModels == null)
        {
            return;
        }
        if (isFirstPage)
        {
            adapter.setNewData(viewModels);
            showContent();
            viewDataBinding.refreshLayout.finishRefresh(true);
        }
        else
        {
            adapter.addData(viewModels);
            showContent();
            viewDataBinding.refreshLayout.finishLoadMore(true);
        }
        
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
}
