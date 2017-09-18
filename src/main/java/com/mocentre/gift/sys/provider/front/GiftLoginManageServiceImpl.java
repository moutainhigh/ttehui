package com.mocentre.gift.sys.provider.front;

import com.mocentre.common.BaseResult;
import com.mocentre.common.PlainResult;
import com.mocentre.gift.frontend.model.GiftCustomerFTInstance;
import com.mocentre.gift.frontend.service.GiftLoginManageService;
import com.mocentre.gift.ps.mapper.GiftCustomerMapper;
import com.mocentre.gift.ps.model.GiftCustomer;
import com.mocentre.gift.ps.service.IGiftCustomerService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class GiftLoginManageServiceImpl implements GiftLoginManageService {

    @Autowired
    private IGiftCustomerService giftCustomerService;

    @Override
    public PlainResult<GiftCustomerFTInstance> customerLoginByPassword(String userName, String password, String requestId) {
        PlainResult<GiftCustomerFTInstance> result = new PlainResult<GiftCustomerFTInstance>();
        result.setRequestId(requestId);
        try {
            GiftCustomer giftCustomer = giftCustomerService.getGiftCustomerByUserName(userName);

            if (giftCustomer == null) {
                result.setErrorMessage("1000", "用户名或密码错误");
                return result;
            }

            String randomNum = giftCustomer.getRandomNum();
            boolean passwd = StringUtils.isBlank(giftCustomer.getPassword())
                    || !(giftCustomer.getPassword().equals(DigestUtils.md5Hex(password + randomNum)));
            if (passwd) {
                result.setErrorMessage("1000", "用户名或密码错误");
                return result;
            }

            GiftCustomerFTInstance customerIns = GiftCustomerMapper.toGiftCustomerFTInstance(giftCustomer);
            result.setData(customerIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("customerLoginByPassword", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult saveTelephoneByCustomerId(Long customerId, String telphone, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            if (StringUtils.isBlank(telphone)) {
                result.setErrorMessage("1000", "手机号不能为空");
                return result;
            }
            GiftCustomer giftCustomer = new GiftCustomer();
            giftCustomer.setId(customerId);
            giftCustomer.setTelephone(telphone);
            giftCustomerService.updateGiftCustomer(giftCustomer);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("saveTelphoneByCustomerId", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }
}
