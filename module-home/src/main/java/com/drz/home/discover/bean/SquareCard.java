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
public class SquareCard implements Serializable {

    public SquareCard(String title, String image, String actionUrl) {
        this.title = title;
        this.image = image;
        this.actionUrl = actionUrl;
    }

    /**
     * dataType : SquareCard
     * id : 14
     * title : #广告
     * image : http://img.kaiyanapp.com/57472e13fd2b6c9655c8a600597daf4d.png?imageMogr2/quality/60/format/jpg
     * actionUrl : eyepetizer://tag/16/?title=%E5%B9%BF%E5%91%8A
     * shade : true
     * adTrack : null
     * description : null
     */



    private String title;
    private String image;
    private String actionUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
