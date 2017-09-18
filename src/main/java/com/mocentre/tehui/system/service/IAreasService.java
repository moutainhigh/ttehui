/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.service;

import java.util.List;

import com.mocentre.tehui.system.model.Areas;

/**
 * 类IAreaService.java的实现描述：区域
 * 
 * @author sz.gong 2016年11月17日 上午11:35:53
 */
public interface IAreasService {

    /**
     * 获取所有的地区
     * 
     * @return
     */
    List<Areas> getAll();

    /**
     * 获取所有省级地区
     * 
     * @return
     */
    List<Areas> getAllProvince();

    /**
     * 获取所有市级地区
     * 
     * @return
     */
    List<Areas> getAllCity();

    /**
     * 获取所有区级地区
     * 
     * @return
     */
    List<Areas> getAllAreas();

    /**
     * 根据p_code获取地区列表
     * 
     * @param p_code
     * @return
     */
    List<Areas> get(String p_code);

    /**
     * 根据code获取地区信息
     * 
     * @param code
     * @return
     */
    Areas getAreas(String code);

    /**
     * 根据code编码获取名称
     * 
     * @param code
     * @return
     */
    String getName(String code);

    /**
     * 从缓存获取省
     * 
     * @return
     */
    List<Areas> getProvinceFromCache();

    /**
     * 从缓存获取市
     * 
     * @return
     */
    List<Areas> getCityFromCache();

    /**
     * 从缓存获取县
     * 
     * @return
     */
    List<Areas> getAreaFromCache();

    /**
     * 从缓存获取所有地区
     * 
     * @return
     */
    List<Areas> getAreasFromCache();

    /**
     * 根据code编码，从缓存中获取
     * 
     * @param code
     * @return
     */
    Areas getAreasFromCache(String code);

    /**
     * 根据code编码，从缓存中获取地区名称
     * 
     * @param code
     * @return
     */
    String getNameFromCache(String code);

    /**
     * 查询所有地区，存入缓存
     */
    void getAllAreasToCache();

}
