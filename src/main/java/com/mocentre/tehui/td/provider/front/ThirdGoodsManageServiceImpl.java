package com.mocentre.tehui.td.provider.front;

import com.mocentre.common.PlainResult;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.frontend.model.TdGoodsFTInstance;
import com.mocentre.tehui.frontend.service.ThirdGoodsManageService;
import com.mocentre.tehui.td.emnus.ThirdGoodsShowLocal;
import com.mocentre.tehui.td.mapper.ThirdGoodsMapper;
import com.mocentre.tehui.td.model.ThirdGoods;
import com.mocentre.tehui.td.service.impl.ThirdGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 第三方商品dubbo接口实现类
 * <p>
 * Created by yukaiji on 2017/5/17.
 */

public class ThirdGoodsManageServiceImpl implements ThirdGoodsManageService {

    @Autowired
    private ThirdGoodsService thirdGoodsService;

    @Override
    public PlainResult<TdGoodsFTInstance> getThirdGoodsByBDCityCode(String bdCityCode, String requestId) {
        PlainResult<TdGoodsFTInstance> result = new PlainResult<TdGoodsFTInstance>();
        result.setRequestId(requestId);
        try {
            List<ThirdGoods> actGoodsList = thirdGoodsService.getThirdGoodsList(ThirdGoodsShowLocal.ACT.getCodeValue(), bdCityCode);
            List<ThirdGoods> specialGoodsList = thirdGoodsService.getThirdGoodsList(ThirdGoodsShowLocal.SPECIAL.getCodeValue(), bdCityCode);
            result.setData(ThirdGoodsMapper.toTdGoodsFTInstance(actGoodsList, specialGoodsList));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getThirdGoodsByBDCityCode", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
