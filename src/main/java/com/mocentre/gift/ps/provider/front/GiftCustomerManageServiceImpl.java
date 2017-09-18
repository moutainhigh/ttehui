package com.mocentre.gift.ps.provider.front;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.gift.frontend.model.GiftCustomerAddressFTInstance;
import com.mocentre.gift.frontend.model.GiftCustomerFTInstance;
import com.mocentre.gift.frontend.param.GiftCustomerAddsParam;
import com.mocentre.gift.frontend.service.GiftCustomerManageService;
import com.mocentre.gift.ps.enums.GiftCustomerStatus;
import com.mocentre.gift.ps.mapper.GiftCustomerAddressMapper;
import com.mocentre.gift.ps.mapper.GiftCustomerMapper;
import com.mocentre.gift.ps.model.GiftCustomer;
import com.mocentre.gift.ps.model.GiftCustomerAddress;
import com.mocentre.gift.ps.service.IGiftCustomerAddressService;
import com.mocentre.gift.ps.service.IGiftCustomerService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.system.model.Areas;
import com.mocentre.tehui.system.service.IAreasService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心相关接口实现。
 *
 * @author update by yukaiji on 2017年1月19日
 */

public class GiftCustomerManageServiceImpl implements GiftCustomerManageService {

    @Autowired
    private IGiftCustomerAddressService giftCustomerAddressService;
    @Autowired
    private IAreasService areasService;
    @Autowired
    private IGiftCustomerService customerService;
    @Override
    public ListResult<GiftCustomerAddressFTInstance> getAddressByCustomerId(Long customerId, String requestId) {
        ListResult<GiftCustomerAddressFTInstance> result = new ListResult<GiftCustomerAddressFTInstance>();
        result.setRequestId(requestId);
        List<GiftCustomerAddressFTInstance> cumAddsInsList = new ArrayList<GiftCustomerAddressFTInstance>();
        try {
            List<GiftCustomerAddress> customerAddressList = giftCustomerAddressService.getAddressByCustomerId(customerId);
            if (customerAddressList != null) {
                for (int i = 0; i < customerAddressList.size(); i++) {
                    GiftCustomerAddress cumAdds = customerAddressList.get(i);

                    GiftCustomerAddressFTInstance cumAddsIns = new GiftCustomerAddressFTInstance();
                    cumAddsIns = GiftCustomerAddressMapper.toGiftCustomerAddressFTInstance(cumAdds);
                    if (!StringUtils.isBlank(cumAdds.getProvince())) {
                        cumAddsIns.setProvinceName(areasService.getNameFromCache(cumAdds.getProvince()));
                    }
                    if (!StringUtils.isBlank(cumAdds.getCity())) {
                        cumAddsIns.setCityName(areasService.getNameFromCache(cumAdds.getCity()));
                    }
                    if (!StringUtils.isBlank(cumAdds.getArea())) {
                        cumAddsIns.setAreaName(areasService.getNameFromCache(cumAdds.getArea()));
                    }
                    cumAddsInsList.add(cumAddsIns);
                }
            }
            result.setData(cumAddsInsList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getAddressByCustomerId", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<GiftCustomerAddressFTInstance> getAddressById(Long customerId, Long id, String requestId) {
        PlainResult<GiftCustomerAddressFTInstance> result = new PlainResult<GiftCustomerAddressFTInstance>();
        result.setRequestId(requestId);
        try {
            GiftCustomerAddress giftCustomerAddress = giftCustomerAddressService.getAddressByIdCum(customerId, id);
            GiftCustomerAddressFTInstance addressIns = GiftCustomerAddressMapper.toGiftCustomerAddressFTInstance(giftCustomerAddress);
            if (giftCustomerAddress != null && StringUtils.isNotBlank(giftCustomerAddress.getProvince())) {
                addressIns.setProvinceName(areasService.getNameFromCache(giftCustomerAddress.getProvince()));
            }
            if (giftCustomerAddress != null && StringUtils.isNotBlank(giftCustomerAddress.getCity())) {
                addressIns.setCityName(areasService.getNameFromCache(giftCustomerAddress.getCity()));
            }
            if (giftCustomerAddress != null && StringUtils.isNotBlank(giftCustomerAddress.getArea())) {
                addressIns.setAreaName(areasService.getNameFromCache(giftCustomerAddress.getArea()));
            }
            result.setData(addressIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getAddressById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<GiftCustomerAddressFTInstance> saveCustomerAddress(GiftCustomerAddsParam giftCustomerAddsParam) {
        PlainResult<GiftCustomerAddressFTInstance> result = new PlainResult<GiftCustomerAddressFTInstance>();
        GiftCustomerAddressFTInstance cumAddsIns = new GiftCustomerAddressFTInstance();
        result.setRequestId(giftCustomerAddsParam.getRequestId());
        try {
            if (!GiftCustomerStatus.EDITDEFAULT.getCodeValue().equals(giftCustomerAddsParam.getFlag())) {
                boolean paramValid = StringUtils.isBlank(giftCustomerAddsParam.getTelephone())
                        || StringUtils.isBlank(giftCustomerAddsParam.getRecipient())
                        || StringUtils.isBlank(giftCustomerAddsParam.getProvince())
                        || StringUtils.isBlank(giftCustomerAddsParam.getArea()) || StringUtils.isBlank(giftCustomerAddsParam.getCity());
                if (paramValid) {
                    result.setErrorMessage("1000", "参数错误");
                    return result;
                }
            }
            if (GiftCustomerStatus.ADD.getCodeValue().equals(giftCustomerAddsParam.getFlag())) {
                GiftCustomerAddress cumAdds = GiftCustomerAddressMapper.toGiftCustomerAddress(giftCustomerAddsParam);
                GiftCustomerAddress cumAddress = giftCustomerAddressService.addAddress(cumAdds);
                if (cumAddress != null) {
                    cumAddress.setId(cumAdds.getId());
                    cumAddsIns = GiftCustomerAddressMapper.toGiftCustomerAddressFTInstance(cumAddress);
                }
                result.setData(cumAddsIns);
                return result;
            }
            if (giftCustomerAddsParam.getId() == null) {
                result.setErrorMessage("1000", "参数错误");
                return result;
            }
            if (giftCustomerAddsParam.getFlag().equals(GiftCustomerStatus.EDIT.getCodeValue())) {
                GiftCustomerAddress cumAdds = GiftCustomerAddressMapper.toGiftCustomerAddress(giftCustomerAddsParam);
                GiftCustomerAddress cumAddress = giftCustomerAddressService.editAddress(cumAdds);
                if (cumAddress != null) {
                    cumAddsIns = GiftCustomerAddressMapper.toGiftCustomerAddressFTInstance(cumAddress);
                    Areas proArea = areasService.getAreasFromCache(cumAddress.getProvince());
                    Areas citArea = areasService.getAreasFromCache(cumAddress.getCity());
                    Areas areArea = areasService.getAreasFromCache(cumAddress.getArea());
                    cumAddsIns.setProvinceName(proArea == null ? null : proArea.getName());
                    cumAddsIns.setCityName(citArea == null ? null : citArea.getName());
                    cumAddsIns.setAreaName(areArea == null ? null : areArea.getName());
                }
                result.setData(cumAddsIns);
                return result;
            }
            if (giftCustomerAddsParam.getFlag().equals(GiftCustomerStatus.EDITDEFAULT.getCodeValue())) {
                giftCustomerAddressService.editAddressDefault(giftCustomerAddsParam.getId(), giftCustomerAddsParam.getCustomerId());
                result.setData(cumAddsIns);
                return result;
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("saveCustomerAddress", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult deleteCustomerAddressByIdList(List<Long> idList, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            int count = giftCustomerAddressService.delAddressList(idList);
            if (count == 0) {
                result.setErrorMessage("1000", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteCustomerAddressByIdList", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<GiftCustomerFTInstance> getcustomerInfo(Long customerId, String requestId) {
        PlainResult<GiftCustomerFTInstance> result = new PlainResult<>();
        result.setRequestId(requestId);
        try {
            GiftCustomer customer = customerService.getGiftCustomer(customerId);
            if (customer == null) {
                result.setErrorMessage("1000", "用户不存在");
            }
            if(result.isSuccess()){
                result.setData( GiftCustomerMapper.toGiftCustomerFTInstance(customer));
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteCustomerAddressByIdList", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }
}
