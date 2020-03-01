package com.drz.more.themes.bean;

import java.io.Serializable;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public class Tabs implements Serializable {
    /**
     * id : -1
     * name : 推荐
     * apiUrl : http://baobab.kaiyanapp.com/api/v7/tag/childTab/0?isRecTab=true
     * tabType : 0
     * nameType : 0
     * adTrack : null
     */

    private int id;
    private String name;
    private String apiUrl;
    private int tabType;
    private int nameType;
    private Object adTrack;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public int getTabType() {
        return tabType;
    }

    public void setTabType(int tabType) {
        this.tabType = tabType;
    }

    public int getNameType() {
        return nameType;
    }

    public void setNameType(int nameType) {
        this.nameType = nameType;
    }

    public Object getAdTrack() {
        return adTrack;
    }

    public void setAdTrack(Object adTrack) {
        this.adTrack = adTrack;
    }
}
