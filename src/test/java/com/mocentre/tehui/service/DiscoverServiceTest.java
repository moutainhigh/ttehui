/*
 *
 *  * Copyright 2016 mocentre.com All right reserved. This software is the
 *  * confidential and proprietary information of mocentre.com ("Confidential
 *  * Information"). You shall not disclose such Confidential Information and shall
 *  * use it only in accordance with the terms of the license agreement you entered
 *  * into with mocentre.com .
 *
 */

package com.mocentre.tehui.service;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.DiscoverManageService;
import com.mocentre.tehui.backend.model.DiscoverInstance;
import com.mocentre.tehui.backend.param.DiscoverQueryParam;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.mapper.DiscoverMapper;
import com.mocentre.tehui.goods.model.Discover;
import com.mocentre.tehui.goods.provider.DiscoverManageServiceImpl;
import com.mocentre.tehui.goods.service.IDiscoverService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.NonStrictExpectations;
import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arvin on 16/12/14.
 */
public class DiscoverServiceTest extends BaseServiceTest {

    @Tested
    public DiscoverManageService discoverManageService = new DiscoverManageServiceImpl();

    @Injectable
    public IDiscoverService discoverService;


    @Test
    public void testQueryPageNormal(){
        new Expectations(){
            {
                List<Discover> lists = new ArrayList<>();
                lists.add(getInstance());
                ListJsonResult<Discover> resultList = new ListJsonResult<>();
                resultList.setData(lists);
                resultList.setRecordsTotal(lists.size());
                discoverService.queryDiscoverPage((Requirement)any);
                result = resultList;
            }
            {
                List<DiscoverInstance> listData = new ArrayList<>();
                listData.add(getDiscoverInstance());
                DiscoverMapper.toInstanceList((List<Discover>)any);
                result = listData;
            }
        };
        DiscoverQueryParam param = new DiscoverQueryParam();
        param.setShop_id(1L);
        ListJsonResult<DiscoverInstance> lr = discoverManageService.queryPage(param);

        Assert.assertEquals(true, lr.isSuccess());
        Assert.assertEquals(1l, lr.getData().size());
    }

    @Test
    public void testQueryPageEmpty(){
        new Expectations(){
            {
                List<Discover> lists = new ArrayList<>();
//                lists.add(getInstance());
                ListJsonResult<Discover> resultList = new ListJsonResult<>();
                resultList.setData(lists);
                resultList.setRecordsTotal(lists.size());
                discoverService.queryDiscoverPage((Requirement)any);
                result = resultList;
            }
        };
        DiscoverQueryParam param = new DiscoverQueryParam();
        param.setShop_id(1L);
        param.setRequestId("23232");
        ListJsonResult<DiscoverInstance> lr = discoverManageService.queryPage(param);

        Assert.assertEquals(true, lr.isSuccess());
        Assert.assertEquals(0l, lr.getRecordsTotal());
        Assert.assertNotNull(lr.getRequestId());
    }

    private Discover getInstance(){
        Discover discover = new Discover();
        discover.setIsShow(1);
        discover.setShopId(1L);
        discover.setUrl("http");
        discover.setId(1L);
        discover.setShowImg("");
        discover.setSorting(2);
        return discover;
    }

    private DiscoverInstance getDiscoverInstance(){
        DiscoverInstance discover = new DiscoverInstance();
        discover.setIsShow(1);
        discover.setShopId(1L);
        discover.setUrl("http");
        discover.setId(1L);
        discover.setShowImg("");
        discover.setSorting(2);
        return discover;
    }
}
