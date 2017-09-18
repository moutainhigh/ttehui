package com.mocentre.tehui.ps.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.CustomerInfoManageService;
import com.mocentre.tehui.backend.model.CustomerAddressInstance;
import com.mocentre.tehui.backend.model.CustomerInfoInstance;
import com.mocentre.tehui.backend.param.CustomerInfoQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.ps.mapper.CustomerAddressMapper;
import com.mocentre.tehui.ps.mapper.CustomerInfoMapper;
import com.mocentre.tehui.ps.model.CustomerAddress;
import com.mocentre.tehui.ps.model.CustomerInfo;
import com.mocentre.tehui.ps.service.ICustomerAddressService;
import com.mocentre.tehui.ps.service.ICustomerInfoService;

/**
 * 买家用户部分 Created by 王雪莹 on 2016/11/23.
 */
public class CustomerInfoManageServiceImpl implements CustomerInfoManageService {

    @Autowired
    private ICustomerInfoService    customerInfoService;
    @Autowired
    private ICustomerAddressService customerAddressService;
    @Autowired
    private CustomerAddressMapper   customerAddressMapper;

    @Override
    public ListJsonResult<CustomerInfoInstance> queryPage(CustomerInfoQueryParam customerQueryParam) {
        ListJsonResult<CustomerInfoInstance> result = new ListJsonResult<CustomerInfoInstance>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("telephone", customerQueryParam.getTelephone());
            paramMap.put("regBeginTime", customerQueryParam.getRegBeginTime());
            paramMap.put("regEndTime", customerQueryParam.getRegEndTime());
            Requirement require = new Requirement(customerQueryParam.getDraw(), customerQueryParam.getStart(),
                    customerQueryParam.getLength(), paramMap);
            result = customerInfoService.queryPage(require);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    /**
     * 根据id查询详细信息
     * 
     * @param id
     * @return
     */
    @Override
    public PlainResult<CustomerInfoInstance> getById(Long id, String requestId) {
        PlainResult<CustomerInfoInstance> result = new PlainResult<CustomerInfoInstance>();
        try {
            List<CustomerAddress> customerAddressList = customerAddressService.getAddressByCustomerId(id);
            CustomerInfo customerInfo = customerInfoService.getCustomerById(id);
            if (customerInfo != null) {
                List<CustomerAddressInstance> customerAddsList = new ArrayList<CustomerAddressInstance>();
                CustomerInfoInstance cumIns = CustomerInfoMapper.toCustomerInfoInstance(customerInfo);
                if (customerAddressList != null) {
                    for (int i = 0; i < customerAddressList.size(); i++) {
                        CustomerAddress cumAdds = customerAddressList.get(i);
                        CustomerAddressInstance cumAddsIns = customerAddressMapper.toCustomerAddressInstance(cumAdds);
                        customerAddsList.add(cumAddsIns);
                    }
                }
                cumIns.setCustomerAddsList(customerAddsList);
                result.setData(cumIns);
            } else {
                result.setErrorMessage("1001", "用户不存在");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getByUserName", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    /**
     * 根据id批量删除
     * 
     * @param idList
     */
    @Override
    public BaseResult deleteCustomerByIdList(List<Long> idList, String requestId) {
        BaseResult result = new BaseResult();
        try {
            Long count = customerInfoService.delCustomById(idList);
            if (count.longValue() == 0) {
                result.setErrorMessage("1000", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteCustomerByIdList", e);
            result.setErrorMessage("999", "删除失败");
        }
        return result;
    }

}
