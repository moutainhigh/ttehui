package com.mocentre.gift.mall.service.impl;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.backend.param.GiftBannerParam;
import com.mocentre.gift.mall.dao.IGiftBannerDao;
import com.mocentre.gift.mall.mapper.GiftBannerMapper;
import com.mocentre.gift.mall.model.GiftBanner;
import com.mocentre.gift.mall.service.IGiftBannerService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 礼品平台 banner图service实现类
 * 
 * @author liqifan
 * @date 创建时间：2017年4月7日 下午4:15:14
 */
@Component
public class GiftBannerService implements IGiftBannerService {

    @Autowired
    private IGiftBannerDao giftBannerDao;

    @Override
    public List<GiftBanner> queryAll(Map<String, Object> paramMap) {
        return giftBannerDao.queryList(paramMap);
    }

    @Override
    public GiftBanner getGiftBanner(Long id) {
        return giftBannerDao.get(id);
    }

    @Override
    public void updateGiftBanner(GiftBannerParam giftBanner) {
        giftBannerDao.updateEntity(GiftBannerMapper.toGiftBanner(giftBanner));
    }

    @Override
    public void saveGiftBanner(GiftBannerParam giftBanner) {
        giftBannerDao.saveEntity(GiftBannerMapper.toGiftBanner(giftBanner));
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
                    giftBannerDao.logicRemoveById(id);
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("delete", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListJsonResult<GiftBanner> queryGiftBannerPage(Requirement require) {
        return giftBannerDao.queryDatatablesPage(require);
    }

}
