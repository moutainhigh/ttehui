package com.mocentre.tehui.system.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.model.User;

public interface IUserDao {

    User queryUser(Long id);

    int logicRemoveById(Serializable id);

    ListJsonResult<User> queryDatatablesPage(Requirement require);

    List<User> queryList(Map<String, Object> paramMap);

    Integer getCount(Map<String, Object> paramMap);

    List<User> queryUserLogin(String name, String password);

    Long saveUser(User user);

    void updateUser(User user);

}
