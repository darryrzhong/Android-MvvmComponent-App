package com.drz.community.attention;

import java.util.ArrayList;

import com.drz.base.model.BasePagingModel;
import com.drz.base.utils.GsonUtils;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.community.attention.bean.AttentionCardBean;
import com.drz.community.attention.bean.AttentionCardViewModel;
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
 * @since 2020-02-19
 */
public class AttentionModel<T> extends BasePagingModel<T> {

    private Disposable disposable;
    private Disposable disposable1;

    @Override
    protected void load() {
        disposable = EasyHttp.get("http://baobab.kaiyanapp.com/api/v6/community/tab/follow")
               .cacheKey(getClass().getSimpleName())
               .execute(new SimpleCallBack<String>() {
                   @Override
                   public void onError(ApiException e) {
                       loadFail(e.getMessage(),isRefresh);
                   }

                   @Override
                   public void onSuccess(String s) {
                       parseData(s);
                   }
               });
    }


    private void loadMore(String nextPageUrl) {
        disposable1 = EasyHttp.get(nextPageUrl)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        loadFail(e.getMessage(),isRefresh);
                    }

                    @Override
                    public void onSuccess(String s) {
                        parseData(s);
                    }

                });
    }

    private void parseData(String data) {

        AttentionCardBean attentionCardBean = GsonUtils.fromLocalJson(data,AttentionCardBean.class);
        ArrayList<BaseCustomViewModel> viewModels = new ArrayList<>();
        nextPageUrl = attentionCardBean.getNextPageUrl();
        for (int i=0;i<attentionCardBean.getItemList().size();i++){
            AttentionCardBean.ItemListBean itemListBean = attentionCardBean.getItemList().get(i);
            AttentionCardViewModel cardViewModel = new AttentionCardViewModel();
            cardViewModel.avatarUrl = itemListBean.getData().getHeader().getIcon();
            cardViewModel.issuerName = itemListBean.getData().getHeader().getIssuerName();
            cardViewModel.releaseTime = itemListBean.getData().getHeader().getTime();
            cardViewModel.title = itemListBean.getData().getContent().getData().getTitle();
            cardViewModel.description = itemListBean.getData().getContent().getData().getDescription();
            cardViewModel.coverUrl = itemListBean.getData().getContent().getData().getCover().getFeed();
            cardViewModel.playUrl = itemListBean.getData().getContent().getData().getPlayUrl();
            cardViewModel.collectionCount = itemListBean.getData().getContent().getData().getConsumption().getCollectionCount();
            cardViewModel.replyCount = itemListBean.getData().getContent().getData().getConsumption().getReplyCount();
            cardViewModel.realCollectionCount = itemListBean.getData().getContent().getData().getConsumption().getRealCollectionCount();
            cardViewModel.shareCount = itemListBean.getData().getContent().getData().getConsumption().getShareCount();
            cardViewModel.category = itemListBean.getData().getContent().getData().getCategory();
            cardViewModel.authorDescription = itemListBean.getData().getContent().getData().getAuthor().getDescription();
            cardViewModel.blurredUrl = itemListBean.getData().getContent().getData().getCover().getBlurred();
            cardViewModel.videoId = itemListBean.getData().getContent().getData().getId();
            viewModels.add(cardViewModel);
        }
        loadSuccess((T) viewModels,viewModels.size() == 0,isRefresh);
    }

    @Override
    public void cancel() {
        super.cancel();
        EasyHttp.cancelSubscription(disposable);
        EasyHttp.cancelSubscription(disposable1);
    }

    public void refresh(){
        isRefresh = true;
        load();
    }



    public void loadMore(){
        isRefresh = false;
        if (!TextUtils.isEmpty(nextPageUrl)){
            loadMore(nextPageUrl);
        }else {
            loadSuccess(null,true,isRefresh);
        }
    }


}

