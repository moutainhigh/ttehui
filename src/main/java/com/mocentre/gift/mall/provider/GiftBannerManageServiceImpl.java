package com.mocentre.gift.mall.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.GiftBannerManageService;
import com.mocentre.gift.backend.model.GiftBannerInstance;
import com.mocentre.gift.backend.param.GiftBannerParam;
import com.mocentre.gift.backend.param.GiftBannerQueryParam;
import com.mocentre.gift.mall.mapper.GiftBannerMapper;
import com.mocentre.gift.mall.model.GiftBanner;
import com.mocentre.gift.mall.service.IGiftBannerService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;

public class GiftBannerManageServiceImpl implements GiftBannerManageService {

    @Autowired
    private IGiftBannerService giftBannerService;

    @Override
    public ListJsonResult<GiftBannerInstance> queryPage(GiftBannerQueryParam queryParam) {
        ListJsonResult<GiftBannerInstance> result = new ListJsonResult<GiftBannerInstance>();
        result.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderBy", queryParam.getOrderBy());
            paramMap.put("column", queryParam.getOrderColumn());
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            ListJsonResult<GiftBanner> lr = giftBannerService.queryGiftBannerPage(require);
            List<GiftBanner> giftBannerList = lr.getData();
            result.setData(GiftBannerMapper.toGiftBannerInstanceList(giftBannerList));
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
    public GiftBannerParam getGiftBanner(Long id) {
        return GiftBannerMapper.toGiftBannerParam(giftBannerService.getGiftBanner(id));
    }

    @Override
    public void updateGiftBanner(GiftBannerParam giftBanner) {
        giftBannerService.updateGiftBanner(giftBanner);
    }

    @Override
    public void saveGiftBanner(GiftBannerParam giftBanner) {
        giftBannerService.saveGiftBanner(giftBanner);
    }

    @Override
    public BaseResult deleteById(List<Long> idList, String requestId) {
        BaseResult br = new BaseResult();
        BaseResult result = giftBannerService.delete(idList, requestId);
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
