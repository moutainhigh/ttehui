package com.mocentre.gift.gd.provider.front;


import com.mocentre.common.ListResult;
import com.mocentre.gift.frontend.model.GiftCategoryFTInstance;
import com.mocentre.gift.frontend.param.GiftCategoreParam;
import com.mocentre.gift.frontend.service.GiftCategoryManageService;
import com.mocentre.gift.gd.mapper.GiftCategoryMapper;
import com.mocentre.gift.gd.model.GiftCategory;
import com.mocentre.gift.gd.service.IGiftCategoryService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 礼品平台分类前端调用接口实现类.
 * <p>
 * Created by yukaiji on 2017/4/11.
 */
public class GiftCategoryManageServiceImpl implements GiftCategoryManageService {

    @Autowired
    private IGiftCategoryService giftCategoryService;

    @Override
    public ListResult<GiftCategoryFTInstance> queryGiftCategory(GiftCategoreParam giftCategoreParam) {
        ListResult<GiftCategoryFTInstance> result = new ListResult<GiftCategoryFTInstance>();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("cType", giftCategoreParam.getType());
            param.put("pid", giftCategoreParam.getPid());
            param.put("isShow", 1);
            List<GiftCategory> giftCategoryList = giftCategoryService.getGiftCategoryByParam(param);
            result.setData(GiftCategoryMapper.toGiftCatgoryFTInsList(giftCategoryList));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryGiftCategory", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }
}
