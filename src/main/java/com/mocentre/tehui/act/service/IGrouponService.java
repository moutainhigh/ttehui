package com.mocentre.tehui.act.service;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.core.service.support.query.Requirement;

import java.util.List;

/**
 * 拼团信息详情接口
 * 
 * @author Created by yukaiji on 2017年02月09日
 */
public interface IGrouponService {

    /**
     * 通过id查询
     * 
     * @param id
     * @return
     */
    Groupon getGrouponById(Long id);

    /**
     * 获取未完成团
     */
    List<Groupon> getGrouponByUnfinished(Long actGoodsId);

    /**
     * 创建拼团
     * 
     * @param groupon
     * @return
     */
    Groupon saveGroupon(Groupon groupon);

    /**
     * 查询需要退款的团购(达到团购时间，团购人数不足)
     * 
     * @return
     */
    List<Groupon> queryGrouponNeedRefund();

    /**
     * 获取拼团列表
     *
     * @return 拼团列表
     */
    ListJsonResult<Groupon> queryGrouponPage(Requirement require);
}
