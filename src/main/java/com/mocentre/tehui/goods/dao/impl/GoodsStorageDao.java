package com.mocentre.tehui.goods.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.common.constant.RedisKeyConstant;
import com.mocentre.tehui.core.dao.BaseRedisDao;
import com.mocentre.tehui.goods.dao.IGoodsStorageDao;
import com.mocentre.tehui.goods.model.GoodsStorage;

/**
 * 商品库存dao实现 Created by 王雪莹 on 2016/11/5.
 */
@Repository
public class GoodsStorageDao extends BaseRedisDao<GoodsStorage, String, GoodsStorage> implements IGoodsStorageDao {

    @Override
    public void saveEntity(final List<GoodsStorage> goodsStorageList) {
        super.insert("GoodsStorage_insert_List", goodsStorageList);
    }

    @Override
    public int delByGoodsId(Long goodsId) {
        return super.delete("GoodsStorage_del_goodsId", goodsId);
    }

    @Override
    public int delByGoodsId(Long goodsId, Long subGoodsId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("goodsId", goodsId);
        map.put("subGoodsId", subGoodsId);
        return super.delete("GoodsStorage_del_goodsIdAndSubGoodsId", map);
    }

    @Override
    public List<GoodsStorage> queryListByGoodsId(Long goodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("goodsId", goodsId);
        return super.queryList(paramMap);
    }

    @Override
    public List<GoodsStorage> queryByGoods(Long goodsId, Long subGoodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("goodsId", goodsId);
        paramMap.put("subGoodsId", subGoodsId);
        return super.queryList(paramMap);
    }

    @Override
    public Long queryTotalStocknumByGoosid(Long goodsId, Long subGoodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("goodsId", goodsId);
        paramMap.put("subGoodsId", subGoodsId);
        Long num = super.queryUniquely("GoodsStorage_TotalStocknum_Goosid", paramMap);
        return num == null ? 0 : num;
    }

    @Override
    public GoodsStorage queryByStandard(Long goodsId, String standardCode, Long subGoodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("standardCode", standardCode);
        paramMap.put("goodsId", goodsId);
        paramMap.put("subGoodsId", subGoodsId);
        return super.queryUniquely(paramMap);
    }

    @Override
    public int updateNeedStockNum(Long goodsId, String standardCode, Long subGoodsId, Long needNum) {
        GoodsStorage storage = new GoodsStorage();
        storage.setGoodsId(goodsId);
        storage.setStandardCode(standardCode);
        storage.setSubGoodsId(subGoodsId);
        storage.setStockNum(needNum);
        return super.update("GoodsStorage_update_neednum", storage);
    }

    @Override
    public int updateStockNum(Long goodsId, String standardCode, Long subGoodsId, Long stockNum) {
        GoodsStorage storage = new GoodsStorage();
        storage.setGoodsId(goodsId);
        storage.setStandardCode(standardCode);
        storage.setSubGoodsId(subGoodsId);
        storage.setStockNum(stockNum);
        return super.update("GoodsStorage_update_stocknum", storage);
    }

    //////////////////////////////////////////缓存操作///////////////////////////////////////
    @Override
    public GoodsStorage queryFromCache(Long goodsId, String standardCode, Long subGoodsId) {
        String key = RedisKeyConstant.GOODS_STORAGE_LIST;
        String hashKey = goodsId + standardCode + subGoodsId;
        Object value = super.getCacheHash(key, hashKey);
        if (value == null) {
            GoodsStorage goodsStorage = this.queryByStandard(goodsId, standardCode, subGoodsId);
            if (goodsStorage != null) {
                goodsStorage.setStockNum(goodsStorage.getStockNum());
                this.updateToCache(goodsStorage);
            }
            return goodsStorage;
        } else {
            GoodsStorage goodsStorage = (GoodsStorage) value;
            String numkey = RedisKeyConstant.GOODS_STOCKNUM + ":" + hashKey;
            Object numValue = super.getIncrValue(numkey);
            if (numValue == null) {
                this.updateStockNumToCache(goodsStorage.getGoodsId(), goodsStorage.getStandardCode(),
                        goodsStorage.getSubGoodsId(), goodsStorage.getStockNum());
            } else {
                goodsStorage.setStockNum((Long) numValue);
            }
            return goodsStorage;
        }
    }

