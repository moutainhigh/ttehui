package com.mocentre.tehui.buy.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.PrizeOrderManageService;
import com.mocentre.tehui.backend.model.LogisticsInstance;
import com.mocentre.tehui.backend.model.PrizeOrderInstance;
import com.mocentre.tehui.backend.param.PrizeOrderParam;
import com.mocentre.tehui.backend.param.PrizeOrderQueryParam;
import com.mocentre.tehui.buy.mapper.LogisticsMapper;
import com.mocentre.tehui.buy.mapper.PrizeOrderMapper;
import com.mocentre.tehui.buy.model.Logistics;
import com.mocentre.tehui.buy.model.PrizeOrder;
import com.mocentre.tehui.buy.service.ILogisticsService;
import com.mocentre.tehui.buy.service.IPrizeOrderService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.ps.model.CustomerInfo;
import com.mocentre.tehui.ps.service.ICustomerInfoService;

/**
 * 实物礼品订单 Created by wangxueying on 2017/8/11.
 */
public class PrizeOrderManageServiceImpl implements PrizeOrderManageService {

    @Autowired
    private IPrizeOrderService   prizeOrderService;
    @Autowired
    private ILogisticsService    logisticsService;
    @Autowired
    private ICustomerInfoService customerInfoService;

    @Override
    public ListJsonResult<PrizeOrderInstance> queryPrizeOrderPage(PrizeOrderQueryParam prizeOrderQuery) {
        ListJsonResult<PrizeOrderInstance> result = new ListJsonResult<PrizeOrderInstance>();
        String requestId = prizeOrderQuery.getRequestId();
        result.setRequestId(requestId);
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("sendStatus", prizeOrderQuery.getSendStatus());
            paramMap.put("prizeName", prizeOrderQuery.getPrizeName());
            paramMap.put("startTime", prizeOrderQuery.getStartTime());
            paramMap.put("endTime", prizeOrderQuery.getEndTime());
            paramMap.put("orderBy", prizeOrderQuery.getOrderBy());
            paramMap.put("column", prizeOrderQuery.getOrderColumn());
            String cumPhoneNum = prizeOrderQuery.getCustomerPhoneNum();
            if (StringUtils.isNotBlank(cumPhoneNum)) {
                CustomerInfo cumInfo = customerInfoService.getCustomerByTelephone(cumPhoneNum);
                if (cumInfo == null) {
                    result.setData(new ArrayList<PrizeOrderInstance>());
                    return result;
                }
                paramMap.put("customerId", cumInfo.getId());
            }
            Requirement require = new Requirement(prizeOrderQuery.getDraw(), prizeOrderQuery.getStart(),
                    prizeOrderQuery.getLength(), paramMap);
            result = prizeOrderService.queryPrizeOrderPage(require);
            List<PrizeOrderInstance> instanceList = result.getData();
            for (PrizeOrderInstance instance : instanceList) {
                CustomerInfo customerInfo = customerInfoService.getCustomerById(instance.getCustomerId());
                instance.setCustomerPhoneNum(customerInfo.getTelephone());
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryPrizeOrderPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult updatePrizeOrder(PrizeOrderParam prizeOrderParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(prizeOrderParam.getRequestId());
        try {
            Long updateNum = prizeOrderService.updatePrizeOrder(prizeOrderParam);
            if (updateNum < 0) {
                result.setErrorMessage("1001", "修改失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updatePrizeOrder", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<Map<String, Object>> getById(Long id, String requestId) {
        PlainResult<Map<String, Object>> result = new PlainResult();
        result.setRequestId(requestId);
        try {
            PrizeOrder prizeOrder = prizeOrderService.getPrizeOrderById(id);
            List<Logistics> logisticsList = logisticsService.getLogisticsList();
            List<LogisticsInstance> logisticsInstances = new ArrayList<LogisticsInstance>();
            for (int i = 0; i < logisticsList.size(); i++) {
                LogisticsInstance logisticsInstance = LogisticsMapper.toLogisticsInstance(logisticsList.get(i));
                logisticsInstances.add(logisticsInstance);
            }
            PrizeOrderInstance prizeOrderInstance = PrizeOrderMapper.toPrizeOrderInstance(prizeOrder);
            HashMap<String, Object> map = new HashMap<>();
            map.put("prizeOrder", prizeOrderInstance);
            map.put("logisticsList", logisticsInstances);
            result.setData(map);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updatePrizeOrder", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }
}
