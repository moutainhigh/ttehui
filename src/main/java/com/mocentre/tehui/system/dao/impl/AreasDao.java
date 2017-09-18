/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.common.constant.RedisKeyConstant;
import com.mocentre.tehui.core.dao.BaseRedisDao;
import com.mocentre.tehui.system.dao.IAreasDao;
import com.mocentre.tehui.system.model.Areas;

/**
 * 类AreasDao.java的实现描述：地区实现类
 * 
 * @author sz.gong 2016年11月17日 上午11:30:55
 */
@Repository
public class AreasDao extends BaseRedisDao<Areas, String, Areas> implements IAreasDao {

    @Override
    public List<Areas> getAllProvince() {
        return super.getList("Areas_getAllProvince");
    }

    @Override
    public List<Areas> getAllCity() {
        return super.getList("Areas_getAllCity");

    }

    @Override
    public List<Areas> getAllAreas() {
        return super.getList("Areas_getAllAreas");
    }

    @Override
    public List<Areas> get(String p_code) {
        return super.getList("Areas_getToPcode", p_code);
    }

    @Override
    public Areas getAreas(String code) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", code);
        return super.queryUniquely(paramMap);
    }

    @Override
    public List<Areas> getAllFromCache() {
        List<Areas> areasList = new ArrayList<Areas>();
        String codeKey = RedisKeyConstant.AREAS_CODE_LIST;
        List<Object> list = getCacheHash(codeKey);
        for (Object object : list) {
            areasList.add((Areas) object);
        }
        return areasList;
    }

    @Override
    public Areas getFromCache(String code) {
        String key = RedisKeyConstant.AREAS_CODE_LIST;
        Object value = getCacheHash(key, code);
        if (value == null) {
            this.getAllToCache();
            return this.getAreas(code);
        }
        return (Areas) value;
    }

    @Override
    public List<Areas> getAllToCache() {
        final List<Areas> list = super.getAll();
        final String codeKey = RedisKeyConstant.AREAS_CODE_LIST;
        //        //批量存入缓存
        //        redisTemplate.execute(new RedisCallback<Boolean>() {
        //            @Override
        //            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
        //                RedisSerializer rawKey = getRedisKeySerializer();
        //                RedisSerializer rawVal = getRedisValueSerializer();
        //                for (Areas areas : list) {
        //                    String key = codeKey + ":" + areas.getCode();
        //                    connection.set(rawKey.serialize(key), rawVal.serialize(areas));
        //                }
        //                return true;
        //            }
        //        }, false, true);
        //批量存入缓存
        Map<String, Areas> map = new HashMap<String, Areas>();
        for (Areas areas : list) {
            String hashKey = areas.getCode();
            map.put(hashKey, areas);
        }
        cacheHash(codeKey, map);
        return list;
    }

}
