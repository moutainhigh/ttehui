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
import com.mocentre.tehui.system.dao.IImageDao;
import com.mocentre.tehui.system.model.Image;

/**
 * 类ImageDao.java的实现描述：图片实现类
 *
 * @author sz.gong 2016年11月17日 上午11:34:01
 */
@Repository
public class ImageDao extends BaseRedisDao<Image, String, Image> implements IImageDao {

    @Override
    public int deleteByParam(Map<String, Object> paramMap) {
        return super.delete("Image_delete", paramMap);
    }

    @Override
    public List<Image> queryList(Long seatId, String seat) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("seatId", seatId);
        paramMap.put("seat", seat);
        paramMap.put("orderColumn", "sorting");
        paramMap.put("orderBy", "asc");
        return super.queryList(paramMap);
    }

    @Override
    public List<Image> getListBySeat(String seat) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("seat", seat);
        paramMap.put("orderColumn", "sorting");
        paramMap.put("orderBy", "asc");
        return super.queryList(paramMap);
    }

    @Override
    public int delete(String type, String seat, Long seatId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type", type);
        paramMap.put("seat", seat);
        paramMap.put("seatId", seatId);
        return super.delete("Image_delete", paramMap);
    }

    @Override
    public List<Image> queryFromCache() {
        List<Image> imageList = new ArrayList<Image>();
        String key = RedisKeyConstant.MALLHOME_BANNER_LIST;
        List<Object> list = getCacheHash(key);
        if (list != null) {
            for (Object object : list) {
                Image image = (Image) object;
                imageList.add(image);
            }
        }
        return imageList;
    }

    @Override
    public boolean updateToCache(Image image) {
        Long id = image.getId();
        if (id == null) {
            return false;
        }
        String key = RedisKeyConstant.MALLHOME_BANNER_LIST;
        String hashKey = String.valueOf(id);
        return cacheHash(key, hashKey, image);
    }

    @Override
    public boolean deleteFromCache(Long id) {
        if (id == null) {
            return false;
        }
        String key = RedisKeyConstant.MALLHOME_BANNER_LIST;
        String hashKey = String.valueOf(id);
        return removeCacheHash(key, hashKey);
    }

}
