package com.mocentre.tehui.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.ShopInstance;
import com.mocentre.tehui.backend.param.ShopParam;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.shop.dao.IShopDao;
import com.mocentre.tehui.shop.mapper.ShopMapper;
import com.mocentre.tehui.shop.model.Shop;
import com.mocentre.tehui.shop.service.IShopService;

/**
 * 店铺信息操作接口实现类. Created by yukaiji on 2016/11/13.
 */
@Component
public class ShopService implements IShopService {

    @Autowired
    private IShopDao shopDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<ShopInstance> queryShopPage(Requirement require) {
        ListJsonResult<ShopInstance> result = new ListJsonResult<ShopInstance>();
        ListJsonResult<Shop> pageInfo = shopDao.queryDatatablesPage(require);
        List<Shop> shopList = pageInfo.getData();
        List<ShopInstance> shopInsList = new ArrayList<ShopInstance>();
        if (shopList != null) {
            for (Shop shop : shopList) {
                ShopInstance shopIns = ShopMapper.toShopInstance(shop);
                shopInsList.add(shopIns);
            }
        }
        result.setData(shopInsList);
        result.setDraw(pageInfo.getDraw());
        result.setRecordsFiltered(pageInfo.getRecordsFiltered());
        result.setRecordsTotal(pageInfo.getRecordsTotal());
        return result;
    }

    @Override
    @DataSource(value = "read")
    public ShopInstance getShop(Long id) {
        Shop shop = shopDao.get(id);
        ShopInstance shopIns = ShopMapper.toShopInstance(shop);
        return shopIns;
    }

    @Override
    @DataSource(value = "read")
    public Shop queryShop(Long id) {
        return shopDao.get(id);
    }

    @Override
    @DataSource(value = "read")
    public List<Shop> getShopListInName(String name) {
        return shopDao.getShopListInName(name);
    }

    @Override
    @DataSource(value = "read")
    public List<Shop> queryShop(Map<String, Object> paramMap) {
        return shopDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public Shop addShop(ShopParam shopParam) {
        Shop shop = ShopMapper.toShop(shopParam);
        shopDao.saveEntity(shop);
        return shop;
    }

    @Override
    @DataSource(value = "write")
    public void editShop(ShopParam shopParam) {
        Shop shop = ShopMapper.toShop(shopParam);
        shop.setLevel(null);
        shop.setBuss_status(null);
        shop.setAudit_status(null);
        shopDao.updateEntity(shop);
    }

    @Override
    @DataSource(value = "write")
    public void deleteShop(List<Long> idList) {
        for (Long id : idList) {
            shopDao.removeById(id);
        }
    }

    @Override
    @DataSource(value = "write")
    public void examineShop(Long id, String audit_status) {
        Shop shop = new Shop();
        shop.setId(id);
        shop.setAudit_status(audit_status);
        shopDao.updateAS(shop);
    }

    @Override
    @DataSource(value = "write")
    public void operationShop(Long id, String buss_status) {
        Shop shop = new Shop();
        shop.setId(id);
        shop.setBuss_status(buss_status);
        shopDao.updateBS(shop);
    }

}
