package com.mocentre.tehui.goods.service;

import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Attribute;

/**
 * 商品属性service接口 Created by 王雪莹 on 2016/11/5.
 */
public interface IAttributeService {

    ListJsonResult<Attribute> getAttributePage(Requirement require);

    /**
     * 查询所有属性
     */
    List<Attribute> getAttributes();

    /**
     * 按条件查询属性
     */
    List<Attribute> getAttributesList(Map<String, Object> param);

    /**
     * 通过code查询
     * 
     * @param code
     * @return
     */
    Attribute getAttributesByCode(String code);

    /**
     * 添加一个属性
     */
    Long addAttribute(Attribute attribute);

    /**
     * 添加一个属性
     */
    Long addAttribute(Map<String, Object> param);

    /**
     * 修改一个属性
     */
    Long updateAttribute(Attribute attribute);

    /**
     * 修改一个属性
     */
    Long updateAttribute(Map<String, Object> param);

    /**
     * 删除一个属性
     * 
     * @param ids
     */
    int delAttribute(List<Long> ids);

    /**
     * 批量删除
     */
    int delAttribute(Long id);

    /**
     * 根据id获取
     */
    Attribute getById(Long id);

}
