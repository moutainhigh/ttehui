package com.mocentre.gift.gd.provider;

import com.mocentre.common.*;
import com.mocentre.gift.GiftGoodsManageService;
import com.mocentre.gift.backend.model.GiftGoodsInstance;
import com.mocentre.gift.backend.model.GiftGoodsSelectInstance;
import com.mocentre.gift.backend.param.GiftGoodsParam;
import com.mocentre.gift.backend.param.GiftGoodsQueryParam;
import com.mocentre.gift.gd.enums.GoodsCheckedType;
import com.mocentre.gift.gd.enums.GoodsShowType;
import com.mocentre.gift.gd.mapper.GiftCategoryMapper;
import com.mocentre.gift.gd.mapper.GiftGoodsMapper;
import com.mocentre.gift.gd.model.GiftCategory;
import com.mocentre.gift.gd.model.GiftGoods;
import com.mocentre.gift.gd.service.IGiftCategoryService;
import com.mocentre.gift.gd.service.IGiftGoodsService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import org.springframework.beans.factory.annotation.Autowired;

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
    public ListJsonResult<GiftGoodsInstance> queryGiftGoodsPage(GiftGoodsQueryParam queryParam) {
        ListJsonResult<GiftGoodsInstance> result = new ListJsonResult<GiftGoodsInstance>();
        result.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("title", queryParam.getTitle());
            paramMap.put("isChecked", queryParam.getIsChecked());
            paramMap.put("isShow", queryParam.getIsShow());
            paramMap.put("orderBy", queryParam.getOrderBy());
            paramMap.put("column", queryParam.getOrderColumn());
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            ListJsonResult<GiftGoods> lr = giftGoodsService.queryGiftGoodsPage(require);
            List<GiftGoods> giftGoodsList = lr.getData();
            result.setData(GiftGoodsMapper.toGiftGoodsInsList(giftGoodsList));
            result.setDraw(lr.getDraw());
            result.setRecordsFiltered(lr.getRecordsFiltered());
            result.setRecordsTotal(lr.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryGiftGoodsPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public MapResult editPage(Long id) {
        MapResult result = new MapResult();
        try {
            GiftGoods giftGoods = giftGoodsService.getGiftGoodsById(id);
            GiftCategory giftCategory = giftCategoryService.getGiftCategoryById(giftGoods.getCategoryId());
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("pid", giftCategory.getPid());
            List<GiftCategory> categories = giftCategoryService.getGiftCategoryByParam(param);
            List<GiftCategory> categoryList = giftCategoryService.getGiftCategoryByParam(null);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pid", giftCategory.getPid());
            map.put("categoryList", GiftCategoryMapper.toGiftCatgoryInsList(categoryList));
            map.put("categories", GiftCategoryMapper.toGiftCatgoryInsList(categories));
            map.put("giftGoods", GiftGoodsMapper.toGiftGoodsIns(giftGoods));
            result.setData(map);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("editPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListResult<GiftGoodsInstance> queryGoodsByCategoryId(Long categoryId, String requestId) {
        ListResult<GiftGoodsInstance> result = new ListResult<GiftGoodsInstance>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("categoryId", categoryId);
            List<GiftGoods> giftGoodsList = giftGoodsService.getGiftGoodsByParam(paramMap);
            result.setData(GiftGoodsMapper.toGiftGoodsInsList(giftGoodsList));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryGoodsByCategoryId", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<GiftGoodsInstance> getGiftGoodsById(Long id, String requestId) {
        PlainResult<GiftGoodsInstance> result = new PlainResult<GiftGoodsInstance>();
        result.setRequestId(requestId);
        try {
            GiftGoods giftGoods = giftGoodsService.getGiftGoodsById(id);
            result.setData(GiftGoodsMapper.toGiftGoodsIns(giftGoods));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getGiftGoodsById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<Long> addGiftGoods(GiftGoodsParam giftGoodsParam) {
        PlainResult<Long> result = new PlainResult<Long>();
        result.setRequestId(giftGoodsParam.getRequestId());
        giftGoodsParam.setIsChecked(GoodsCheckedType.UNCHECK.getCodeValue());
        giftGoodsParam.setIsShow(GoodsShowType.NOTSHELF.getCodeValue());
        try {
            GiftGoods giftGoods = giftGoodsService.addGiftGoods(giftGoodsParam);
            if (giftGoods.getId() == null) {
                result.setErrorMessage("999", "添加失败");
                return result;
            }
            result.setData(giftGoods.getId());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addGiftGoods", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult updateGiftGoods(GiftGoodsParam GiftGoodsParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(GiftGoodsParam.getRequestId());
        try {
            Long num = giftGoodsService.updateGiftGoods(GiftGoodsParam);
            if (num < 0) {
                result.setErrorMessage("999", "修改失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateGiftGoods", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult removeById(Long id, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            int num = giftGoodsService.removeById(id);
            if (num <= 0) {
                result.setErrorMessage("999", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("removeById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListJsonResult<GiftGoodsSelectInstance> queryForSelect(String requestId) {
        ListJsonResult<GiftGoodsSelectInstance> result = new ListJsonResult();
        result.setRequestId(requestId);
        try {
            List<GiftGoods> giftList = giftGoodsService.selectGiftList();
            List<GiftGoodsSelectInstance> instanceList = GiftGoodsMapper.toGiftGoodsSelectInstanceList(giftList);
            result.setData(instanceList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("removeById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }
}
