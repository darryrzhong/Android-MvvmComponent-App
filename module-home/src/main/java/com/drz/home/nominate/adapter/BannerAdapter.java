package com.drz.home.nominate.adapter;

import java.util.ArrayList;

import androidx.databinding.BindingAdapter;

import com.drz.home.nominate.adapter.provider.NetBannerProvider;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.PageStyle;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-13
 */
public class BannerAdapter
{
    @BindingAdapter("initBannerView")
    public static void initBannerView(BannerViewPager bannerViewPager,
                                      ArrayList<String> list)
    {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add(
            "http://img.kaiyanapp.com/b5b00c67dfc759a02c8b457e104b3ec6.png?imageMogr2/quality/60/format/jpg");
        list1.add(
            "http://img.kaiyanapp.com/b5b00c67dfc759a02c8b457e104b3ec6.png?imageMogr2/quality/60/format/jpg");
        list1.add(
            "http://img.kaiyanapp.com/1eaf8827688ea3b910b7b6b6cb192a5f.png?imageMogr2/quality/60/format/jpg");
        list1.add(
            "http://img.kaiyanapp.com/1eaf8827688ea3b910b7b6b6cb192a5f.png?imageMogr2/quality/60/format/jpg");
        
        bannerViewPager.setHolderCreator(NetBannerProvider::new)
            .setPageStyle(PageStyle.MULTI_PAGE_OVERLAP)
            .create(list1);
    }
}
