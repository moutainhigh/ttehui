package com.mocentre.tehui.act.provider.front;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.ListResult;
import com.mocentre.tehui.act.mapper.GrouponMapper;
import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.act.service.IGrouponService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.frontend.model.GrouponFTInstance;
import com.mocentre.tehui.frontend.service.GrouponManageService;

/**
 * 前台拼团活动接口实现. Created by yukaiji on 2016/11/24.
 */
public class GrouponManageServiceImpl implements GrouponManageService {

    @Autowired
    private IGrouponService grouponService;

    @Override
    public ListResult<GrouponFTInstance> queryGrouponByUnfinished(String actGoodsId, String requestId) {
        ListResult<GrouponFTInstance> result = new ListResult<GrouponFTInstance>();
        result.setRequestId(requestId);
        try {
            if (StringUtils.isBlank(actGoodsId)) {
                result.setErrorMessage("1001", "关联商品Id不能为空");
            }
            if (result.isSuccess()) {
                List<Groupon> grouponList = grouponService.getGrouponByUnfinished(Long.valueOf(actGoodsId));
                result.setData(GrouponMapper.toGrouponFTInstanceList(grouponList));
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryGrouponByUnfinished", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
