package com.drz.home.nominate.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-11
 */
public class Banner2Bean implements Serializable {

    /**
     * type : banner2
     * data : {"dataType":"Banner","id":1691,"title":"","description":"","image":"http://img.kaiyanapp.com/1eaf8827688ea3b910b7b6b6cb192a5f.png?imageMogr2/quality/60/format/jpg","actionUrl":"eyepetizer://webview/?title=%E5%BC%80%E7%9C%BC%E4%B8%80%E5%8F%B7%E6%94%BE%E6%98%A0%E5%8E%85&url=https%3A%2F%2Fwww.kaiyanapp.com%2Fnew_article.html%3Fnid%3D1516%26shareable%3Dtrue%26type%3DarticleTopic%26cid%3D1691","adTrack":[],"shade":false,"label":{"text":"","card":"","detail":null},"labelList":[],"header":{"id":0,"title":null,"font":null,"subTitle":null,"subTitleFont":null,"textAlign":"left","cover":null,"label":null,"actionUrl":null,"labelList":null,"rightText":null,"icon":null,"description":null},"autoPlay":false}
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
         * dataType : Banner
         * id : 1691
         * title :
         * description :
         * image : http://img.kaiyanapp.com/1eaf8827688ea3b910b7b6b6cb192a5f.png?imageMogr2/quality/60/format/jpg
         * actionUrl : eyepetizer://webview/?title=%E5%BC%80%E7%9C%BC%E4%B8%80%E5%8F%B7%E6%94%BE%E6%98%A0%E5%8E%85&url=https%3A%2F%2Fwww.kaiyanapp.com%2Fnew_article.html%3Fnid%3D1516%26shareable%3Dtrue%26type%3DarticleTopic%26cid%3D1691
         * adTrack : []
         * shade : false
         * label : {"text":"","card":"","detail":null}
         * labelList : []
         * header : {"id":0,"title":null,"font":null,"subTitle":null,"subTitleFont":null,"textAlign":"left","cover":null,"label":null,"actionUrl":null,"labelList":null,"rightText":null,"icon":null,"description":null}
         * autoPlay : false
         */

        private String dataType;
        private int id;
        private String title;
        private String description;
        private String image;
        private String actionUrl;
        private boolean shade;
        private LabelBean label;
        private HeaderBean header;
        private boolean autoPlay;
        private List<?> adTrack;
        private List<?> labelList;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getActionUrl() {
            return actionUrl;
        }

        public void setActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
        }

        public boolean isShade() {
            return shade;
        }

        public void setShade(boolean shade) {
            this.shade = shade;
        }

        public LabelBean getLabel() {
            return label;
        }

        public void setLabel(LabelBean label) {
            this.label = label;
        }

        public HeaderBean getHeader() {
            return header;
        }

        public void setHeader(HeaderBean header) {
            this.header = header;
        }

        public boolean isAutoPlay() {
            return autoPlay;
        }

        public void setAutoPlay(boolean autoPlay) {
            this.autoPlay = autoPlay;
        }

        public List<?> getAdTrack() {
            return adTrack;
        }

        public void setAdTrack(List<?> adTrack) {
            this.adTrack = adTrack;
        }

        public List<?> getLabelList() {
            return labelList;
        }

        public void setLabelList(List<?> labelList) {
            this.labelList = labelList;
        }

        public static class LabelBean {
            /**
             * text :
             * card :
             * detail : null
             */

            private String text;
            private String card;
            private Object detail;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCard() {
                return card;
            }

            public void setCard(String card) {
                this.card = card;
            }

            public Object getDetail() {
                return detail;
            }

            public void setDetail(Object detail) {
                this.detail = detail;
            }
        }

        public static class HeaderBean {
            /**
             * id : 0
             * title : null
             * font : null
             * subTitle : null
             * subTitleFont : null
             * textAlign : left
             * cover : null
             * label : null
             * actionUrl : null
             * labelList : null
             * rightText : null
             * icon : null
             * description : null
             */

            private int id;
            private Object title;
            private Object font;
            private Object subTitle;
            private Object subTitleFont;
            private String textAlign;
            private Object cover;
            private Object label;
            private Object actionUrl;
            private Object labelList;
            private Object rightText;
            private Object icon;
            private Object description;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getTitle() {
                return title;
            }

            public void setTitle(Object title) {
                this.title = title;
            }

            public Object getFont() {
                return font;
            }

            public void setFont(Object font) {
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

            public Object getActionUrl() {
                return actionUrl;
            }

            public void setActionUrl(Object actionUrl) {
                this.actionUrl = actionUrl;
            }

            public Object getLabelList() {
                return labelList;
            }

            public void setLabelList(Object labelList) {
                this.labelList = labelList;
            }

            public Object getRightText() {
                return rightText;
            }

            public void setRightText(Object rightText) {
                this.rightText = rightText;
            }

            public Object getIcon() {
                return icon;
            }

            public void setIcon(Object icon) {
                this.icon = icon;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }
        }
    }
}
