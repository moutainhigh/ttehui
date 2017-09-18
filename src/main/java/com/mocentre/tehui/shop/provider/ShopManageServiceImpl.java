package com.mocentre.tehui.shop.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.ShopManageService;
import com.mocentre.tehui.backend.model.ShopInstance;
import com.mocentre.tehui.backend.param.ShopParam;
import com.mocentre.tehui.backend.param.ShopQueryParam;
import com.mocentre.tehui.backend.param.UserParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.shop.model.Shop;
import com.mocentre.tehui.shop.service.IShopService;
import com.mocentre.tehui.system.service.IUserService;

/**
 * 店铺信息接口. Created by yukaiji on 2016/11/13.
 */
public class ShopManageServiceImpl implements ShopManageService {

    @Autowired
    private IShopService shopService;
    @Autowired
    private IUserService userService;

    @Override
    public ListJsonResult<ShopInstance> queryPage(ShopQueryParam shopQuery) {
        ListJsonResult<ShopInstance> result = new ListJsonResult<ShopInstance>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Requirement require = new Requirement(shopQuery.getDraw(), shopQuery.getStart(), shopQuery.getLength(),
                    paramMap);
            result = shopService.queryShopPage(require);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<ShopInstance> getShop(Long id, String requestId) {

        PlainResult<ShopInstance> result = new PlainResult<ShopInstance>();
        try {
            ShopInstance shopIns = shopService.getShop(id);
            result.setData(shopIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getShop", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public String getShopListInName(String name) {
        return JSON.toJSONString(shopService.getShopListInName(name));
    }

    @Override
    public String queryShop(Map<String, Object> paramMap) {
        return JSON.toJSONString(shopService.queryShop(paramMap));
    }

    @Override
    public PlainResult<Long> addShop(ShopParam shopParam) {

        PlainResult<Long> result = new PlainResult<Long>();
        try {
            Shop shop = shopService.addShop(shopParam);
            if (shop != null && shop.getId() != null) {
                UserParam userParam = new UserParam();
                userParam.setId(shopParam.getUserId());
                userParam.setShopId(shop.getId());
                userService.updateUser(userParam);
                result.setData(shop.getId());
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addShop", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult editShop(ShopParam shopParam) {

        BaseResult result = new BaseResult();
        try {
            shopService.editShop(shopParam);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("editShop", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult deleteShop(List<Long> idList, String requestId) {

        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            shopService.deleteShop(idList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteShop", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;

    }

    @Override
    public BaseResult examineShop(Long id, String audit_status, String requestId) {

        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            shopService.examineShop(id, audit_status);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("examineShop", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult operationShop(Long id, String buss_status, String requestId) {

        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            shopService.operationShop(id, buss_status);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("operationShop", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }
}
