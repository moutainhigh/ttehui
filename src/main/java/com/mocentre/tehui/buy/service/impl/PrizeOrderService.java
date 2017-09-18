package com.mocentre.tehui.buy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.PrizeOrderInstance;
import com.mocentre.tehui.backend.param.PrizeOrderParam;
import com.mocentre.tehui.buy.dao.IPrizeOrderDao;
import com.mocentre.tehui.buy.enums.PrizeOrderSendStatus;
import com.mocentre.tehui.buy.mapper.PrizeOrderMapper;
import com.mocentre.tehui.buy.model.PrizeOrder;
import com.mocentre.tehui.buy.service.IPrizeOrderService;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.core.utils.DateUtils;
import com.mocentre.tehui.ps.dao.ICustomerInfoDao;
import com.mocentre.tehui.ps.model.CustomerInfo;

/**
 * 实物礼品订单service实现 Created by wangxueying on 2017/8/10.
 */
@Component
public class PrizeOrderService implements IPrizeOrderService {

    @Autowired
    private IPrizeOrderDao   prizeOrderDao;
    @Autowired
    private ICustomerInfoDao customerInfoDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<PrizeOrderInstance> queryPrizeOrderPage(Requirement require) {
        ListJsonResult<PrizeOrderInstance> result = new ListJsonResult<PrizeOrderInstance>();
        ListJsonResult<PrizeOrder> pageInfo = prizeOrderDao.queryDatatablesPage(require);
        List<PrizeOrder> prizeOrderList = pageInfo.getData();
        List<PrizeOrderInstance> instanceList = PrizeOrderMapper.toPrizeOrderInstanceList(prizeOrderList);
        result.setData(instanceList);
        result.setDraw(pageInfo.getDraw());
        result.setRecordsFiltered(pageInfo.getRecordsFiltered());
        result.setRecordsTotal(pageInfo.getRecordsTotal());
        return result;
    }

    @Override
    @DataSource(value = "read")
    public List<PrizeOrder> getPrizeOrderList(String customerId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customerId);
        return prizeOrderDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public PrizeOrder getPrizeOrderById(Long id) {
        return prizeOrderDao.getById(id);
    }

    @Override
    @DataSource(value = "write")
    public Long addPrizeOrder(String telephone, String prizeName, String prizeImg, String startTime, String endTime) {
        Long count = 0l;
        CustomerInfo cumInfo = customerInfoDao.getByTelephone(telephone);
        if (cumInfo != null) {
            Long uid = cumInfo.getId();
            PrizeOrder prizeOrder = new PrizeOrder();
            prizeOrder.setCustomerId(uid);
            prizeOrder.setStartTime(DateUtils.parseDate(startTime));
            prizeOrder.setEndTime(DateUtils.parseDate(endTime));
            prizeOrder.setPrizeImg(prizeImg);
            prizeOrder.setPrizeName(prizeName);
            prizeOrder.setSendStatus(PrizeOrderSendStatus.WAIT_SEND.getCodeValue());
            count = prizeOrderDao.saveEntity(prizeOrder);
        }
        return count;
    }

    @Override
    @DataSource(value = "write")
    public Long updatePrizeOrder(PrizeOrderParam param) {
        PrizeOrder prizeOrder = PrizeOrderMapper.toPrizeOrder(param);
        return prizeOrderDao.updateEntity(prizeOrder);
    }

    @Override
    @DataSource(value = "write")
    public Long updatePrizeOrder(PrizeOrder prizeOrder) {
        return prizeOrderDao.updateEntity(prizeOrder);
    }

    @Override
    @DataSource(value = "read")
    public PageInfo<PrizeOrder> queryPrizeOrderPage(Long customerId, Integer start, Integer length) {
        return prizeOrderDao.queryPageInfo(customerId, start, length);
    }

}
