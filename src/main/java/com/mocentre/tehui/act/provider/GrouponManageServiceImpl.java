package com.mocentre.tehui.act.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.GrouponManageService;
import com.mocentre.tehui.act.mapper.GrouponMapper;
import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.act.service.IGrouponService;
import com.mocentre.tehui.backend.model.GrouponInstance;
import com.mocentre.tehui.backend.param.GrouponQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.ps.model.CustomerInfo;
import com.mocentre.tehui.ps.service.ICustomerInfoService;

/**
 * 后台拼团管理接口实现 Created by yukaiji on 2016/11/24.
 */
public class GrouponManageServiceImpl implements GrouponManageService {

    @Autowired
    private IGrouponService      grouponService;
    @Autowired
    private ICustomerInfoService customerInfoService;

    @Override
    public ListJsonResult<GrouponInstance> queryGrouponPage(GrouponQueryParam grouponQueryParam) {
        ListJsonResult<GrouponInstance> lr = new ListJsonResult<GrouponInstance>();
        lr.setRequestId(grouponQueryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("actGoodsId", grouponQueryParam.getActGoodsId());
            Requirement require = new Requirement(grouponQueryParam.getDraw(), grouponQueryParam.getStart(),
                    grouponQueryParam.getLength(), paramMap);
            ListJsonResult<Groupon> result = grouponService.queryGrouponPage(require);
            List<Groupon> lists = result.getData();
            List<GrouponInstance> listData = GrouponMapper.toGrouponInstanceList(lists);
            for (GrouponInstance grouponIns : listData) {
                CustomerInfo customerInfo = customerInfoService.getCustomerById(grouponIns.getOpenUserId());
                grouponIns.setOpenUserName(customerInfo.getUserName());
            }
            lr.setData(listData);
            lr.setDraw(result.getDraw());
            lr.setCode(result.getCode());
            lr.setMessage(result.getMessage());
            lr.setRecordsTotal(result.getRecordsTotal());
            lr.setRecordsFiltered(result.getRecordsFiltered());
            lr.setSuccess(result.isSuccess());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryActivityGoodsPage", e);
            lr.setErrorMessage("999", "系统错误");
        }
        return lr;
    }
}
