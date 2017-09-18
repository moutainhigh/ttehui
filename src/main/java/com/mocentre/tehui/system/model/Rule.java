package com.mocentre.tehui.system.model;

import java.util.List;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类Rule.java的实现描述：操作domain
 * 
 * @author sz.gong 2016年3月9日 上午11:20:07
 */
public class Rule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 父id **/
    private Long              pid;

    /** 连接url **/
    private String            url;

    /** 菜单名称 **/
    private String            title;

    /** 图标 **/
    private String            icon;

    /** 类型 **/
    private Integer           type;

    /** 状态 **/
    private Integer           status;

    /** **/
    private Integer           islink;

    /** 排序 **/
    private Integer           orderby;

    /** 提示 **/
    private String            tips;

    private List<Rule>        children;

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getPid() {
        return pid;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setIslink(Integer islink) {
        this.islink = islink;
    }

    public Integer getIslink() {
        return islink;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getTips() {
        return tips;
    }

    public List<Rule> getChildren() {
        return children;
    }

    public void setChildren(List<Rule> children) {
        this.children = children;
    }
}
