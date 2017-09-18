/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job.queue;

import org.springframework.util.StopWatch;

import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.utils.SpringContextUtil;
import com.mocentre.tehui.goods.service.IGoodsStorageService;

/**
 * 类SyncStorageThread.java的实现描述：执行同步库存线程
 * 
 * @author sz.gong 2017年3月13日 下午4:34:47
 */
public class SyncStorageThread implements Runnable {

    private StorageMsgVo         storageMsg;
    private IGoodsStorageService goodsStorageService;

    public SyncStorageThread(StorageMsgVo storageMsg) {
        super();
        this.storageMsg = storageMsg;
        goodsStorageService = SpringContextUtil.getBean("goodsStorageService");
    }

    @Override
    public void run() {
        StopWatch watch = new StopWatch();
        watch.start();
        Long goodsId = storageMsg.getGoodsId();
        String stcode = storageMsg.getStandardCode();
        Long subGoodsId = storageMsg.getSubGoodsId();
        Integer goodsNum = storageMsg.getNeedNum();
        LoggerUtil.tehuiLog.info("SyncStorageThread=goodsId:" + goodsId + ",standardCode:" + stcode + ",subGoodsId:"
                + subGoodsId + ",goodsNum:" + goodsNum);
        goodsStorageService.updateGoodsStorageNeednum(goodsId, stcode, subGoodsId, Long.valueOf(goodsNum));
        watch.stop();
    }

}
