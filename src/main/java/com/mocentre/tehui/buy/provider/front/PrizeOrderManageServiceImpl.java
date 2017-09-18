package com.mocentre.tehui.buy.provider.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListResult;
import com.mocentre.tehui.buy.mapper.PrizeOrderMapper;
import com.mocentre.tehui.buy.model.PrizeOrder;
import com.mocentre.tehui.buy.service.IPrizeOrderService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.frontend.model.CustomerAddressFTInstance;
import com.mocentre.tehui.frontend.model.PrizeOrderFTInstance;
import com.mocentre.tehui.frontend.param.PrizeOrderQueryParam;
import com.mocentre.tehui.frontend.service.PrizeOrderManageService;
import com.mocentre.tehui.ps.mapper.CustomerAddressMapper;
import com.mocentre.tehui.ps.model.CustomerAddress;
import com.mocentre.tehui.ps.service.ICustomerAddressService;

/**
 * 实物礼品订单 Created by wangxueying on 2017/8/11.
 */
public class PrizeOrderManageServiceImpl implements PrizeOrderManageService {

    @Autowired
    private IPrizeOrderService      prizeOrderService;
    @Autowired
    private ICustomerAddressService addressService;
    @Autowired
    private CustomerAddressMapper   customerAddressMapper;

    @Override
    public ListResult<PrizeOrderFTInstance> prizeOrderList(PrizeOrderQueryParam orderParam) {
        ListResult<PrizeOrderFTInstance> result = new ListResult<PrizeOrderFTInstance>();
        result.setRequestId(orderParam.getRequestId());
        try {
            Long customerId = orderParam.getCustomerId();
            Integer start = orderParam.getStart();
            Integer length = orderParam.getLength();
            PageInfo<PrizeOrder> pageInfo = prizeOrderService.queryPrizeOrderPage(customerId, start, length);
            List<PrizeOrder> prizeOrderList = pageInfo.getRows();
            List<PrizeOrderFTInstance> orderInsList = PrizeOrderMapper.toPrizeOrderFTInstanceList(prizeOrderList);
            result.setData(orderInsList);
            result.setCount((int) pageInfo.getTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("prizeOrderList", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public BaseResult addAddress(Long id, Long addressId, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            if (addressId == null || id == null) {
                result.setErrorMessage("1000", "参数错误");
                return result;
            }
            PrizeOrder prizeOrder = new PrizeOrder();
            prizeOrder.setId(id);
            if (addressId != null) {
                CustomerAddress address = addressService.getAddressById(addressId);
                if (address == null) {
                    result.setErrorMessage("1001", "操作失败");
                    return result;
                }
                prizeOrder.setAddressId(addressId);
                prizeOrder.setRecipient(address.getRecipient());
                prizeOrder.setTelephone(address.getTelephone());
                CustomerAddressFTInstance cumAddressIns = customerAddressMapper.toCustomerAddressFTInstance(address);
                String prov = cumAddressIns.getProvinceName();
                String city = cumAddressIns.getCityName();
                String area = cumAddressIns.getAreaName();
                String adds = cumAddressIns.getAddress();
                prizeOrder.setAddress(prov + city + area + adds);
            }
            prizeOrderService.updatePrizeOrder(prizeOrder);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addAddress", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

}
