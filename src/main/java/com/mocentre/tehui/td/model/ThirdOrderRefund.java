package com.mocentre.tehui.td.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类ThirdOrderRefund.java的实现描述：订单退款记录表
 * 
 * @author sz.gong 2017年6月29日 下午4:37:44
 */
public class ThirdOrderRefund extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -445889259909230144L;

    /** 退款单号 **/
    private String            refundNum;

    /** 订单编号 **/
    private String            orderNum;

    /** 公钥 **/
    private String            appKey;

    /** 退款状态（success:退款成功 fail:退款失败） **/
    private String            refundStatus;

    /** 交易流水号 **/
    private String            irspRef;

    /** 交易状态 **/
    private String            batchStatus;

    /** 交易批次号 **/
    private String            batchNo;

    /** 申请退款金额 **/
    private Long              refundFee;

    /** 订单金额 **/
    private Long              totalFee;

    /** 交易凭证号 **/
    private String            voucherNo;

    /** 银行交易日期 **/
    private String            hostDate;

    /** 银行交易时间 **/
    private String            hostTime;

    /** 商户号 **/
    private String            mchId;

    public String getRefundNum() {
        return refundNum;
    }

    public void setRefundNum(String refundNum) {
        this.refundNum = refundNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getIrspRef() {
        return irspRef;
    }

    public void setIrspRef(String irspRef) {
        this.irspRef = irspRef;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Long getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Long refundFee) {
        this.refundFee = refundFee;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getHostDate() {
        return hostDate;
    }

    public void setHostDate(String hostDate) {
        this.hostDate = hostDate;
    }

    public String getHostTime() {
        return hostTime;
    }

    public void setHostTime(String hostTime) {
        this.hostTime = hostTime;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

}
