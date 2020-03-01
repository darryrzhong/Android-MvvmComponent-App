package com.drz.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.drz.base.fragment.MvvmBaseFragment;
import com.drz.base.viewmodel.IMvvmBaseViewModel;
import com.drz.common.router.RouterFragmentPath;
import com.drz.user.adapter.RecyclerAdapter;
import com.drz.user.databinding.UserFragmentLayoutBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-28
 */
@Route(path = RouterFragmentPath.User.PAGER_USER)
public class UserFragment
    extends MvvmBaseFragment<UserFragmentLayoutBinding, IMvvmBaseViewModel>
{
    
    private RecyclerAdapter adapter;
    
    @Override
    public int getLayoutId()
    {
        return R.layout.user_fragment_layout;
    }
    
    @Override
    public void onViewCreated(@NonNull View view,
        @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }
    
    private void initData()
    {
        List<String> items = new ArrayList<>();
        items.add("我的关注");
        items.add("我的收藏");
        items.add("视频功能声明");
        items.add("用户协议");
        items.add("版权声明");
        items.add("关于作者");
        adapter.setNewData(items);
    }

    private void start(Context context){
        startActivity(new Intent(context,LoginActivity.class));
    }
    
    private void initView()
    {
        Glide.with(getContext()).load(getContext().getDrawable(R.drawable.avatar))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(viewDataBinding.ivAvatar);
        viewDataBinding.rvTables.setHasFixedSize(true);
        viewDataBinding.rvTables
            .setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerAdapter();
        adapter.setFooterView(getFooterView());
        viewDataBinding.rvTables.setAdapter(adapter);
        viewDataBinding.ivMore.setOnClickListener(v -> {start(getContext());});
        viewDataBinding.tvLike.setOnClickListener(v -> {start(getContext());});
        viewDataBinding.tvReply.setOnClickListener(v -> {start(getContext());});
    }

    private View getFooterView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.user_item_footer_view,viewDataBinding.rvTables,false);
    }

    @Override
    public int getBindingVariable()
    {
        return 0;
    }
    
    @Override
    protected IMvvmBaseViewModel getViewModel()
    {
        return null;
    }
    
    @Override
    protected void onRetryBtnClick()
    {
        
    }
}
