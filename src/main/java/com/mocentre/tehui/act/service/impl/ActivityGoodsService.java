package com.mocentre.tehui.act.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.act.dao.IActivityGoodsDao;
import com.mocentre.tehui.act.enums.ActivityType;
import com.mocentre.tehui.act.model.ActivityGoods;
import com.mocentre.tehui.act.service.IActivityGoodsService;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.dao.IGoodsStorageDao;
import com.mocentre.tehui.goods.model.GoodsStorage;
import com.mocentre.tehui.job.queue.TokenQueue;

/**
 * 活动商品关联接口实现 Created by yukaiji on 2017/1/16.
 */

@Component
public class ActivityGoodsService implements IActivityGoodsService {

    @Autowired
    private IActivityGoodsDao activityGoodsDao;
    @Autowired
    private IGoodsStorageDao  goodsStorageDao;
    @Autowired
    private TokenQueue        tokenQueue;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<ActivityGoods> queryActivityPage(Requirement require) {
        return activityGoodsDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "read")
    public ActivityGoods getActivityGoodsById(Long id) {
        return activityGoodsDao.get(id);
    }

    @Override
    @DataSource(value = "read")
    public List<ActivityGoods> getActivityGoodsListByActivityId(Long activityId) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("activityId", activityId);
        return activityGoodsDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public ActivityGoods insertActivityGoods(ActivityGoods actGoods, List<GoodsStorage> storageList) {
        Map<String, String> priceMap = this.calcPrice(storageList);
        String salePrice = priceMap.get("salePrice");
        String oldPrice = priceMap.get("oldPrice");
        String saleLowPrice = priceMap.get("saleLowPrice");
        String oldLowPrice = priceMap.get("oldLowPrice");
        actGoods.setSellPrice(salePrice);
        actGoods.setOldPrice(oldPrice);
        actGoods.setSellLowPrice(new BigDecimal(saleLowPrice).multiply(new BigDecimal(100)).longValue());
        actGoods.setOldLowPrice(new BigDecimal(oldLowPrice).multiply(new BigDecimal(100)).longValue());
        String holdStandard = "";
        if (storageList != null && storageList.size() > 0) {
            JSONArray holdArr = new JSONArray();
            for (GoodsStorage goodsStorage : storageList) {
                String stcode = goodsStorage.getStandardCode();
                if (StringUtils.isBlank(stcode)) {
                    return null;
                }
                String stname = goodsStorage.getDescribe();
                JSONObject jobj = new JSONObject();
                jobj.put("code", stcode);
                jobj.put("name", stname);
                holdArr.add(jobj);
            }
            holdStandard = holdArr.toJSONString();
        }
        actGoods.setHoldStandard(holdStandard);
        activityGoodsDao.saveEntity(actGoods);
        Long goodsId = actGoods.getGoodsId();
        Long id = actGoods.getId();
        if (id != null) {
            activityGoodsDao.updateToCache(actGoods);
            if (storageList != null && storageList.size() > 0) {
                this.updateActGoodsStorageAndCache(goodsId, id, storageList);
                //秒杀增加库存令牌
                String actType = actGoods.getActivityType();
                if (ActivityType.SECKILL.getCodeValue().equals(actType)) {
                    for (GoodsStorage goodsStorage : storageList) {
                        String key = goodsId + goodsStorage.getStandardCode() + id;
                        tokenQueue.pushFromHead(key, goodsStorage.getStockNum().intValue());
                    }
                }
            }
        }
        return actGoods;
    }

    @Override
    @DataSource(value = "write")
    public Long updateActivityGoods(ActivityGoods actGoods, List<GoodsStorage> storageList) {
        if (storageList == null || storageList.size() == 0) {
            return 0l;
        }
        Long id = actGoods.getId();
        Map<String, String> priceMap = this.calcPrice(storageList);
        String salePrice = priceMap.get("salePrice");
        String oldPrice = priceMap.get("oldPrice");
        String saleLowPrice = priceMap.get("saleLowPrice");
        String oldLowPrice = priceMap.get("oldLowPrice");
        actGoods.setSellPrice(salePrice);
        actGoods.setOldPrice(oldPrice);
        actGoods.setSellLowPrice(new BigDecimal(saleLowPrice).multiply(new BigDecimal(100)).longValue());
        actGoods.setOldLowPrice(new BigDecimal(oldLowPrice).multiply(new BigDecimal(100)).longValue());
        JSONArray holdArr = new JSONArray();
        for (GoodsStorage goodsStorage : storageList) {
            String stcode = goodsStorage.getStandardCode();
            if (StringUtils.isBlank(stcode)) {
                return 0l;
            }
            String stname = goodsStorage.getDescribe();
            JSONObject jobj = new JSONObject();
            jobj.put("code", stcode);
            jobj.put("name", stname);
            holdArr.add(jobj);
        }
        String holdStandard = holdArr.toJSONString();
        actGoods.setHoldStandard(holdStandard);
        Long updateNum = activityGoodsDao.updateEntity(actGoods);
        if (updateNum >= 0) {
            Long goodsId = actGoods.getGoodsId();
            activityGoodsDao.updateToCache(actGoods);
            if (storageList != null && storageList.size() > 0) {
                this.updateActGoodsStorageAndCache(goodsId, id, storageList);
                //秒杀增加库存令牌
                String actType = actGoods.getActivityType();
                if (ActivityType.SECKILL.getCodeValue().equals(actType)) {
                    for (GoodsStorage goodsStorage : storageList) {
                        String key = goodsId + goodsStorage.getStandardCode() + id;
                        tokenQueue.clean(key);
                        tokenQueue.pushFromHead(key, goodsStorage.getStockNum().intValue());
                    }
                }
            }
        }
        return updateNum;
    }

