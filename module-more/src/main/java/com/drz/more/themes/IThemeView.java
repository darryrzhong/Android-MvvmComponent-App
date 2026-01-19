package com.drz.more.themes;

import com.drz.base.activity.IBaseView;
import com.drz.more.themes.bean.Tabs;

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
public interface IThemeView extends IBaseView {

    /**
     * 数据加载完成
     *
     * @param tabs tabs
     *
     */
    void onDataLoaded(ArrayList<Tabs> tabs);
}
