package com.mocentre.gift.ps.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 礼品平台 收获地址实体类
 * @author liqifan
 * @date 创建时间：2017年4月13日 上午10:17:20
 */
public class GiftCustomerAddress extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private Long              customerId;
    /** 收件人 */
    private String            recipient;
    /** 收件人电话 */
    private String            telephone;
    /** 省份 */
    private String            province;
    /** 市 */
    private String            city;
    /** 区 */
    private String            area;
    /** 详细地址 */
    private String            address;
    /** 邮政编码 */
    private String            postCode;
    /** 收货时间 */
    private Integer           period;
    /** 是否为默认收货地址 */
    private String            isDefault;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

}
