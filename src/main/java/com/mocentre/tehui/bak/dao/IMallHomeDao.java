package com.mocentre.tehui.bak.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.bak.model.MallHome;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 掌上银行首页数据库操作接口。
 * <p>
 * Created by yukaiji on 2017/5/17.
 */
public interface IMallHomeDao {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    MallHome get(Serializable id);

    /**
     * 分页查询商品列表
     *
     * @return 商品列表
     */
    ListJsonResult<MallHome> queryDatatablesPage(Requirement require);

    /**
     * 根据条件获取关联商品
     *
     * @param paramMap
     * @return
     */
    List<MallHome> getList(Map<String, Object> paramMap);

    /**
     * 添加关联商品或活动
     *
     * @param mallHome
     */
    Long saveEntity(MallHome mallHome);

    /**
     * 修改关联商品或活动
     *
     * @param mallHome
     */
    Long updateEntity(MallHome mallHome);

    /**
     * 根据id删除
     *
     * @param id
     */
    int logicRemoveById(Serializable id);

    /**
     * 查询分页列表
     *
     * @param showLocal
     * @param start
     * @param length
     * @return
     */
    PageInfo<MallHome> getPage(String showLocal, Integer start, Integer length);

    List<MallHome> queryFromCache(String showLocal, Integer start, Integer length);

    boolean updateToCache(MallHome mallHome);

    boolean deleteFromCache(Long id, String showLocal);
}
