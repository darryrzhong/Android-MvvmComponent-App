package com.drz.home.discover.bean;

import com.drz.common.contract.BaseCustomViewModel;

import java.io.Serializable;
import java.util.List;

/**
 * 应用模块:
 * <p>
 * 类描述: 专题策划
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-15
 */
public class SubjectCardBean  extends BaseCustomViewModel implements Serializable {

    /**
     * type : columnCardList
     * data : {"dataType":"ItemCollection","header":{"id":7,"title":"专题策划","font":"bold","subTitle":null,"subTitleFont":null,"textAlign":"left","cover":null,"label":null,"actionUrl":"eyepetizer://campaign/list/?title=%E4%B8%93%E9%A2%98","labelList":null,"rightText":"查看全部"},"itemList":[{"type":"squareCardOfColumn","data":{"dataType":"SquareCard","id":487,"title":"异地恋也可以很甜","image":"http://img.kaiyanapp.com/5c47944ec1bd021aef2900ae8a41b605.gif","actionUrl":"eyepetizer://lightTopic/detail/487?title=%E7%BD%91%E6%81%8B%E5%90%97%EF%BC%9F","shade":true,"adTrack":null,"description":null},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfColumn","data":{"dataType":"SquareCard","id":486,"title":"第 92 届奥斯卡入围短片","image":"http://img.kaiyanapp.com/f761e7a9d24bd4ec7a275938d3ed0b07.jpeg?imageMogr2/quality/60/format/jpg","actionUrl":"eyepetizer://lightTopic/detail/486?title=%E7%AC%AC%2092%20%E5%B1%8A%E5%A5%A5%E6%96%AF%E5%8D%A1%E5%85%A5%E5%9B%B4%E7%9F%AD%E7%89%87","shade":true,"adTrack":null,"description":null},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfColumn","data":{"dataType":"SquareCard","id":483,"title":"关于「新型冠状病毒」你想知道的一切","image":"http://img.kaiyanapp.com/9b3e452a3f78a2ef894ffd0119b0cb64.png?imageMogr2/quality/60/format/jpg","actionUrl":"eyepetizer://lightTopic/detail/483?title=%E5%85%B3%E4%BA%8E%E3%80%8C%E6%96%B0%E5%9E%8B%E5%86%A0%E7%8A%B6%E7%97%85%E6%AF%92%E3%80%8D%E4%BD%A0%E6%83%B3%E7%9F%A5%E9%81%93%E7%9A%84%E4%B8%80%E5%88%87","shade":true,"adTrack":null,"description":null},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfColumn","data":{"dataType":"SquareCard","id":479,"title":"今年过年，我可不是吃素的！","image":"http://img.kaiyanapp.com/7094d1cb251854e21b9c876f604ea1bb.jpeg?imageMogr2/quality/60/format/jpg","actionUrl":"eyepetizer://lightTopic/detail/479?title=%E6%97%A0%E8%82%89%E4%B8%8D%E6%AC%A2","shade":true,"adTrack":null,"description":null},"tag":null,"id":0,"adIndex":-1}],"count":4,"adTrack":null,"footer":null}
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
         * header : {"id":7,"title":"专题策划","font":"bold","subTitle":null,"subTitleFont":null,"textAlign":"left","cover":null,"label":null,"actionUrl":"eyepetizer://campaign/list/?title=%E4%B8%93%E9%A2%98","labelList":null,"rightText":"查看全部"}
         * itemList : [{"type":"squareCardOfColumn","data":{"dataType":"SquareCard","id":487,"title":"异地恋也可以很甜","image":"http://img.kaiyanapp.com/5c47944ec1bd021aef2900ae8a41b605.gif","actionUrl":"eyepetizer://lightTopic/detail/487?title=%E7%BD%91%E6%81%8B%E5%90%97%EF%BC%9F","shade":true,"adTrack":null,"description":null},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfColumn","data":{"dataType":"SquareCard","id":486,"title":"第 92 届奥斯卡入围短片","image":"http://img.kaiyanapp.com/f761e7a9d24bd4ec7a275938d3ed0b07.jpeg?imageMogr2/quality/60/format/jpg","actionUrl":"eyepetizer://lightTopic/detail/486?title=%E7%AC%AC%2092%20%E5%B1%8A%E5%A5%A5%E6%96%AF%E5%8D%A1%E5%85%A5%E5%9B%B4%E7%9F%AD%E7%89%87","shade":true,"adTrack":null,"description":null},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfColumn","data":{"dataType":"SquareCard","id":483,"title":"关于「新型冠状病毒」你想知道的一切","image":"http://img.kaiyanapp.com/9b3e452a3f78a2ef894ffd0119b0cb64.png?imageMogr2/quality/60/format/jpg","actionUrl":"eyepetizer://lightTopic/detail/483?title=%E5%85%B3%E4%BA%8E%E3%80%8C%E6%96%B0%E5%9E%8B%E5%86%A0%E7%8A%B6%E7%97%85%E6%AF%92%E3%80%8D%E4%BD%A0%E6%83%B3%E7%9F%A5%E9%81%93%E7%9A%84%E4%B8%80%E5%88%87","shade":true,"adTrack":null,"description":null},"tag":null,"id":0,"adIndex":-1},{"type":"squareCardOfColumn","data":{"dataType":"SquareCard","id":479,"title":"今年过年，我可不是吃素的！","image":"http://img.kaiyanapp.com/7094d1cb251854e21b9c876f604ea1bb.jpeg?imageMogr2/quality/60/format/jpg","actionUrl":"eyepetizer://lightTopic/detail/479?title=%E6%97%A0%E8%82%89%E4%B8%8D%E6%AC%A2","shade":true,"adTrack":null,"description":null},"tag":null,"id":0,"adIndex":-1}]
         * count : 4
         * adTrack : null
         * footer : null
         */

