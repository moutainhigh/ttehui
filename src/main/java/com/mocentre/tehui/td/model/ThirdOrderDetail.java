/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.model;

import com.mocentre.common.frontend.BaseParam;

/**
 * 类ThirdOrderDetail.java的实现描述：第三方订单详情
 * 
 * @author sz.gong 2017年8月22日 下午6:04:31
 */
public class ThirdOrderDetail extends BaseParam {

    /**
     * 
     */
    private static final long serialVersionUID = -752370481458271318L;

    /** 商品id **/
    private String            productId;

    /** 商品名称 **/
    private String            productName;

    /** 商品总价格(单位：分) **/
    private Long              productPrice;

    /** 商品购买数量 **/
    private Integer           productNum;

    /** 商品备注 **/
    private String            productRemark;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public String getProductRemark() {
        return productRemark;
    }

    public void setProductRemark(String productRemark) {
        this.productRemark = productRemark;
    }

}
