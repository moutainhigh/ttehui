package com.mocentre.gift.gd.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 礼品平台分类实体类。
 *
 * Created by yukaiji on 2017/4/6.
 */
public class GiftCategory extends BaseEntity {

    private static final long   serialVersionUID = 1L;
    /** 父类id */
    private Long                pid;
    /** 分类名称 */
    private String              name;
    /** 是否展示（1 展示， 0 不展示） */
    private Integer             isShow;
    /** 分类类型（virtual 虚拟， material 实物） */
    private String              cType;
    /** 排序 */
    private Integer             sorting;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }
}
