package com.mocentre.tehui.act.model;

import java.util.Date;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 用户参团信息实体类
 * 
 * @author Created by yukaiji on 2017年02月09日
 */
public class GrouponDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 参团id */
    private Long              grouponId;
    /** 活动商品id */
    private Long              actGoodsId;
    /** 参团时间 */
    private Date              takeTime;
    /** 参团用户id */
    private Long              takeUserId;
    /** 参团用户头像 */
    private String            takeProfile;
    /** 是否团长 */
    private Integer           isHead;
    /** 是否付款 **/
    private Integer           isPay;

    public Long getGrouponId() {
        return grouponId;
    }

    public void setGrouponId(Long grouponId) {
        this.grouponId = grouponId;
    }

    public Long getActGoodsId() {
        return actGoodsId;
    }

    public void setActGoodsId(Long actGoodsId) {
        this.actGoodsId = actGoodsId;
    }

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }

    public Long getTakeUserId() {
        return takeUserId;
    }

    public void setTakeUserId(Long takeUserId) {
        this.takeUserId = takeUserId;
    }

    public String getTakeProfile() {
        return takeProfile;
    }

    public void setTakeProfile(String takeProfile) {
        this.takeProfile = takeProfile;
    }

    public Integer getIsHead() {
        return isHead;
    }

    public void setIsHead(Integer isHead) {
        this.isHead = isHead;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

}
