package com.drz.more.topic;

import java.util.List;

import com.drz.base.activity.IBasePagingView;
import com.drz.common.contract.BaseCustomViewModel;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public interface ITopicView extends IBasePagingView {

    /**
     * @param data 数据
     * @param isFirstPage 是否是第一页
     * */
    void onDataLoaded(List<BaseCustomViewModel> data, boolean isFirstPage);
}
