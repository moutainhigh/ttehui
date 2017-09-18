package com.mocentre.tehui.goods.service;

import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Discover;

/**
 * 商城发现页接口 Created by yukaiji on 2016/11/17.
 */
public interface IDiscoverService {

    /**
     * 获取所有的发现页
     * 
     * @return 发现页List
     */
    ListJsonResult<Discover> queryDiscoverPage(Requirement require);

    /**
     * 查询显示的发现页
     * 
     * @return
     */
    List<Discover> getShowDiscoverList();

    /**
     * 根据id获取发现页
     * 
     * @return 发现页
     */
    Discover getDiscoverById(Long id);

    /**
     * 根据条件获取发现页
     * 
     * @param paramMap 条件
     * @return 发现页List
     */
    List<Discover> queryDiscover(Map<String, Object> paramMap);

    /**
     * 添加一个配置页
     * 
     * @param discover
     * @return id
     */
    Long addDiscover(Discover discover);

    /**
     * 修改一个配置页
     * 
     * @param discover
     * @return id
     */
    Long updateDiscover(Discover discover);

    /**
     * 根据id列表删除一个配置页
     * 
     * @param idList id列表
     * @return
     */
    int deleteDiscover(List<Long> idList);

    /**
     * 开启关闭显示页
     */
    Long show(Long id, Integer show);

    /**
     * 更新到缓存
     * 
     * @param discover
     * @return
     */
    Boolean updateDiscoverToCache(Discover discover);
}
