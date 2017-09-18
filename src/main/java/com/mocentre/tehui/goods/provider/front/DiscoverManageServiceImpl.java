package com.mocentre.tehui.goods.provider.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.ListResult;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.frontend.model.DiscoverFTInstance;
import com.mocentre.tehui.frontend.service.DiscoverManageService;
import com.mocentre.tehui.goods.mapper.DiscoverMapper;
import com.mocentre.tehui.goods.model.Discover;
import com.mocentre.tehui.goods.service.IDiscoverService;

/**
 * 发现页前台接口实现类.
 * 
 * @author Created by yukaiji on 2016年12月14日
 */
public class DiscoverManageServiceImpl implements DiscoverManageService {

    @Autowired
    private IDiscoverService discoverService;

    @Override
    public ListResult<DiscoverFTInstance> getDiscoverList(String requestId) {
        ListResult<DiscoverFTInstance> lr = new ListResult<DiscoverFTInstance>();
        lr.setRequestId(requestId);
        try {
            List<Discover> discoverList = discoverService.getShowDiscoverList();
            List<DiscoverFTInstance> listData = DiscoverMapper.toDiscoverFTInstanceList(discoverList);
            lr.setCount(listData.size());
            lr.setData(listData);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getDiscoverList", e);
            lr.setErrorMessage("999", "系统错误");
        }
        return lr;
    }

}
