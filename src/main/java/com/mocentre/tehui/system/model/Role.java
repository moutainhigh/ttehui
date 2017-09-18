package com.mocentre.tehui.system.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类Role.java的实现描述：角色实体类
 * 
 * @author sz.gong 2016年11月4日 下午3:38:08
 */
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 角色名称 **/
    private String            roleName;

    /** 角色状态 **/
    private Integer           status;

    private String            ruleids;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRuleids() {
        return ruleids;
    }

    public void setRuleids(String ruleids) {
        this.ruleids = ruleids;
    }
}
