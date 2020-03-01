package com.drz.community.recommend;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.drz.base.model.BasePagingModel;
import com.drz.base.utils.GsonUtils;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.community.recommend.bean.CommunityColumnsCard;
import com.drz.community.recommend.bean.HorizontalScrollCard;
import com.drz.community.recommend.bean.viewmodel.CloumnsCardViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import android.text.TextUtils;

import io.reactivex.disposables.Disposable;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-16
 */
public class RecommendModel<T> extends BasePagingModel<T>
{
    
    private Disposable disposable;
    
    private Disposable disposable1;
    
    @Override
    protected void load()
    {
        disposable =
            EasyHttp.get("http://baobab.kaiyanapp.com/api/v7/community/tab/rec")
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
    
    private void loadMore(String nextPageUrl)
    {
        disposable1 = EasyHttp.get(nextPageUrl)
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
                }
            });
    }
    
    @Override
    public void cancel()
    {
        super.cancel();
        EasyHttp.cancelSubscription(disposable);
        EasyHttp.cancelSubscription(disposable1);
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
    
    public void refresh()
    {
        isRefresh = true;
        load();
    }
    
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
                        case "horizontalScrollCard":
                            HorizontalScrollCard scrollCard = GsonUtils
                                .fromLocalJson(ccurrentObject.toString(),
                                    HorizontalScrollCard.class);
                            viewModels.add(scrollCard);
                            break;
                        case "communityColumnsCard":
                            CommunityColumnsCard communityColumnsCard =
                                GsonUtils.fromLocalJson(
                                    ccurrentObject.toString(),
                                    CommunityColumnsCard.class);
                            parseCard(viewModels, communityColumnsCard);
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
    
    private void parseCard(List<BaseCustomViewModel> viewModels,
        CommunityColumnsCard columnsCard)
    {
        CloumnsCardViewModel cardViewModel = new CloumnsCardViewModel();
        if (columnsCard != null)
        {
            cardViewModel.coverUrl = columnsCard.getData()
                .getContent()
                .getData()
                .getCover()
                .getFeed();
            cardViewModel.description =
                columnsCard.getData().getContent().getData().getDescription();
            cardViewModel.nickName = columnsCard.getData()
                .getContent()
                .getData()
                .getOwner()
                .getNickname();
            cardViewModel.avatarUrl = columnsCard.getData()
                .getContent()
                .getData()
                .getOwner()
                .getAvatar();
            cardViewModel.collectionCount = columnsCard.getData()
                .getContent()
                .getData()
                .getConsumption()
                .getCollectionCount();
            cardViewModel.imgWidth =
                columnsCard.getData().getContent().getData().getWidth();
            cardViewModel.imgHeight =
                columnsCard.getData().getContent().getData().getHeight();
            viewModels.add(cardViewModel);
        }
        
    }
    
}
