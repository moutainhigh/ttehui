package com.mocentre.tehui.buy.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类Logistics.java的实现描述：物流实体类
 * 
 * @author sz.gong 2016年11月22日 下午1:47:15
 */
public class OrderLogistics extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 订单id **/
    private Long              orderId;

    /** 物流公司 **/
    private String            company;

    /** 物流单号 **/
    private String            expNum;

    /** 短信提醒 **/
    private String            isSms;

    /** 物流状态 **/
    private String            status;

    /** 物流公司编码 **/
    private String            logisticsCode;

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setExpNum(String expNum) {
        this.expNum = expNum;
    }

    public String getExpNum() {
        return expNum;
    }

    public void setIsSms(String isSms) {
        this.isSms = isSms;
    }

    public String getIsSms() {
        return isSms;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }
}
