package com.drz.player;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.drz.base.model.BaseModel;
import com.drz.base.utils.GsonUtils;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.player.bean.LeftAlignTextHeader;
import com.drz.player.bean.ReplyBean;
import com.drz.player.bean.TextCard;
import com.drz.player.bean.VideoSmallCard;
import com.drz.player.bean.viewmodel.ReplyViewModel;
import com.drz.player.bean.viewmodel.VideoCardViewModel;
import com.drz.player.bean.viewmodel.VideoTextViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-20
 */
public class VideoPlayerModel<T> extends BaseModel<T>
{
    /**
     * 相关推荐
     */
    public static final String NOMINATE_URL =
        "http://baobab.kaiyanapp.com/api/v4/video/related";
    
    /**
     * 热门评论
     */
    public static final String REPLY_URL =
        "http://baobab.kaiyanapp.com/api/v2/replies/video";
    
    public int videoId = 186856;
    
    @Override
    protected void load()
    {
        Observable<String> nominateObservable = EasyHttp.get(NOMINATE_URL)
            .params("id", String.valueOf(videoId))
            .cacheKey("nominate")
            .execute(String.class);
        Observable<String> replyObservable = EasyHttp.get(REPLY_URL)
            .params("videoId", String.valueOf(videoId))
            .cacheKey("reply")
            .execute(String.class);
        // 使用zip操作符 合并网络请求 统一处理结果
        Observable.zip(nominateObservable,
            replyObservable,
            new BiFunction<String, String, ArrayList<BaseCustomViewModel>>()
            {
                @Override
                public ArrayList<BaseCustomViewModel> apply(String s, String s2)
                    throws Exception
                {
                    return parseJson(s, s2);
                    
                }
            }).subscribe(new BaseSubscriber<ArrayList<BaseCustomViewModel>>()
            {
                @Override
                public void onError(ApiException e)
                {
                    loadFail(e.getMessage());
                }
                
                @Override
                public void onNext(ArrayList<BaseCustomViewModel> viewModels)
                {
                    loadSuccess((T)viewModels);
                }
            });
    }
    
    private ArrayList<BaseCustomViewModel> parseJson(String nominateData,
        String replyData)
    {
        ArrayList<BaseCustomViewModel> viewModels = new ArrayList<>();
        parseNominateData(viewModels, nominateData);
        parseReplyData(viewModels, replyData);
        return viewModels;
    }
    
    private void parseNominateData(ArrayList<BaseCustomViewModel> viewModels,
        String nominateData)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(nominateData);
            JSONArray itemList = jsonObject.optJSONArray("itemList");
            if (itemList != null)
            {
                for (int i = 0; i < itemList.length(); i++)
                {
                    JSONObject ccurrentObject = itemList.getJSONObject(i);
                    switch (ccurrentObject.optString("type"))
                    {
                        case "textCard":
                            TextCard textCard = GsonUtils.fromLocalJson(
                                ccurrentObject.toString(),
                                TextCard.class);
                            VideoTextViewModel textViewModel =
                                new VideoTextViewModel();
                            textViewModel.textTitle =
                                textCard.getData().getText();
                            viewModels.add(textViewModel);
                            break;
                        case "videoSmallCard":
                            VideoSmallCard videoSmallCard = GsonUtils
                                .fromLocalJson(ccurrentObject.toString(),
                                    VideoSmallCard.class);
                            paresVideoCard(viewModels, videoSmallCard);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    
    private void parseReplyData(ArrayList<BaseCustomViewModel> viewModels,
        String replyData)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(replyData);
            JSONArray itemList = jsonObject.optJSONArray("itemList");
            if (itemList != null)
            {
                for (int i = 0; i < itemList.length(); i++)
                {
                    JSONObject ccurrentObject = itemList.getJSONObject(i);
                    switch (ccurrentObject.optString("type"))
                    {
                        case "leftAlignTextHeader":
                            LeftAlignTextHeader alignTextHeader = GsonUtils
                                .fromLocalJson(ccurrentObject.toString(),
                                    LeftAlignTextHeader.class);
                            VideoTextViewModel textViewModel =
                                new VideoTextViewModel();
                            textViewModel.textTitle =
                                alignTextHeader.getData().getText();
                            viewModels.add(textViewModel);
                            break;
                        case "reply":
                            ReplyBean reply = GsonUtils.fromLocalJson(
                                ccurrentObject.toString(),
                                ReplyBean.class);
                            ReplyViewModel replyViewModel =
                                new ReplyViewModel();
                            if (reply != null)
                            {
                                replyViewModel.avatar =
                                    reply.getData().getUser().getAvatar();
                                replyViewModel.nickName =
                                    reply.getData().getUser().getNickname();
                                replyViewModel.replyMessage =
                                    reply.getData().getMessage();
                                replyViewModel.releaseTime =
                                    reply.getData().getUser().getReleaseDate();
                                replyViewModel.likeCount =
                                    reply.getData().getLikeCount();
                                viewModels.add(replyViewModel);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    
    private void paresVideoCard(ArrayList<BaseCustomViewModel> viewModels,
        VideoSmallCard videoSmallCard)
    {
        if (videoSmallCard == null)
        {
            return;
        }
        VideoCardViewModel videoCardViewModel = new VideoCardViewModel();
        videoCardViewModel.coverUrl =
            videoSmallCard.getData().getCover().getDetail();
        videoCardViewModel.videoTime = videoSmallCard.getData().getDuration();
        videoCardViewModel.title = videoSmallCard.getData().getTitle();
        videoCardViewModel.description =
            videoSmallCard.getData().getAuthor().getName() + " / # "
                + videoSmallCard.getData().getCategory();
        videoCardViewModel.authorUrl =
            videoSmallCard.getData().getAuthor().getIcon();
        videoCardViewModel.userDescription =
            videoSmallCard.getData().getAuthor().getDescription();
        videoCardViewModel.nickName =
            videoSmallCard.getData().getAuthor().getName();
        videoCardViewModel.video_description =
            videoSmallCard.getData().getDescription();
        videoCardViewModel.playerUrl = videoSmallCard.getData().getPlayUrl();
        videoCardViewModel.blurredUrl =
            videoSmallCard.getData().getCover().getBlurred();
        videoCardViewModel.videoId = videoSmallCard.getData().getId();
        videoCardViewModel.collectionCount =
            videoSmallCard.getData().getConsumption().getCollectionCount();
        videoCardViewModel.shareCount =
            videoSmallCard.getData().getConsumption().getShareCount();
        viewModels.add(videoCardViewModel);
    }
    
}
