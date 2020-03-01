package com.drz.common.contract;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * 应用模块:
 * <p>
 * 类描述: 用户信息
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-01-27
 */
public class UserInfo implements Serializable
{
    @NonNull
    private String uuid;
    
    private String birthday;
    
    private String sex;
    
    private String accessToken;
    
    private String refreshToken;
    
    // 到期时间
    private String tokenExpireTime;
    
    private int type;
    
    private String name;
    
    private String email;
    
    private String phone;
    
    private String signature;
    
    private String avatarRemoteUrl;
    
    private ThirdAccount[] thirdAccounts;
    
    private String regionId;

    public class ThirdAccount implements Serializable {
        public String avatarUrl;
        public String nickName;
        public String openId;
        public int thirdType;
        public String token;
        public String unionId;
        public String email;
    }

    private UserInfo(){
        throw new UnsupportedOperationException("Do not instantiate");
    }

    public static UserInfo getInstance(){
        return UserHolder.INSTANCE;
    }

    private static class UserHolder{
        private static  final UserInfo INSTANCE =  new UserInfo();
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(@NonNull String uuid) {
        this.uuid = uuid;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(String tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatarRemoteUrl() {
        return avatarRemoteUrl;
    }

    public void setAvatarRemoteUrl(String avatarRemoteUrl) {
        this.avatarRemoteUrl = avatarRemoteUrl;
    }

    public ThirdAccount[] getThirdAccounts() {
        return thirdAccounts;
    }

    public void setThirdAccounts(ThirdAccount[] thirdAccounts) {
        this.thirdAccounts = thirdAccounts;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

}
