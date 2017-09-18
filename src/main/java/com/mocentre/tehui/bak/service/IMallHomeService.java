package com.mocentre.tehui.bak.service;

import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.MallHomeParam;
import com.mocentre.tehui.bak.model.MallHome;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 农行掌银客户端首页后台接口
 * <p>
 * Created by yukaiji on 2017/5/17.
 */
public interface IMallHomeService {

    /**
     * 分页查询首页商品列表
     *
     * @param require
     */
    ListJsonResult<MallHome> queryMallHomePage(Requirement require);

    /**
     * 根据商品id获取商品详情
     *
     * @param id
     */
    MallHome getMallHomeById(Long id);

    /**
     * 根据商品id查询
     *
     * @param goodsId
     */
    List<MallHome> getMallHomeByGoods(Long goodsId);

    /**
     * 根据活动id查询
     * 
     * @param activityId
     * @return
     */
    List<MallHome> getMallHomeByActivity(Long activityId);

    /**
     * 根据活动商品id查询
     * 
     * @param actGoodsId
     * @return
     */
    List<MallHome> getMallHomeByActGoods(Long actGoodsId);

    /**
     * 添加一个商品或活动
     *
     * @param mallHomeParam
     */
    MallHome addMallHome(MallHomeParam mallHomeParam);

    /**
     * 修改一个商品或活动
     *
     * @param mallHomeParam
     * @return id
     */
    Long updateMallHome(MallHomeParam mallHomeParam);

    /**
     * 根据id删除一个分类
     *
     * @param id
     * @return
     */
    int removeById(Long id, String showLocal);

    /**
     * 查询爆品分页
     *
     * @param start
     * @param length
     * @return
     */
    List<MallHome> getMallHomePage(Integer start, Integer length);

    /**
     * 查询活动列表
     *
     * @return
     */
    List<MallHome> getMallHomeByAct();

    /**
     * 查询列表
     * 
     * @param showLocal 位置
     * @return
     */
    List<MallHome> getMallHomeListByLocal(String showLocal);

}