    @Override
    public Long queryStockNumFromCache(Long goodsId, String standardCode, Long subGoodsId) {
        String pxKey = RedisKeyConstant.GOODS_STOCKNUM;
        String hashKey = goodsId + standardCode + subGoodsId;
        Long value = getIncrValue(pxKey + ":" + hashKey);
        if (value == null) {
            GoodsStorage goodsStorage = this.queryByStandard(goodsId, standardCode, subGoodsId);
            Long stockNum = 0l;
            if (goodsStorage != null) {
                stockNum = goodsStorage.getStockNum();
                this.updateStockNumToCache(goodsId, standardCode, subGoodsId, stockNum);
            }
            return stockNum;
        } else {
            return (Long) value;
        }
    }

    @Override
    public void updateToCache(GoodsStorage goodsStorage) {
        String key = RedisKeyConstant.GOODS_STORAGE_LIST;
        Long goodsId = goodsStorage.getGoodsId();
        String stcode = goodsStorage.getStandardCode();
        Long subGoodsId = goodsStorage.getSubGoodsId();
        Long stockNum = goodsStorage.getStockNum();
        String hashKey = goodsId + stcode + subGoodsId;
        super.cacheHash(key, hashKey, goodsStorage);
        this.updateStockNumToCache(goodsId, stcode, subGoodsId, stockNum);
    }

    @Override
    public void updateStockNumToCache(Long goodsId, String standardCode, Long actGoodsId, Long nums) {
        String pxKey = RedisKeyConstant.GOODS_STOCKNUM;
        String hashKey = goodsId + standardCode + actGoodsId;
        String key = pxKey + ":" + hashKey;
        super.cacheValueIncr(key, nums);
    }

    @Override
    public void updateBatchToCache(List<GoodsStorage> goodsStorageList) {
        String key = RedisKeyConstant.GOODS_STORAGE_LIST;
        if (goodsStorageList != null && goodsStorageList.size() > 0) {
            Map<String, GoodsStorage> map = new HashMap<String, GoodsStorage>();
            for (GoodsStorage storage : goodsStorageList) {
                String hashKey = storage.getGoodsId() + storage.getStandardCode() + storage.getSubGoodsId();
                map.put(hashKey, storage);
                this.updateStockNumToCache(storage.getGoodsId(), storage.getStandardCode(), storage.getSubGoodsId(),
                        storage.getStockNum());
            }
            super.cacheHash(key, map);
        }
    }

    @Override
    public void updateStockNumAndCache(Long goodsId, String standardCode, Long actGoodsId, Long nums) {
        this.updateNeedStockNum(goodsId, standardCode, actGoodsId, nums);
        this.updateStockNumToCache(goodsId, standardCode, actGoodsId, nums);
    }

    @Override
    public void deleteFromCache(Long goodsId, Long subGoodsId) {
        List<GoodsStorage> storageList = this.queryByGoods(goodsId, subGoodsId);
        this.deleteFromCache(storageList);
    }

    @Override
    public void deleteFromCache(List<GoodsStorage> goodsStorageList) {
        String key = RedisKeyConstant.GOODS_STORAGE_LIST;
        String pxKey = RedisKeyConstant.GOODS_STOCKNUM;
        if (goodsStorageList != null && goodsStorageList.size() > 0) {
            for (GoodsStorage storage : goodsStorageList) {
                String hashKey = storage.getGoodsId() + storage.getStandardCode() + storage.getSubGoodsId();
                super.removeCacheHash(key, hashKey);
                super.removeCacheValue(pxKey + ":" + hashKey);
            }
        }
    }

}
