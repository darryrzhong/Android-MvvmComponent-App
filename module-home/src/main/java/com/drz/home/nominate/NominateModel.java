package com.drz.home.nominate;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.drz.base.model.BasePagingModel;
import com.drz.base.utils.GsonUtils;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.home.nominate.bean.FollowCardBean;
import com.drz.home.nominate.bean.SquareCardCollectionBean;
import com.drz.home.nominate.bean.TextCardBean;
import com.drz.home.nominate.bean.VideoSmallCardBean;
import com.drz.home.nominate.bean.viewmodel.FollowCardViewModel;
import com.drz.home.nominate.bean.viewmodel.SingleTitleViewModel;
import com.drz.home.nominate.bean.viewmodel.TitleViewModel;
import com.drz.home.nominate.bean.viewmodel.VideoCardViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import android.text.TextUtils;
import android.util.Log;

import io.reactivex.disposables.Disposable;

/**
 * 应用模块: 首页
 * <p>
 * 类描述: 首页 业务逻辑 处理中心
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-10
 */
public class NominateModel<T> extends BasePagingModel<T>
{

    private Disposable disposable;
    private Disposable disposable1;

    @Override
    protected void load()
    {
        disposable = EasyHttp.get("/api/v5/index/tab/allRec")
            .cacheKey(getClass().getSimpleName())
            .execute(new SimpleCallBack<String>()
            {
                @Override
                public void onError(ApiException e)
                {
                    loadFail(e.getMessage(), isRefresh);
                }
                
                @Override
                public void onSuccess(String s)
                {
                    parseJson(s);
                }
            });
    }

    public void loadMore(String url){
        disposable1 = EasyHttp.get(url)
                 .cacheMode(CacheMode.NO_CACHE)
                 .execute(new SimpleCallBack<String>()
                 {
                     @Override
                     public void onError(ApiException e)
                     {
                         loadFail(e.getMessage(), isRefresh);
                     }

                     @Override
                     public void onSuccess(String s)
                     {
                         parseJson(s);
                         Log.d("NominateModel", s);
                     }
                 });
    }

    @Override
    public void cancel() {
        super.cancel();
        EasyHttp.cancelSubscription(disposable);
        EasyHttp.cancelSubscription(disposable1);
    }

