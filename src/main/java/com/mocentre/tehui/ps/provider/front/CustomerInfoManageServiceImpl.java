package com.mocentre.tehui.ps.provider.front;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.common.utils.CommUtil;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.frontend.model.CustomerAddressFTInstance;
import com.mocentre.tehui.frontend.model.CustomerInfoFTInstance;
import com.mocentre.tehui.frontend.param.CustomerAddsParam;
import com.mocentre.tehui.frontend.param.CustomerInfoParam;
import com.mocentre.tehui.frontend.service.CustomerInfoManageService;
import com.mocentre.tehui.ps.enums.CustomerStatus;
import com.mocentre.tehui.ps.mapper.CustomerAddressMapper;
import com.mocentre.tehui.ps.mapper.CustomerInfoMapper;
import com.mocentre.tehui.ps.model.CustomerAddress;
import com.mocentre.tehui.ps.model.CustomerInfo;
import com.mocentre.tehui.ps.service.ICustomerAddressService;
import com.mocentre.tehui.ps.service.ICustomerInfoService;

/**
 * 商城用户个人中心调用接口实现。
 *
 * @author update by yukaiji on 2017年1月19日
 */

public class CustomerInfoManageServiceImpl implements CustomerInfoManageService {

    @Autowired
    private ICustomerInfoService    customerInfoService;
    @Autowired
    private ICustomerAddressService customerAddressService;
    @Autowired
    private CustomerAddressMapper   customerAddressMapper;

