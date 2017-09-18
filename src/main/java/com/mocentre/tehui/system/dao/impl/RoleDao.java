package com.mocentre.tehui.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.system.dao.IRoleDao;
import com.mocentre.tehui.system.model.Role;

/**
 * 类RoleDao.java的实现描述：TODO 类实现描述
 * 
 * @author sz.gong 2016年4月20日 下午3:41:13
 */
@Repository
public class RoleDao extends BaseDao<Role> implements IRoleDao {

    @Override
    public Role queryRoleById(Long id) {
        return super.get(id);
    }

    @Override
    public int logicDelete(Long id) {
        return super.logicRemoveById(id);
    }

}
