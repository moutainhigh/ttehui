package com.mocentre.tehui.weixin.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 微信分享配置
 * Created by 王雪莹 on 2017/05/04.
 */
public class ShareConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /** 标题 **/
    private String  title;
    /** 分享url **/
    private String  linkUrl;
    /** 分享描述 **/
    private String  description;
    /** 分享图片地址 **/
    private String  imgUrl;
    /** 分享类型 music、video或link **/
    private String  type;
    /** 如果type是music或video，则要提供数据链接，默认为空 **/
    private String  dataUrl;
    /** 是否默认 0:是 1:否 **/
    private Integer isDefault;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}
