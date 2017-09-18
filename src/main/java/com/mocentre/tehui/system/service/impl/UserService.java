package com.mocentre.tehui.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.UserInstance;
import com.mocentre.tehui.backend.param.UserParam;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.shop.dao.IShopDao;
import com.mocentre.tehui.shop.model.Shop;
import com.mocentre.tehui.system.dao.IUserDao;
import com.mocentre.tehui.system.mapper.UserMapper;
import com.mocentre.tehui.system.model.User;
import com.mocentre.tehui.system.service.IUserService;

/**
 * 类UserService.java的实现描述：用户service
 * 
 * @author sz.gong 2016年4月20日 下午3:33:43
 */
@Component
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IShopDao shopDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<UserInstance> queryUserPage(Requirement require, String requestId) {
        ListJsonResult<UserInstance> result = new ListJsonResult<UserInstance>();
        result.setRequestId(requestId);
        ListJsonResult<User> pageInfo = userDao.queryDatatablesPage(require);
        List<User> userList = pageInfo.getData();
        List<UserInstance> userInsList = new ArrayList<UserInstance>();
        if (userList != null) {
            for (User user : userList) {
                UserInstance userIns = UserMapper.toUserInstance(user);
                Long shopId = user.getShopId();
                if (shopId != null) {
                    Shop shop = shopDao.get(shopId);
                    if (shop != null) {
                        userIns.setShopName(shop.getName());
                    }
                }
                userInsList.add(userIns);
            }
        }
        result.setData(userInsList);
        result.setDraw(pageInfo.getDraw());
        result.setRecordsFiltered(pageInfo.getRecordsFiltered());
        result.setRecordsTotal(pageInfo.getRecordsTotal());
        return result;
    }

    @Override
    @DataSource(value = "read")
    public User queryUser(Long id) {
        return userDao.queryUser(id);
    }

    @Override
    @DataSource(value = "write")
    public Long saveUser(UserParam mUser) {
        User user = UserMapper.toUser(mUser);
        user.setPassword(DigestUtils.md5Hex(mUser.getPassword()));
        return userDao.saveUser(user);
    }

    @Override
    @DataSource(value = "write")
    public void updateUser(UserParam mUser) {
        User user = UserMapper.toUser(mUser);
        userDao.updateUser(user);
    }

    @Override
    @DataSource(value = "write")
    public void logicDelete(Long id) {
        userDao.logicRemoveById(id);
    }

    /**
     * 登入验证
     * 
     * @param name
     * @param password
     * @return
     */
    @Override
    @DataSource(value = "read")
    public List<User> queryLoginVerify(String name, String password) {
        Assert.notNull(name);
        Assert.notNull(password);
        password = DigestUtils.md5Hex(password);
        List<User> list = userDao.queryUserLogin(name, password);
        return list;
    }

    /**
     * 用户是否存在
     * 
     * @param uname
     * @param id
     * @return
     */
    @Override
    @DataSource(value = "read")
    public boolean queryExistUser(String uname, Long id) {
        Assert.notNull(uname);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userName", uname);
        paramMap.put("id", id);
        Integer count = userDao.getCount(paramMap);
        if (count > 0) {
            return true;
        }
        return false;
    }

}
