package com.mocentre.tehui.system.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类Image.java的实现描述：图片实体类
 * 
 * @author sz.gong 2016年11月17日 上午11:19:43
 */
public class Image extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 图片类型（logo:logo thumbnail:缩略图 banner:banner other:其他） **/
    private String            type;

    /** 来源类型（seckill:秒杀 groupon:团购 common:普通） **/
    private String            sourceType;

    /** 图片位置（goods:商品 discover:发现页） **/
    private String            seat;

    /** 对应图片位置id **/
    private Long              seatId;

    /** 图片地址 **/
    private String            location;

    /** 链接地址 **/
    private String            linkUrl;

    /** 排序 **/
    private Integer           sorting;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}
