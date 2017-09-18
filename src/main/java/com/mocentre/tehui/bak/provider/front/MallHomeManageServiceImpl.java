/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.bak.provider.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.bak.enums.MallHomeShowLocal;
import com.mocentre.tehui.bak.mapper.MallHomeMapper;
import com.mocentre.tehui.bak.model.MallHome;
import com.mocentre.tehui.bak.service.IMallHomeService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.frontend.model.ImageFTInstance;
import com.mocentre.tehui.frontend.model.MallAllFTInstance;
import com.mocentre.tehui.frontend.model.MallBannerWithActFTInstance;
import com.mocentre.tehui.frontend.model.MallHomeFTInstance;
import com.mocentre.tehui.frontend.param.SpecialSellQueryParam;
import com.mocentre.tehui.frontend.service.MallHomeManageService;
import com.mocentre.tehui.system.enums.ImageSeat;
import com.mocentre.tehui.system.mapper.ImageMapper;
import com.mocentre.tehui.system.model.Image;
import com.mocentre.tehui.system.service.IImageService;

/**
 * 类MallHomeManageServiceImpl.java的实现描述：客户端首页
 *
 * @author sz.gong 2017年5月19日 下午2:40:59
 */
public class MallHomeManageServiceImpl implements MallHomeManageService {

    @Autowired
    private IMallHomeService mallHomeService;
    @Autowired
    private IImageService    imageService;

    @Override
    public ListResult<MallHomeFTInstance> getSpecialSellPage(SpecialSellQueryParam queryParam) {
        ListResult<MallHomeFTInstance> lr = new ListResult<MallHomeFTInstance>();
        lr.setRequestId(queryParam.getRequestId());
        try {
            Integer start = queryParam.getStart();
            Integer length = queryParam.getLength();
            List<MallHome> list = mallHomeService.getMallHomePage(start, length);
            List<MallHomeFTInstance> mhFtInsList = MallHomeMapper.toMallHomeFTInstanceList(list);
            lr.setData(mhFtInsList);
            lr.setCount(list.size());
        } catch (Exception e) {
            lr.setErrorMessage("999", "接口异常");
        }
        return lr;
    }

    @Override
    public PlainResult<MallBannerWithActFTInstance> getBannerWithActivity(String requestId) {
        PlainResult<MallBannerWithActFTInstance> pr = new PlainResult<MallBannerWithActFTInstance>();
        pr.setRequestId(requestId);
        MallBannerWithActFTInstance info = new MallBannerWithActFTInstance();
        try {
            List<MallHome> mallHomes = mallHomeService.getMallHomeByAct();
            List<Image> images = imageService.queryImageList(null, ImageSeat.MALLHOME.getCodeValue());
            info.setImageList(ImageMapper.toImageFTInstanceList(images));
            info.setMallHomeList(MallHomeMapper.toMallHomeFTInstanceList(mallHomes));
            pr.setData(info);
        } catch (Exception e) {
            pr.setErrorMessage("999", "接口异常");
        }
        return pr;
    }

    @Override
    public PlainResult<MallAllFTInstance> getMallHomeAll() {
        PlainResult<MallAllFTInstance> pr = new PlainResult<MallAllFTInstance>();
        try {
            MallAllFTInstance mallAllIns = new MallAllFTInstance();
            List<MallHome> actMallHomeList = mallHomeService.getMallHomeListByLocal(MallHomeShowLocal.ACT
                    .getCodeValue());
            List<MallHome> comMallHomeList = mallHomeService.getMallHomeListByLocal(MallHomeShowLocal.SPECIAL
                    .getCodeValue());
            List<Image> imageList = imageService.getImageBySeatList(ImageSeat.MALLHOME.getCodeValue());
            List<ImageFTInstance> imageInsList = ImageMapper.toImageFTInstanceList(imageList);
            List<MallHomeFTInstance> actMallHomeInsList = MallHomeMapper.toMallHomeFTInstanceList(actMallHomeList);
            List<MallHomeFTInstance> comMallHomeInsList = MallHomeMapper.toMallHomeFTInstanceList(comMallHomeList);
            mallAllIns.setActMallHomeInstanceList(actMallHomeInsList);
            mallAllIns.setComMallHomeInstanceList(comMallHomeInsList);
            mallAllIns.setImageInstanceList(imageInsList);
            pr.setData(mallAllIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getMallHomeAll", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }
}
