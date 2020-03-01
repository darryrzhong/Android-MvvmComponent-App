package com.drz.home.discover.bean;

import java.io.Serializable;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-15
 */
public class BriefCard implements Serializable {

    /**
     * type : briefCard
     * data : {"dataType":"TagBriefCard","id":930,"icon":"http://img.kaiyanapp.com/586d2cdc4a807eb84bed71fc523ed24b.jpeg?imageMogr2/quality/60/format/jpg","iconType":"square","title":"#摄影师日常","subTitle":null,"description":"透过镜头，我看到了更加真实的世界","actionUrl":"eyepetizer://tag/930/?title=%E6%91%84%E5%BD%B1%E5%B8%88%E6%97%A5%E5%B8%B8","adTrack":null,"follow":{"itemType":"tag","itemId":930,"followed":false},"ifPgc":false,"uid":0,"ifShowNotificationIcon":false,"switchStatus":false,"medalIcon":true,"haveReward":false,"ifNewest":false,"newestEndTime":null,"expert":false}
     * tag : null
     * id : 0
     * adIndex : -1
     */

    private String type;
    private DataBean data;
    private Object tag;
    private int id;
    private int adIndex;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdIndex() {
        return adIndex;
    }

    public void setAdIndex(int adIndex) {
        this.adIndex = adIndex;
    }

    public static class DataBean {
        /**
         * dataType : TagBriefCard
         * id : 930
         * icon : http://img.kaiyanapp.com/586d2cdc4a807eb84bed71fc523ed24b.jpeg?imageMogr2/quality/60/format/jpg
         * iconType : square
         * title : #摄影师日常
         * subTitle : null
         * description : 透过镜头，我看到了更加真实的世界
         * actionUrl : eyepetizer://tag/930/?title=%E6%91%84%E5%BD%B1%E5%B8%88%E6%97%A5%E5%B8%B8
         * adTrack : null
         * follow : {"itemType":"tag","itemId":930,"followed":false}
         * ifPgc : false
         * uid : 0
         * ifShowNotificationIcon : false
         * switchStatus : false
         * medalIcon : true
         * haveReward : false
         * ifNewest : false
         * newestEndTime : null
         * expert : false
         */

        private String dataType;
        private int id;
        private String icon;
        private String iconType;
        private String title;
        private Object subTitle;
        private String description;
        private String actionUrl;
        private Object adTrack;
        private FollowBean follow;
        private boolean ifPgc;
        private int uid;
        private boolean ifShowNotificationIcon;
        private boolean switchStatus;
        private boolean medalIcon;
        private boolean haveReward;
        private boolean ifNewest;
        private Object newestEndTime;
        private boolean expert;

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIconType() {
            return iconType;
        }

        public void setIconType(String iconType) {
            this.iconType = iconType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(Object subTitle) {
            this.subTitle = subTitle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getActionUrl() {
            return actionUrl;
        }

        public void setActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
        }

        public Object getAdTrack() {
            return adTrack;
        }

        public void setAdTrack(Object adTrack) {
            this.adTrack = adTrack;
        }

        public FollowBean getFollow() {
            return follow;
        }

        public void setFollow(FollowBean follow) {
            this.follow = follow;
        }

        public boolean isIfPgc() {
            return ifPgc;
        }

        public void setIfPgc(boolean ifPgc) {
            this.ifPgc = ifPgc;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public boolean isIfShowNotificationIcon() {
            return ifShowNotificationIcon;
        }

        public void setIfShowNotificationIcon(boolean ifShowNotificationIcon) {
            this.ifShowNotificationIcon = ifShowNotificationIcon;
        }

        public boolean isSwitchStatus() {
            return switchStatus;
        }

        public void setSwitchStatus(boolean switchStatus) {
            this.switchStatus = switchStatus;
        }

        public boolean isMedalIcon() {
            return medalIcon;
        }

        public void setMedalIcon(boolean medalIcon) {
            this.medalIcon = medalIcon;
        }

        public boolean isHaveReward() {
            return haveReward;
        }

        public void setHaveReward(boolean haveReward) {
            this.haveReward = haveReward;
        }

        public boolean isIfNewest() {
            return ifNewest;
        }

        public void setIfNewest(boolean ifNewest) {
            this.ifNewest = ifNewest;
        }

        public Object getNewestEndTime() {
            return newestEndTime;
        }

        public void setNewestEndTime(Object newestEndTime) {
            this.newestEndTime = newestEndTime;
        }

        public boolean isExpert() {
            return expert;
        }

        public void setExpert(boolean expert) {
            this.expert = expert;
        }

        public static class FollowBean {
            /**
             * itemType : tag
             * itemId : 930
             * followed : false
             */

            private String itemType;
            private int itemId;
            private boolean followed;

            public String getItemType() {
                return itemType;
            }

            public void setItemType(String itemType) {
                this.itemType = itemType;
            }

            public int getItemId() {
                return itemId;
            }

            public void setItemId(int itemId) {
                this.itemId = itemId;
            }

            public boolean isFollowed() {
                return followed;
            }

            public void setFollowed(boolean followed) {
                this.followed = followed;
            }
        }
    }
}