        private String dataType;
        private HeaderBean header;
        private int count;
        private Object adTrack;
        private Object footer;
        private List<ItemListBean> itemList;

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public HeaderBean getHeader() {
            return header;
        }

        public void setHeader(HeaderBean header) {
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

        public List<ItemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBean> itemList) {
            this.itemList = itemList;
        }

        public static class HeaderBean {
            /**
             * id : 7
             * title : 专题策划
             * font : bold
             * subTitle : null
             * subTitleFont : null
             * textAlign : left
             * cover : null
             * label : null
             * actionUrl : eyepetizer://campaign/list/?title=%E4%B8%93%E9%A2%98
             * labelList : null
             * rightText : 查看全部
             */

            private int id;
            private String title;
            private String font;
            private Object subTitle;
            private Object subTitleFont;
            private String textAlign;
            private Object cover;
            private Object label;
            private String actionUrl;
            private Object labelList;
            private String rightText;

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

            public String getFont() {
                return font;
            }

            public void setFont(String font) {
                this.font = font;
            }

            public Object getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(Object subTitle) {
                this.subTitle = subTitle;
            }

            public Object getSubTitleFont() {
                return subTitleFont;
            }

            public void setSubTitleFont(Object subTitleFont) {
                this.subTitleFont = subTitleFont;
            }

            public String getTextAlign() {
                return textAlign;
            }

            public void setTextAlign(String textAlign) {
                this.textAlign = textAlign;
            }

            public Object getCover() {
                return cover;
            }

            public void setCover(Object cover) {
                this.cover = cover;
            }

            public Object getLabel() {
                return label;
            }

            public void setLabel(Object label) {
                this.label = label;
            }

            public String getActionUrl() {
                return actionUrl;
            }

            public void setActionUrl(String actionUrl) {
                this.actionUrl = actionUrl;
            }

            public Object getLabelList() {
                return labelList;
            }

            public void setLabelList(Object labelList) {
                this.labelList = labelList;
            }

            public String getRightText() {
                return rightText;
            }

            public void setRightText(String rightText) {
                this.rightText = rightText;
            }
        }

        public static class ItemListBean {
            /**
             * type : squareCardOfColumn
             * data : {"dataType":"SquareCard","id":487,"title":"异地恋也可以很甜","image":"http://img.kaiyanapp.com/5c47944ec1bd021aef2900ae8a41b605.gif","actionUrl":"eyepetizer://lightTopic/detail/487?title=%E7%BD%91%E6%81%8B%E5%90%97%EF%BC%9F","shade":true,"adTrack":null,"description":null}
             * tag : null
             * id : 0
             * adIndex : -1
             */

            private String type;
            private SquareCard data;
            private Object tag;
            private int id;
            private int adIndex;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public SquareCard getData() {
                return data;
            }

            public void setData(SquareCard data) {
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

        }
    }
}
