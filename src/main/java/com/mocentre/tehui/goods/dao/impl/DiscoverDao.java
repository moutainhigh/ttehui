package com.mocentre.tehui.goods.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.common.constant.RedisKeyConstant;
import com.mocentre.tehui.core.dao.BaseRedisDao;
import com.mocentre.tehui.goods.dao.IDiscoverDao;
import com.mocentre.tehui.goods.model.Discover;

/**
 * 发现页Dao接口实现 Created by yukaiji on 2016/11/17.
 */
@Repository
public class DiscoverDao extends BaseRedisDao<Discover, String, Discover> implements IDiscoverDao {

    @Override
    public List<Discover> getAllDiscover() {
        return super.getAll();
    }

    @Override
    public List<Discover> getShowList() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("isShow", 1);
        paramMap.put("orderColumn", "sorting");
        return super.queryList(paramMap);
    }

    @Override
    public Discover queryById(Long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        return super.queryUniquely(paramMap);
    }

    @Override
    public int delByIdList(List<Long> idList) {
        int count = 0;
        for (Long id : idList) {
            count += super.logicRemoveById(id);
        }
        return count;
    }

    @Override
    public List<Discover> getFromCacheList() {
        List<Discover> disList = new ArrayList<Discover>();
        String key = RedisKeyConstant.DISCOVER_LIST;
        List<Object> list = getCacheHash(key);
        if (list != null) {
            for (Object object : list) {
                Discover discover = (Discover) object;
                disList.add(discover);
            }
        }
        return disList;
    }

    @Override
    public Boolean updateToCache(Discover discover) {
        Long id = discover.getId();
        if (id == null) {
            return false;
        }
        String key = RedisKeyConstant.DISCOVER_LIST;
        String hashKey = String.valueOf(id);
        return cacheHash(key, hashKey, discover);
    }

    public Boolean deleteFromCache(Long id) {
        if (id == null) {
            return false;
        }
        String key = RedisKeyConstant.DISCOVER_LIST;
        String hashKey = String.valueOf(id);
        return removeCacheHash(key, hashKey);
    }

}
