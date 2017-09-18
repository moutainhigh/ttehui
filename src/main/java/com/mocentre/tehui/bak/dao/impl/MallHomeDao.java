package com.mocentre.tehui.bak.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mocentre.tehui.bak.dao.IMallHomeDao;
import com.mocentre.tehui.bak.enums.MallHomeShowLocal;
import com.mocentre.tehui.bak.model.MallHome;
import com.mocentre.tehui.common.constant.RedisKeyConstant;
import com.mocentre.tehui.core.dao.BaseRedisDao;
import com.mocentre.tehui.core.service.support.paging.PageInfo;

/**
 * 掌上银行首页数据库操作接口实现类。
 * <p>
 * Created by yukaiji on 2017/5/17.
 */

@Repository
public class MallHomeDao extends BaseRedisDao<MallHome, String, MallHome> implements IMallHomeDao {

    @Override
    public List<MallHome> getList(Map<String, Object> paramMap) {
        return super.queryList(paramMap);
    }

    @Override
    public PageInfo<MallHome> getPage(String showLocal, Integer start, Integer length) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("length", length);
        paramMap.put("start", start);
        paramMap.put("orderColumn", "sorting");
        paramMap.put("orderBy", "asc");
        paramMap.put("showLocal", showLocal);
        PageInfo<MallHome> pageInfo = super.queryPaged(paramMap);
        return pageInfo;
    }

    @Override
    public List<MallHome> queryFromCache(String showLocal, Integer start, Integer length) {
        List<MallHome> mallHomes = new ArrayList<MallHome>();
        List<Object> list = new ArrayList<Object>();
        if (MallHomeShowLocal.ACT.getCodeValue().equals(showLocal)) {
            String key = RedisKeyConstant.MALLHOME_ACTGOODS_LIST;
            list = getCacheHash(key);
        }
        if (MallHomeShowLocal.SPECIAL.getCodeValue().equals(showLocal)) {
            String key = RedisKeyConstant.MALLHOME_GOODS_LIST;
            list.addAll(getCacheZSet(key, start, length));
        }
        if (list != null) {
            for (Object object : list) {
                MallHome mallHome = (MallHome) object;
                mallHomes.add(mallHome);
            }
        }
        return mallHomes;
    }

    @Override
    public boolean updateToCache(MallHome mallHome) {
        Long id = mallHome.getId();
        String showLocal = mallHome.getShowLocal();
        if (id == null) {
            return false;
        }
        if (StringUtils.isBlank(showLocal)) {
            return false;
        }
        if (MallHomeShowLocal.ACT.getCodeValue().equals(showLocal)) {
            String key = RedisKeyConstant.MALLHOME_ACTGOODS_LIST;
            String hashKey = String.valueOf(id);
            return cacheHash(key, hashKey, mallHome);
        }
        if (MallHomeShowLocal.SPECIAL.getCodeValue().equals(showLocal)) {
            String key = RedisKeyConstant.MALLHOME_GOODS_LIST;
            removeCacheZSet(key, id);
            return cacheZSet(key, mallHome, id);
        }
        return false;
    }

    @Override
    public boolean deleteFromCache(Long id, String showLocal) {
        if (id == null) {
            return false;
        }
        if (StringUtils.isBlank(showLocal)) {
            return false;
        }
        if (MallHomeShowLocal.ACT.getCodeValue().equals(showLocal)) {
            String key = RedisKeyConstant.MALLHOME_ACTGOODS_LIST;
            String hashKey = String.valueOf(id);
            return removeCacheHash(key, hashKey);
        }
        if (MallHomeShowLocal.SPECIAL.getCodeValue().equals(showLocal)) {
            String key = RedisKeyConstant.MALLHOME_GOODS_LIST;
            return removeCacheZSet(key, id);
        }
        return false;
    }
}
