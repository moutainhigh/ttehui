package com.mocentre.tehui.shop.service;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.ShopInstance;
import com.mocentre.tehui.backend.param.ShopParam;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.shop.model.Shop;

import java.util.List;
import java.util.Map;

/**
 * 店铺信息接口. Created by yukaiji on 2016/11/13.
 */
public interface IShopService {

    /**
     * 获取所有的店铺信息
     * 
     * @return 所有的店铺信息
     */
    ListJsonResult<ShopInstance> queryShopPage(Requirement require);

    /**
     * 根据Id获取获取店铺的信息
     * 
     * @param id 店铺id
     * @return 模板信息
     */
    Shop queryShop(Long id);

    /**
     * 根据id获取店铺的信息
     * 
     * @param id
     * @return
     */
    ShopInstance getShop(Long id);

    /**
     * 根据店铺名称获取店铺信息
     * 
     * @param name 店铺名称
     * @return
     */
    List<Shop> getShopListInName(String name);

    /**
     * 根据条件获取店铺信息
     * 
     * @param paramMap 条件
     * @return
     */
    List<Shop> queryShop(Map<String, Object> paramMap);

    /**
     * 添加店铺信息
     * 
     * @param shopParam
     */
    Shop addShop(ShopParam shopParam);

    /**
     * 修改店铺信息
     * 
     * @param shopParam
     */
    void editShop(ShopParam shopParam);

    /**
     * 根据Id删除店铺
     * 
     * @param idList id列表
     */
    void deleteShop(List<Long> idList);

    /**
     * 根据Id审核店铺
     * 
     * @param id id
     */
    void examineShop(Long id, String audit_status);

    /**
     * 根据Id关闭/暂停/开启店铺
     * 
     * @param id id
     */
    void operationShop(Long id, String buss_status);
}
