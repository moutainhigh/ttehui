/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.provider.front;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.PlainResult;
import com.mocentre.tehui.buy.mapper.ShopCartMapper;
import com.mocentre.tehui.buy.model.ShoppingCart;
import com.mocentre.tehui.buy.service.IShoppingCartService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.frontend.model.CustomerInfoFTInstance;
import com.mocentre.tehui.frontend.param.AbcMallParam;
import com.mocentre.tehui.frontend.param.CustomerInfoParam;
import com.mocentre.tehui.frontend.param.ShopCartDetailParam;
import com.mocentre.tehui.frontend.service.LoginManageService;
import com.mocentre.tehui.ps.mapper.CustomerInfoMapper;
import com.mocentre.tehui.ps.model.CustomerInfo;
import com.mocentre.tehui.ps.service.impl.CustomerInfoService;
import com.mocentre.tehui.weixin.service.IConfigService;

/**
 * 用户登陆接口实现类
 *
 * @author Created by yukaiji on 2016年12月16日
 */
public class LoginManageServiceImpl implements LoginManageService {

    @Autowired
    private CustomerInfoService  customerInfoService;
    @Autowired
    private IConfigService       configService;
    @Autowired
    private IShoppingCartService shopCartService;

    @Override
    public PlainResult<CustomerInfoFTInstance> customerLoginByPassword(String telephone, String password,
                                                                       String requestId) {
        PlainResult<CustomerInfoFTInstance> result = new PlainResult<CustomerInfoFTInstance>();
        result.setRequestId(requestId);
        try {
            if (StringUtils.isBlank(telephone)) {
                result.setErrorMessage("1000", "用户名不能为空");
            }
            if (StringUtils.isBlank(password)) {
                result.setErrorMessage("1000", "密码不能为空");
            }
            if (result.isSuccess()) {
                CustomerInfo customerInfo = customerInfoService.getCustomerByTelephone(telephone);
                if (customerInfo == null) {
                    result.setErrorMessage("1000", "用户名或密码错误");
                    return result;
                }
                String paswd = customerInfo.getPassword();
                String randomNum = customerInfo.getRandomNum();
                randomNum = StringUtils.isBlank(randomNum) ? "" : randomNum;
                boolean passwd = StringUtils.isBlank(paswd) || !paswd.equals(DigestUtils.md5Hex(password + randomNum));
                if (passwd) {
                    result.setErrorMessage("1000", "用户名或密码错误");
                    return result;
                }
                customerInfoService.updateCustomerLoginTime(customerInfo.getId());
                CustomerInfoFTInstance cumInfoInstance = CustomerInfoMapper.toCustomerInfoFTInstance(customerInfo);
                result.setData(cumInfoInstance);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("customerLoginByPassword", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<CustomerInfoFTInstance> customerLoginByVerificationCode(String telephone, String vcode,
                                                                               String requestId) {
        PlainResult<CustomerInfoFTInstance> result = new PlainResult<CustomerInfoFTInstance>();
        result.setRequestId(requestId);
        try {
            if (StringUtils.isBlank(telephone)) {
                result.setErrorMessage("1000", "电话号码不能为空");
            }
            if (StringUtils.isBlank(vcode)) {
                result.setErrorMessage("1000", "验证码不能为空");
            }
            // 如果用户是首次登陆，创建用户的信息并保存
            if (result.isSuccess()) {
                CustomerInfo customerInfo = customerInfoService.getCustomerByTelephone(telephone);
                if (customerInfo == null) {
                    CustomerInfoParam customerInfoParam = new CustomerInfoParam();
                    customerInfoParam.setUserName(telephone);
                    customerInfoParam.setTelephone(telephone);
                    Long id = customerInfoService.addCustomerInfo(customerInfoParam);
                    CustomerInfoFTInstance cumInfoInstance = new CustomerInfoFTInstance();
                    cumInfoInstance.setId(id);
                    cumInfoInstance.setRegisterTime(new Date());
                    cumInfoInstance.setUserName(customerInfoParam.getUserName());
                    cumInfoInstance.setTelephone(customerInfoParam.getTelephone());
                    result.setData(cumInfoInstance);
                } else {
                    customerInfoService.updateCustomerLoginTime(customerInfo.getId());
                    CustomerInfoFTInstance cumInfoInstance = CustomerInfoMapper.toCustomerInfoFTInstance(customerInfo);
                    result.setData(cumInfoInstance);
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("customerLoginByVerificationCode", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    /**
     * 微信登录
     *
     * @param openid
     * @param requestId
     * @return
     */
    @Override
    public PlainResult<CustomerInfoFTInstance> customerLoginByWeixin(String openid, String requestId) {
        PlainResult<CustomerInfoFTInstance> result = new PlainResult<CustomerInfoFTInstance>();
        result.setRequestId(requestId);
        CustomerInfo customerInfo = customerInfoService.getCustomerByOpenid(openid);
        if (customerInfo != null) {
            CustomerInfoFTInstance cumInfoInstance = CustomerInfoMapper.toCustomerInfoFTInstance(customerInfo);
            result.setData(cumInfoInstance);
        } else {
            result.setErrorMessage("999", "未绑定微信");
        }
        return result;
    }

    /**
     * @param requestId
     * @return
     */
    @Override
    public PlainResult<CustomerInfoFTInstance> loginByAbcaid(AbcMallParam mallParam) {
        PlainResult<CustomerInfoFTInstance> result = new PlainResult<CustomerInfoFTInstance>();

        String abcaid = mallParam.getId();
        result.setRequestId(mallParam.getRequestId());
        if (StringUtils.isEmpty(abcaid)) {
            result.setErrorMessage("1000", "参数错误");
        }
        if (result.isSuccess()) {
            CustomerInfo customerInfo = customerInfoService.getCustomerByAbcaid(abcaid);
            if (customerInfo == null) {
                customerInfo = new CustomerInfo();
                customerInfo.setAbcaid(abcaid);
                Long id = customerInfoService.addCustomerInfo(customerInfo);
                customerInfo.setId(id);
            }
            result.setData(CustomerInfoMapper.toCustomerInfoFTInstance(customerInfo));
        }
        return result;
    }

    @Override
    public PlainResult<CustomerInfoFTInstance> loginByCodeAndSyncCart(String telephone, String code,
                                                                      List<ShopCartDetailParam> shopCartParamList,
                                                                      String requestId) {
        PlainResult<CustomerInfoFTInstance> result = new PlainResult<CustomerInfoFTInstance>();
        result.setRequestId(requestId);
        try {
            if (StringUtils.isBlank(telephone)) {
                result.setErrorMessage("1000", "电话号码不能为空");
            }
            if (StringUtils.isBlank(code)) {
                result.setErrorMessage("1000", "验证码不能为空");
            }
            // 如果用户是首次登陆，创建用户的信息并保存
            if (result.isSuccess()) {
                Long customerId = null;
                CustomerInfo customerInfo = customerInfoService.getCustomerByTelephone(telephone);
                if (customerInfo == null) {
                    CustomerInfoParam customerInfoParam = new CustomerInfoParam();
                    customerInfoParam.setUserName(telephone);
                    customerInfoParam.setTelephone(telephone);
                    Long id = customerInfoService.addCustomerInfo(customerInfoParam);
                    CustomerInfoFTInstance cumInfoInstance = new CustomerInfoFTInstance();
                    cumInfoInstance.setId(id);
                    cumInfoInstance.setRegisterTime(new Date());
                    cumInfoInstance.setUserName(customerInfoParam.getUserName());
                    cumInfoInstance.setTelephone(customerInfoParam.getTelephone());
                    result.setData(cumInfoInstance);
                    customerId = id;
                } else {
                    customerInfoService.updateCustomerLoginTime(customerInfo.getId());
                    CustomerInfoFTInstance cumInfoInstance = CustomerInfoMapper.toCustomerInfoFTInstance(customerInfo);
                    result.setData(cumInfoInstance);
                    customerId = customerInfo.getId();
                }
                //同步购物车
                if (shopCartParamList == null || shopCartParamList.size() == 0) {
                    return result;
                }
                if (customerId != null) {
                    List<ShoppingCart> shopCartAllList = new ArrayList<ShoppingCart>();
                    List<ShoppingCart> shopCartList = shopCartService.queryShopCartByCustomer(customerId);
                    if (shopCartList != null) {
                        for (ShopCartDetailParam shopCartParam : shopCartParamList) {
                            Long goodsId = shopCartParam.getGoodsId();
                            Long actGoodsId = shopCartParam.getActGoodsId();
                            String goodsSku = shopCartParam.getGoodsSku();
                            boolean exists = false;
                            for (ShoppingCart shopCart : shopCartList) {
                                if (goodsId.equals(shopCart.getGoodsId())
                                        && actGoodsId.equals(shopCart.getActGoodsId())
                                        && goodsSku.equals(shopCart.getGoodsSku())) {//是否已经存在
                                    shopCart.setNum(shopCart.getNum() + shopCartParam.getBuyNum());
                                    exists = true;
                                    break;
                                }
                            }
                            if (!exists) {
                                ShoppingCart shopCart = ShopCartMapper.toShoppingCart(customerId, shopCartParam);
                                shopCartAllList.add(shopCart);
                            }
                        }
                        shopCartAllList.addAll(shopCartList);
                    } else {
                        for (ShopCartDetailParam scdParam : shopCartParamList) {
                            ShoppingCart shopCart = ShopCartMapper.toShoppingCart(customerId, scdParam);
                            shopCartAllList.add(shopCart);
                        }
                    }
                    shopCartService.saveShopCart(customerId, shopCartAllList);
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("loginByCodeAndSyncCart", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
