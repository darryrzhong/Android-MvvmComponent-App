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
public class BannerBean implements Serializable
{
    
    /**
     * type : banner data :
     * {"dataType":"Banner","id":1682,"title":"新年购物车：2020年手帐清单","description":null,"image":"http://img.kaiyanapp.com/80b385aa137e223421c92ee389c99e83.jpeg?imageMogr2/quality/60/format/jpg","actionUrl":"eyepetizer://webview/?title=&url=https%3A%2F%2Fwww.kaiyanapp.com%2Fnew_article.html%3Fnid%3D1468%26shareable%3Dtrue","adTrack":null,"shade":false,"label":null,"labelList":null,"header":null,"autoPlay":false}
     * tag : null id : 0 adIndex : -1
     */
    
    private String type;
    
    private DataBean data;
    
    private Object tag;
    
    private int id;
    
    private int adIndex;
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public DataBean getData()
    {
        return data;
    }
    
    public void setData(DataBean data)
    {
        this.data = data;
    }
    
    public Object getTag()
    {
        return tag;
    }
    
    public void setTag(Object tag)
    {
        this.tag = tag;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getAdIndex()
    {
        return adIndex;
    }
    
    public void setAdIndex(int adIndex)
    {
        this.adIndex = adIndex;
    }
    
    public static class DataBean
    {
        /**
         * dataType : Banner id : 1682 title : 新年购物车：2020年手帐清单 description :
         * null image :
         * http://img.kaiyanapp.com/80b385aa137e223421c92ee389c99e83.jpeg?imageMogr2/quality/60/format/jpg
         * actionUrl :
         * eyepetizer://webview/?title=&url=https%3A%2F%2Fwww.kaiyanapp.com%2Fnew_article.html%3Fnid%3D1468%26shareable%3Dtrue
         * adTrack : null shade : false label : null labelList : null header :
         * null autoPlay : false
         */
        
        private String dataType;
        
        private int id;
        
        private String title;
        
        private Object description;
        
        private String image;
        
        private String actionUrl;
        
        private Object adTrack;
        
        private boolean shade;
        
        private Object label;
        
        private Object labelList;
        
        private Object header;
        
        private boolean autoPlay;
        
        public String getDataType()
        {
            return dataType;
        }
        
        public void setDataType(String dataType)
        {
            this.dataType = dataType;
        }
        
        public int getId()
        {
            return id;
        }
        
        public void setId(int id)
        {
            this.id = id;
        }
        
        public String getTitle()
        {
            return title;
        }
        
        public void setTitle(String title)
        {
            this.title = title;
        }
        
        public Object getDescription()
        {
            return description;
        }
        
        public void setDescription(Object description)
        {
            this.description = description;
        }
        
        public String getImage()
        {
            return image;
        }
        
        public void setImage(String image)
        {
            this.image = image;
        }
        
        public String getActionUrl()
        {
            return actionUrl;
        }
        
        public void setActionUrl(String actionUrl)
        {
            this.actionUrl = actionUrl;
        }
        
        public Object getAdTrack()
        {
            return adTrack;
        }
        
        public void setAdTrack(Object adTrack)
        {
            this.adTrack = adTrack;
        }
        
        public boolean isShade()
        {
            return shade;
        }
        
        public void setShade(boolean shade)
        {
            this.shade = shade;
        }
        
        public Object getLabel()
        {
            return label;
        }
        
        public void setLabel(Object label)
        {
            this.label = label;
        }
        
        public Object getLabelList()
        {
            return labelList;
        }
        
        public void setLabelList(Object labelList)
        {
            this.labelList = labelList;
        }
        
        public Object getHeader()
        {
            return header;
        }
        
        public void setHeader(Object header)
        {
            this.header = header;
        }
        
        public boolean isAutoPlay()
        {
            return autoPlay;
        }
        
        public void setAutoPlay(boolean autoPlay)
        {
            this.autoPlay = autoPlay;
        }
    }
}
