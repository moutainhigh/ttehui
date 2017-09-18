///*
// *
// *  * Copyright 2016 mocentre.com All right reserved. This software is the
// *  * confidential and proprietary information of mocentre.com ("Confidential
// *  * Information"). You shall not disclose such Confidential Information and shall
// *  * use it only in accordance with the terms of the license agreement you entered
// *  * into with mocentre.com .
// *
// */
//
//package com.mocentre.tehui.dubbo;
//
//import com.mocentre.common.ListJsonResult;
//import com.mocentre.common.ListResult;
//import com.mocentre.tehui.DiscoverManageService;
//import com.mocentre.tehui.backend.model.DiscoverInstance;
//import com.mocentre.tehui.backend.param.DiscoverQueryParam;
//import org.junit.Assert;
//import org.junit.Test;
//
///**
// * Created by Arvin on 16/12/13.
// */
//public class DiscoverManageServiceDemoTest extends BaseDubboServiceTest {
//
//    private DiscoverManageService discoverManageService;
//
//    public DiscoverManageServiceDemoTest(){
//        super();
//        discoverManageService = (DiscoverManageService)context.getBean("discoverManageService");
//    }
//
//    @Test
//    public void testQueryPage(){
//        DiscoverQueryParam discoverQueryParam = new DiscoverQueryParam();
//        discoverQueryParam.setRequestId(generageRequestId());
//        discoverQueryParam.setShop_id(1l);
//        ListJsonResult<DiscoverInstance> listResult = discoverManageService.queryPage(discoverQueryParam);
//        if (listResult.isSuccess()){
//            System.out.println("QueryPage success!\n");
//            System.out.println("requestId:" + listResult.getRequestId() +", code:" + listResult.getCode() + ", msg:" + listResult.getMessage() + ", total:" + listResult.getRecordsTotal() );
//        } else {
//            System.out.println("QueryPage failed!\n" + listResult.getCode() +", "+ listResult.getMessage());
//        }
//        Assert.assertTrue(listResult.isSuccess());
//    }
//
//    @Test
//    public void testGetDiscoverListToShopId(){
//        String requestId = generageRequestId();
//        ListResult<DiscoverInstance> listResult = discoverManageService.getDiscoverListToShopId(1L, requestId);
//        if (listResult.isSuccess()){
//            System.out.println("GetDiscoverListToShopId success!\n");
//            System.out.println("requestId:" + listResult.getRequestId() +", code:" + listResult.getCode() + ", msg:" + listResult.getMessage() );
//        } else {
//            System.out.println("GetDiscoverListToShopId failed!\n" + listResult.getCode() +", "+ listResult.getMessage());
//        }
//        Assert.assertTrue(listResult.isSuccess());
//        Assert.assertEquals(requestId, listResult.getRequestId());
//    }
//
//}
