package com.mocentre.gift.gd.mapper;

import com.mocentre.gift.backend.model.GiftCategoryInstance;
import com.mocentre.gift.backend.param.GiftCategoryParam;
import com.mocentre.gift.frontend.model.GiftCategoryFTInstance;
import com.mocentre.gift.gd.model.GiftCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * 礼品平台分类实体转换类.
 *
 * Created by yukaiji on 2017/4/6.
 */
public class GiftCategoryMapper {

    public static GiftCategory toGiftCatgory(GiftCategoryParam giftCategoryParam){
        GiftCategory giftCategory = new GiftCategory();
        giftCategory.setId(giftCategoryParam.getId());
        giftCategory.setcType(giftCategoryParam.getCtype());
        giftCategory.setIsShow(giftCategoryParam.getIsShow());
        giftCategory.setName(giftCategoryParam.getName());
        giftCategory.setPid(giftCategoryParam.getPid());
        giftCategory.setSorting(giftCategoryParam.getSorting());
        return giftCategory;
    }

    public static GiftCategoryInstance toGiftCatgoryIns(GiftCategory giftCategory){
        GiftCategoryInstance giftCategoryInstance = new GiftCategoryInstance();
        giftCategoryInstance.setId(giftCategory.getId());
        giftCategoryInstance.setcType(giftCategory.getcType());
        giftCategoryInstance.setIsShow(giftCategory.getIsShow());
        giftCategoryInstance.setName(giftCategory.getName());
        giftCategoryInstance.setPid(giftCategory.getPid());
        giftCategoryInstance.setGmtCreated(giftCategory.getGmtCreated());
        giftCategoryInstance.setGmtModified(giftCategory.getGmtModified());
        giftCategoryInstance.setSorting(giftCategory.getSorting());
        return giftCategoryInstance;
    }

    public static List<GiftCategoryInstance> toGiftCatgoryInsList(List<GiftCategory> giftCategoryList){
        List<GiftCategoryInstance> categoryInstanceList = new ArrayList<GiftCategoryInstance>();
        if (giftCategoryList == null || giftCategoryList.size() < 1){
            return categoryInstanceList;
        }

        for (GiftCategory giftCategory : giftCategoryList) {
            categoryInstanceList.add(toGiftCatgoryIns(giftCategory));
        }
        return categoryInstanceList;
    }

    public static GiftCategoryFTInstance toGiftCatgoryFTIns(GiftCategory giftCategory){
        GiftCategoryFTInstance ftInstance = new GiftCategoryFTInstance();
        ftInstance.setId(giftCategory.getId());
        ftInstance.setcType(giftCategory.getcType());
        ftInstance.setIsShow(giftCategory.getIsShow());
        ftInstance.setName(giftCategory.getName());
        ftInstance.setPid(giftCategory.getPid());
        ftInstance.setGmtCreated(giftCategory.getGmtCreated());
        ftInstance.setGmtModified(giftCategory.getGmtModified());
        ftInstance.setSorting(giftCategory.getSorting());
        return ftInstance;
    }

    public static List<GiftCategoryFTInstance> toGiftCatgoryFTInsList(List<GiftCategory> giftCategoryList){
        List<GiftCategoryFTInstance> ftInstanceList = new ArrayList<GiftCategoryFTInstance>();
        if (giftCategoryList == null || giftCategoryList.size() < 1){
            return ftInstanceList;
        }

        for (GiftCategory giftCategory : giftCategoryList) {
            ftInstanceList.add(toGiftCatgoryFTIns(giftCategory));
        }
        return ftInstanceList;
    }
}
