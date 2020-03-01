package com.drz.community.recommend.bean;

import com.drz.common.contract.BaseCustomViewModel;

import java.util.List;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-17
 */
public class HorizontalScrollCard extends BaseCustomViewModel {


    /**
     * type : horizontalScrollCard
     * data : {"dataType":"ItemCollection","header":null,"itemList":[{"type":"squareCardOfCommunityContent","data":{"dataType":"SquareContentCard","title":"主题创作广场","subTitle":"发布你的作品和日常","bgPicture":"http://img.kaiyanapp.com/04eef7e9f3b14a597bd04a8d81a4c8f3.png?imageMogr2/quality/60/format/jpg","actionUrl":"eyepetizer://community/tagSquare?tabIndex=0"},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfCommunityContent","data":{"dataType":"SquareContentCard","title":"话题讨论大厅","subTitle":"分享你的故事和观点","bgPicture":"http://img.kaiyanapp.com/ccd8be3b1a97cc34c55f9897b06d47e8.png?imageMogr2/quality/60/format/jpg","actionUrl":"eyepetizer://community/topicSquare"},"tag":null,"id":0,"adIndex":-1}],"count":2,"adTrack":null,"footer":null}
     * tag : null
     * id : 0
     * adIndex : -1
     */

    private String type;
    private DataBeanX data;
    private Object tag;
    private int id;
    private int adIndex;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
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

    public static class DataBeanX {
        /**
         * dataType : ItemCollection
         * header : null
         * itemList : [{"type":"squareCardOfCommunityContent","data":{"dataType":"SquareContentCard","title":"主题创作广场","subTitle":"发布你的作品和日常","bgPicture":"http://img.kaiyanapp.com/04eef7e9f3b14a597bd04a8d81a4c8f3.png?imageMogr2/quality/60/format/jpg","actionUrl":"eyepetizer://community/tagSquare?tabIndex=0"},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfCommunityContent","data":{"dataType":"SquareContentCard","title":"话题讨论大厅","subTitle":"分享你的故事和观点","bgPicture":"http://img.kaiyanapp.com/ccd8be3b1a97cc34c55f9897b06d47e8.png?imageMogr2/quality/60/format/jpg","actionUrl":"eyepetizer://community/topicSquare"},"tag":null,"id":0,"adIndex":-1}]
         * count : 2
         * adTrack : null
         * footer : null
         */

        private String dataType;
        private Object header;
        private int count;
        private Object adTrack;
        private Object footer;
        private List<SquareContentCard> itemList;

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public Object getHeader() {
            return header;
        }

        public void setHeader(Object header) {
            this.header = header;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Object getAdTrack() {
            return adTrack;
        }

        public void setAdTrack(Object adTrack) {
            this.adTrack = adTrack;
        }

        public Object getFooter() {
            return footer;
        }

        public void setFooter(Object footer) {
            this.footer = footer;
        }

        public List<SquareContentCard> getItemList() {
            return itemList;
        }

        public void setItemList(List<SquareContentCard> itemList) {
            this.itemList = itemList;
        }

    }
}
