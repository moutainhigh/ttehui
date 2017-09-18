package com.mocentre.tehui.sub.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.SubjectGoodsInstance;
import com.mocentre.tehui.backend.param.SubjectGoodsParam;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.dao.IGoodsDao;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.sub.dao.ISubjectGoodsDao;
import com.mocentre.tehui.sub.mapper.SubjectGoodsMapper;
import com.mocentre.tehui.sub.model.SubjectGoods;
import com.mocentre.tehui.sub.service.ISubjectGoodsService;

/**
 * 专题商品关联接口实现 Created by yukaiji on 2016/12/02.
 */

@Component
public class SubjectGoodsService implements ISubjectGoodsService {

    @Autowired
    private ISubjectGoodsDao subjectGoodsDao;
    @Autowired
    private IGoodsDao        goodsDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<SubjectGoodsInstance> querySubjectPage(Requirement require) {
        ListJsonResult<SubjectGoodsInstance> result = new ListJsonResult<SubjectGoodsInstance>();
        ListJsonResult<SubjectGoods> pageInfo = subjectGoodsDao.queryDatatablesPage(require);
        List<SubjectGoodsInstance> subGoodInsList = SubjectGoodsMapper.toSubjectGoodsInstanceList(pageInfo.getData());
        result.setData(subGoodInsList);
        result.setDraw(pageInfo.getDraw());
        result.setRecordsFiltered(pageInfo.getRecordsFiltered());
        result.setRecordsTotal(pageInfo.getRecordsTotal());
        return result;
    }

    @Override
    @DataSource(value = "read")
    public List<SubjectGoods> getSubjectGoodsListBySubjectId(Long subjectId) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("subjectId", subjectId);
        return subjectGoodsDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public SubjectGoods querySubjectGoods(Long id) {
        return subjectGoodsDao.get(id);
    }

    @Override
    @DataSource(value = "write")
    public SubjectGoods insertSubjectGoods(SubjectGoodsParam subjectGoodsParam) {
        SubjectGoods subjectGoods = SubjectGoodsMapper.toSubjectGoods(subjectGoodsParam);
        Goods goods = goodsDao.get(subjectGoods.getGoodsId());
        subjectGoods.setGoodsName(goods.getTitle());
        subjectGoods.setSellPrice(goods.getSellPrice());
        subjectGoods.setOldPrice(goods.getOldPrice());
        subjectGoods.setSellLowPrice(goods.getSellLowPrice());
        subjectGoods.setOldLowPrice(goods.getOldLowPrice());
        subjectGoodsDao.saveEntity(subjectGoods);
        return subjectGoods;
    }

    @Override
    @DataSource(value = "write")
    public Long updateSubjectGoods(SubjectGoodsParam subjectGoodsParam) {
        SubjectGoods subjectGoods = SubjectGoodsMapper.toSubjectGoods(subjectGoodsParam);
        Goods goods = goodsDao.get(subjectGoods.getGoodsId());
        subjectGoods.setGoodsName(goods.getTitle());
        subjectGoods.setSellPrice(goods.getSellPrice());
        subjectGoods.setOldPrice(goods.getOldPrice());
        subjectGoods.setSellLowPrice(goods.getSellLowPrice());
        subjectGoods.setOldLowPrice(goods.getOldLowPrice());
        return subjectGoodsDao.updateEntity(subjectGoods);
    }

    @Override
    @DataSource(value = "write")
    public int delByid(Long id) {
        return subjectGoodsDao.logicRemoveById(id);
    }

    @Override
    @DataSource(value = "write")
    public int delAllBySubjectId(Long subjectId) {
        return subjectGoodsDao.logicRemoveBySubject(subjectId);
    }

    @Override
    @DataSource(value = "read")
    public PageInfo<SubjectGoods> querySubjectPage(Long subjectId, Integer start, Integer length) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderColumn", "sorting");
        paramMap.put("orderBy", "asc");
        paramMap.put("subjectId", subjectId);
        paramMap.put("length", length);
        paramMap.put("start", start);
        return subjectGoodsDao.queryPaged(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public List<SubjectGoods> queryListByGoods(Long goodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("goodsId", goodsId);
        return subjectGoodsDao.queryList(paramMap);
    }

}
