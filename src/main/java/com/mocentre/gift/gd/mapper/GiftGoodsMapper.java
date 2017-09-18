package com.mocentre.gift.gd.mapper;

import com.mocentre.gift.backend.model.GiftGoodsInstance;
import com.mocentre.gift.backend.model.GiftGoodsSelectInstance;
import com.mocentre.gift.backend.param.GiftGoodsParam;
import com.mocentre.gift.frontend.model.GiftGoodsFTInstance;
import com.mocentre.gift.gd.model.GiftGoods;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 礼品平台礼品实体转换类.
 * <p>
 * Created by yukaiji on 2017/4/6.
 */
public class GiftGoodsMapper {

    public static GiftGoods toGiftGoods(GiftGoodsParam giftGoodsParam) {
        GiftGoods giftGoods = new GiftGoods();
        giftGoods.setCategoryId(giftGoodsParam.getCategoryId());
        giftGoods.setTitle(giftGoodsParam.getTitle());
        giftGoods.setDescribe(giftGoodsParam.getDescribe());
        giftGoods.setDetails(giftGoodsParam.getDetails());
        giftGoods.setImgBanner(giftGoodsParam.getImgBanner());
        giftGoods.setImgListPageMobile(giftGoodsParam.getImgListPageMobile());
        giftGoods.setImgListPagePc(giftGoodsParam.getImgListPagePc());
        giftGoods.setIsChecked(giftGoodsParam.getIsChecked());
        giftGoods.setIsShow(giftGoodsParam.getIsShow());
        if (giftGoodsParam.getLimitNums() == null) {
            giftGoods.setIsLimitBuy(0);
        } else {
            giftGoods.setIsLimitBuy(1);
        }
        giftGoods.setDeliveryType(giftGoodsParam.getDeliveryType());
        giftGoods.setLimitNums(giftGoodsParam.getLimitNums());
        if (giftGoodsParam.getPrice() != null) {
            giftGoods.setPrice(giftGoodsParam.getPrice().multiply(new BigDecimal(100)));
        }
        giftGoods.setSorting(giftGoodsParam.getSorting());
        giftGoods.setId(giftGoodsParam.getId());
        return giftGoods;
    }

    public static GiftGoodsInstance toGiftGoodsIns(GiftGoods giftGoods) {
        GiftGoodsInstance giftGoodsInstance = new GiftGoodsInstance();
        giftGoodsInstance.setCategoryId(giftGoods.getCategoryId());
        giftGoodsInstance.setTitle(giftGoods.getTitle());
        giftGoodsInstance.setDescribe(giftGoods.getDescribe());
        giftGoodsInstance.setDetails(giftGoods.getDetails());
        giftGoodsInstance.setImgBanner(giftGoods.getImgBanner());
        giftGoodsInstance.setImgListPageMobile(giftGoods.getImgListPageMobile());
        giftGoodsInstance.setImgListPagePc(giftGoods.getImgListPagePc());
        giftGoodsInstance.setIsChecked(giftGoods.getIsChecked());
        giftGoodsInstance.setIsLimitBuy(giftGoods.getIsLimitBuy());
        giftGoodsInstance.setDeliveryType(giftGoods.getDeliveryType());
        giftGoodsInstance.setIsShow(giftGoods.getIsShow());
        giftGoodsInstance.setLimitNums(giftGoods.getLimitNums());
        if (null != giftGoods.getPrice()) {
            giftGoodsInstance.setPrice(String.valueOf(giftGoods.getPrice().divide(new BigDecimal(100))));
        } else {
            giftGoodsInstance.setPrice("暂无报价");
        }
        giftGoodsInstance.setSorting(giftGoods.getSorting());
        giftGoodsInstance.setId(giftGoods.getId());
        return giftGoodsInstance;
    }

    public static List<GiftGoodsInstance> toGiftGoodsInsList(List<GiftGoods> giftGoodsList) {
        List<GiftGoodsInstance> goodsInstanceList = new ArrayList<GiftGoodsInstance>();
        if (giftGoodsList == null || giftGoodsList.size() < 1) {
            return goodsInstanceList;
        }
        for (GiftGoods GiftGoods : giftGoodsList) {
            goodsInstanceList.add(toGiftGoodsIns(GiftGoods));
        }
        return goodsInstanceList;
    }


    public static GiftGoodsFTInstance toGiftGoodsFTIns(GiftGoods giftGoods) {
        GiftGoodsFTInstance giftGoodsFTInstance = new GiftGoodsFTInstance();
        giftGoodsFTInstance.setCategoryId(giftGoods.getCategoryId());
        giftGoodsFTInstance.setTitle(giftGoods.getTitle());
        giftGoodsFTInstance.setDescribe(giftGoods.getDescribe());
        giftGoodsFTInstance.setDetails(giftGoods.getDetails());
        giftGoodsFTInstance.setImgBanner(giftGoods.getImgBanner());
        giftGoodsFTInstance.setImgListPageMobile(giftGoods.getImgListPageMobile());
        giftGoodsFTInstance.setImgListPagePc(giftGoods.getImgListPagePc());
        giftGoodsFTInstance.setIsLimitBuy(giftGoods.getIsLimitBuy());
        giftGoodsFTInstance.setDeliveryType(giftGoods.getDeliveryType());
        giftGoodsFTInstance.setLimitNums(giftGoods.getLimitNums());
        if (null != giftGoods.getPrice()) {
            giftGoodsFTInstance.setPrice(String.valueOf(giftGoods.getPrice().divide(new BigDecimal(100))));
        } else {
            giftGoodsFTInstance.setPrice("暂无报价");
        }
        giftGoodsFTInstance.setSorting(giftGoods.getSorting());
        giftGoodsFTInstance.setId(giftGoods.getId());
        return giftGoodsFTInstance;
    }

    public static List<GiftGoodsFTInstance> toGiftGoodsFTInsList(List<GiftGoods> giftGoodsList) {
        List<GiftGoodsFTInstance> goodsFTInstanceList = new ArrayList<GiftGoodsFTInstance>();
        if (giftGoodsList == null || giftGoodsList.size() < 1) {
            return goodsFTInstanceList;
        }
        for (GiftGoods GiftGoods : giftGoodsList) {
            goodsFTInstanceList.add(toGiftGoodsFTIns(GiftGoods));
        }
        return goodsFTInstanceList;
    }

    public static GiftGoodsSelectInstance toGiftGoodsSelectInstance(GiftGoods giftGoods) {
        GiftGoodsSelectInstance ins = new GiftGoodsSelectInstance();
        ins.setPrice(giftGoods.getPrice().toString());
        ins.setTitle(giftGoods.getTitle());
        ins.setId(giftGoods.getId());
        return ins;
    }
    public static List<GiftGoodsSelectInstance> toGiftGoodsSelectInstanceList(List<GiftGoods> giftGoodsList) {
        ArrayList<GiftGoodsSelectInstance> insList = new ArrayList<>();
        for(GiftGoods gift:giftGoodsList){
            insList.add(toGiftGoodsSelectInstance(gift));
        }
        return insList;
    }

}
