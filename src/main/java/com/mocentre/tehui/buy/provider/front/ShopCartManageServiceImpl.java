/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.provider.front;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.buy.mapper.PayConfigMapper;
import com.mocentre.tehui.buy.mapper.ShopCartMapper;
import com.mocentre.tehui.buy.model.PayConfig;
import com.mocentre.tehui.buy.model.ShoppingCart;
import com.mocentre.tehui.buy.service.IPayConfigService;
import com.mocentre.tehui.buy.service.IShoppingCartService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.frontend.model.CashGoodsFTInstance;
import com.mocentre.tehui.frontend.model.CashOrderFTInstance;
import com.mocentre.tehui.frontend.model.CashPayFTInstance;
import com.mocentre.tehui.frontend.model.CustomerAddressFTInstance;
import com.mocentre.tehui.frontend.model.ShopCartDetailFTInstance;
import com.mocentre.tehui.frontend.model.ShopCartFTInstance;
import com.mocentre.tehui.frontend.model.ShopCartNumFTInstance;
import com.mocentre.tehui.frontend.param.ShopCartAddParam;
import com.mocentre.tehui.frontend.param.ShopCartChangeParam;
import com.mocentre.tehui.frontend.param.ShopCartDetailParam;
import com.mocentre.tehui.frontend.param.ShopCartListParam;
import com.mocentre.tehui.frontend.service.ShopCartManageService;
import com.mocentre.tehui.goods.enums.GoodsCheckedType;
import com.mocentre.tehui.goods.enums.GoodsShowType;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.model.GoodsStorage;
import com.mocentre.tehui.goods.service.IGoodsService;
import com.mocentre.tehui.goods.service.IGoodsStorageService;
import com.mocentre.tehui.ps.mapper.CustomerAddressMapper;
import com.mocentre.tehui.ps.model.CustomerAddress;
import com.mocentre.tehui.ps.service.ICustomerAddressService;
import com.mocentre.tehui.shop.enums.ShopBussStatus;
import com.mocentre.tehui.shop.model.Shop;
import com.mocentre.tehui.shop.service.IShopService;
import com.mocentre.tehui.system.service.IAreasService;

/**
 * 类ShopCartManageServiceImpl.java的实现描述：购物车提供者
 * 
 * @author sz.gong 2016年11月23日 下午3:23:12
 */
public class ShopCartManageServiceImpl implements ShopCartManageService {

    @Autowired
    private IShoppingCartService    shopCartService;
    @Autowired
    private IGoodsStorageService    goodsStorageService;
    @Autowired
    private IGoodsService           goodsService;
    @Autowired
    private ICustomerAddressService cumAddsService;
    @Autowired
    private IPayConfigService       payConfigService;
    @Autowired
    private IShopService            shopService;
    @Autowired
    private IAreasService           areasService;
    @Autowired
    private CustomerAddressMapper   customerAddressMapper;

