package com.mocentre.tehui.act.model;

import java.util.List;

import com.mocentre.tehui.core.model.BaseEntity;
import com.mocentre.tehui.system.model.Image;

/**
 * 活动基本信息实体类.
 * 
 * @author Created by yukaiji on 2017年1月16日
 */
public class Activity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 店铺id */
    private Long              shopId;
    /** 标题 */
    private String            title;
    /** 是否展示 */
    private Integer           isShow;
    /** 简介 */
    private String            intro;
    /** 排序 */
    private Integer           sorting;
    /** 所属类别(seckill秒杀，groupon秒杀) */
    private String            type;
    /** banner图 */
    private List<Image>       imageList;
    /** 展示位置 **/
    private String            showLocal;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public String getShowLocal() {
        return showLocal;
    }

    public void setShowLocal(String showLocal) {
        this.showLocal = showLocal;
    }

}
