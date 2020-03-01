package com.drz.community.recommend.bean;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-17
 */
public class SquareContentCard  {

    /**
     * type : squareCardOfCommunityContent
     * data : {"dataType":"SquareContentCard","title":"主题创作广场","subTitle":"发布你的作品和日常","bgPicture":"http://img.kaiyanapp.com/04eef7e9f3b14a597bd04a8d81a4c8f3.png?imageMogr2/quality/60/format/jpg","actionUrl":"eyepetizer://community/tagSquare?tabIndex=0"}
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
         * dataType : SquareContentCard
         * title : 主题创作广场
         * subTitle : 发布你的作品和日常
         * bgPicture : http://img.kaiyanapp.com/04eef7e9f3b14a597bd04a8d81a4c8f3.png?imageMogr2/quality/60/format/jpg
         * actionUrl : eyepetizer://community/tagSquare?tabIndex=0
         */

        private String dataType;
        private String title;
        private String subTitle;
        private String bgPicture;
        private String actionUrl;

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getBgPicture() {
            return bgPicture;
        }

        public void setBgPicture(String bgPicture) {
            this.bgPicture = bgPicture;
        }

        public String getActionUrl() {
            return actionUrl;
        }

        public void setActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
        }
    }
}
