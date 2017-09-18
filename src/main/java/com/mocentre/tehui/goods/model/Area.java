package com.mocentre.tehui.goods.model;

import java.util.List;

/**
 * Created by 22938 on 2016/11/17.
 */
public class Area {
    String code;
    String name;
    List<Area> childrenList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Area> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<Area> childrenList) {
        this.childrenList = childrenList;
    }
}
