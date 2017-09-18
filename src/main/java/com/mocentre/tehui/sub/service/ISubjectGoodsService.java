package com.mocentre.tehui.sub.service;

import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.SubjectGoodsInstance;
import com.mocentre.tehui.backend.param.SubjectGoodsParam;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.sub.model.SubjectGoods;

/**
 * 专题商品关联接口. Created by yukaiji on 2016/12/02.
 */
public interface ISubjectGoodsService {

    /**
     * 获取所有的专题商品(分页查询)
     *
     * @return 所有的专题商品
     */
    ListJsonResult<SubjectGoodsInstance> querySubjectPage(Requirement require);

    /**
     * 根据id获取商品关联信息
     *
     * @return
     */
    SubjectGoods querySubjectGoods(Long id);

    /**
     * 修改关联商品信息
     */
    Long updateSubjectGoods(SubjectGoodsParam subjectGoodsParam);

    /**
     * 根据subjectId获取关联信息
     *
     * @param subjectId
     */
    List<SubjectGoods> getSubjectGoodsListBySubjectId(Long subjectId);

    /**
     * 添加关联信息
     */
    SubjectGoods insertSubjectGoods(SubjectGoodsParam subjectGoodsParam);

    /**
     * 根据id删除
     */
    int delByid(Long id);

    /**
     * 根据subjectId全部删除
     */
    int delAllBySubjectId(Long subjectId);

    /**
     * 专题关联商品分页
     */
    PageInfo<SubjectGoods> querySubjectPage(Long subjectId, Integer start, Integer length);

    /**
     * 通过商品id查询
     * 
     * @param goodsId
     * @return
     */
    List<SubjectGoods> queryListByGoods(Long goodsId);
}
