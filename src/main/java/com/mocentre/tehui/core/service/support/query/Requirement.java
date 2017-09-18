/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.core.service.support.query;

import java.util.HashMap;
import java.util.Map;

/**
 * 类Requirement.java的实现描述：分页查询条件
 * 
 * @author sz.gong 2016年11月11日 上午11:37:01
 */
public class Requirement {

    // 分页查询
    protected Integer             start;
    protected Integer             length;
    protected Integer             draw;

    protected Map<String, Object> map = new HashMap<String, Object>();

    public Requirement(Integer draw, Integer start, Integer length, Map<String, Object> map) {
        this.draw = draw;
        this.start = start;
        this.length = length;
        this.map = map;
    }

    public Requirement(Integer draw, Integer start, Integer length) {
        this.draw = draw;
        this.start = start;
        this.length = length;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

}
