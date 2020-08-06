package com.drz.home.daily;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.drz.base.model.BasePagingModel;
import com.drz.base.utils.GsonUtils;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.home.nominate.bean.FollowCardBean;
import com.drz.home.nominate.bean.TextCardBean;
import com.drz.home.nominate.bean.viewmodel.FollowCardViewModel;
import com.drz.home.nominate.bean.viewmodel.SingleTitleViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import android.text.TextUtils;
import android.util.Log;

import io.reactivex.disposables.Disposable;

/**
 * 应用模块: daily
 * <p>
 * 类描述: 数据处理层
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-14
 */
public class DailyModel<T> extends BasePagingModel<T>
{

    private Disposable disposable;

    @Override
    protected void load()
    {
        disposable = EasyHttp.get("/api/v5/index/tab/feed")
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

    @Override
    public void cancel() {
        super.cancel();
        EasyHttp.cancelSubscription(disposable);
    }

    public void loadMore(String nextPageUrl)
    {
        EasyHttp.get(nextPageUrl)
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
                    e("DailyModel", "onSuccess: " + s.replace("\r", "").replace("\n","").replace("\t", ""));
                    parseJson(s);
                }
            });
    }
    
    public void refresh()
    {
        isRefresh = true;
        load();
        
    }
    
    public void loadMore()
    {
        isRefresh = false;
        if (!TextUtils.isEmpty(nextPageUrl))
        {
            loadMore(nextPageUrl);
        }
        else
        {
            loadSuccess(null, true, isRefresh);
        }
    }
    
    /**
     * 解析json 数据
     * 
     * @param s json字符串
     */
    private void parseJson(String s)
    {
        List<BaseCustomViewModel> viewModels = new ArrayList<>();
        JSONObject jsonObject = null;
        try
        {
            jsonObject = new JSONObject(s);
            nextPageUrl = jsonObject.optString("nextPageUrl", "");
            JSONArray itemList = jsonObject.optJSONArray("itemList");
            if (itemList != null)
            {
                for (int i = 0; i < itemList.length(); i++)
                {
                    JSONObject ccurrentObject = itemList.getJSONObject(i);
                    switch (ccurrentObject.optString("type"))
                    {
                        case "textCard":
                            TextCardBean textCardBean = GsonUtils.fromLocalJson(
                                ccurrentObject.toString(),
                                TextCardBean.class);
                            if (textCardBean.getData().getText().equals("今日社区精选")){
                                break;
                            }
                            SingleTitleViewModel viewModel =
                                new SingleTitleViewModel();
                            viewModel.title = textCardBean.getData().getText();
                            viewModels.add(viewModel);
                            break;
                        case "followCard":
                            FollowCardBean followCardBean = GsonUtils
                                .fromLocalJson(ccurrentObject.toString(),
                                    FollowCardBean.class);
                            paresFollowCard(viewModels, followCardBean);
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
    
    private void paresFollowCard(List<BaseCustomViewModel> viewModels,
        FollowCardBean cardBean)
    {
        FollowCardViewModel followCardViewModel = new FollowCardViewModel();
        if (cardBean.getData() != null && cardBean.getData().getContent().getData() != null){
            FollowCardBean.DataBeanX.ContentBean.DataBean bean = cardBean.getData().getContent().getData();
            if (bean.getCover()!= null){
                followCardViewModel.coverUrl =
                        bean.getCover().getDetail();
                followCardViewModel.blurredUrl = bean.getCover().getBlurred();
            }

            followCardViewModel.videoTime =
                    cardBean.getData().getContent().getData().getDuration();

            if (bean.getAuthor() != null){
                followCardViewModel.authorUrl =
                        cardBean.getData().getContent().getData().getAuthor().getIcon();
                followCardViewModel.description =
                        cardBean.getData().getContent().getData().getAuthor().getName()
                                + " / #"
                                + cardBean.getData().getContent().getData().getCategory();
                followCardViewModel.userDescription = bean.getAuthor().getDescription();

            }

            followCardViewModel.title =
                    cardBean.getData().getContent().getData().getTitle();
            followCardViewModel.video_description = cardBean.getData().getContent().getData().getDescription();
            followCardViewModel.playerUrl = cardBean.getData().getContent().getData().getPlayUrl();
            followCardViewModel.videoId = cardBean.getData().getContent().getData().getId();
            viewModels.add(followCardViewModel);
        }
    }

    public static void e(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize ) {// 长度小于等于限制直接打印
            Log.e(tag, msg);
        }else {
            while (msg.length() > segmentSize ) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize );
                msg = msg.replace(logContent, "");
                Log.e(tag, logContent);
            }
            Log.e(tag, msg);// 打印剩余日志
        }
    }
    
}
