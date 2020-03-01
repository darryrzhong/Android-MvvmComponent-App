package com.drz.player;

import java.util.ArrayList;

import com.drz.base.activity.IBaseView;
import com.drz.common.contract.BaseCustomViewModel;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-20
 */
public interface IVideoPlayerView extends IBaseView {
    /**
     * 数据加载完成
     *
     * @param viewModels data
     */
    void onDataLoadFinish(ArrayList<BaseCustomViewModel> viewModels);
}
