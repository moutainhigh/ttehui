/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.service;

import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.ImageParam;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.model.Image;

/**
 * 类IImageService.java的实现描述：图片Service
 *
 * @author sz.gong 2016年11月17日 上午11:38:13
 */
public interface IImageService {

    ListJsonResult<Image> queryBannerPage(Requirement require);

    Image saveImage(ImageParam imageParam);

    Image getBannerById(Long id);

    Long updateImage(ImageParam imageParam);

    List<Image> queryImageList(Long seatId, String seat);

    List<Image> queryImageByParam(Map<String, Object> paramMap);

    List<Image> getImageBySeatList(String seat);

    int delByParam(Long seatId, String seat);

    int removeById(Long id, String seat);
}
