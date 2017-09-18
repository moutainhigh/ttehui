package com.mocentre.tehui.act.model;

import java.util.Date;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 开团信息详情实体类
 * 
 * @author Created by yukaiji on 2017年02月09日
 */
public class Groupon extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 活动商品id */
    private Long              actGoodsId;
    /** 开团用户id */
    private Long              openUserId;
    /** 开团用户头像 */
    private String            openProfile;
    /** 团长开团时间 */
    private Date              openTime;
    /** 拼团结束时间 */
    private Date              closeTime;
    /** 商品开始时间 */
    private Date              beginTime;
    /** 商品结束时间 */
    private Date              endTime;
    /** 实际参团人数 */
    private Integer           takeNum;
    /** 需参团人数 **/
    private Integer           grouponNum;
    /** 是否付款 **/
    private Integer           isPay;
    /** 成团状态（ ungroup:未成团 grouped:已成团 grouping:成团中） **/
    private String            groupStatus;
    /** 成团时间 **/
    private Date              groupTime;
    /** 是否处理 **/
    private Integer           isDeal;

    public Long getActGoodsId() {
        return actGoodsId;
    }

    public void setActGoodsId(Long actGoodsId) {
        this.actGoodsId = actGoodsId;
    }

    public Long getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(Long openUserId) {
        this.openUserId = openUserId;
    }

    public String getOpenProfile() {
        return openProfile;
    }

    public void setOpenProfile(String openProfile) {
        this.openProfile = openProfile;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTakeNum() {
        return takeNum;
    }

    public void setTakeNum(Integer takeNum) {
        this.takeNum = takeNum;
    }

    public Integer getGrouponNum() {
        return grouponNum;
    }

    public void setGrouponNum(Integer grouponNum) {
        this.grouponNum = grouponNum;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(String groupStatus) {
        this.groupStatus = groupStatus;
    }

    public Date getGroupTime() {
        return groupTime;
    }

    public void setGroupTime(Date groupTime) {
        this.groupTime = groupTime;
    }

    public Integer getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Integer isDeal) {
        this.isDeal = isDeal;
    }

}
