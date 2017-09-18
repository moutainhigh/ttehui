/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.payment.abcpay;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类PayOrderItem.java的实现描述：订单明细
 * 
 * @author sz.gong 2016年12月21日 下午5:59:17
 */
public class PayOrderItem {

    public String SubMerName;        //二级商户名称
    public String SubMerId;          //二级商户代码
    public String SubMerMCC;         //二级商户MCC码
    public String SubMerchantRemarks; //二级商户备注项
    public String ProductID;         //商品代码，预留字段
    public String ProductName;       //商品名称（必须设定）
    public String UnitPrice;         //商品总价
    public String Qty;               //商品数量
    public String ProductRemarks;    //商品备注项
    public String ProductType;       //商品类型
    public String ProductDiscount;   //商品折扣
    public String ProductExpiredDate; //商品有效期

    public PayOrderItem(String ProductName) {
        this.ProductName = ProductName;
    }

    public Map<String, String> getOrderItemMap() {
        Map<String, String> items = new LinkedHashMap<String, String>();
        items.put("ProductName", this.ProductName);
        if (SubMerName != null) {
            items.put("SubMerName", this.SubMerName);
        }
        if (SubMerId != null) {
            items.put("SubMerId", this.SubMerId);
        }
        if (SubMerMCC != null) {
            items.put("SubMerMCC", this.SubMerMCC);
        }
        if (SubMerchantRemarks != null) {
            items.put("SubMerchantRemarks", this.SubMerchantRemarks);
        }
        if (ProductID != null) {
            items.put("ProductID", this.ProductID);
        }
        if (UnitPrice != null) {
            items.put("UnitPrice", this.UnitPrice);
        }
        if (Qty != null) {
            items.put("Qty", this.Qty);
        }
        if (ProductRemarks != null) {
            items.put("ProductRemarks", this.ProductRemarks);
        }
        if (ProductType != null) {
            items.put("ProductType", this.ProductType);
        }
        if (ProductDiscount != null) {
            items.put("ProductDiscount", this.ProductDiscount);
        }
        if (ProductExpiredDate != null) {
            items.put("ProductExpiredDate", this.ProductExpiredDate);
        }
        return items;
    }

    public String getSubMerName() {
        return SubMerName;
    }

    public void setSubMerName(String subMerName) {
        SubMerName = subMerName;
    }

    public String getSubMerId() {
        return SubMerId;
    }

    public void setSubMerId(String subMerId) {
        SubMerId = subMerId;
    }

    public String getSubMerMCC() {
        return SubMerMCC;
    }

    public void setSubMerMCC(String subMerMCC) {
        SubMerMCC = subMerMCC;
    }

    public String getSubMerchantRemarks() {
        return SubMerchantRemarks;
    }

    public void setSubMerchantRemarks(String subMerchantRemarks) {
        SubMerchantRemarks = subMerchantRemarks;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getProductRemarks() {
        return ProductRemarks;
    }

    public void setProductRemarks(String productRemarks) {
        ProductRemarks = productRemarks;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public String getProductDiscount() {
        return ProductDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        ProductDiscount = productDiscount;
    }

    public String getProductExpiredDate() {
        return ProductExpiredDate;
    }

    public void setProductExpiredDate(String productExpiredDate) {
        ProductExpiredDate = productExpiredDate;
    }
}
