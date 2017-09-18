package com.mocentre.gift.dma.service.impl;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.dma.dao.IGiftDemandDao;
import com.mocentre.gift.dma.model.GiftDemand;
import com.mocentre.gift.dma.service.IGiftDemandService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 礼品平台 极速获取方案service
 * 
 * @author liqifan
 * @date 创建时间：2017年4月7日 下午2:32:22
 */
@Component
public class GiftDemandService implements IGiftDemandService {

    @Autowired
    private IGiftDemandDao giftDemandDao;

    @Override
    public ListJsonResult<GiftDemand> queryGiftDemandPage(Requirement require) {
        return giftDemandDao.queryDatatablesPage(require);
    }

    @Override
    public List<GiftDemand> queryAll(Map<String, Object> paramMap) {
        return giftDemandDao.queryList(paramMap);
    }

    @Override
    public void changeGiftDemandStatus(Map<String, Object> paramMap) {
        giftDemandDao.changeGiftDemandStatus(paramMap);
    }

    @Override
    public BaseResult delete(List<Long> idList, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            if (idList == null) {
                result.setErrorMessage("1000", "参数不能为空");
            }
            if (result.isSuccess()) {
                for (Long id : idList) {
                    giftDemandDao.logicRemoveById(id);
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("delete", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public Long addGiftDemand(GiftDemand giftDemand) {
        return giftDemandDao.saveEntity(giftDemand);
    }

    @Override
    public Long updateGiftDemand(GiftDemand giftDemand) {
        return giftDemandDao.updateEntity(giftDemand);
    }

}
