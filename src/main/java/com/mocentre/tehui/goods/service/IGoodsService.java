package com.mocentre.tehui.goods.service;

import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.GoodsTopNParam;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.model.GoodsParam;
import com.mocentre.tehui.goods.model.GoodsStorage;

/**
 * 商品service接口 Created by 王雪莹 on 2016/11/10.
 */
public interface IGoodsService {

    /**
     * 添加一件新商品
     */
    Goods addGoodsBaseInfo(Goods goods);

    /**
     * 根据id删除一件商品
     */
    void delGoodsById(Long id, Long shopId);

    /**
     * 根据id查询商品详细信息
     */
    Goods getGoodsById(Long id);

    /**
     * 按分类id查询
     * 
     * @param categoryId 分类id
     * @return
     */
    List<Goods> getOnShelfGoodsByCategory(Long categoryId);

    /**
     * 根据店铺id查询所有商品
     */
    List<Goods> getAllGoodsByShopId(Long shopId);

    /**
     * 根据店铺id查询所有上架的商品
     */
    List<Goods> getAllGoodsIsShow(Long shopId);

    /**
     * 分页查询
     */
    ListJsonResult<Goods> queryPage(Requirement require);

    /**
     * 更新商品参数
     * 
     * @param goodsId
     * @param goodsParamList
     */
    void updateGoodsParam(Long shopId, Long goodsId, List<GoodsParam> goodsParamList) throws Exception;

    /**
     * 更新商品基本信息和投放信息
     * 
     * @param goods
     * @param putAreas
     * @return
     */
    Long updateGoodsBaseInfoAndArea(Goods goods, String putAreas);

    /**
     * 更新商品基本和缓存
     * 
     * @param goods
     * @return
     */
    Long updateGoodsAndCache(Goods goods);

    /**
     * 更新商品详细信息
     * 
     * @param id 商品id
     * @param imgBanner 商品banner
     * @param details 详情内容
     * @return
     */
    Long updateGoodsDetail(Long id, Long shopId, String imgBanner, String details);

    /**
     * 更新商品状态
     * 
     * @param id 商品id
     * @param shopId 店铺id
     * @param status 状态
     * @return
     */
    Long updateGoodsStatus(Long id, Long shopId, String status);

    /**
     * 更加商品库存编码和库存库存规格，获得规格
     * 
     * @param standardJson
     * @param standardCode
     * @return
     */
    String getGoodsStandardByCode(String standardJson, String standardCode);

    /**
     * 通过id和店铺id查询商品
     * 
     * @param id
     * @param shopId
     * @return
     */
    Goods getGoodsByIdShop(Long id, Long shopId);

    /**
     * 按条件 按顺序 取前n件
     * 
     * @param param
     * @return
     */
    List<Goods> getTopN(GoodsTopNParam param);

    /**
     * 销量最高的前Num件在线商品
     * 
     * @param shopId
     * @param Num
     * @return
     */
    List<Goods> getSellTopN(Long shopId, int Num);

    /**
     * 库存量最高的前Num件在线商品
     * 
     * @param shopId
     * @param Num
     * @return
     */
    List<Goods> getStoreTopN(Long shopId, int Num);

    /**
     * 从缓存中查询商品
     * 
     * @param id
     * @return
     */
    Goods getGoodsBaseInfoFromCache(Long id);

    /**
     * 更新商品基本信息到缓存
     * 
     * @param goods
     */
    void updateGoodsBaseInfoToCache(Goods goods);

    /**
     * 新增或修改商品库存和缓存
     * 
     * @param goodsId 商品id
     * @param standard 商品规格编码
     * @param shopId 店铺id
     * @param goodsStorageList 商品库存集合
     */
    Boolean saveOrupdateGoodsSkuAndCache(Long goodsId, String standard, Long shopId, List<GoodsStorage> goodsStorageList);

    /**
     * 获取全部上线商品
     * 
     * @return
     */
    List<Goods> getAllGoodsIsShow();
}
