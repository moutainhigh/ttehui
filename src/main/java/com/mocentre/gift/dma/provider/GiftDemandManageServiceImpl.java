package com.mocentre.gift.dma.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.GiftDemandManageService;
import com.mocentre.gift.backend.model.GiftDemandInstance;
import com.mocentre.gift.backend.param.GiftDemandQueryParam;
import com.mocentre.gift.dma.mapper.GiftDemandMapper;
import com.mocentre.gift.dma.model.GiftDemand;
import com.mocentre.gift.dma.service.IGiftDemandService;
import com.mocentre.gift.ps.model.GiftCustomer;
import com.mocentre.gift.ps.service.IGiftCustomerService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 礼品平台 极速获取方案manage实现类
 * 
 * @author liqifan
 * @date 创建时间：2017年4月7日 下午2:16:04
 */
public class GiftDemandManageServiceImpl implements GiftDemandManageService {

    @Autowired
    private IGiftDemandService   giftDemandService;
    @Autowired
    private IGiftCustomerService giftCustomerService;

    @Override
    public ListJsonResult<GiftDemandInstance> queryPage(GiftDemandQueryParam queryParam) {
        ListJsonResult<GiftDemandInstance> result = new ListJsonResult<GiftDemandInstance>();
        result.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderBy", queryParam.getOrderBy());
            paramMap.put("column", queryParam.getOrderColumn());
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            ListJsonResult<GiftDemand> lr = giftDemandService.queryGiftDemandPage(require);
            List<GiftDemand> giftDemandList = lr.getData();
            List<GiftDemandInstance> giftDemandInstanceList = new ArrayList<GiftDemandInstance>();
            for (GiftDemand giftDemand : giftDemandList) {
                GiftCustomer giftCustomer = giftCustomerService.getGiftCustomer(giftDemand.getCustomerId());
                giftDemandInstanceList.add(GiftDemandMapper.toGiftDemandInstance(giftDemand, giftCustomer));
            }
            result.setData(giftDemandInstanceList);
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
    public void changeGiftDemandStatus(Map<String, Object> paramMap) {
        giftDemandService.changeGiftDemandStatus(paramMap);
    }

    @Override
    public BaseResult deleteById(List<Long> idList, String requestId) {
        BaseResult br = new BaseResult();

        BaseResult result = giftDemandService.delete(idList, requestId);
        if (result != null) {
            if (!result.isSuccess()) {
                br.setErrorMessage("100", "删除失败");
            }
        } else {
            br.setErrorMessage("99", "系统繁忙，请稍后再试");
        }
        return br;
    }

}
