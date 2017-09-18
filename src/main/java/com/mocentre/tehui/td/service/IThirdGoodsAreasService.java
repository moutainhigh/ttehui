package com.mocentre.tehui.td.service;

import com.mocentre.tehui.td.model.ThirdGoodsAreas;

import java.util.List;

/**
 * 三方商品区域serivce
 *
 * @author yukaiji 2017年7月12日
 */
public interface IThirdGoodsAreasService {

    /**
     * 查询列表
     *
     * @return
     */
    List<ThirdGoodsAreas> getThirdGoodsAreasByGoodsId(Long goodsId);

    List<ThirdGoodsAreas> getThirdGoodsAreasByBDCityCode(String bdCityCode);


}
