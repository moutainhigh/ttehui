package com.mocentre.tehui.system.dao;

import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.model.Role;

/**
 * 类IRoleDao.java的实现描述：TODO 类实现描述
 * 
 * @author sz.gong 2016年4月20日 下午3:40:59
 */
public interface IRoleDao {

    ListJsonResult<Role> queryDatatablesPage(Requirement require);

    List<Role> queryList(Map<String, Object> paramMap);

    Role queryRoleById(Long id);

    Long updateEntity(Role role);

    Long saveEntity(Role role);

    int logicDelete(Long id);

}
