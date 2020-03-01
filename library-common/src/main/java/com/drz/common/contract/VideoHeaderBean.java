package com.drz.common.contract;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 应用模块:
 * <p>
 * 类描述: 用于调用视频播放组件时传递信息的契约类
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-21
 */
public class VideoHeaderBean implements Parcelable
{
    public String videoTitle;
    
    public String category;
    
    public String video_description;
    
    // 点赞
    public int collectionCount;
    
    // 分享
    public int shareCount;
    
    public String avatar;
    
    public String nickName;
    
    public String userDescription;
    
    public String playerUrl;
    
    public String blurredUrl;
    
    public int videoId;

    public VideoHeaderBean() {

    }

    public VideoHeaderBean(String videoTitle, String category,
                           String video_description, int collectionCount, int shareCount,
                           String avatar, String nickName, String userDescription,
                           String playerUrl, String blurredUrl, int videoId)
    {
        this.videoTitle = videoTitle;
        this.category = category;
        this.video_description = video_description;
        this.collectionCount = collectionCount;
        this.shareCount = shareCount;
        this.avatar = avatar;
        this.nickName = nickName;
        this.userDescription = userDescription;
        this.playerUrl = playerUrl;
        this.blurredUrl = blurredUrl;
        this.videoId = videoId;
    }
    
    protected VideoHeaderBean(Parcel in)
    {
        videoTitle = in.readString();
        category = in.readString();
        video_description = in.readString();
        collectionCount = in.readInt();
        shareCount = in.readInt();
        avatar = in.readString();
        nickName = in.readString();
        userDescription = in.readString();
        playerUrl = in.readString();
        blurredUrl = in.readString();
        videoId = in.readInt();
        
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(videoTitle);
        dest.writeString(category);
        dest.writeString(video_description);
        dest.writeInt(collectionCount);
        dest.writeInt(shareCount);
        dest.writeString(avatar);
        dest.writeString(nickName);
        dest.writeString(userDescription);
        dest.writeString(playerUrl);
        dest.writeString(blurredUrl);
        dest.writeInt(videoId);
    }
    
    @Override
    public int describeContents()
    {
        return 0;
    }
    
    public static final Creator<VideoHeaderBean> CREATOR =
        new Creator<VideoHeaderBean>()
        {
            @Override
            public VideoHeaderBean createFromParcel(Parcel in)
            {
                return new VideoHeaderBean(in);
            }
            
            @Override
            public VideoHeaderBean[] newArray(int size)
            {
                return new VideoHeaderBean[size];
            }
        };


    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getBlurredUrl()
    {
        return blurredUrl;
    }
    
    public void setBlurredUrl(String blurredUrl)
    {
        this.blurredUrl = blurredUrl;
    }
    
    public String getUserDescription()
    {
        return userDescription;
    }
    
    public void setUserDescription(String userDescription)
    {
        this.userDescription = userDescription;
    }
    
    public String getPlayerUrl()
    {
        return playerUrl;
    }
    
    public void setPlayerUrl(String playerUrl)
    {
        this.playerUrl = playerUrl;
    }
    
    public String getVideoTitle()
    {
        return videoTitle;
    }
    
    public void setVideoTitle(String videoTitle)
    {
        this.videoTitle = videoTitle;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getVideo_description()
    {
        return video_description;
    }
    
    public void setVideo_description(String video_description)
    {
        this.video_description = video_description;
    }
    
    public int getCollectionCount()
    {
        return collectionCount;
    }
    
    public void setCollectionCount(int collectionCount)
    {
        this.collectionCount = collectionCount;
    }
    
    public int getShareCount()
    {
        return shareCount;
    }
    
    public void setShareCount(int shareCount)
    {
        this.shareCount = shareCount;
    }
    
    public String getAvatar()
    {
        return avatar;
    }
    
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }
    
    public String getNickName()
    {
        return nickName;
    }
    
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
    
    public String getUser_description()
    {
        return userDescription;
    }
    
    public void setUser_description(String user_description)
    {
        this.userDescription = user_description;
    }

    @Override
    public String toString() {
        return "VideoHeaderBean{" +
                "videoTitle='" + videoTitle + '\'' +
                ", category='" + category + '\'' +
                ", video_description='" + video_description + '\'' +
                ", collectionCount=" + collectionCount +
                ", shareCount=" + shareCount +
                ", avatar='" + avatar + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userDescription='" + userDescription + '\'' +
                ", playerUrl='" + playerUrl + '\'' +
                ", blurredUrl='" + blurredUrl + '\'' +
                ", videoId=" + videoId +
                '}';
    }
}
