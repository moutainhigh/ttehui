package com.mocentre.gift.ps.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.ps.model.GiftCustomer;
import com.mocentre.tehui.core.service.support.query.Requirement;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 礼品平台 客户Dao接口
 * 
 * @author liqifan
 * @date 创建时间：2017年4月6日 上午11:22:44
 */
public interface IGiftCustomerDao {

    /**
     * 根据id查询
     * 
     * @param id
     * @return
     */
    GiftCustomer get(Serializable id);

    /**
     * 根据用户名查询
     *
     * @param userName
     * @return
     */
    GiftCustomer getByUserName(String userName);

    /**
     * 查询客户
     * 
     * @return 所有的客户
     */
    List<GiftCustomer> queryList(Map<String, Object> paramMap);

    /**
     * 添加客户
     * 
     * @param giftCustomer
     */
    Long saveEntity(GiftCustomer giftCustomer);

    /**
     * 修改客户
     * 
     * @param giftCustomer
     */
    Long updateEntity(GiftCustomer giftCustomer);

    /**
     * 根据id删除
     * 
     * @param id
     */
    int logicRemoveById(Serializable id);

    /**
     * 修改密码
     * 
     * @param paramMap
     */
    int updateGiftCustomerPassWord(Map<String, Object> paramMap);

    /**
     * 分页查询
     *
     * @param require
     */
    ListJsonResult<GiftCustomer> queryDatatablesPage(Requirement require);
}
