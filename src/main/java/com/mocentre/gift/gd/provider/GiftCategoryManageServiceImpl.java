package com.mocentre.gift.gd.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.gift.GiftCategoryManageService;
import com.mocentre.gift.backend.model.GiftCategoryInstance;
import com.mocentre.gift.backend.param.GiftCategoryParam;
import com.mocentre.gift.backend.param.GiftCategoryQueryParam;
import com.mocentre.gift.gd.mapper.GiftCategoryMapper;
import com.mocentre.gift.gd.model.GiftCategory;
import com.mocentre.gift.gd.service.IGiftCategoryService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 礼品平台分类dubbo接口实现类.
 * <p>
 * Created by yukaiji on 2017/4/6.
 */
public class GiftCategoryManageServiceImpl implements GiftCategoryManageService {

    @Autowired
    private IGiftCategoryService giftCategoryService;

    @Override
    public ListJsonResult<GiftCategoryInstance> queryGiftCategoryPage(GiftCategoryQueryParam queryParam) {
        ListJsonResult<GiftCategoryInstance> result = new ListJsonResult<GiftCategoryInstance>();
        result.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("pid", queryParam.getPid());
            paramMap.put("orderBy", queryParam.getOrderBy());
            paramMap.put("column", queryParam.getOrderColumn());
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            ListJsonResult<GiftCategory> lr = giftCategoryService.queryGiftCategoryPage(require);
            List<GiftCategory> categoryList = lr.getData();
            result.setData(GiftCategoryMapper.toGiftCatgoryInsList(categoryList));
            result.setDraw(lr.getDraw());
            result.setRecordsFiltered(lr.getRecordsFiltered());
            result.setRecordsTotal(lr.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryGiftCategoryPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<GiftCategoryInstance> getGiftCategoryById(Long id, String requestId) {
        PlainResult<GiftCategoryInstance> result = new PlainResult<GiftCategoryInstance>();
        result.setRequestId(requestId);
        try {
            GiftCategory giftCategory = giftCategoryService.getGiftCategoryById(id);
            result.setData(GiftCategoryMapper.toGiftCatgoryIns(giftCategory));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getGiftCategoryById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListResult<GiftCategoryInstance> queryGiftCategory(String cType, String pid) {
        ListResult<GiftCategoryInstance> result = new ListResult<GiftCategoryInstance>();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("cType", cType);
            param.put("pid", pid);
            List<GiftCategory> giftCategoryList = giftCategoryService.getGiftCategoryByParam(param);
            result.setData(GiftCategoryMapper.toGiftCatgoryInsList(giftCategoryList));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryGiftCategory", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult addGiftCategory(GiftCategoryParam giftCategoryParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(giftCategoryParam.getRequestId());
        try {
            GiftCategory giftCategory = giftCategoryService.addGiftCategory(giftCategoryParam);
            if (giftCategory.getId() == null) {
                result.setErrorMessage("999", "添加失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addGiftCategory", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult updateGiftCategory(GiftCategoryParam giftCategoryParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(giftCategoryParam.getRequestId());
        try {
            Long num = giftCategoryService.updateGiftCategory(giftCategoryParam);
            if (num < 0) {
                result.setErrorMessage("999", "修改失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateGiftCategory", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult removeById(Long id, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            int num = giftCategoryService.removeById(id);
            if (num <= 0) {
                result.setErrorMessage("999", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("removeById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }
}
