package com.mocentre.gift.dma.provider.front;

import com.mocentre.common.BaseResult;
import com.mocentre.gift.dma.mapper.GiftDemandMapper;
import com.mocentre.gift.dma.model.GiftDemand;
import com.mocentre.gift.dma.service.IGiftDemandService;
import com.mocentre.gift.frontend.param.GiftDemandParam;
import com.mocentre.gift.frontend.service.GiftDemandManageService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 礼品平台 极速获取方案manage实现类
 *
 * @author liqifan
 * @date 创建时间：2017年4月7日 下午2:16:04
 */
public class GiftDemandManageServiceImpl implements GiftDemandManageService {

    @Autowired
    private IGiftDemandService giftDemandService;

    @Override
    public BaseResult addGiftDemand(Long customerId, GiftDemandParam giftDemandParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(giftDemandParam.getRequestId());
        try {
            if (StringUtils.isBlank(giftDemandParam.getBudget())) {
                result.setErrorMessage("1001", "预算为空");
                return result;
            }
            if (StringUtils.isBlank(giftDemandParam.getScene())) {
                result.setErrorMessage("1001", "应用场景为空");
                return result;
            }
            if (StringUtils.isBlank(giftDemandParam.getTelephone())) {
                result.setErrorMessage("1001", "联系电话为空");
                return result;
            }
            if (StringUtils.isBlank(giftDemandParam.getGiftFeature())) {
                result.setErrorMessage("1001", "礼品特征为空");
                return result;
            }
            if (StringUtils.isBlank(giftDemandParam.getGiftNum())) {
                result.setErrorMessage("1001", "礼品总量为空");
                return result;
            }
            if (customerId != null) {
                giftDemandParam.setCustomerId(customerId);
                GiftDemand giftDemand = GiftDemandMapper.toGiftDemand(giftDemandParam);
                giftDemand.setStatus("2");
                giftDemandService.addGiftDemand(giftDemand);
            } else {
                result.setErrorMessage("1001", "登录已过期");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addGiftDemand", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }
}
