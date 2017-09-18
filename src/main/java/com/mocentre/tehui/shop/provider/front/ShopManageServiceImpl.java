package com.mocentre.tehui.shop.provider.front;


import com.alibaba.fastjson.JSON;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.frontend.model.GoodsListPageInfoFTInstance;
import com.mocentre.tehui.frontend.model.ShopHomInfoFTInstance;
import com.mocentre.tehui.frontend.service.ShopManageService;
import com.mocentre.tehui.goods.mapper.GoodsMapper;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.service.impl.CategoryService;
import com.mocentre.tehui.goods.service.impl.GoodsService;
import com.mocentre.tehui.shop.mapper.ShopMapper;
import com.mocentre.tehui.shop.model.Shop;
import com.mocentre.tehui.shop.service.impl.ShopService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 店铺部分接口 Created by 王雪莹 on 2016/12/16.
 */
public class ShopManageServiceImpl implements ShopManageService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ShopService shopService;

    /**
     * 获取上新的商品
     *
     * @param shopId
     * @return
     */
    @Override
    public ListResult<GoodsListPageInfoFTInstance> getAllShowGoods(Long shopId, String requestId) {
        ListResult<GoodsListPageInfoFTInstance> result = new ListResult<>();
        result.setRequestId(requestId);
        // 按上架日期排序（暂无上架日期字段）// TODO: 2016/12/16
        List<Goods> goodsList = goodsService.getAllGoodsIsShow(shopId);
        List<GoodsListPageInfoFTInstance> goodsInstanceList = GoodsMapper.toGoodsListPageInfoFTInstanceList(goodsList);
        result.setData(goodsInstanceList);
        System.out.println(JSON.toJSONString(result));
        return result;
    }

    /**
     * 获取店铺首页信息
     *
     * @param shopId
     * @return
     */
    @Override
    public PlainResult<ShopHomInfoFTInstance> getShopHomPageInfo(Long shopId, String requestId) {
        PlainResult<ShopHomInfoFTInstance> result = new PlainResult<>();
        result.setRequestId(requestId);
        // 查询店铺信息
        Shop shop = shopService.queryShop(shopId);
        ShopHomInfoFTInstance instance = ShopMapper.toShopHomPageFTInstance(shop);

        // 获取销量前十的商品
        List<Goods> topSell = goodsService.getSellTopN(shopId, 10);

        // 获取库存前十的销量
        List<Goods> topStore = goodsService.getStoreTopN(shopId, 10);

        instance.setTopSell(GoodsMapper.toGoodsListPageInfoFTInstanceList(topSell));
        instance.setCommended(GoodsMapper.toGoodsListPageInfoFTInstanceList(topStore));

        result.setData(instance);
        return result;
    }


}
