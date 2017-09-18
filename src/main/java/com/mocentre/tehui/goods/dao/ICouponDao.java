package com.mocentre.tehui.goods.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Coupon;

/**
 * 优惠券dao接口 Created by yukaiji on 2016/11/24.
 */
public interface ICouponDao {

    /**
     * 获取所有的优惠券信息
     * 
     * @return 发现页List
     */
    ListJsonResult<Coupon> queryDatatablesPage(Requirement require);

    Coupon get(Serializable id);

    /**
     * 查询优惠券
     * 
     * @param id
     * @param shopId
     * @return
     */
    Coupon queryByIdShop(Long id, Long shopId);

    /**
     * 根据条件获取优惠券
     * 
     * @param paramMap 条件
     * @return
     */
    List<Coupon> queryList(Map<String, Object> paramMap);

    /**
     * 添加一个优惠券
     * 
     * @param paramMap
     * @return id
     */
    Long saveEntity(Coupon coupon);

    /**
     * 修改一个优惠券
     * 
     * @param paramMap
     * @return id
     */
    Long updateEntity(Coupon coupon);

    /**
     * 根据id删除一个优惠券
     * 
     * @param id
     * @return
     */
    int removeById(Serializable id);

    /**
     * 根据id删除一个优惠券(逻辑删除)
     * 
     * @param id
     * @return
     */
    int logicRemoveById(Serializable id);

    /**
     * 查询商城内展示且未过期的优惠券
     * 
     * @return
     */
    List<Coupon> getInnerNotExpiredList();
}
