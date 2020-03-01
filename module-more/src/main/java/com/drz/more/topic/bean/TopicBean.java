package com.drz.more.topic.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public class TopicBean implements Serializable {

    /**
     * itemList : [{"type":"squareCardOfTopic","data":{"dataType":"SquareTopicCard","id":245,"title":"你心中，一句最令人心动的表白","joinCount":4381,"viewCount":27632,"showHotSign":true,"actionUrl":"eyepetizer://topic/detail?id=245","imageUrl":"http://img.kaiyanapp.com/2620517918873b55306f991e404a07ce.gif","haveReward":false,"ifNewest":true,"newestEndTime":1580366259000},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfTopic","data":{"dataType":"SquareTopicCard","id":262,"title":"丧尸围城的10小时前，你会做些什么？","joinCount":97,"viewCount":1133,"showHotSign":false,"actionUrl":"eyepetizer://topic/detail?id=262","imageUrl":"http://img.kaiyanapp.com/0f9483d3d299f5ae43c3a4dadd04ddef.jpeg?imageMogr2/quality/60/format/jpg","haveReward":false,"ifNewest":true,"newestEndTime":1582427463000},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfTopic","data":{"dataType":"SquareTopicCard","id":261,"title":"我们这行业，因吹斯听","joinCount":445,"viewCount":4215,"showHotSign":false,"actionUrl":"eyepetizer://topic/detail?id=261","imageUrl":"http://img.kaiyanapp.com/7d69c60f70f16d29cf92b81b736879cb.jpeg?imageMogr2/quality/60/format/jpg","haveReward":false,"ifNewest":true,"newestEndTime":1582257736000},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfTopic","data":{"dataType":"SquareTopicCard","id":260,"title":"当代年轻人的快乐源泉","joinCount":684,"viewCount":5848,"showHotSign":false,"actionUrl":"eyepetizer://topic/detail?id=260","imageUrl":"http://img.kaiyanapp.com/d37d7d2719f4e35ef114ed37cdc098dc.gif","haveReward":false,"ifNewest":true,"newestEndTime":1582173346000},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfTopic","data":{"dataType":"SquareTopicCard","id":259,"title":"宅家的你，如何感受到时间流逝？","joinCount":660,"viewCount":6404,"showHotSign":false,"actionUrl":"eyepetizer://topic/detail?id=259","imageUrl":"http://img.kaiyanapp.com/0cdc586967dcbcbd60df1c270f0bb91e.jpeg?imageMogr2/quality/60/format/jpg","haveReward":false,"ifNewest":true,"newestEndTime":1582005897000},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfTopic","data":{"dataType":"SquareTopicCard","id":258,"title":"属于冬天的观影 LIST","joinCount":548,"viewCount":5908,"showHotSign":false,"actionUrl":"eyepetizer://topic/detail?id=258","imageUrl":"http://img.kaiyanapp.com/b70d8242f350fc28d3f5cc4ee1b42ef9.jpeg?imageMogr2/quality/60/format/jpg","haveReward":false,"ifNewest":true,"newestEndTime":1581908325000},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfTopic","data":{"dataType":"SquareTopicCard","id":257,"title":"为了守护你的异地恋，你都做过哪些努力？","joinCount":652,"viewCount":8827,"showHotSign":false,"actionUrl":"eyepetizer://topic/detail?id=257","imageUrl":"http://img.kaiyanapp.com/842956a06d15831c606e54bbe2186458.gif","haveReward":false,"ifNewest":true,"newestEndTime":1581670835000},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfTopic","data":{"dataType":"SquareTopicCard","id":256,"title":"第92届奥斯卡，你有哪些意难平和小惊喜？","joinCount":162,"viewCount":7184,"showHotSign":false,"actionUrl":"eyepetizer://topic/detail?id=256","imageUrl":"http://img.kaiyanapp.com/3fd4daaea26e9d080109710be24e6c41.jpeg?imageMogr2/quality/60/format/jpg","haveReward":false,"ifNewest":true,"newestEndTime":1581397784000},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfTopic","data":{"dataType":"SquareTopicCard","id":255,"title":"用假期重新认识我们的父母","joinCount":192,"viewCount":7517,"showHotSign":false,"actionUrl":"eyepetizer://topic/detail?id=255","imageUrl":"http://img.kaiyanapp.com/e60fa2e6c307fb9505350665c09b4206.gif","haveReward":false,"ifNewest":true,"newestEndTime":1581304708000},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfTopic","data":{"dataType":"SquareTopicCard","id":254,"title":"被疫情隔成异地恋的我们","joinCount":687,"viewCount":10004,"showHotSign":false,"actionUrl":"eyepetizer://topic/detail?id=254","imageUrl":"http://img.kaiyanapp.com/329640764960e0bc863c418fe0f727e8.gif","haveReward":false,"ifNewest":true,"newestEndTime":1581218276000},"tag":null,"id":0,"adIndex":-1}]
     * count : 10
     * total : 0
     * nextPageUrl : http://baobab.kaiyanapp.com/api/v7/topic/list?start=10&num=10
     * adExist : false
     */

    private int count;
    private int total;
    private String nextPageUrl;
    private boolean adExist;
    private List<ItemListBean> itemList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public boolean isAdExist() {
        return adExist;
    }

    public void setAdExist(boolean adExist) {
        this.adExist = adExist;
    }

    public List<ItemListBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemListBean> itemList) {
        this.itemList = itemList;
    }

    public static class ItemListBean {
        /**
         * type : squareCardOfTopic
         * data : {"dataType":"SquareTopicCard","id":245,"title":"你心中，一句最令人心动的表白","joinCount":4381,"viewCount":27632,"showHotSign":true,"actionUrl":"eyepetizer://topic/detail?id=245","imageUrl":"http://img.kaiyanapp.com/2620517918873b55306f991e404a07ce.gif","haveReward":false,"ifNewest":true,"newestEndTime":1580366259000}
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
             * dataType : SquareTopicCard
             * id : 245
             * title : 你心中，一句最令人心动的表白
             * joinCount : 4381
             * viewCount : 27632
             * showHotSign : true
             * actionUrl : eyepetizer://topic/detail?id=245
             * imageUrl : http://img.kaiyanapp.com/2620517918873b55306f991e404a07ce.gif
             * haveReward : false
             * ifNewest : true
             * newestEndTime : 1580366259000
             */

            private String dataType;
            private int id;
            private String title;
            private int joinCount;
            private int viewCount;
            private boolean showHotSign;
            private String actionUrl;
            private String imageUrl;
            private boolean haveReward;
            private boolean ifNewest;
            private long newestEndTime;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getJoinCount() {
                return joinCount;
            }

            public void setJoinCount(int joinCount) {
                this.joinCount = joinCount;
            }

            public int getViewCount() {
                return viewCount;
            }

            public void setViewCount(int viewCount) {
                this.viewCount = viewCount;
            }

            public boolean isShowHotSign() {
                return showHotSign;
            }

            public void setShowHotSign(boolean showHotSign) {
                this.showHotSign = showHotSign;
            }

            public String getActionUrl() {
                return actionUrl;
            }

            public void setActionUrl(String actionUrl) {
                this.actionUrl = actionUrl;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
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

            public long getNewestEndTime() {
                return newestEndTime;
            }

            public void setNewestEndTime(long newestEndTime) {
                this.newestEndTime = newestEndTime;
            }
        }
    }
}
