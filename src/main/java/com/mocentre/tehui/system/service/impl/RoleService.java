package com.mocentre.tehui.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.RoleInstance;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.dao.IRoleDao;
import com.mocentre.tehui.system.dao.IRoleRuleDao;
import com.mocentre.tehui.system.mapper.RoleMapper;
import com.mocentre.tehui.system.model.Role;
import com.mocentre.tehui.system.service.IRoleService;

/**
 * 类RoleService.java的实现描述：角色Service
 * 
 * @author sz.gong 2016年4月21日 上午10:37:23
 */
@Component
public class RoleService implements IRoleService {

    @Autowired
    private IRoleDao     roleDao;
    @Autowired
    private IRoleRuleDao roleRuleDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<RoleInstance> queryRolePage(Requirement require) {
        ListJsonResult<RoleInstance> result = new ListJsonResult<RoleInstance>();
        ListJsonResult<Role> pageInfo = roleDao.queryDatatablesPage(require);
        List<Role> roleList = pageInfo.getData();
        List<RoleInstance> roleInsList = new ArrayList<RoleInstance>();
        if (roleList != null) {
            for (Role role : roleList) {
                RoleInstance roleIns = RoleMapper.toRoleInstance(role);
                roleInsList.add(roleIns);
            }
        }
        result.setData(roleInsList);
        result.setDraw(pageInfo.getDraw());
        result.setRecordsFiltered(pageInfo.getRecordsFiltered());
        result.setRecordsTotal(pageInfo.getRecordsTotal());
        return result;
    }

    /**
     * 查询启用的角色
     * 
     * @return
     */
    @Override
    @DataSource(value = "read")
    public List<Role> queryEnableRoleList() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", 1);
        List<Role> roleList = roleDao.queryList(paramMap);
        return roleList;
    }

    /**
     * 角色是否存在
     * 
     * @param roleName
     * @param id
     * @return
     */
    @DataSource(value = "read")
    public boolean queryExistRole(String roleName, Long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("rName", roleName);
        List<Role> list = roleDao.queryList(paramMap);
        if (list != null && list.size() > 1) {
            return true;
        }
        if (list == null || list.size() == 0) {
            return false;
        }
        if (id == null && list.size() > 0) {
            return true;
        }
        Role role = list.get(0);
        if (id != null && role.getId().intValue() == id.intValue()) {
            return false;
        }
        return true;
    }

    @Override
    @DataSource(value = "read")
    public Role queryRoleById(Long id) {
        Role role = roleDao.queryRoleById(id);
        return role;
    }

    @Override
    @DataSource(value = "write")
    public Long updateRole(Role role) {
        return roleDao.updateEntity(role);
    }

    @Override
    @DataSource(value = "write")
    public void saveRole(Role role) {
        roleDao.saveEntity(role);
    }

    @Override
    @DataSource(value = "write")
    public void deleteRole(List<Long> roleIdList) {
        for (int i = 0; i < roleIdList.size(); i++) {
            Long id = roleIdList.get(i);
            roleRuleDao.deleteRoleRule(id);
            roleDao.logicDelete(id);
        }
    }

}
