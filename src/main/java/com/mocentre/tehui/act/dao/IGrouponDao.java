package com.mocentre.tehui.act.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.core.service.support.query.Requirement;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 拼团详情数据库操作接口
 * 
 * @author Created by yukaiji on 2017年02月09日
 */
public interface IGrouponDao {

    /**
     * 根据id获取拼团详情
     */
    Groupon get(Serializable id);

    /**
     * 获取所有未完成的团
     */
    List<Groupon> getAllByUnfinished(Map<String, Object> paramMap);

    /**
     * 创建拼团
     */
    Long saveEntity(Groupon groupon);

    /**
     * 更新团购状态和团支付状态
     * 
     * @param id
     * @param groupStatus
     */
    void updateStatusAndPay(Long id, String groupStatus);

    /**
     * 更新团购状态
     * 
     * @param id
     * @param groupStatus
     */
    void updateStatus(Long id, String groupStatus);

    /**
     * 更新处理状态：已处理
     * 
     * @param id
     */
    void updateIsDeal(Long id);

    /**
     * 更新团购状态和参团人数
     * 
     * @param id
     * @param groupStatus
     * @param num
     */
    void updateStatusAndNum(Long id, String groupStatus, Integer num);

    /**
     * 查询需要退款处理的团购
     * 
     * @return
     */
    List<Groupon> queryNeedClose();

    /**
    *
    * */
    ListJsonResult<Groupon> queryDatatablesPage(Requirement require);
}
