package com.drz.more.themes;

import com.drz.base.fragment.MvvmLazyFragment;
import com.drz.more.R;
import com.drz.more.databinding.MoreFragmentThemesBinding;
import com.drz.more.themes.adapter.ThemesFragmentPageAdapter;
import com.drz.more.themes.bean.Tabs;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-22
 */
public class ThemesFragment extends MvvmLazyFragment<MoreFragmentThemesBinding, ThemeFragmentViewModel> implements IThemeView{

    private ThemesFragmentPageAdapter adapter;

    public static ThemesFragment newInstance(){
        return new ThemesFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.more_fragment_themes;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
    }

    private void initView() {
        viewDataBinding.vpContent.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(viewDataBinding.tabLayout));
        viewDataBinding.vpContent.setOffscreenPageLimit(1);
        viewDataBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewDataBinding.vpContent.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        adapter = new ThemesFragmentPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
        viewDataBinding.vpContent.setAdapter(adapter);
        viewModel.initModel();
    }


    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected ThemeFragmentViewModel getViewModel() {
        return ViewModelProviders.of(this).get(ThemeFragmentViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void onDataLoaded(ArrayList<Tabs> tabs) {
        adapter.setData(tabs);
        viewDataBinding.tabLayout.removeAllTabs();
        for (Tabs tabs1 : tabs){
            viewDataBinding.tabLayout.addTab(viewDataBinding.tabLayout.newTab().setText(tabs1.getName()));
        }
        viewDataBinding.tabLayout.scrollTo(0,0);
    }
}
