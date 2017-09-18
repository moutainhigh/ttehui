package com.mocentre.tehui.goods.dao;

import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Discover;

/**
 * 发现页dao接口 Created by yukaiji on 2016/11/17.
 */
public interface IDiscoverDao {

    ListJsonResult<Discover> queryDatatablesPage(Requirement require);

    /**
     * 获取全部的配置页
     * 
     * @return
     */
    List<Discover> getAllDiscover();

    /**
     * 查询显示的配置页
     * 
     * @return
     */
    List<Discover> getShowList();

    /**
     * 根据条件获取配置页
     * 
     * @param paramMap 条件
     * @return
     */
    List<Discover> queryList(Map<String, Object> paramMap);

    /**
     * 添加发现页
     * 
     * @param discover 发现页对象
     * @return
     */
    Long saveEntity(Discover discover);

    /**
     * 修改发现页
     * 
     * @param discover 发现页对象
     * @return
     */
    Long updateEntity(Discover discover);

    /**
     * 按id和店铺id查询
     * 
     * @param id
     * @return
     */
    Discover queryById(Long id);

    /**
     * 根据idList删除邮费模板
     * 
     * @param id
     * @return
     */
    int delByIdList(List<Long> idList);

    /**
     * 查询所有缓存
     * 
     * @return
     */
    List<Discover> getFromCacheList();

    /**
     * 更新到缓存
     * 
     * @param discover
     * @return
     */
    Boolean updateToCache(Discover discover);

    /**
     * 从缓存中移除
     * 
     * @param id
     * @return
     */
    Boolean deleteFromCache(Long id);
}
