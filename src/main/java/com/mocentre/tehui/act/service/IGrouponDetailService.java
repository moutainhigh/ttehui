package com.mocentre.tehui.act.service;

import java.util.List;

import com.mocentre.tehui.act.model.GrouponDetail;
import com.mocentre.tehui.frontend.param.GrouponDetailParam;

/**
 * 参团信息详情接口
 * 
 * @author Created by yukaiji on 2017年02月09日
 */
public interface IGrouponDetailService {

	/**
     * 获取参团人详情
     */
    List<GrouponDetail> getByGourponId(Long grouponId);
    
    /**
     * 参加拼团
     */
    GrouponDetail participateGrouponDetail(GrouponDetailParam grouponDetailParam);
    
    /**
     * 根据团id和参与用户id获取是否参与
     */
    GrouponDetail getByUserId(Long grouponId ,Long takeUserId);
}
