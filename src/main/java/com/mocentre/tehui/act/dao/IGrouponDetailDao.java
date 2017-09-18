package com.mocentre.tehui.act.dao;

import java.util.List;
import java.util.Map;

import com.mocentre.tehui.act.model.GrouponDetail;

/**
 * 参团用户数据库操作接口
 * 
 * @author Created by yukaiji on 2017年02月09日
 */
public interface IGrouponDetailDao {

    /**
     * 根据id获取拼团详情
     */
    List<GrouponDetail> getDetailList(Long grouponId);

    /**
     * 参与拼团
     */
    Long saveEntity(GrouponDetail grouponDetail);

    /**
     * 根据条件获取参团信息
     */
    GrouponDetail getGrouponDetailByParam(Map<String, Object> param);

    /**
     * 更新参团支付状态
     * 
     * @param grouponId 团购id
     * @param takeUserId 参团用户
     * @return
     */
    int updateGrouponDetailPay(Long grouponId, Long takeUserId);
}
