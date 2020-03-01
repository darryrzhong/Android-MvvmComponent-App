package com.drz.common.services;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 应用模块:
 * <p>
 * 类描述: 与设备相关的配置信息
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-01-27
 */
public interface IDeviceService  extends IProvider
{
    
    String DEVICE_SERVICE_NAME = "device_name";
    
    /**
     * 获取设备默认语言
     */
    public String getDeviceDefaultLanguage();
    
    /**
     * 获取ClientId
     *
     * @return
     */
    public String getClientId();
    
    /**
     * 获取当前设备安卓系统版本号
     */
    public String getSystemVersion();
    
    /**
     * 获取手机品牌
     *
     * @return
     */
    public String getPhoneBrand();
    
    /**
     * 获取手机Android API等级（22、23 ...）
     *
     * @return
     */
    public int getBuildLevel();
    
    /**
     * 获取设备宽度（px）
     *
     * @return
     */
    public int getDeviceWidth();
    
    /**
     * 获取设备高度（px）
     *
     * @return
     */
    public int getDeviceHeight();
    
    /**
     * SD卡判断
     *
     * @return
     */
    public boolean isSDCardAvailable();
    
    /**
     * 是否有网
     *
     * @return
     */
    public boolean isNetworkConnected();
    
    /**
     * 是否这次安装时新安装
     */
    boolean isNewInstall();
    
}
