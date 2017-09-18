package com.mocentre.gift.gd.provider.front;


import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.gift.frontend.model.GiftGoodsFTInstance;
import com.mocentre.gift.frontend.param.GiftGoodsParam;
import com.mocentre.gift.frontend.param.GiftGoodsQueryParam;
import com.mocentre.gift.frontend.service.GiftGoodsManageService;
import com.mocentre.gift.gd.enums.GoodsQueryStatus;
import com.mocentre.gift.gd.enums.GoodsShowType;
import com.mocentre.gift.gd.mapper.GiftGoodsMapper;
import com.mocentre.gift.gd.model.GiftCategory;
import com.mocentre.gift.gd.model.GiftGoods;
import com.mocentre.gift.gd.service.IGiftCategoryService;
import com.mocentre.gift.gd.service.IGiftGoodsService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 礼品平台礼品相关接口实现类.
 * <p>
 * Created by yukaiji on 2017/4/6.
 */
public class GiftGoodsManageServiceImpl implements GiftGoodsManageService {

    @Autowired
    private IGiftGoodsService       giftGoodsService;
    @Autowired
    private IGiftCategoryService    giftCategoryService;

    @Override
    public ListResult<GiftGoodsFTInstance> queryGiftGoodsList(GiftGoodsQueryParam queryParam) {
        ListResult<GiftGoodsFTInstance> result = new ListResult<GiftGoodsFTInstance>();
        result.setRequestId(queryParam.getRequestId());
        try {
            if (StringUtils.isBlank(queryParam.getStatus())) {
                result.setErrorMessage("1001", "操作标识为空");
                return result;
            }
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("start", queryParam.getStart());
            paramMap.put("length", queryParam.getLength());
            paramMap.put("orderBy", queryParam.getOrderby());
            paramMap.put("column", queryParam.getColumn());
            paramMap.put("isShow", GoodsShowType.ONSHELF.getCodeValue());
            if (null != queryParam.getStartPrice()) {
                paramMap.put("startPrice", queryParam.getStartPrice() * 100);
            }
            if (null != queryParam.getEndPrice()) {
                paramMap.put("endPrice", queryParam.getEndPrice() * 100);
            }
            Map<String, Object> categoryParam = new HashMap<String, Object>();
            if (GoodsQueryStatus.ALL.getCodeValue().equals(queryParam.getStatus())) {
                categoryParam.put("pid", "0");
            }
            if (GoodsQueryStatus.FRIST.getCodeValue().equals(queryParam.getStatus())) {
                categoryParam.put("pid", queryParam.getCategoryId());
                if (queryParam.getCategoryId() == null) {
                    result.setErrorMessage("1001", "分类id为空");
                    return result;
                }
            }
            // 获取一级分类下的所有商品，例如获取所有优惠券商品
            if (!GoodsQueryStatus.SECOND.getCodeValue().equals(queryParam.getStatus())) {
                List<Long> idList = new ArrayList<>();
                categoryParam.put("cType", queryParam.getType());
                // 获取与优惠券有关的所有分类id
                List<GiftCategory> giftCategoryList = giftCategoryService.getGiftCategoryByParam(categoryParam);
                for (GiftCategory giftCategory : giftCategoryList) {
                    idList.add(giftCategory.getId());
                }
                if (idList.size() <= 0) {
                    result.setData(new ArrayList<GiftGoodsFTInstance>());
                    return result;
                }
                paramMap.put("idList", idList);
            }
            if (GoodsQueryStatus.SECOND.getCodeValue().equals(queryParam.getStatus())) {
                paramMap.put("categoryId", queryParam.getCategoryId());
            }
            List<GiftGoods> giftGoodsList = giftGoodsService.getGiftGoodsListByCategoryId(paramMap);
            result.setData(GiftGoodsMapper.toGiftGoodsFTInsList(giftGoodsList));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryGiftGoodsList", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<GiftGoodsFTInstance> getGiftGoodsById(GiftGoodsParam giftGoodsParam) {
        PlainResult<GiftGoodsFTInstance> result = new PlainResult<GiftGoodsFTInstance>();
        result.setRequestId(giftGoodsParam.getRequestId());
        try {
            if (null == giftGoodsParam.getId()) {
                result.setErrorMessage("1001", "礼品id为空");
                return result;
            }
            GiftGoods giftGoods = giftGoodsService.getGiftGoodsById(giftGoodsParam.getId());
            result.setData(GiftGoodsMapper.toGiftGoodsFTIns(giftGoods));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getGiftGoodsById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }
}