    @Override
    public PlainResult<CustomerInfoFTInstance> getCustomerInfoById(Long id, String requestId) {
        CustomerInfoFTInstance cumIns = new CustomerInfoFTInstance();
        PlainResult<CustomerInfoFTInstance> result = new PlainResult<CustomerInfoFTInstance>();
        result.setRequestId(requestId);
        try {
            CustomerInfo customerInfo = customerInfoService.getCustomerById(id);
            if (customerInfo == null) {
                result.setErrorMessage("1001", "用户不存在");
                return result;
            }
            cumIns = CustomerInfoMapper.toCustomerInfoFTInstance(customerInfo);
            result.setData(cumIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getCustomerInfoById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult saveCustomerInfo(CustomerInfoParam customerInfoParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(customerInfoParam.getRequestId());
        try {
            customerInfoParam.setPassword(null);
            customerInfoService.updateCustomerInfo(customerInfoParam);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("saveCustomerInfo", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult editPassword(CustomerInfoParam customerInfoParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(customerInfoParam.getRequestId());
        try {
            Long id = customerInfoParam.getId();
            // 旧密码
            String password = customerInfoParam.getPassword();
            //新密码
            String newPassword = customerInfoParam.getNewPassword();
            // 重复新密码
            String repeatPassword = customerInfoParam.getRepeatPassword();
            // 6位随机数
            String randomNum = "";
            for (int i = 0; i < 6; i++) {
                randomNum += (CommUtil.randomRang(0, 9));
            }
            CustomerInfo customerInfo = customerInfoService.getCustomerById(id);
            // 库原始密码
            String dbPassword = customerInfo.getPassword();
            if (!newPassword.equals(repeatPassword)) {
                result.setErrorMessage("1001", "两次输入密码不一致");
                return result;
            }
            if (StringUtils.isBlank(dbPassword)) {
                customerInfoService.updatePassWord(id, DigestUtils.md5Hex(newPassword + randomNum), randomNum);
                return result;
            }
            if (!dbPassword.equals(DigestUtils.md5Hex(password + customerInfo.getRandomNum()))) {
                result.setErrorMessage("1002", "原密码错误");
                return result;
            }
            customerInfoService.updatePassWord(id, DigestUtils.md5Hex(newPassword + randomNum), randomNum);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("editPassword", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<String> getPasswordIsNull(Long id, String requestId) {
        PlainResult<String> result = new PlainResult<>();
        try {
            CustomerInfo customerInfo = customerInfoService.getCustomerById(id);
            if (customerInfo.getPassword() == null || customerInfo.getPassword().equals("")) {
                result.setData("isNull");
            } else {
                result.setData("unNull");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getPasswordIsNull", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<CustomerAddressFTInstance> saveCustomerAddress(CustomerAddsParam cumAddsParam) {
        PlainResult<CustomerAddressFTInstance> result = new PlainResult<CustomerAddressFTInstance>();
        CustomerAddressFTInstance cumAddsIns = new CustomerAddressFTInstance();
        result.setRequestId(cumAddsParam.getRequestId());
        try {
            if (!CustomerStatus.EDITDEFAULT.getCodeValue().equals(cumAddsParam.getFlag())) {
                boolean paramValid = StringUtils.isBlank(cumAddsParam.getTelephone())
                        || StringUtils.isBlank(cumAddsParam.getRecipient())
                        || StringUtils.isBlank(cumAddsParam.getProvince())
                        || StringUtils.isBlank(cumAddsParam.getArea()) || StringUtils.isBlank(cumAddsParam.getCity());
                if (paramValid) {
                    result.setErrorMessage("1000", "参数错误");
                    return result;
                }
            }
            if (CustomerStatus.ADD.getCodeValue().equals(cumAddsParam.getFlag())) {
                CustomerAddress cumAdds = customerAddressMapper.toCustomerAddress(cumAddsParam);
                CustomerAddress cumAddress = customerAddressService.addAddress(cumAdds);
                if (cumAddress != null) {
                    cumAddress.setId(cumAdds.getId());
                    cumAddsIns = customerAddressMapper.toCustomerAddressFTInstance(cumAddress);
                }
                result.setData(cumAddsIns);
                return result;
            }
            if (cumAddsParam.getId() == null) {
                result.setErrorMessage("1000", "参数错误");
                return result;
            }
            if (cumAddsParam.getFlag().equals(CustomerStatus.EDIT.getCodeValue())) {
                CustomerAddress cumAdds = customerAddressMapper.toCustomerAddress(cumAddsParam);
                CustomerAddress cumAddress = customerAddressService.editAddress(cumAdds);
                if (cumAddress != null) {
                    cumAddsIns = customerAddressMapper.toCustomerAddressFTInstance(cumAddress);
                }
                result.setData(cumAddsIns);
                return result;
            }
            if (cumAddsParam.getFlag().equals(CustomerStatus.EDITDEFAULT.getCodeValue())) {
                customerAddressService.editAddressDefault(cumAddsParam.getId(), cumAddsParam.getCustomerId());
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
            int count = customerAddressService.delAddressList(idList);
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
    public ListResult<CustomerAddressFTInstance> getAddressByCustomerId(Long customerId, String requestId) {
        ListResult<CustomerAddressFTInstance> result = new ListResult<CustomerAddressFTInstance>();
        result.setRequestId(requestId);
        List<CustomerAddressFTInstance> cumAddsInsList = new ArrayList<CustomerAddressFTInstance>();
        try {
            List<CustomerAddress> customerAddressList = customerAddressService.getAddressByCustomerId(customerId);
            if (customerAddressList != null) {
                for (int i = 0; i < customerAddressList.size(); i++) {
                    CustomerAddress cumAdds = customerAddressList.get(i);

                    CustomerAddressFTInstance cumAddsIns = new CustomerAddressFTInstance();
                    cumAddsIns = customerAddressMapper.toCustomerAddressFTInstance(cumAdds);
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
    public PlainResult<CustomerAddressFTInstance> getAddressById(Long customerId, Long id, String requestId) {
        PlainResult<CustomerAddressFTInstance> result = new PlainResult<CustomerAddressFTInstance>();
        result.setRequestId(requestId);
        try {
            CustomerAddress customerAddress = customerAddressService.getAddressByIdCum(customerId, id);
            CustomerAddressFTInstance addressIns = customerAddressMapper.toCustomerAddressFTInstance(customerAddress);
            result.setData(addressIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getAddressById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
