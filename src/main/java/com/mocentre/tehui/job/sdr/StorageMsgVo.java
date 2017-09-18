/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job.sdr;

import java.io.Serializable;

/**
 * 类StorageVo.java的实现描述：库存对象
 * 
 * @author sz.gong 2017年3月9日 下午2:10:00
 */
public class StorageMsgVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3531949117140162274L;

    /** 商品id **/
    private Long              goodsId;

    /** 规格编码 **/
    private String            standardCode;

    /** 活动商品关联id */
    private Long              subGoodsId;

    /** 增减数量（正数或负数） **/
    private Integer           needNum;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getStandardCode() {
        return standardCode;
    }

    public void setStandardCode(String standardCode) {
        this.standardCode = standardCode;
    }

    public Long getSubGoodsId() {
        return subGoodsId;
    }

    public void setSubGoodsId(Long subGoodsId) {
        this.subGoodsId = subGoodsId;
    }

    public Integer getNeedNum() {
        return needNum;
    }

    public void setNeedNum(Integer needNum) {
        this.needNum = needNum;
    }

}
