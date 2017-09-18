package com.mocentre.tehui.goods.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.MapResult;
import com.mocentre.tehui.CategoryGoodsManageService;
import com.mocentre.tehui.backend.model.CategoryGoodsInstance;
import com.mocentre.tehui.backend.param.CategoryGoodsParam;
import com.mocentre.tehui.backend.param.CategoryGoodsQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.mapper.CategoryGoodsMapper;
import com.mocentre.tehui.goods.mapper.CategoryMapper;
import com.mocentre.tehui.goods.mapper.GoodsMapper;
import com.mocentre.tehui.goods.model.Category;
import com.mocentre.tehui.goods.model.CategoryGoods;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.service.ICategoryGoodsService;
import com.mocentre.tehui.goods.service.ICategoryService;
import com.mocentre.tehui.goods.service.IGoodsService;

/**
 * 分类商品service Created by yukaiji on 2017/7/4.
 */
public class CategoryGoodsManageServiceImpl implements CategoryGoodsManageService {

    @Autowired
    private ICategoryGoodsService categoryGoodsService;
    @Autowired
    private ICategoryService      categoryService;
    @Autowired
    private IGoodsService         goodsService;

    @Override
    public ListJsonResult<CategoryGoodsInstance> queryCategoryGoodsPage(CategoryGoodsQueryParam queryParam) {
        ListJsonResult<CategoryGoodsInstance> ljr = new ListJsonResult<CategoryGoodsInstance>();
        ljr.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("showName", queryParam.getShowName());
            paramMap.put("orderBy", queryParam.getOrderBy());
            paramMap.put("orderColumn", queryParam.getOrderColumn());
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            ListJsonResult<CategoryGoods> pageInfo = categoryGoodsService.queryCategoryGoodsPage(require);
            List<CategoryGoods> list = pageInfo.getData();
            List<CategoryGoodsInstance> listIns = new ArrayList<CategoryGoodsInstance>();
            if (list != null) {
                for (CategoryGoods categoryGoods : list) {
                    CategoryGoodsInstance cgIns = CategoryGoodsMapper.toCategoryGoodsIns(categoryGoods);
                    Category category = categoryService.getById(categoryGoods.getCategoryId());
                    cgIns.setCategoryName(category.getName());
                    listIns.add(cgIns);
                }
            }
            ljr.setData(listIns);
            ljr.setDraw(pageInfo.getDraw());
            ljr.setRecordsFiltered(pageInfo.getRecordsFiltered());
            ljr.setRecordsTotal(pageInfo.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryCategoryGoodsPage", e);
            ljr.setErrorMessage("999", "系统异常");
        }
        return ljr;
    }

    @Override
    public MapResult getCategoryGoodsById(Long id, String requestId) {
        MapResult result = new MapResult();
        Map<String, Object> map = new HashMap<String, Object>();
        result.setRequestId(requestId);
        try {
            CategoryGoods categoryGoods = categoryGoodsService.getCategoryGoodsById(id);
            List<Category> categories = categoryService.getAllCategoryList();
            map.put("categoryGoods", CategoryGoodsMapper.toCategoryGoodsIns(categoryGoods));
            map.put("categories", CategoryMapper.toInstanceList(categories));
            if ("0".equals(categoryGoods.getIsChain())) {
                List<Goods> goodsList = goodsService.getOnShelfGoodsByCategory(categoryGoods.getCategoryId());
                map.put("goodsList", GoodsMapper.toInstanceList(goodsList));
            }
            result.setData(map);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getCategoryGoodsById", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public BaseResult addCategoryGoods(CategoryGoodsParam categoryGoodsParam) {
        BaseResult br = new BaseResult();
        try {
            Long id = categoryGoodsService.addCategoryGoods(categoryGoodsParam);
            if (id == null) {
                br.setErrorMessage("999", "添加失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addCategoryGoods", e);
            br.setErrorMessage("999", "系统异常");
        }
        return br;
    }

    @Override
    public BaseResult updateCategoryGoods(CategoryGoodsParam categoryGoodsParam) {
        BaseResult br = new BaseResult();
        try {
            Long num = categoryGoodsService.updateCategoryGoods(categoryGoodsParam);
            if (num < 0) {
                br.setErrorMessage("999", "修改失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateCategoryGoods", e);
            br.setErrorMessage("999", "系统异常");
        }
        return br;
    }

    @Override
    public BaseResult deleteCategoryGoodsById(Long id, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            int num = categoryGoodsService.deleteCategoryGoodsById(id);
            if (num <= 0) {
                br.setErrorMessage("999", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteCategoryGoodsById", e);
            br.setErrorMessage("999", "系统异常");
        }
        return br;
    }
}
