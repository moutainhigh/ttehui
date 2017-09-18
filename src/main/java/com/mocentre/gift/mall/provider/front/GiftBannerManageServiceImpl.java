package com.mocentre.gift.mall.provider.front;

import com.mocentre.common.ListResult;
import com.mocentre.gift.frontend.model.GiftBannerFTInstance;
import com.mocentre.gift.frontend.service.GiftBannerManageService;
import com.mocentre.gift.mall.mapper.GiftBannerMapper;
import com.mocentre.gift.mall.model.GiftBanner;
import com.mocentre.gift.mall.service.IGiftBannerService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiftBannerManageServiceImpl implements GiftBannerManageService {

    @Autowired
    private IGiftBannerService giftBannerService;


    @Override
    public ListResult<GiftBannerFTInstance> queryBannerList(String requestId) {
        ListResult<GiftBannerFTInstance> result = new ListResult<GiftBannerFTInstance>();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("isShow", "1");
            param.put("column", "sort");
            param.put("orderBy", "asc");
            List<GiftBanner> bannerList = giftBannerService.queryAll(param);
            result.setData(GiftBannerMapper.toGiftBannerFTInstanceList(bannerList));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryBannerList", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }
}
