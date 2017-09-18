package com.mocentre.tehui.goods.provider.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mocentre.common.BaseResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.frontend.model.StoreFTInstance;
import com.mocentre.tehui.frontend.param.StoreParam;
import com.mocentre.tehui.frontend.service.StoreManageService;
import com.mocentre.tehui.goods.enums.StoreStatus;
import com.mocentre.tehui.goods.mapper.StoreMapper;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.model.Store;
import com.mocentre.tehui.goods.service.IGoodsService;
import com.mocentre.tehui.goods.service.IStoreService;
import com.mocentre.tehui.shop.model.Shop;
import com.mocentre.tehui.shop.service.IShopService;

/**
 * 优惠券接口实现 Created by yukaiji on 2016/11/24.
 */
public class StoreManageServiceImpl implements StoreManageService {

    @Autowired
    private IStoreService storeService;
    @Autowired
    private IShopService  shopService;
    @Autowired
    private IGoodsService goodsService;

    @Override
    public PlainResult<StoreFTInstance> getStore(Long id, Long customerId, String requestId) {
        PlainResult<StoreFTInstance> result = new PlainResult<StoreFTInstance>();
        result.setRequestId(requestId);
        try {
            Store store = storeService.getStore(id, customerId);
            StoreFTInstance storeFTInstance = StoreMapper.toInstance(store);
            result.setData(storeFTInstance);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getStore", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListResult<StoreFTInstance> getStoreList(Long customerId, String requestId) {
        ListResult<StoreFTInstance> result = new ListResult<>();
        result.setRequestId(requestId);
        try {
            List<Store> storeList = storeService.getStoreList(customerId);
            List<StoreFTInstance> dataList = StoreMapper.toInstanceList(storeList);
            result.setData(dataList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getStoreList", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult saveStore(StoreParam storeParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(storeParam.getRequestId());
        try {
            Long cumId = storeParam.getCustomerId();
            Long shopId = storeParam.getShopId();
            Long goodsId = storeParam.getGoodsId();
            String status = storeParam.getStatus();
            boolean paramValid = goodsId == null || shopId == null || StringUtils.isBlank(status);
            if (paramValid) {
                result.setErrorMessage("1000", "参数无效");
                return result;
            }
            Store store = new Store();
            store.setCustomerId(cumId);
            store.setShopId(shopId);
            store.setGoodsId(goodsId);
            if (status.equals(StoreStatus.ADD.getCodeValue())) {
                int count = storeService.countStoreByGoods(goodsId, cumId);
                if (count > 0) {
                    result.setErrorMessage("1001", "该商品已经收藏");
                    return result;
                }
                Shop shop = shopService.queryShop(shopId);
                Goods goods = goodsService.getGoodsById(goodsId);
                store.setShopName(shop.getName());
                store.setGoodsName(goods.getTitle());
                store.setOldPrice(goods.getOldPrice());
                store.setSellPrice(goods.getSellPrice());
                store.setShowLogo(goods.getImgCart());
                Store resultStore = storeService.addStore(store);
                if (resultStore == null) {
                    result.setErrorMessage("999", "添加失败");
                }
            } else if (status.equals(StoreStatus.DELETE.getCodeValue())) {
                int delNum = storeService.deleteStore(store);
                if (delNum <= 0) {
                    result.setErrorMessage("999", "删除失败");
                }
            } else {
                result.setErrorMessage("999", "操作标识无效");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("saveStore", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }
}
