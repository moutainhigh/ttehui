package com.mocentre.tehui.sub.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.sub.model.SubjectGoods;

/**
 * 专题商品关联表数据库操作接口. Created by yukaiji on 2016/12/2.
 */
public interface ISubjectGoodsDao {

    /**
     * 获取所有的专题商品(分页查询)
     * 
     * @return 所有的专题商品
     */
    ListJsonResult<SubjectGoods> queryDatatablesPage(Requirement require);

    /**
     * 根据条件查询
     * 
     * @param paramMap
     * @return
     */
    List<SubjectGoods> queryList(Map<String, Object> paramMap);

    /**
     * 根据id获取关联信息
     */
    SubjectGoods get(Serializable id);

    /**
     * 修改专题关联商品
     * 
     * @return
     */
    Long updateEntity(SubjectGoods subjectGoods);

    /**
     * 添加关联信息
     * 
     * @param SubjectGoods
     * @return id
     */
    Long saveEntity(SubjectGoods subjectGoods);

    /**
     * 根据id删除关联信息
     * 
     * @param id
     * @return
     */
    int logicRemoveById(Serializable id);

    /**
     * 根据subjectId删除
     * 
     * @param subjectId
     * @return
     */
    int logicRemoveBySubject(Long subjectId);

    /**
     * 分页查询专题关联商品
     */
    PageInfo<SubjectGoods> queryPaged(Map<String, Object> paramMap);

    /**
     * 更新专题商品冗余信息
     * 
     * @param goodsId
     * @param goodsName
     * @param sellPrice
     * @param oldPrice
     * @param sellLowPrice
     * @param oldLowPrice
     * @return
     */
    int updateByGoods(Long goodsId, String goodsName, String sellPrice, String oldPrice, Long sellLowPrice,
                      Long oldLowPrice);

}
