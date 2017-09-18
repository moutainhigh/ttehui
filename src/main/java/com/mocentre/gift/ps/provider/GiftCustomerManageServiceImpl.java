package com.mocentre.gift.ps.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.GiftCustomerManageService;
import com.mocentre.gift.backend.model.GiftCustomerInstance;
import com.mocentre.gift.backend.param.GiftCustomerParam;
import com.mocentre.gift.backend.param.GiftCustomerQueryParam;
import com.mocentre.gift.ps.mapper.GiftCustomerMapper;
import com.mocentre.gift.ps.model.GiftCustomer;
import com.mocentre.gift.ps.service.IGiftCustomerService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;

public class GiftCustomerManageServiceImpl implements GiftCustomerManageService {

    @Autowired
    private IGiftCustomerService giftCustomerService;

    @Override
    public ListJsonResult<GiftCustomerInstance> queryPage(GiftCustomerQueryParam queryParam) {
        ListJsonResult<GiftCustomerInstance> result = new ListJsonResult<GiftCustomerInstance>();
        result.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderBy", queryParam.getOrderBy());
            paramMap.put("column", queryParam.getOrderColumn());
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            ListJsonResult<GiftCustomer> lr = giftCustomerService.queryGiftCustomerPage(require);
            List<GiftCustomer> giftCustomers = lr.getData();
            result.setData(GiftCustomerMapper.toGiftCustomerInstanceList(giftCustomers));
            result.setDraw(lr.getDraw());
            result.setRecordsFiltered(lr.getRecordsFiltered());
            result.setRecordsTotal(lr.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult updateGiftCustomer(GiftCustomerParam giftCustomer) {
        BaseResult result = new BaseResult();
        try {
            giftCustomerService.updateGiftCustomer(GiftCustomerMapper.toGiftCustomer(giftCustomer));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateGiftCustomer", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult saveGiftCustomer(GiftCustomerParam giftCustomer) {
        BaseResult result = new BaseResult();
        try {
            giftCustomerService.saveGiftCustomer(GiftCustomerMapper.toGiftCustomer(giftCustomer));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateGiftCustomer", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public boolean queryExistGiftCustomer(String uName) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userName", uName);
        return giftCustomerService.queryExistGiftCustomer(paramMap);
    }

    @Override
    public BaseResult deleteById(List<Long> idList, String requestId) {
        BaseResult br = new BaseResult();
        BaseResult result = giftCustomerService.delete(idList, requestId);
        if (result != null) {
            if (!result.isSuccess()) {
                br.setErrorMessage("100", "删除失败");
            }
        } else {
            br.setErrorMessage("99", "系统繁忙，请稍后再试");
        }
        return br;
    }

    @Override
    public GiftCustomerInstance getGiftCustomer(Long id) {
        return GiftCustomerMapper.toGiftCustomerInstance(giftCustomerService.getGiftCustomer(id));
    }

    @Override
    public BaseResult updateGiftCustomerPassWord(Map<String, Object> paramMap) {
        BaseResult result = new BaseResult();
        try {
            giftCustomerService.updateGiftCustomerPassWord(paramMap);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateGiftCustomer", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
