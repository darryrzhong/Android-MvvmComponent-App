package com.drz.community.attention;

import java.util.ArrayList;

import com.drz.base.fragment.MvvmLazyFragment;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.common.recyclerview.RecyclerItemDecoration;
import com.drz.common.utils.DensityUtils;
import com.drz.community.R;
import com.drz.community.attention.adapter.AttentionRecyclerAdapter;
import com.drz.community.databinding.CommunityFragmentAttentionBinding;
import com.drz.video.helper.ScrollCalculatorHelper;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-16
 */
public class AttentionFragment
    extends MvvmLazyFragment<CommunityFragmentAttentionBinding, AttentionViewModel>
    implements IAttentionView
{
    
    private AttentionRecyclerAdapter adapter;
    
    private ScrollCalculatorHelper scrollCalculatorHelper;
    
    private LinearLayoutManager linearLayoutManager;
    
    boolean mFull = false;
    
    public static AttentionFragment newInstance()
    {
        AttentionFragment fragment = new AttentionFragment();
        return fragment;
    }
    
    @Override
    public int getLayoutId()
    {
        return R.layout.community_fragment_attention;
    }
    
    @Override
    protected void onFragmentFirstVisible()
    {
        super.onFragmentFirstVisible();
        initView();
    }
    
    private void initView()
    {
        viewDataBinding.rvAttentionView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewDataBinding.rvAttentionView.setLayoutManager(linearLayoutManager);
        adapter =
            new AttentionRecyclerAdapter(R.layout.community_item_attention_card_view);
        int decoration = DensityUtils.dp2px(getContext(), 10);
        viewDataBinding.rvAttentionView.addItemDecoration(
            new RecyclerItemDecoration(decoration, 0, decoration, decoration));
        viewDataBinding.rvAttentionView.setAdapter(adapter);
        //上拉刷新 & 下拉加载
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
        // 限定范围为屏幕一半的上下偏移180
        int playTop = CommonUtil.getScreenHeight(getContext()) / 2
            - CommonUtil.dip2px(getContext(), 180);
        int playBottom = CommonUtil.getScreenHeight(getContext()) / 2
            + CommonUtil.dip2px(getContext(), 180);
        // 控制自动播放帮助类
        scrollCalculatorHelper = new ScrollCalculatorHelper(
            R.id.video_item_player, playTop, playBottom);
        
        // 滑动监听
        viewDataBinding.rvAttentionView
            .addOnScrollListener(new RecyclerView.OnScrollListener()
            {
                int firstVisibleItem, lastVisibleItem;
                
                @Override
                public void onScrollStateChanged(
                        @NonNull RecyclerView recyclerView, int newState)
                {
                    super.onScrollStateChanged(recyclerView, newState);
                    scrollCalculatorHelper.onScrollStateChanged(recyclerView,
                        newState);
                }
                
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView,
                    int dx, int dy)
                {
                    super.onScrolled(recyclerView, dx, dy);
                    firstVisibleItem =
                        linearLayoutManager.findFirstVisibleItemPosition();
                    lastVisibleItem =
                        linearLayoutManager.findLastVisibleItemPosition();
                    
                    // 这是滑动自动播放的代码
                    if (!mFull)
                    {
                        scrollCalculatorHelper.onScroll(recyclerView,
                            firstVisibleItem,
                            lastVisibleItem,
                            lastVisibleItem - firstVisibleItem);
                    }
                }
            });
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
    protected AttentionViewModel getViewModel()
    {
        return ViewModelProviders.of(this).get(AttentionViewModel.class);
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
        viewDataBinding.refreshLayout.finishLoadMoreWithNoMoreData();
    }
    
    @Override
    public void onDataLoadFinish(ArrayList<BaseCustomViewModel> viewModels,
        boolean isFirstPage)
    {
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
    protected void onFragmentResume() {
        super.onFragmentResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onFragmentPause() {
        super.onFragmentPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }
}