    @Override
    public PlainResult<ShopCartNumFTInstance> add(ShopCartAddParam shopCartParam) {

        PlainResult<ShopCartNumFTInstance> pr = new PlainResult<ShopCartNumFTInstance>();
        pr.setRequestId(shopCartParam.getRequestId());
        ShopCartNumFTInstance scnFTIns = new ShopCartNumFTInstance();
        try {
            Long customerId = shopCartParam.getCustomerId();
            Long goodsId = shopCartParam.getGoodsId();
            Integer num = shopCartParam.getNum();
            Long actGoodsId = shopCartParam.getActGoodsId() == null ? 0l : shopCartParam.getActGoodsId();
            String goodsSku = shopCartParam.getGoodsSku();
            Goods goods = goodsService.getGoodsBaseInfoFromCache(goodsId);
            if (goods == null) {
                pr.setErrorMessage("1002", "商品不存在");
                return pr;
            }
            Long shopId = goods.getShopId();
            Shop shop = shopService.queryShop(shopId);
            if (shop == null || !shop.getBuss_status().equals(ShopBussStatus.OPENING.getCodeValue())) {
                pr.setErrorMessage("1001", "店铺已关闭");
                return pr;
            }
            GoodsStorage goodsStorage = goodsStorageService.queryGoodsStorageFromCache(goodsId, goodsSku, actGoodsId);
            boolean storagePass = goodsStorage == null
                    || num.longValue() > (goodsStorage.getStockNum() == null ? 0 : goodsStorage.getStockNum()
                            .longValue());
            if (storagePass) {
                pr.setErrorMessage("1003", "库存不足");
                return pr;
            }
            ShoppingCart shopCart = new ShoppingCart();
            shopCart.setCustomerId(customerId);
            shopCart.setShopId(shopId);
            shopCart.setShopName(shop.getName());
            shopCart.setGoodsId(goodsId);
            shopCart.setGoodsName(goods.getTitle());
            shopCart.setOldPrice(goodsStorage.getOldPrice());
            shopCart.setSellPrice(goodsStorage.getSalePrice());
            shopCart.setGoodsSku(goodsStorage.getStandardCode());
            shopCart.setGoodsSkuDes(goodsStorage.getDescribe());
            shopCart.setActGoodsId(actGoodsId);
            shopCart.setShowLogo(goods.getImgCart());
            shopCart.setNum(num);
            String limitBuy = goods.getIsLimitBuy();
            Integer limitNum = goods.getLimitNums();
            limitNum = limitNum == null ? 0 : limitNum;
            if (customerId == null) {
                if ("1".equals(limitBuy)) {
                    if (num > limitNum) {
                        pr.setErrorMessage("1004", "商品限制购买数量：" + limitNum);
                        return pr;
                    }
                }
                scnFTIns = ShopCartMapper.toShopCartNumFTInstance(shopCart, 0);
            } else {
                ShoppingCart spCart = shopCartService.getShopCartUnique(customerId, goodsId, actGoodsId, goodsSku);
                if (spCart != null) {
                    if ("1".equals(limitBuy)) {
                        Integer havaNum = shopCartService.getGoodsSum(customerId, goodsId, actGoodsId);
                        Integer allNum = havaNum + num;
                        if (allNum > limitNum) {
                            pr.setErrorMessage("1004", "商品限制购买数量：" + limitNum);
                            return pr;
                        }
                    }
                    Long id = spCart.getId();
                    Integer nums = spCart.getNum() + num;
                    shopCartService.updateShopCartNum(id, nums);
                } else {
                    shopCartService.saveShopCart(shopCart);
                }
                Integer totalNum = shopCartService.getShopCartCount(customerId);
                scnFTIns.setTotalNum(totalNum);
            }
            pr.setData(scnFTIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("add", e);
            pr.setErrorMessage("999", "接口异常");
        }
        return pr;
    }

    @Override
    public ListResult<ShopCartFTInstance> queryList(ShopCartListParam listParam) {

        ListResult<ShopCartFTInstance> result = new ListResult<ShopCartFTInstance>();
        result.setRequestId(listParam.getRequestId());
        Long customerId = listParam.getCustomerId();
        List<ShopCartDetailParam> shopCartDetailParamList = listParam.getShopCartDetailParamList();
        try {
            List<ShoppingCart> shopCartList = new ArrayList<ShoppingCart>();
            if (customerId == null) {
                if (shopCartDetailParamList != null) {
                    for (ShopCartDetailParam scdParam : shopCartDetailParamList) {
                        ShoppingCart sc = new ShoppingCart();
                        sc.setActGoodsId(scdParam.getActGoodsId());
                        sc.setGoodsId(scdParam.getGoodsId());
                        sc.setGoodsSku(scdParam.getGoodsSku());
                        sc.setShopId(scdParam.getShopId());
                        sc.setShopName(scdParam.getShopName());
                        sc.setGoodsSkuDes(scdParam.getGoodsSkuDes());
                        sc.setGoodsName(scdParam.getGoodsName());
                        sc.setNum(scdParam.getBuyNum());
                        sc.setShowLogo(scdParam.getShowLogo());
                        sc.setOldPrice(scdParam.getOldPrice() == null ? 0l : scdParam.getOldPrice()
                                .multiply(new BigDecimal(100)).longValue());
                        sc.setSellPrice(scdParam.getSellPrice() == null ? 0l : scdParam.getSellPrice()
                                .multiply(new BigDecimal(100)).longValue());
                        shopCartList.add(sc);
                    }
                }
            } else {
                shopCartList = shopCartService.queryShopCartByCustomer(customerId);
            }
            List<ShopCartFTInstance> cartInList = new ArrayList<ShopCartFTInstance>();
            Set<Long> shopIds = new HashSet<Long>();//店铺id，去重复
            if (shopCartList != null) {
                for (int i = 0; i < shopCartList.size(); i++) {
                    ShoppingCart sc = shopCartList.get(i);
                    shopIds.add(sc.getShopId());
                }
                Iterator<Long> it = shopIds.iterator();
                while (it.hasNext()) {
                    ShopCartFTInstance cartIns = new ShopCartFTInstance();
                    List<ShopCartDetailFTInstance> cartdInsList = new ArrayList<ShopCartDetailFTInstance>();
                    Long shopId = it.next();
                    boolean exist = false;
                    for (int i = 0; i < shopCartList.size(); i++) {
                        ShoppingCart sc = shopCartList.get(i);
                        Long goodsId = sc.getGoodsId();
                        String stCode = sc.getGoodsSku();
                        Long actGoodsId = sc.getActGoodsId();
                        if (shopId == sc.getShopId() && !exist) {
                            exist = true;
                            cartIns.setShopId(shopId);
                            cartIns.setShopName(sc.getShopName());
                        }
                        if (shopId == sc.getShopId()) {
                            Goods goods = goodsService.getGoodsBaseInfoFromCache(goodsId);
                            GoodsStorage goodsStorage = goodsStorageService.queryGoodsStorageFromCache(goodsId, stCode,
                                    actGoodsId);
                            //                            Goods goods = goodsService.getGoodsById(goodsId);
                            //                            GoodsStorage goodsStorage = goodsStorageService
                            //                                    .queryGoodsStorageByStandard(goodsId, stCode);
                            ShopCartDetailFTInstance scdIns = ShopCartMapper.toShopCartDetailFTInstance(sc, goods,
                                    goodsStorage);
                            cartdInsList.add(scdIns);
                        }
                    }
                    cartIns.setShopDetailList(cartdInsList);
                    cartInList.add(cartIns);
                }
            }
            result.setData(cartInList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryList", e);
            result.setErrorMessage("999", "接口异常");
        }
        return result;
    }

    @Override
    public BaseResult changeNum(ShopCartChangeParam changeParam) {

        BaseResult result = new BaseResult();
        result.setRequestId(changeParam.getRequestId());
        try {
            Long customerId = changeParam.getCustomerId();
            Long goodsId = changeParam.getGoodsId();
            Long actGoodsId = changeParam.getActGoodsId();
            Integer changeNum = changeParam.getChangeNum();
            String goodsSku = changeParam.getGoodsSku();
            boolean paramValid = goodsId == null || changeNum == null || changeNum == 0
                    || StringUtils.isBlank(goodsSku);
            if (paramValid) {
                result.setErrorMessage("1000", "参数错误");
                return result;
            }
            ShoppingCart shopCart = null;
            if (actGoodsId == null) {
                actGoodsId = 0l;
            }
            if (customerId != null) {
                shopCart = shopCartService.getShopCartUnique(customerId, goodsId, actGoodsId, goodsSku);
                if (shopCart == null) {
                    result.setErrorMessage("1000", "购物车商品不存在");
                    return result;
                }
            }
            Goods goods = goodsService.getGoodsBaseInfoFromCache(goodsId);
            if (goods == null) {
                result.setErrorMessage("1001", "商品不存在");
                return result;
            }
            String limitBuy = goods.getIsLimitBuy();
            Integer limitNum = goods.getLimitNums();
            limitNum = limitNum == null ? 0 : limitNum;
            if (changeNum > 0) {
                if ("1".equals(limitBuy)) {
                    if (changeNum > limitNum) {
                        result.setErrorMessage("1002", "商品限制购买数量：" + limitNum);
                        return result;
                    }
                }
            }
            GoodsStorage storage = goodsStorageService.queryGoodsStorageFromCache(goodsId, goodsSku, actGoodsId);
            //Long storage = goodsStorageDao.queryStorageByStandard(goodsId, standardCode,actGoodsId);
            if (storage == null) {
                result.setErrorMessage("1001", "商品库存不足");
                return result;
            }
            Long stockNum = storage.getStockNum();
            if (stockNum.longValue() == 0 || stockNum < changeNum) {
                result.setErrorMessage("1001", "商品库存不足");
                return result;
            }
            if (customerId != null && shopCart != null) {
                Long id = shopCart.getId();
                Long count = shopCartService.updateShopCartNum(id, changeNum);
                if (count == 0) {
                    result.setErrorMessage("1002", "操作失败");
                    return result;
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("changeNum", e);
            result.setErrorMessage("999", "接口异常");
        }
        return result;
    }

    @Override
    public BaseResult delete(ShopCartChangeParam changeParam) {

        BaseResult result = new BaseResult();
        result.setRequestId(changeParam.getRequestId());
        try {
            Long customerId = changeParam.getCustomerId();
            Long goodsId = changeParam.getGoodsId();
            Long actGoodsId = changeParam.getActGoodsId();
            String goodsSku = changeParam.getGoodsSku();
            if (actGoodsId == null) {
                actGoodsId = 0l;
            }
            boolean paramValid = goodsId == null || StringUtils.isBlank(goodsSku);
            if (paramValid) {
                result.setErrorMessage("1000", "参数错误");
                return result;
            }
            if (customerId != null) {
                ShoppingCart shopCart = shopCartService.getShopCartUnique(customerId, goodsId, actGoodsId, goodsSku);
                if (shopCart == null) {
                    result.setErrorMessage("1001", "操作失败");
                    return result;
                }
                Long id = shopCart.getId();
                int count = shopCartService.deleteShopCart(customerId, id);
                if (count == 0) {
                    result.setErrorMessage("1001", "操作失败");
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("delete", e);
            result.setErrorMessage("999", "接口异常");
        }
        return result;
    }

    @Override
    public PlainResult<CashOrderFTInstance> buyCart(ShopCartListParam listParam) {

        PlainResult<CashOrderFTInstance> result = new PlainResult<CashOrderFTInstance>();
        CashOrderFTInstance cashOrderIns = new CashOrderFTInstance();
        result.setRequestId(listParam.getRequestId());
        Long customerId = listParam.getCustomerId();
        List<ShopCartDetailParam> scdParamList = listParam.getShopCartDetailParamList();
        try {
            List<ShoppingCart> shopCartList = null;
            if (customerId == null) {//未登入
                shopCartList = new ArrayList<ShoppingCart>();
                if (scdParamList != null) {
                    for (ShopCartDetailParam scdParam : scdParamList) {
                        ShoppingCart shopCart = new ShoppingCart();
                        shopCart.setGoodsId(scdParam.getGoodsId());
                        shopCart.setActGoodsId(scdParam.getActGoodsId() == null ? 0l : scdParam.getActGoodsId());
                        shopCart.setNum(scdParam.getBuyNum());
                        shopCart.setGoodsSku(scdParam.getGoodsSku());
                        shopCartList.add(shopCart);
                    }
                }
            } else {
                shopCartList = shopCartService.queryShopCartByCustomer(customerId);
            }
            if (shopCartList == null || shopCartList.size() == 0) {
                result.setErrorMessage("1000", "购物车无商品");
                return result;
            }
            Long totalPrice = 0L;
            List<CashGoodsFTInstance> cashGoodsList = new ArrayList<CashGoodsFTInstance>();
            for (ShoppingCart shopCart : shopCartList) {
                Long goodsId = shopCart.getGoodsId();
                String goodsSku = shopCart.getGoodsSku();
                Integer buyNum = shopCart.getNum();
                Long actGoodsId = shopCart.getActGoodsId();
                Goods goods = goodsService.getGoodsBaseInfoFromCache(goodsId);
                //Goods goods = goodsService.getGoodsById(goodsId);
                if (goods != null) {
                    boolean goodsPass = GoodsCheckedType.CHECKPASS.getCodeValue().equals(goods.getIsChecked())
                            && GoodsShowType.ONSHELF.getCodeValue().equals(goods.getIsShow());
                    if (goodsPass) {
                        GoodsStorage storage = goodsStorageService.queryGoodsStorageFromCache(goodsId, goodsSku,
                                actGoodsId);
                        // GoodsStorage storage = goodsStorageService.queryGoodsStorageByStandard(goodsId, goodsSku);
                        if (storage != null) {
                            Long sellPrice = storage.getSalePrice();
                            CashGoodsFTInstance cashGoods = new CashGoodsFTInstance();
                            cashGoods.setBuyNum(buyNum);
                            cashGoods.setGoodsId(goodsId);
                            cashGoods.setStandardCode(goodsSku);
                            cashGoods.setActGoodsId(actGoodsId);
                            cashGoods.setSellPrice(new BigDecimal(sellPrice).divide(new BigDecimal(100)));
                            cashGoods.setShowImg(goods.getImgCart());
                            cashGoodsList.add(cashGoods);
                            totalPrice += sellPrice * buyNum;
                        }
                    }
                }
            }
            CustomerAddressFTInstance cumAddsIns = null;
            if (customerId != null) {
                cashOrderIns.setIsLogin(1);
                CustomerAddress cumAdds = cumAddsService.getDefAddressByCustomerId(customerId);
                if (cumAdds != null) {
                    cumAddsIns = customerAddressMapper.toCustomerAddressFTInstance(cumAdds);
                }
            } else {
                cashOrderIns.setIsLogin(0);
            }
            List<PayConfig> payConfigList = payConfigService.queryOpenPay();
            List<CashPayFTInstance> cashPayList = new ArrayList<CashPayFTInstance>();
            if (payConfigList != null) {
                for (PayConfig payConf : payConfigList) {
                    CashPayFTInstance cashPay = PayConfigMapper.toCashPayFTInstance(payConf);
                    cashPayList.add(cashPay);
                }
            }
            cashOrderIns.setCashGoodsList(cashGoodsList);
            cashOrderIns.setCustomerAddress(cumAddsIns);
            cashOrderIns.setGoodsNum(shopCartList.size());
            cashOrderIns.setPayList(cashPayList);
            cashOrderIns.setTotalPrice(new BigDecimal(totalPrice).divide(new BigDecimal(100)));
            cashOrderIns.setComefrom("1");
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("buyCart", e);
            result.setErrorMessage("999", "接口异常");
        }
        result.setData(cashOrderIns);
        return result;
    }

    @Override
    public PlainResult<Integer> getShopCartCount(Long customerId, String requestId) {

        PlainResult<Integer> result = new PlainResult<Integer>();
        result.setRequestId(requestId);
        try {
            Integer count = shopCartService.getShopCartCount(customerId);
            result.setData(count);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getShopCartCount", e);
            result.setErrorMessage("999", "接口异常");
        }
        return result;
    }

    //    @Override
    //    public BaseResult syncCart(ShopCartListParam listParam) {
    //        BaseResult br = new BaseResult();
    //        br.setRequestId(listParam.getRequestId());
    //        List<ShopCartDetailParam> shopCartParamList = listParam.getShopCartDetailParamList();
    //        if (listParam == null || shopCartParamList == null || shopCartParamList.size() == 0) {
    //            return br;
    //        }
    //        Long customerId = listParam.getCustomerId();
    //        try {
    //            List<ShoppingCart> shopCartAllList = new ArrayList<ShoppingCart>();
    //            List<ShoppingCart> shopCartList = shopCartService.queryShopCartByCustomer(customerId);
    //            if (shopCartList != null) {
    //                for (ShopCartDetailParam shopCartParam : shopCartParamList) {
    //                    Long goodsId = shopCartParam.getGoodsId();
    //                    Long actGoodsId = shopCartParam.getActGoodsId();
    //                    String goodsSku = shopCartParam.getGoodsSku();
    //                    boolean exists = false;
    //                    for (ShoppingCart shopCart : shopCartList) {
    //                        if (goodsId.equals(shopCart.getGoodsId()) && actGoodsId.equals(shopCart.getActGoodsId())
    //                                && goodsSku.equals(shopCart.getGoodsSku())) {//是否已经存在
    //                            shopCart.setNum(shopCart.getNum() + shopCartParam.getBuyNum());
    //                            exists = true;
    //                            break;
    //                        }
    //                    }
    //                    if (!exists) {
    //                        ShoppingCart shopCart = ShopCartMapper.toShoppingCart(customerId, shopCartParam);
    //                        shopCartAllList.add(shopCart);
    //                    }
    //                }
    //                shopCartAllList.addAll(shopCartList);
    //            } else {
    //                for (ShopCartDetailParam scdParam : shopCartParamList) {
    //                    ShoppingCart shopCart = ShopCartMapper.toShoppingCart(customerId, scdParam);
    //                    shopCartAllList.add(shopCart);
    //                }
    //            }
    //            shopCartService.saveShopCart(customerId, shopCartAllList);
    //        } catch (Exception e) {
    //            LoggerUtil.tehuiLog.error("syncCart", e);
    //            br.setErrorMessage("999", "接口异常");
    //        }
    //        return br;
    //    }
}