    /**
     * 更新活动库存商品到库存和缓存
     * 
     * @param goodsId 商品id
     * @param subGoodsId 活动商品关联id
     * @param storageList 库存list
     */
    @DataSource(value = "write")
    private void updateActGoodsStorageAndCache(Long goodsId, Long subGoodsId, List<GoodsStorage> storageList) {
        List<GoodsStorage> oldStorageList = goodsStorageDao.queryByGoods(goodsId, subGoodsId);
        if (oldStorageList != null && oldStorageList.size() > 0) {
            goodsStorageDao.delByGoodsId(goodsId, subGoodsId);
        }
        for (GoodsStorage goodsStorage : storageList) {
            goodsStorage.setSubGoodsId(subGoodsId);
            goodsStorage.setGoodsId(goodsId);
        }
        goodsStorageDao.saveEntity(storageList);
        if (oldStorageList != null && oldStorageList.size() > 0) {
            goodsStorageDao.deleteFromCache(oldStorageList);
        }
        goodsStorageDao.updateBatchToCache(storageList);
    }

    //计算售价和原价
    private Map<String, String> calcPrice(List<GoodsStorage> storageList) {
        Map<String, String> map = new HashMap<String, String>();
        GoodsStorage firstStorage = storageList.get(0);
        GoodsStorage lastStorage = storageList.get(storageList.size() - 1);
        Collections.sort(storageList, new Comparator<GoodsStorage>() {
            @Override
            public int compare(GoodsStorage o1, GoodsStorage o2) {
                return o1.getSalePrice().compareTo(o2.getSalePrice());
            }
        });
        String salePrice;
        BigDecimal sfPrice = new BigDecimal(firstStorage.getSalePrice()).divide(new BigDecimal(100));
        BigDecimal stPrice = new BigDecimal(lastStorage.getSalePrice()).divide(new BigDecimal(100));
        if (sfPrice.compareTo(stPrice) != 0) {
            salePrice = sfPrice.toString() + "-" + stPrice.toString();
        } else {
            salePrice = sfPrice.toString();
        }
        Collections.sort(storageList, new Comparator<GoodsStorage>() {
            @Override
            public int compare(GoodsStorage o1, GoodsStorage o2) {
                return o1.getOldPrice().compareTo(o2.getOldPrice());
            }
        });
        String oldPrice;
        BigDecimal ofPrice = new BigDecimal(firstStorage.getOldPrice()).divide(new BigDecimal(100));
        BigDecimal otPrice = new BigDecimal(lastStorage.getOldPrice()).divide(new BigDecimal(100));
        if (ofPrice.compareTo(otPrice) != 0) {
            oldPrice = ofPrice.toString() + "-" + otPrice.toString();
        } else {
            oldPrice = ofPrice.toString();
        }
        map.put("salePrice", salePrice);
        map.put("oldPrice", oldPrice);
        map.put("saleLowPrice", sfPrice.toString());
        map.put("oldLowPrice", ofPrice.toString());
        return map;
    }

    @Override
    @DataSource(value = "write")
    public int deleteActGoodsById(Long id) {
        int count = 0;
        ActivityGoods actGoods = activityGoodsDao.get(id);
        if (actGoods != null) {
            Long goodsId = actGoods.getGoodsId();
            count += this.deleteActGoods(id, goodsId);
        }
        return count;
    }

    @Override
    @DataSource(value = "write")
    public void deleteActGoodsByActId(Long activityId) {
        List<ActivityGoods> actGoodsList = this.getActivityGoodsListByActivityId(activityId);
        if (actGoodsList != null && actGoodsList.size() > 0) {
            for (ActivityGoods actGoods : actGoodsList) {
                this.deleteActGoods(actGoods.getId(), actGoods.getGoodsId());
            }
        }
    }

    private int deleteActGoods(Long id, Long goodsId) {
        goodsStorageDao.delByGoodsId(goodsId, id);
        int count = activityGoodsDao.logicRemove(id);
        goodsStorageDao.deleteFromCache(goodsId, id);
        return count;
    }

    @Override
    @DataSource(value = "read")
    public ActivityGoods getActivityGoodsFromCache(Long id) {
        return activityGoodsDao.getFromCache(id);
    }

    @Override
    @DataSource(value = "read")
    public List<ActivityGoods> queryListByGoods(Long goodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("goodsId", goodsId);
        return activityGoodsDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public Long updateActivityGoodsTime(Long activityId, String beginTime, String endTime) {
        return activityGoodsDao.updateAllTime(activityId, beginTime, endTime);
    }

    @Override
    public Boolean putStorageToken(Long goodsId, String goodsSku, Long id) {
        String key = goodsId + goodsSku + id;
        Boolean suc = tokenQueue.pushFromHead(key, 1);
        return suc;
    }

    @Override
    public Boolean getStorageToken(Long goodsId, String goodsSku, Long id) {
        String key = goodsId + goodsSku + id;
        String isExists = tokenQueue.removeFromHead(key);
        if (StringUtils.isBlank(isExists)) {
            return false;
        } else {
            return true;
        }
    }

}
