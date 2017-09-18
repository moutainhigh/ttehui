package com.mocentre.tehui.goods.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 商品分享表 Created by 王雪莹 on 2016/11/16.
 */
public class GoodsShare extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -1590283096876264822L;

    /**
     * 商品id
     */
    private Long              goodsId;

    /**
     * 分享标题
     */
    private String            title;

    /**
     * 分享图片
     */
    private String            sharePic;

    /**
     * 分享描述
     */
    private String            description;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
