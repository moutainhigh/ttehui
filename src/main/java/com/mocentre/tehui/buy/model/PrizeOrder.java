package com.mocentre.tehui.buy.model;

import com.mocentre.tehui.core.model.BaseEntity;

import java.util.Date;

/**
 * 实物奖品管理
 * Created by wangxueying on 2017/8/10.
 */
public class PrizeOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;
    // 奖品图片
    private String prizeImg;

    // 奖品名称
    private String prizeName;

    // 开始时间
    private Date startTime;

    // 结束时间
    private Date endTime;

    // 发货状态
    private String sendStatus;

    // 用户id
    private Long customerId;

    // 发货地址id
    private Long addressId;

    // 收货人
    private String recipient;

    // 电话号码
    private String telephone;

    // 收获地址
    private String address;

    // 物流公司code
    private String logisticsCode;

    // 物流公司名称
    private String company;

    // 物流单号
    private String logisticsNum;

    public String getPrizeImg() {
        return prizeImg;
    }

    public void setPrizeImg(String prizeImg) {
        this.prizeImg = prizeImg;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLogisticsNum() {
        return logisticsNum;
    }

    public void setLogisticsNum(String logisticsNum) {
        this.logisticsNum = logisticsNum;
    }
}
