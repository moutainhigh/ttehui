/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.model.Image;

/**
 * 类IImageDao.java的实现描述：图片
 * 
 * @author sz.gong 2016年11月17日 上午11:33:32
 */
public interface IImageDao {

    ListJsonResult<Image> queryDatatablesPage(Requirement require);

    Long saveEntity(Image img);

    Image get(Serializable id);

    Long updateEntity(Image img);

    List<Image> queryList(Long seatId, String seat);

    List<Image> queryList(Map<String, Object> paramMap);

    List<Image> getListBySeat(String seat);

    int deleteByParam(Map<String, Object> paramMap);

    int logicRemoveById(Serializable id);

    int delete(String type, String seat, Long seatId);

    List<Image> queryFromCache();

    boolean updateToCache(Image image);

    boolean deleteFromCache(Long id);
}
