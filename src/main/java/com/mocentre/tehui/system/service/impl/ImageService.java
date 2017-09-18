/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.ImageParam;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.dao.IImageDao;
import com.mocentre.tehui.system.enums.ImageSeat;
import com.mocentre.tehui.system.mapper.ImageMapper;
import com.mocentre.tehui.system.model.Image;
import com.mocentre.tehui.system.service.IImageService;

/**
 * 类ImageService.java的实现描述：图片Service实现类
 *
 * @author sz.gong 2016年11月17日 上午11:38:54
 */
@Component
public class ImageService implements IImageService {

    @Autowired
    private IImageDao imageDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<Image> queryBannerPage(Requirement require) {
        return imageDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "write")
    public Image saveImage(ImageParam imageParam) {
        Image img = ImageMapper.toImage(imageParam);
        imageDao.saveEntity(img);
        if (ImageSeat.MALLHOME.getCodeValue().equals(img.getSeat()) && img.getId() != null) {
            imageDao.updateToCache(img);
        }
        return img;
    }

    @Override
    @DataSource(value = "read")
    public Image getBannerById(Long id) {
        return imageDao.get(id);
    }

    @Override
    @DataSource(value = "write")
    public Long updateImage(ImageParam imageParam) {
        Image image = ImageMapper.toImage(imageParam);
        Long count = imageDao.updateEntity(image);
        if (ImageSeat.MALLHOME.getCodeValue().equals(image.getSeat()) && count > 0) {
            imageDao.updateToCache(image);
        }
        return count;
    }

    @Override
    @DataSource(value = "read")
    public List<Image> queryImageList(Long seatId, String seat) {
        List<Image> imageList = new ArrayList<Image>();
        if (ImageSeat.MALLHOME.getCodeValue().equals(seat)) {
            imageList = imageDao.queryFromCache();
            if (imageList == null || imageList.size() <= 0) {
                imageList = imageDao.queryList(seatId, seat);
            } else {
                Collections.sort(imageList, new Comparator<Image>() {
                    @Override
                    public int compare(Image o1, Image o2) {
                        return o1.getSorting().compareTo(o2.getSorting());
                    }
                });
            }
            return imageList;
        }
        return imageDao.queryList(seatId, seat);
    }

    @Override
    @DataSource(value = "read")
    public List<Image> getImageBySeatList(String seat) {
        return imageDao.getListBySeat(seat);
    }

    @Override
    @DataSource(value = "read")
    public List<Image> queryImageByParam(Map<String, Object> paramMap) {
        return imageDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public int delByParam(Long seatId, String seat) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("seatId", seatId);
        paramMap.put("seat", seat);
        return imageDao.deleteByParam(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public int removeById(Long id, String seat) {
        int count = imageDao.logicRemoveById(id);
        if (ImageSeat.MALLHOME.getCodeValue().equals(seat) && count > 0) {
            imageDao.deleteFromCache(id);
        }
        return count;
    }

}
