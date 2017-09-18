package com.mocentre.tehui.ps.model;

import java.util.Date;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 用户基本信息 Created by 王雪莹 on 2016/11/22.
 */
public class CustomerInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 手机号 */
    private String            telephone;
    /** openid */
    private String            openid;
    /** 农行app 用户id */
    private String            abcaid;
    /** 注册密码 */
    private String            password;
    /** 加密后的密码 */
    private String            encryptedPassword;
    /** 密码随机数 */
    private String            randomNum;
    /** 注册时间 */
    private Date              registerTime;
    /** 最近一次登入时间 */
    private Date              lastLoginTime;
    /** 用户名 */
    private String            userName;
    /** 性别 */
    private Integer           sex;
    /** 生日 */
    private Date              birthday;
    /** 邮箱 */
    private String            email;
    /** 用户头像 */
    private String            profile;
    /** 积分 */
    private Integer           integral;
    /** 等级 */
    private Integer           level;
    /** 成长值 */
    private Long              growth;

    public String getAbcaid() {
        return abcaid;
    }

    public void setAbcaid(String abcaid) {
        this.abcaid = abcaid;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(String randomNum) {
        this.randomNum = randomNum;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getGrowth() {
        return growth;
    }

    public void setGrowth(Long growth) {
        this.growth = growth;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
