package com.mocentre.tehui.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.system.dao.IUserDao;
import com.mocentre.tehui.system.model.User;

@Repository
public class UserDao extends BaseDao<User> implements IUserDao {

    @Override
    public User queryUser(Long id) {
        return super.get(id);
    }

    @Override
    public Integer getCount(Map<String, Object> parameterMap) {
        return super.getCount(User.class, parameterMap);
    }

    @Override
    public List<User> queryUserLogin(String name, String password) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userName", name);
        paramMap.put("password", password);
        return super.query("User_login", paramMap);
    }

    @Override
    public Long saveUser(User user) {
        return super.save(user);
    }

    @Override
    public void updateUser(User user) {
        super.update(user);
    }

}
