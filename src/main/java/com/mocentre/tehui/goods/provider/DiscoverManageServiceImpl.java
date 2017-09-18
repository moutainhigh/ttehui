package com.mocentre.tehui.goods.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.DiscoverManageService;
import com.mocentre.tehui.backend.model.DiscoverAllMsgInstance;
import com.mocentre.tehui.backend.model.DiscoverInstance;
import com.mocentre.tehui.backend.param.DiscoverParam;
import com.mocentre.tehui.backend.param.DiscoverQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.mapper.CategoryMapper;
import com.mocentre.tehui.goods.mapper.DiscoverMapper;
import com.mocentre.tehui.goods.model.Category;
import com.mocentre.tehui.goods.model.Discover;
import com.mocentre.tehui.goods.service.ICategoryService;
import com.mocentre.tehui.goods.service.IDiscoverService;

/**
 * 发现页接口实现 Created by yukaiji on 2016/11/17.
 */
public class DiscoverManageServiceImpl implements DiscoverManageService {

    @Autowired
    private IDiscoverService discoverService;
    @Autowired
    private ICategoryService categoryService;

    @Override
    public ListJsonResult<DiscoverInstance> queryPage(DiscoverQueryParam discoverQueryParam) {
        ListJsonResult<Discover> result = new ListJsonResult<Discover>();
        ListJsonResult<DiscoverInstance> resultData = new ListJsonResult<DiscoverInstance>();
        resultData.setRequestId(discoverQueryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderColumn", discoverQueryParam.getOrderColumn());
            paramMap.put("orderBy", discoverQueryParam.getOrderBy());
            Requirement require = new Requirement(discoverQueryParam.getDraw(), discoverQueryParam.getStart(),
                    discoverQueryParam.getLength(), paramMap);
            result = discoverService.queryDiscoverPage(require);
            List<DiscoverInstance> listData = new ArrayList<>();
            if (result.isSuccess()) {
                if (result.getData() != null && result.getData().size() > 0) {
                    List<Discover> lists = result.getData();
                    listData = DiscoverMapper.toInstanceList(lists);
                }
                resultData.setData(listData);
                resultData.setDraw(result.getDraw());
                resultData.setCode(result.getCode());
                resultData.setMessage(result.getMessage());
                resultData.setRecordsTotal(result.getRecordsTotal());
                resultData.setRecordsFiltered(result.getRecordsFiltered());
            } else {
                resultData.setErrorMessage(result.getCode(), result.getMessage());
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryPage", e);
            resultData.setErrorMessage("999", "系统错误");
        }
        return resultData;
    }

    @Override
    public PlainResult<DiscoverAllMsgInstance> preEdit(Long id, String requestId) {
        PlainResult<DiscoverAllMsgInstance> pr = new PlainResult<DiscoverAllMsgInstance>();
        pr.setRequestId(requestId);
        try {
            DiscoverAllMsgInstance data = new DiscoverAllMsgInstance();
            Discover discover = discoverService.getDiscoverById(id);
            List<Category> categoryList = categoryService.getAllCategoryList();
            data.setDiscoverIns(DiscoverMapper.toInstance(discover));
            data.setCyIns(CategoryMapper.toInstanceList(categoryList));
            pr.setData(data);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("preEdit", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

    @Override
    public BaseResult addDiscover(DiscoverParam discoverParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(discoverParam.getRequestId());
        try {
            Discover discover = DiscoverMapper.toDiscover(discoverParam);
            Long res = discoverService.addDiscover(discover);
            if (res == 0) {
                result.setErrorMessage("1001", "操作失败");
            } else {
                discoverService.updateDiscoverToCache(discover);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addDiscover", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult updateDiscover(DiscoverParam discoverParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(discoverParam.getRequestId());
        try {
            Discover discover = DiscoverMapper.toDiscover(discoverParam);
            discover.setId(discoverParam.getId());
            Long res = discoverService.updateDiscover(discover);
            if (res == 0) {
                result.setErrorMessage("1001", "操作失败");
            } else {
                discoverService.updateDiscoverToCache(discover);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateDiscover", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult deleteDiscover(List<Long> idList, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            int res = discoverService.deleteDiscover(idList);
            if (res == 0) {
                result.setErrorMessage("1001", "操作失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteDiscover", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult show(Long id, Integer show, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        boolean paramValid = id == null || show == null;
        if (paramValid) {
            result.setErrorMessage("1000", "参数错误");
            return result;
        }
        try {
            Long res = discoverService.show(id, show);
            if (res == 0) {
                result.setErrorMessage("1001", "操作失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("show", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
