package com.mocentre.tehui.goods.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.CategoryManageService;
import com.mocentre.tehui.backend.model.CategoryInstance;
import com.mocentre.tehui.backend.param.CategoryParam;
import com.mocentre.tehui.backend.param.CategoryQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.mapper.CategoryMapper;
import com.mocentre.tehui.goods.model.Category;
import com.mocentre.tehui.goods.service.impl.CategoryService;

/**
 * 商品分类provider Created by 王雪莹 on 2016/11/21.
 */
public class CategoryManageServiceImpl implements CategoryManageService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ListJsonResult<CategoryInstance> getCategoryPage(CategoryQueryParam queryParam) {
        ListJsonResult<CategoryInstance> ljr = new ListJsonResult<CategoryInstance>();
        ljr.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("name", queryParam.getName());
            paramMap.put("orderBy", queryParam.getOrderBy() == null ? "asc" : queryParam.getOrderBy());
            paramMap.put("orderColumn", queryParam.getOrderColumn());
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            ListJsonResult<Category> pageInfo = categoryService.getCategoryPage(require);
            List<Category> list = pageInfo.getData();
            List<CategoryInstance> listIns = new ArrayList<CategoryInstance>();
            if (list != null) {
                listIns = CategoryMapper.toInstanceList(list);
            }
            ljr.setData(listIns);
            ljr.setDraw(pageInfo.getDraw());
            ljr.setRecordsFiltered(pageInfo.getRecordsFiltered());
            ljr.setRecordsTotal(pageInfo.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getCategoryPage", e);
            ljr.setErrorMessage("999", "系统异常");
        }
        return ljr;
    }

    @Override
    public ListResult<CategoryInstance> getAllCategoryList(String requestId) {
        ListResult<CategoryInstance> lr = new ListResult<CategoryInstance>();
        lr.setRequestId(requestId);
        try {
            List<Category> list = categoryService.getAllCategoryList();
            if (list != null) {
                List<CategoryInstance> listIns = CategoryMapper.toInstanceList(list);
                lr.setData(listIns);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getAllCategoryList", e);
            lr.setErrorMessage("999", "系统异常");
        }
        return lr;
    }

    /**
     * 根据id查询分类信息
     * 
     * @param id
     * @return
     */
    @Override
    public PlainResult<CategoryInstance> getCategoryById(Long id, String requestId) {
        PlainResult<CategoryInstance> pr = new PlainResult<CategoryInstance>();
        pr.setRequestId(requestId);
        Category category = categoryService.getById(id);
        CategoryInstance categoryInstance = null;
        if (category != null) {
            categoryInstance = CategoryMapper.toInstance(category);
        }
        pr.setData(categoryInstance);
        return pr;
    }

    /**
     * 保存category
     * 
     * @param shopId
     * @param param
     * @param requestId
     * @return
     */
    @Override
    public PlainResult<Long> addCategory(CategoryParam param, String requestId) {
        PlainResult<Long> pr = new PlainResult<Long>();
        pr.setRequestId(requestId);
        try {
            Category category = CategoryMapper.toCategory(param);
            Long i = categoryService.addCategory(category);
            pr.setData(i);
        } catch (Exception e) {
            LoggerUtil.mainLog.error("addCategory", e);
            pr.setErrorMessage("999", "接口异常");
        }
        return pr;
    }

    /**
     * 根据id删除
     * 
     * @param id
     * @return
     */
    @Override
    public PlainResult<Long> delCategory(Long id, String requestId) {
        PlainResult<Long> pr = new PlainResult<Long>();
        pr.setRequestId(requestId);
        try {
            int count = categoryService.delCategory(id);
            if (count == 0) {
                pr.setErrorMessage("1001", "操作失败");
            }
        } catch (Exception e) {
            LoggerUtil.mainLog.error("delCategory", e);
            pr.setErrorMessage("999", "接口异常");
        }
        pr.setData(id);
        return pr;
    }

    /**
     * 根据idList删除
     * 
     * @param ids
     * @param shopId
     * @return
     */
    @Override
    public BaseResult delCategoryList(List<Long> ids, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            int count = categoryService.delCategory(ids);
            if (count == 0) {
                br.setErrorMessage("1001", "操作失败");
            }
        } catch (Exception e) {
            LoggerUtil.mainLog.error("delCategoryList", e);
            br.setErrorMessage("999", "接口异常");
        }
        return br;
    }

    @Override
    public BaseResult editCategory(CategoryParam param) {
        BaseResult br = new BaseResult();
        br.setRequestId(param.getRequestId());
        try {
            Category category = CategoryMapper.toCategory(param);
            Long res = categoryService.updateCategory(category);
            if (res == 0) {
                br.setErrorMessage("1001", "操作失败");
            }
        } catch (Exception e) {
            LoggerUtil.mainLog.error("editCategory", e);
            br.setErrorMessage("999", "接口异常");
        }
        return br;
    }

}
