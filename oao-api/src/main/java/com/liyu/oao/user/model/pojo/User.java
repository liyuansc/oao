package com.liyu.oao.user.model.pojo;

import com.liyu.oao.common.model.SuperPojo;

/**
 * <p>
 * 
 * </p>
 *
 * @author liyu
 * @since 2020-06-23
 */
public class User extends SuperPojo {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private String nickname;

    private String headImgUrl;

    private String mobile;

    private Boolean sex;

    private Boolean enabled;

    private String type;

    private String company;

    private String openId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "User{" +
            "username=" + username +
            ", password=" + password +
            ", nickname=" + nickname +
            ", headImgUrl=" + headImgUrl +
            ", mobile=" + mobile +
            ", sex=" + sex +
            ", enabled=" + enabled +
            ", type=" + type +
            ", company=" + company +
            ", openId=" + openId +
        "}";
    }
}
