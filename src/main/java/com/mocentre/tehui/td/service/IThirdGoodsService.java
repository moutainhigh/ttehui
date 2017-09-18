package com.mocentre.tehui.td.service;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.ThirdGoodsParam;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.td.model.ThirdGoods;

import java.util.List;

/**
 * 三方商品serivce
 *
 * @author yukaiji 2017年7月12日
 */
public interface IThirdGoodsService {

    /**
     * 分页查询首页商品列表
     *
     * @param require
     */
    ListJsonResult<ThirdGoods> queryThirdGoodsPage(Requirement require);

    /**
     * 根据商品id获取商品详情
     *
     * @param id
     */
    ThirdGoods getThirdGoodsById(Long id);

    /**
     * 根据商品id查询
     *
     * @param goodsId
     */
    List<ThirdGoods> getThirdGoodsByGoods(Long goodsId);


    /**
     * 添加一个商品
     *
     * @param thirdGoodsParam
     */
    ThirdGoods addThirdGoods(ThirdGoodsParam thirdGoodsParam);

    /**
     * 修改一个商品
     *
     * @param thirdGoodsParam
     * @return id
     */
    Long updateThirdGoods(ThirdGoodsParam thirdGoodsParam);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int removeById(Long id, String showLocal);

    /**
     * 根据城市码和展示位置查询投放商品列表
     *
     * @param showLocal
     * @param bdCityCode
     * @return
     */
    List<ThirdGoods> getThirdGoodsList(String showLocal, String bdCityCode);



}
