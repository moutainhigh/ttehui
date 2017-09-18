package com.mocentre.tehui.buy.provider;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.PayConfigManageService;
import com.mocentre.tehui.backend.model.PayConfigInstance;
import com.mocentre.tehui.backend.model.PrizeOrderInstance;
import com.mocentre.tehui.buy.mapper.PayConfigMapper;
import com.mocentre.tehui.buy.model.PayConfig;
import com.mocentre.tehui.buy.service.impl.PayConfigService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 支付管理
 *
 * Created by 王雪莹 on 2017/8/30.
 */
public class PayConfigManageServiceImpl implements PayConfigManageService {
    @Autowired
    private PayConfigService payConfigService;
    @Override
    public ListJsonResult<PayConfigInstance> getAll(String requestId) {
        ListJsonResult<PayConfigInstance> result = new ListJsonResult<>();
        result.setRequestId(requestId);
        try {
            List<PayConfig> payList = payConfigService.getAll();
            result.setData(PayConfigMapper.toPayConfigFTInstanceList(payList));
            result.setRecordsTotal(payList.size());
            result.setRecordsFiltered(payList.size());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getAll", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult updateOpenStatus(Long id,String status, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try{
            Long updateNum = payConfigService.updateOpenStatus(id,status);
            if (updateNum < 0) {
                result.setErrorMessage("1001", "修改失败");
            }
        }catch (Exception e){
            LoggerUtil.tehuiLog.error("updateOpenStatus",e);
            result.setErrorMessage("999","系统错误");
        }
        return result;
    }
}
