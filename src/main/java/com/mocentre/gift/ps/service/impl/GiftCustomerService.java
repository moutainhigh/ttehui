package com.mocentre.gift.ps.service.impl;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.ps.dao.IGiftCustomerDao;
import com.mocentre.gift.ps.model.GiftCustomer;
import com.mocentre.gift.ps.service.IGiftCustomerService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 礼品平台 客户Service实现类
 * 
 * @author liqifan
 * @date 创建时间：2017年4月6日 上午11:23:35
 */
@Component
public class GiftCustomerService implements IGiftCustomerService {

    @Autowired
    private IGiftCustomerDao giftCustomerDao;

    @Override
    public List<GiftCustomer> queryAll(Map<String, Object> paramMap) {
        return giftCustomerDao.queryList(paramMap);
    }

    @Override
    public void updateGiftCustomer(GiftCustomer giftCustomer) {
        giftCustomerDao.updateEntity(giftCustomer);
    }

    @Override
    public void saveGiftCustomer(GiftCustomer giftCustomer) {
        giftCustomerDao.saveEntity(giftCustomer);
    }

    @Override
    public boolean queryExistGiftCustomer(Map<String, Object> paramMap) {
        if (giftCustomerDao.queryList(paramMap).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BaseResult delete(List<Long> idList, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            if (idList == null) {
                result.setErrorMessage("1000", "参数不能为空");
            }
            if (result.isSuccess()) {
                for (Long id : idList) {
                    giftCustomerDao.logicRemoveById(id);
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("delete", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public GiftCustomer getGiftCustomer(Long id) {
        return giftCustomerDao.get(id);
    }

    @Override
    public GiftCustomer getGiftCustomerByUserName(String userName) {
        return giftCustomerDao.getByUserName(userName);
    }

    @Override
    public void updateGiftCustomerPassWord(Map<String, Object> paramMap) {
        giftCustomerDao.updateGiftCustomerPassWord(paramMap);
    }

    @Override
    public ListJsonResult<GiftCustomer> queryGiftCustomerPage(Requirement require) {
        return giftCustomerDao.queryDatatablesPage(require);
    }

    @Override
    public List<GiftCustomer> selectCustomer(HashMap<String, Object> map) {
        return giftCustomerDao.queryList(map);
    }
}
