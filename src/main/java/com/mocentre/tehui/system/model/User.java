package com.mocentre.tehui.system.model;

import java.util.Date;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类User.java的实现描述：用户实体类
 * 
 * @author sz.gong 2016年3月4日 下午2:39:38
 */
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户名 **/
    private String            userName;

    /** 真实姓名 **/
    private String            realName;

    /** 密码 **/
    private String            password;

    /** 头像 **/
    private String            head;

    /** 性别 **/
    private Integer           sex;

    /** 生日 **/
    private Date              birthday;

    /** 电话 **/
    private String            phone;

    /** qq **/
    private String            qq;

    /** email **/
    private String            email;

    /** 注册时间 **/
    private Date              regDate;

    /** 店铺id **/
    private Long              shopId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

}
