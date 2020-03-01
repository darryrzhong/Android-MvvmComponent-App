package com.drz.home.discover;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.drz.base.model.BaseModel;
import com.drz.base.utils.GsonUtils;
import com.drz.common.contract.BaseCustomViewModel;
import com.drz.home.discover.bean.BannerBean;
import com.drz.home.discover.bean.BriefCard;
import com.drz.home.discover.bean.CategoryCardBean;
import com.drz.home.discover.bean.SubjectCardBean;
import com.drz.home.discover.bean.TextCardbean;
import com.drz.home.discover.bean.TopBannerBean;
import com.drz.home.discover.bean.viewmodel.BriefCardViewModel;
import com.drz.home.discover.bean.viewmodel.ContentBannerViewModel;
import com.drz.home.discover.bean.viewmodel.TopBannerViewModel;
import com.drz.home.nominate.bean.VideoSmallCardBean;
import com.drz.home.nominate.bean.viewmodel.TitleViewModel;
import com.drz.home.nominate.bean.viewmodel.VideoCardViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import io.reactivex.disposables.Disposable;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-15
 */
public class DisCoverModel<T> extends BaseModel<T>
{
    public static final String DEFAULT_URL =
        "http://baobab.kaiyanapp.com/api/v7/index/tab/discovery?udid=fa53872206ed42e3857755c2756ab683fc22d64a&vc=591&vn=6.2.1&size=720X1280&deviceModel=Che1-CL20&first_channel=eyepetizer_zhihuiyun_market&last_channel=eyepetizer_zhihuiyun_market&system_version_code=19";
    private Disposable disposable;

    @Override
    protected void load()
    {
        disposable = EasyHttp.get(DEFAULT_URL)
              .cacheKey(getClass().getSimpleName())
              .execute(new SimpleCallBack<String>()
              {
                  @Override
                  public void onError(ApiException e)
                  {
                      loadFail(e.getMessage());
                  }

                  @Override
                  public void onSuccess(String s)
                  {
                      parseJson(s);
                  }
              });
    }
    
    private void parseJson(String s)
    {
        List<BaseCustomViewModel> viewModels = new ArrayList<>();
        try
        {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemList = jsonObject.optJSONArray("itemList");
            if (itemList != null)
            {
                for (int i = 0; i < itemList.length(); i++)
                {
                    JSONObject ccurrentObject = itemList.getJSONObject(i);
                    switch (ccurrentObject.optString("type"))
                    {
                        case "horizontalScrollCard":
                            TopBannerBean topBannerBean = GsonUtils.fromLocalJson(ccurrentObject.toString(),TopBannerBean.class);
                            TopBannerViewModel topBannerViewModel = new TopBannerViewModel();
                            topBannerViewModel.bannerUrl = topBannerBean.getData().getItemList().get(0).getData().getImage();
                            viewModels.add(topBannerViewModel);
                            break;
                        case "specialSquareCardCollection":
                            CategoryCardBean categoryCardBean = GsonUtils.fromLocalJson(ccurrentObject.toString(),CategoryCardBean.class);
                            viewModels.add(categoryCardBean);
                            break;
                        case "columnCardList":
                            SubjectCardBean subjectCardBean = GsonUtils.fromLocalJson(ccurrentObject.toString(),SubjectCardBean.class);
                            viewModels.add(subjectCardBean);
                            break;
                        case "textCard":
                            TextCardbean textCardbean = GsonUtils.fromLocalJson(ccurrentObject.toString(),TextCardbean.class);
                            TitleViewModel titleViewModel = new TitleViewModel();
                            titleViewModel.title = textCardbean.getData().getText();
                            titleViewModel.actionTitle = textCardbean.getData().getRightText();
                            viewModels.add(titleViewModel);
                            break;
                        case "banner":
                            BannerBean bannerBean = GsonUtils.fromLocalJson(ccurrentObject.toString(),BannerBean.class);
                            ContentBannerViewModel bannerViewModel = new ContentBannerViewModel();
                            bannerViewModel.bannerUrl = bannerBean.getData().getImage();
                            viewModels.add(bannerViewModel);
                            break;
                        case "videoSmallCard":
                            VideoSmallCardBean videoSmallCardBean = GsonUtils
                                    .fromLocalJson(ccurrentObject.toString(),
                                            VideoSmallCardBean.class);
                            paresVideoCard(viewModels, videoSmallCardBean);
                            break;
                        case "briefCard":
                            BriefCard briefCard = GsonUtils.fromLocalJson(ccurrentObject.toString(),BriefCard.class);
                            BriefCardViewModel briefCardViewModel = new BriefCardViewModel();
                            briefCardViewModel.coverUrl = briefCard.getData().getIcon();
                            briefCardViewModel.title = briefCard.getData().getTitle();
                            briefCardViewModel.description = briefCard.getData().getDescription();
                            viewModels.add(briefCardViewModel);
                            break;
                            default:
                                break;
                    }
                }
                loadSuccess((T) viewModels);
            }

            
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        
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

    @Override
    public void cancel() {
        super.cancel();
        EasyHttp.cancelSubscription(disposable);
    }
}
