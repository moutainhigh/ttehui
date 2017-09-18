package com.mocentre.tehui.goods.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mocentre.tehui.backend.model.GoodsStorageInstance;
import com.mocentre.tehui.backend.param.GoodsStorageParam;
import com.mocentre.tehui.goods.model.GoodsStorage;

/**
 * Created by 王雪莹 on 2016/12/19.
 */
public class GoodsStorageMapper {

    public static GoodsStorage toGoodsStorage(GoodsStorageParam param) {
        GoodsStorage goodsStorage = new GoodsStorage();
        goodsStorage.setId(param.getId());
        goodsStorage.setSubGoodsId(param.getSubGoodsId());
        goodsStorage.setDescribe(param.getDescribe());
        goodsStorage.setGoodsId(param.getGoodsId());
        BigDecimal oldPrice = param.getOldPrice();
        BigDecimal salePrice = param.getSalePrice();
        goodsStorage.setOldPrice(oldPrice == null ? 0 : oldPrice.multiply(new BigDecimal(100)).longValue());
        goodsStorage.setSalePrice(salePrice == null ? 0 : salePrice.multiply(new BigDecimal(100)).longValue());
        goodsStorage.setStandardCode(param.getStandardCode());
        goodsStorage.setStockNum(param.getStockNum());
        goodsStorage.setVersion(param.getVersion());
        return goodsStorage;
    }

    public static List<GoodsStorage> toGoodsStorageList(List<GoodsStorageParam> paramList) {
        ArrayList<GoodsStorage> resultList = new ArrayList<GoodsStorage>();
        for (GoodsStorageParam param : paramList) {
            GoodsStorage goodsStorage = toGoodsStorage(param);
            resultList.add(goodsStorage);
        }
        return resultList;
    }

    public static GoodsStorageInstance toGoodsStorageIns(GoodsStorage goodsStorage) {
        GoodsStorageInstance goodsStorageInstance = new GoodsStorageInstance();
        goodsStorageInstance.setGoodsId(goodsStorage.getGoodsId());
        goodsStorageInstance.setSubGoodsId(goodsStorage.getSubGoodsId());
        goodsStorageInstance.setId(goodsStorage.getId());
        Long oldPrice = goodsStorage.getOldPrice();
        Long salePrice = goodsStorage.getSalePrice();
        goodsStorageInstance.setOldPrice(oldPrice == null ? new BigDecimal(0) : new BigDecimal(oldPrice)
                .divide(new BigDecimal(100)));
        goodsStorageInstance.setSalePrice(salePrice == null ? new BigDecimal(0) : new BigDecimal(salePrice)
                .divide(new BigDecimal(100)));
        goodsStorageInstance.setStandardCode(goodsStorage.getStandardCode());
        goodsStorageInstance.setStockNum(goodsStorage.getStockNum());
        goodsStorageInstance.setVersion(goodsStorage.getVersion());
        goodsStorageInstance.setDescribe(goodsStorage.getDescribe());
        return goodsStorageInstance;
    }

    public static List<GoodsStorageInstance> toGoodsStorageInstancesList(List<GoodsStorage> goodsStorageList) {
        List<GoodsStorageInstance> gsInsList = new ArrayList<GoodsStorageInstance>();
        if (goodsStorageList != null && goodsStorageList.size() > 0) {
            for (GoodsStorage goodsStorage : goodsStorageList) {
                gsInsList.add(toGoodsStorageIns(goodsStorage));
            }
        }
        return gsInsList;
    }

}
