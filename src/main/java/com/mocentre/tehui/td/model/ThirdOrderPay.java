package com.mocentre.tehui.td.model;

import java.util.Date;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类ThirdOrderPay.java的实现描述：第三方订单支付
 * 
 * @author sz.gong 2017年6月20日 下午5:32:26
 */
public class ThirdOrderPay extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 4359513133992855661L;

    /** 支付编号 **/
    private String            paymentNum;

    /** 订单编号 **/
    private String            orderNum;

    /** 订单金额 **/
    private Long              orderAmount;

    /** 付款时间 **/
    private Date              payDate;

    /** 支付方式（kmpay：K码支付 wxpay：微信支付 alipay：支付宝支付） **/
    private String            payType;

    /** 支付状态 **/
    private String            payStatus;

    /** 交易批次号 **/
    private String            batchNo;

    /** 交易状态 **/
    private String            batchStatus;

    /** 交易金额 **/
    private Long              amount;

    /** 商户备注 **/
    private String            remark;

    /** 通知类型(0:页面通知 1:服务器通知) **/
    private String            notifyType;

    /** 支付流水号 **/
    private String            irspRef;

    /** 交易凭证号 **/
    private String            voucherNo;

    /** 银行交易日期 **/
    private String            hostDate;

    /** 银行交易时间 HH:mm:ss **/
    private String            hostTime;

    /** 商户编号 **/
    private String            mchId;

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setIrspRef(String irspRef) {
        this.irspRef = irspRef;
    }

    public String getIrspRef() {
        return irspRef;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setHostDate(String hostDate) {
        this.hostDate = hostDate;
    }

    public String getHostDate() {
        return hostDate;
    }

    public void setHostTime(String hostTime) {
        this.hostTime = hostTime;
    }

    public String getHostTime() {
        return hostTime;
    }

    public String getPaymentNum() {
        return paymentNum;
    }

    public void setPaymentNum(String paymentNum) {
        this.paymentNum = paymentNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

}
