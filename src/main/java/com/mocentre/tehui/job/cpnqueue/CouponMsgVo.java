/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job.cpnqueue;

import java.io.Serializable;

/**
 * 类CouponMsgVo.java的实现描述：优惠券实例
 * 
 * @author sz.gong 2017年7月27日 下午2:07:52
 */
public class CouponMsgVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7719237467986146119L;

    /** 手机号 **/
    private String            telephone;

    /** 类型 （1.实物 2.优惠券） **/
    private String            type;

    /** 优惠券编码 **/
    private String            couponCode;

    /** 实物名称 **/
    private String            prizeName;

    /** 实物图片 **/
    private String            prizeImg;

    /** 开始时间 **/
    private String            startTime;

    /** 结束时间 **/
    private String            endTime;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getPrizeImg() {
        return prizeImg;
    }

    public void setPrizeImg(String prizeImg) {
        this.prizeImg = prizeImg;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
