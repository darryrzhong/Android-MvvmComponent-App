package com.drz.player;

import com.drz.base.model.BaseModel;
import com.drz.base.model.IModelListener;
import com.drz.base.viewmodel.MvmBaseViewModel;
import com.drz.common.contract.BaseCustomViewModel;

import java.util.ArrayList;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-20
 */
public class VideoPlayerViewModel
    extends MvmBaseViewModel<IVideoPlayerView, VideoPlayerModel>
    implements IModelListener<ArrayList<BaseCustomViewModel>>
{
    @Override
    protected void initModel()
    {
        model = new VideoPlayerModel();
        model.register(this);
    }

    public void loadData(int videoId){
        model.videoId = videoId;
        model.load();
    }
    
    @Override
    public void onLoadFinish(BaseModel model,
                             ArrayList<BaseCustomViewModel> data)
    {
        if (getPageView() != null) {
            if (data != null && data.size() > 0){
                getPageView().onDataLoadFinish(data);
            }else {
                getPageView().showEmpty();
            }
        }
    }
    
    @Override
    public void onLoadFail(BaseModel model, String prompt)
    {
        if (getPageView() != null){
            getPageView().showFailure(prompt);
        }
    }
    
    @Override
    public void detachUi()
    {
        super.detachUi();
        if (model != null)
        {
            model.unRegister(this);
        }
    }
    
}
