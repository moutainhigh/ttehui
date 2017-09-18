package com.mocentre.gift.mall.mapper;

import com.mocentre.gift.backend.model.GiftBannerInstance;
import com.mocentre.gift.backend.param.GiftBannerParam;
import com.mocentre.gift.frontend.model.GiftBannerFTInstance;
import com.mocentre.gift.mall.model.GiftBanner;

import java.util.ArrayList;
import java.util.List;

public class GiftBannerMapper {

	/**
     * 转前台返回实体类
     */
    public static GiftBannerInstance toGiftBannerInstance(GiftBanner giftBanner) {
        GiftBannerInstance giftBannerInstance = new GiftBannerInstance();
        giftBannerInstance.setId(giftBanner.getId());
        giftBannerInstance.setName(giftBanner.getName());
        giftBannerInstance.setSort(giftBanner.getSort());
        giftBannerInstance.setLinkUrl(giftBanner.getLinkUrl());
        giftBannerInstance.setMobImg(giftBanner.getMobImg());
        giftBannerInstance.setIsShow(giftBanner.getIsShow());
        giftBannerInstance.setGmtCreated(giftBanner.getGmtCreated());
        giftBannerInstance.setGmtModified(giftBanner.getGmtModified());
		giftBannerInstance.setGoodsId(giftBanner.getGoodsId());
		giftBannerInstance.setStatus(giftBanner.getStatus());
        return giftBannerInstance;
    }
    
    /**
     * 转前台返回值列表
     */
    public static List<GiftBannerInstance> toGiftBannerInstanceList(List<GiftBanner> giftBannerList) {
        List<GiftBannerInstance> giftBannerInsList = new ArrayList<GiftBannerInstance>();
        if (giftBannerList == null || giftBannerList.size() < 1) {
            return giftBannerInsList;
        }

        for (GiftBanner actGoods : giftBannerList) {
        	giftBannerInsList.add(toGiftBannerInstance(actGoods));
        }
        return giftBannerInsList;
    }

	public static GiftBanner toGiftBanner(GiftBannerParam giftBannerParam) {
		GiftBanner giftBanner = new GiftBanner();
		giftBanner.setId(giftBannerParam.getId());
		giftBanner.setName(giftBannerParam.getName());
		giftBanner.setSort(giftBannerParam.getSort());
		giftBanner.setLinkUrl(giftBannerParam.getLinkUrl());
		giftBanner.setPcImg(giftBannerParam.getPcImg());
		giftBanner.setMobImg(giftBannerParam.getMobImg());
		giftBanner.setIsShow(giftBannerParam.getIsShow());
		giftBanner.setGoodsId(giftBannerParam.getGoodsId());
		giftBanner.setStatus(giftBannerParam.getStatus());
		return giftBanner;
	}
	
	public static GiftBannerParam toGiftBannerParam(GiftBanner giftBanner) {
		GiftBannerParam giftBannerParam = new GiftBannerParam();
		giftBannerParam.setId(giftBanner.getId());
		giftBannerParam.setName(giftBanner.getName());
		giftBannerParam.setSort(giftBanner.getSort());
		giftBannerParam.setLinkUrl(giftBanner.getLinkUrl());
		giftBannerParam.setPcImg(giftBanner.getPcImg());
		giftBannerParam.setMobImg(giftBanner.getMobImg());
		giftBannerParam.setIsShow(giftBanner.getIsShow());
		giftBannerParam.setGoodsId(giftBanner.getGoodsId());
		giftBannerParam.setStatus(giftBanner.getStatus());
		return giftBannerParam;
	}

	public static GiftBannerFTInstance toGiftBannerFTInstance(GiftBanner giftBanner) {
		GiftBannerFTInstance ftInstance = new GiftBannerFTInstance();
		ftInstance.setLinkUrl(giftBanner.getLinkUrl());
		ftInstance.setPcImg(giftBanner.getPcImg());
		ftInstance.setMobImg(giftBanner.getMobImg());
		ftInstance.setStatus(giftBanner.getStatus());
		ftInstance.setGoodsId(giftBanner.getGoodsId());
		return ftInstance;
	}

	public static List<GiftBannerFTInstance> toGiftBannerFTInstanceList(List<GiftBanner> giftBannerList) {
		List<GiftBannerFTInstance> ftInstanceList = new ArrayList<GiftBannerFTInstance>();
		if (giftBannerList == null || giftBannerList.size() < 1) {
			return ftInstanceList;
		}

		for (GiftBanner actGoods : giftBannerList) {
			ftInstanceList.add(toGiftBannerFTInstance(actGoods));
		}
		return ftInstanceList;
	}
}
