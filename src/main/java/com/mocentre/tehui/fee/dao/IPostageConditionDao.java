package com.mocentre.tehui.fee.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.tehui.fee.model.PostageCondition;

/**
 * 包邮条件信息数据库操作接口 Created by yukaiji on 2016/11/04.
 */
public interface IPostageConditionDao {

    /**
     * 根据id获取包邮条件
     * 
     * @param id
     * @return
     */
    PostageCondition get(Serializable id);

    /**
     * 根据条件查询包邮条件
     * 
     * @param paramMap
     * @return
     */
    List<PostageCondition> queryList(Map<String, Object> paramMap);

    /**
     * 修改运费条件
     * 
     * @param postageCondition
     * @return
     */
    Long updateEntity(PostageCondition postageCondition);

    /**
     * 删除运费条件
     * 
     * @param id
     * @return
     */
    int logicRemoveById(Serializable id);

    /**
     * 插入运费条件
     * 
     * @param postageCondition
     * @return
     */
    Long saveEntity(PostageCondition postageCondition);
}
