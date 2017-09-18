package com.mocentre.gift.buy.provider.front;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.gift.buy.mapper.GiftGiftSheetMapper;
import com.mocentre.gift.buy.model.GiftGiftSheet;
import com.mocentre.gift.buy.service.IGiftGiftSheetService;
import com.mocentre.gift.buy.service.impl.GiftGiftSheetService;
import com.mocentre.gift.frontend.model.GiftConfirmOrderFTInstance;
import com.mocentre.gift.frontend.model.GiftGiftSheetFTInstance;
import com.mocentre.gift.frontend.param.GiftGiftSheetFTParam;
import com.mocentre.gift.frontend.service.GiftGiftSheetManageService;
import com.mocentre.gift.gd.model.GiftGoods;
import com.mocentre.gift.gd.service.IGiftGoodsService;
import com.mocentre.gift.gd.service.impl.GiftGoodsService;
import com.mocentre.gift.ps.model.GiftCustomerAddress;
import com.mocentre.gift.ps.service.IGiftCustomerAddressService;
import com.mocentre.gift.ps.service.impl.GiftCustomerAddressService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车相关dubbo实现
 * Created by 王雪莹 on 2017/4/15.
 */
public class GiftGiftSheetManageServiceImpl implements GiftGiftSheetManageService {
    @Autowired
    private IGiftGiftSheetService service;
    @Autowired
    private IGiftGoodsService goodsService;
    @Autowired
    private IGiftCustomerAddressService addressService;

    @Override
    public BaseResult addGiftSheet(Long customerId, GiftGiftSheetFTParam param) {
        BaseResult result = new BaseResult();
        GiftGoods goods = goodsService.getGiftGoodsById(param.getGoodsId());
        if (goods == null) {
            result.setErrorMessage("999", "该商品不存在");
        }
        if (result.isSuccess()) {
            try {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("goodsId", param.getGoodsId());
                paramMap.put("customerId", customerId);
                List<GiftGiftSheet> giftSheets = service.queryGiftSheetByParam(paramMap);
                if (giftSheets.size() == 1) {
                    GiftGiftSheet giftGiftSheet = giftSheets.get(0);
                    service.updateGiftSheetNum(customerId, giftGiftSheet.getId(), giftGiftSheet.getNum() + param.getNum());
                    return result;
                } else if (giftSheets.size() <= 0) {
                    GiftGiftSheet giftSheet = GiftGiftSheetMapper.toGiftGiftSheet(param);
                    giftSheet.setGoodsImg(goods.getImgListPageMobile());
                    giftSheet.setGoodsName(goods.getTitle());
                    giftSheet.setPrice(goods.getPrice().longValue());
                    giftSheet.setCustomerId(customerId);
                    if(goods.getIsLimitBuy() == 1){
                        giftSheet.setLimitNum(goods.getLimitNums());
                    }else {
                        giftSheet.setLimitNum(1);
                    }
                    service.saveGiftSheet(giftSheet);
                } else {
                    result.setErrorMessage("999", "礼品单查询数量有误");
                }

            } catch (Exception e) {
                LoggerUtil.tehuiLog.error("queryPage", e);
                result.setErrorMessage("999", "系统异常");
            }
        }
        return result;
    }

    @Override
    public ListResult<GiftGiftSheetFTInstance> queryList(Long customerId, String requestId) {
        ListResult<GiftGiftSheetFTInstance> result = new ListResult<GiftGiftSheetFTInstance>();
        try {
            List<GiftGiftSheet> GiftGiftSheetList = service.queryGiftListByCustomer(customerId);
            List<GiftGiftSheetFTInstance> instanceList = GiftGiftSheetMapper.toGIftGiftSheetFTInstanceList(GiftGiftSheetList);
            result.setData(instanceList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryList", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public BaseResult changeNum(Long customerId, Long id, Integer num, String requestId) {
        BaseResult result = new BaseResult();
        try {
            service.updateGiftSheetNum(customerId, id, num);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("changeNum", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public BaseResult delete(Long customerId, Long id, String requestId) {
        BaseResult result = new BaseResult();
        try {
            service.deleteGiftList(customerId, id);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("delete", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<GiftConfirmOrderFTInstance> buy(Long customerId, List<Long> idList, String requestId) {
        PlainResult<GiftConfirmOrderFTInstance> result = new PlainResult<GiftConfirmOrderFTInstance>();
        try {
            GiftConfirmOrderFTInstance instance = new GiftConfirmOrderFTInstance();
            List<GiftGiftSheet> giftGiftSheetList = service.getByIdList(idList, customerId);
            instance.setCashGoodsList(GiftGiftSheetMapper.toGIftGiftSheetFTInstanceList(giftGiftSheetList));

            GiftCustomerAddress address = addressService.getDefAddressByCustomerId(customerId);
            if (address == null) {
                List<GiftCustomerAddress> addressList = addressService.getAddressByCustomerId(customerId);
                if (addressList.size() > 0) {
                    address = addressList.get(0);
                }
            }
            if (address != null) {
                instance.setAddress(address.getProvince() + address.getCity() + address.getArea() + address.getAddress());
                instance.setAddressId(address.getId());
            }
            instance.setGoodsNum(giftGiftSheetList.size());
            Long totalPrice = 0l;
            for (GiftGiftSheet giftSheet : giftGiftSheetList) {
                totalPrice += giftSheet.getPrice() * giftSheet.getNum();
            }
            instance.setTotalPrice(String.valueOf(totalPrice / 100d));
            result.setData(instance);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("buy", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<Long> querySheetNum(Long customerId, String requestId) {
        PlainResult<Long> result = new PlainResult<Long>();
        Long num = 0L;
        try {
            List<GiftGiftSheet> giftSheets = service.queryGiftListByCustomer(customerId);
            for (GiftGiftSheet giftsheet : giftSheets) {
                num += giftsheet.getNum();
            }
            result.setData(num);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("querySheetNum", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }
}
