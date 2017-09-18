package com.mocentre.tehui.td.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.common.constant.RedisKeyConstant;
import com.mocentre.tehui.core.dao.BaseRedisDao;
import com.mocentre.tehui.td.dao.IThirdGoodsDao;
import com.mocentre.tehui.td.emnus.ThirdGoodsShowLocal;
import com.mocentre.tehui.td.model.ThirdGoods;

/**
 * 三方商品数据库操作接口实现类。
 * <p>
 * Created by yukaiji on 2017/7/12.
 */

@Repository
public class ThirdGoodsDao extends BaseRedisDao<ThirdGoods, String, ThirdGoods> implements IThirdGoodsDao {

    @Override
    public List<ThirdGoods> getList(Map<String, Object> paramMap) {
        return super.queryList(paramMap);
    }

    @Override
    public List<ThirdGoods> getPage(String showLocal, Long bdCityCode) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("bdCityCode", bdCityCode);
        paramMap.put("orderColumn", "sorting");
        paramMap.put("orderBy", "asc");
        paramMap.put("showLocal", showLocal);
        return super.queryList(paramMap);
    }

    @Override
    public List<ThirdGoods> queryFromCache(String showLocal) {
        List<ThirdGoods> thirdGoodsList = new ArrayList<ThirdGoods>();
        List<Object> list = new ArrayList<Object>();
        String key = null;
        if (ThirdGoodsShowLocal.ACT.getCodeValue().equals(showLocal)) {
            key = RedisKeyConstant.THIRDGOODS_ACTGOODS_LIST;
        }
        if (ThirdGoodsShowLocal.SPECIAL.getCodeValue().equals(showLocal)) {
            key = RedisKeyConstant.THIRDGOODS_GOODS_LIST;
        }
        list = getCacheHash(key);
        if (list != null) {
            for (Object object : list) {
                ThirdGoods thirdGoods = (ThirdGoods) object;
                thirdGoodsList.add(thirdGoods);
            }
        }
        return thirdGoodsList;
    }

    @Override
    public ThirdGoods queryFromCacheById(String showLocal, Long id) {
        String hashKey = String.valueOf(id);
        String key = null;
        if (ThirdGoodsShowLocal.ACT.getCodeValue().equals(showLocal)) {
            key = RedisKeyConstant.THIRDGOODS_ACTGOODS_LIST;
        }
        if (ThirdGoodsShowLocal.SPECIAL.getCodeValue().equals(showLocal)) {
            key = RedisKeyConstant.THIRDGOODS_GOODS_LIST;
        }
        return (ThirdGoods) getCacheHash(key, hashKey);
    }

    @Override
    public boolean updateToCache(ThirdGoods thirdGoods) {
        Long id = thirdGoods.getId();
        String showLocal = thirdGoods.getShowLocal();
        String key = null;
        String hashKey = String.valueOf(id);
        if (ThirdGoodsShowLocal.ACT.getCodeValue().equals(showLocal)) {
            key = RedisKeyConstant.THIRDGOODS_ACTGOODS_LIST;
        }
        if (ThirdGoodsShowLocal.SPECIAL.getCodeValue().equals(showLocal)) {
            key = RedisKeyConstant.THIRDGOODS_GOODS_LIST;
        }
        return cacheHash(key, hashKey, thirdGoods);
    }

    @Override
    public boolean deleteFromCache(Long id, String showLocal) {
        String key = null;
        String hashKey = String.valueOf(id);
        if (ThirdGoodsShowLocal.ACT.getCodeValue().equals(showLocal)) {
            key = RedisKeyConstant.THIRDGOODS_ACTGOODS_LIST;
        }
        if (ThirdGoodsShowLocal.SPECIAL.getCodeValue().equals(showLocal)) {
            key = RedisKeyConstant.THIRDGOODS_GOODS_LIST;
        }
        return removeCacheHash(key, hashKey);
    }
}
