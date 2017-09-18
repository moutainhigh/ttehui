package com.mocentre.tehui.system.model;

import com.mocentre.tehui.core.model.BaseEntity;

public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户id **/
    private Long              userId;

    /** 角色id **/
    private Long              roleId;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

}
