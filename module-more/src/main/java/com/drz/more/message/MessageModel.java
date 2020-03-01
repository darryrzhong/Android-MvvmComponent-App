package com.drz.more.message;

import java.util.ArrayList;
import java.util.List;

import com.drz.base.model.BasePagingModel;
import com.drz.base.utils.GsonUtils;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.common.utils.DateTimeUtils;
import com.drz.more.message.bean.Message;
import com.drz.more.message.bean.MessageViewModel;
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
 * @since 2020-02-23
 */
public class MessageModel<T> extends BasePagingModel<T>
{
    
    private Disposable disposable;
    
    private Disposable disposable1;
    
    @Override
    protected void load()
    {
        disposable = EasyHttp.get("/api/v3/messages")
            .params("vc", "591")
            .params("deviceModel", "Che1-CL20")
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
                    parseData(s);
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
                    parseData(s);
                }
            });
    }
    
    private void parseData(String s)
    {
        List<BaseCustomViewModel> viewModels = new ArrayList<>();
        Message message = GsonUtils.fromLocalJson(s,Message.class);
        if (message != null){
            nextPageUrl = message.getNextPageUrl();
            for (Message.MessageListBean itemBean : message.getMessageList()){
                MessageViewModel viewModel = new MessageViewModel();
                viewModel.coverUrl = itemBean.getIcon();
                viewModel.title = itemBean.getTitle();
                viewModel.comtent = itemBean.getContent();
                viewModel.messageDate = formatDate(itemBean.getDate());
                viewModels.add(viewModel);
            }
        }
        loadSuccess((T) viewModels,viewModels.size() == 0,isRefresh);
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
    
    @Override
    public void cancel()
    {
        super.cancel();
        EasyHttp.cancelSubscription(disposable);
        EasyHttp.cancelSubscription(disposable1);
    }

    /**
     * 根据时间戳转换成对应格式的时间
     * */
    private String formatDate(long date){
       return DateTimeUtils.getDate(String.valueOf(date),"HH");

    }
}