    /**
     * 解析数据
     * 
     * @param s json字符串
     */
    private void parseJson(String s)
    {
        List<BaseCustomViewModel> viewModels = new ArrayList<>();
        try
        {
            JSONObject jsonObject = new JSONObject(s);
            nextPageUrl = jsonObject.optString("nextPageUrl", "");
            JSONArray itemList = jsonObject.optJSONArray("itemList");
            if (itemList != null)
            {
                for (int i = 0; i < itemList.length(); i++)
                {
                    JSONObject ccurrentObject = itemList.getJSONObject(i);
                    switch (ccurrentObject.optString("type"))
                    {
                        case "squareCardCollection":
                            SquareCardCollectionBean squareCardCollectionBean =
                                GsonUtils.fromLocalJson(
                                    ccurrentObject.toString(),
                                    SquareCardCollectionBean.class);
                            paresCollectionCard(viewModels,
                                squareCardCollectionBean);
                            break;
                        case "textCard":
                            TextCardBean textCardBean = GsonUtils.fromLocalJson(
                                ccurrentObject.toString(),
                                TextCardBean.class);
                            SingleTitleViewModel viewModel =
                                new SingleTitleViewModel();
                            viewModel.title = textCardBean.getData().getText();
                            viewModels.add(viewModel);
                            break;
                        case "videoSmallCard":
                            VideoSmallCardBean videoSmallCardBean = GsonUtils
                                .fromLocalJson(ccurrentObject.toString(),
                                    VideoSmallCardBean.class);
                            paresVideoCard(viewModels, videoSmallCardBean);
                            break;
                        case "followCard":
                            FollowCardBean followCardBean = GsonUtils
                                .fromLocalJson(ccurrentObject.toString(),
                                    FollowCardBean.class);
                            paresFollowCard(viewModels, followCardBean);
                            break;
                        default:
                            break;
                        
                    }
                }
                loadSuccess((T)viewModels, viewModels.size() == 0, isRefresh);
            }
            
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
    
    public void refresh()
    {
        isRefresh = true;
        load();
    }
    
    public void loadMore()
    {
        isRefresh = false;
        if (!TextUtils.isEmpty(nextPageUrl)){
            loadMore(nextPageUrl);
        }else {
            loadSuccess(null,true,isRefresh);
        }
    }
    
    private void paresCollectionCard(List<BaseCustomViewModel> viewModels,
        SquareCardCollectionBean squareCardCollectionBean)
    {
        TitleViewModel titleLeftAndRightViewModel = new TitleViewModel();
        titleLeftAndRightViewModel.title =
            squareCardCollectionBean.getData().getHeader().getTitle();
        titleLeftAndRightViewModel.actionTitle =
            squareCardCollectionBean.getData().getHeader().getRightText();
        viewModels.add(titleLeftAndRightViewModel);
        // 解析精选视频
        for (int i1 = 0; i1 < squareCardCollectionBean.getData()
            .getItemList()
            .size(); i1++)
        {
            paresFollowCard(viewModels,
                squareCardCollectionBean.getData().getItemList().get(i1));
        }
    }
    
    private void paresFollowCard(List<BaseCustomViewModel> viewModelList,
        FollowCardBean cardBean)
    {
        FollowCardViewModel followCardViewModel = new FollowCardViewModel();
        followCardViewModel.coverUrl =
            cardBean.getData().getContent().getData().getCover().getDetail();
        followCardViewModel.videoTime =
            cardBean.getData().getContent().getData().getDuration();
        followCardViewModel.authorUrl =
            cardBean.getData().getContent().getData().getAuthor().getIcon();
        followCardViewModel.description =
            cardBean.getData().getContent().getData().getAuthor().getName()
                + " / #"
                + cardBean.getData().getContent().getData().getCategory();
        followCardViewModel.title =
            cardBean.getData().getContent().getData().getTitle();
        followCardViewModel.nickName = cardBean.getData().getContent().getData().getAuthor().getName();
        followCardViewModel.video_description = cardBean.getData().getContent().getData().getDescription();
        followCardViewModel.userDescription = cardBean.getData().getContent().getData().getAuthor().getDescription();
        followCardViewModel.playerUrl = cardBean.getData().getContent().getData().getPlayUrl();
        followCardViewModel.blurredUrl = cardBean.getData().getContent().getData().getCover().getBlurred();
        followCardViewModel.videoId = cardBean.getData().getContent().getData().getId();
        viewModelList.add(followCardViewModel);
    }
    
    private void paresVideoCard(List<BaseCustomViewModel> viewModels,
        VideoSmallCardBean videoSmallCardBean)
    {
        VideoCardViewModel videoCardViewModel = new VideoCardViewModel();
        videoCardViewModel.coverUrl =
            videoSmallCardBean.getData().getCover().getDetail();
        videoCardViewModel.videoTime =
            videoSmallCardBean.getData().getDuration();
        videoCardViewModel.title = videoSmallCardBean.getData().getTitle();
        videoCardViewModel.description =
            videoSmallCardBean.getData().getAuthor().getName() + " / # "
                + videoSmallCardBean.getData().getCategory();
        videoCardViewModel.authorUrl = videoSmallCardBean.getData().getAuthor().getIcon();
        videoCardViewModel.userDescription = videoSmallCardBean.getData().getAuthor().getDescription();
        videoCardViewModel.nickName = videoSmallCardBean.getData().getAuthor().getName();
        videoCardViewModel.video_description = videoSmallCardBean.getData().getDescription();
        videoCardViewModel.playerUrl = videoSmallCardBean.getData().getPlayUrl();
        videoCardViewModel.blurredUrl = videoSmallCardBean.getData().getCover().getBlurred();
        videoCardViewModel.videoId = videoSmallCardBean.getData().getId();
        videoCardViewModel.collectionCount = videoSmallCardBean.getData().getConsumption().getCollectionCount();
        videoCardViewModel.shareCount = videoSmallCardBean.getData().getConsumption().getShareCount();
        viewModels.add(videoCardViewModel);
    }
    
}
