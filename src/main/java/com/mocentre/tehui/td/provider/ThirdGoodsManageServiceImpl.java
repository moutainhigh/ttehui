package com.mocentre.tehui.td.provider;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.ThirdGoodsManageService;
import com.mocentre.tehui.backend.model.ThirdGoodsInstance;
import com.mocentre.tehui.backend.param.ThirdGoodsParam;
import com.mocentre.tehui.backend.param.ThirdGoodsQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.td.mapper.ThirdGoodsMapper;
import com.mocentre.tehui.td.model.ThirdGoods;
import com.mocentre.tehui.td.model.ThirdGoodsAreas;
import com.mocentre.tehui.td.service.IThirdGoodsAreasService;
import com.mocentre.tehui.td.service.IThirdGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第三方商品dubbo接口实现类
 * <p>
 * Created by yukaiji on 2017/5/17.
 */

public class ThirdGoodsManageServiceImpl implements ThirdGoodsManageService {

    @Autowired
    private IThirdGoodsService thirdGoodsService;
    @Autowired
    private IThirdGoodsAreasService thirdGoodsAreasService;

    @Override
    public ListJsonResult<ThirdGoodsInstance> queryThirdGoodsPage(ThirdGoodsQueryParam queryParam) {
        ListJsonResult<ThirdGoodsInstance> result = new ListJsonResult<ThirdGoodsInstance>();
        result.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderBy", queryParam.getOrderBy());
            paramMap.put("orderColumn", queryParam.getOrderColumn());
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            ListJsonResult<ThirdGoods> lr = thirdGoodsService.queryThirdGoodsPage(require);
            List<ThirdGoods> thirdGoodss = lr.getData();
            result.setData(ThirdGoodsMapper.toThirdGoodsInsList(thirdGoodss));
            result.setDraw(lr.getDraw());
            result.setRecordsFiltered(lr.getRecordsFiltered());
            result.setRecordsTotal(lr.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryThirdGoodsPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<ThirdGoodsInstance> getThirdGoodsById(Long id, String requestId) {
        PlainResult<ThirdGoodsInstance> result = new PlainResult<ThirdGoodsInstance>();
        result.setRequestId(requestId);
        try {
            ThirdGoods thirdGoods = thirdGoodsService.getThirdGoodsById(id);
            ThirdGoodsInstance instance = ThirdGoodsMapper.toThirdGoodsIns(thirdGoods);
            List<ThirdGoodsAreas> areasList = thirdGoodsAreasService.getThirdGoodsAreasByGoodsId(id);
            instance.setAreasList(ThirdGoodsMapper.toThirdGoodsAreasInsList(areasList));
            result.setData(instance);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getThirdGoodsById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult addThirdGoods(ThirdGoodsParam ThirdGoodsParam) {
        BaseResult br = new BaseResult();
        br.setRequestId(ThirdGoodsParam.getRequestId());
        try {
            ThirdGoods ThirdGoods = thirdGoodsService.addThirdGoods(ThirdGoodsParam);
            if (ThirdGoods.getId() == null) {
                br.setErrorMessage("999", "添加失败");
                return br;
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addThirdGoods", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult updateThirdGoods(ThirdGoodsParam ThirdGoodsParam) {
        BaseResult br = new BaseResult();
        br.setRequestId(ThirdGoodsParam.getRequestId());
        try {
            Long updateNum = thirdGoodsService.updateThirdGoods(ThirdGoodsParam);
            if (updateNum < 0) {
                br.setErrorMessage("999", "修改失败");
                return br;
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateThirdGoods", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult delById(Long id, String showLocal) {
        BaseResult result = new BaseResult();
        try {
            int updateNum = thirdGoodsService.removeById(id, showLocal);
            if (updateNum <= 0) {
                result.setErrorMessage("999", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("removeById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
