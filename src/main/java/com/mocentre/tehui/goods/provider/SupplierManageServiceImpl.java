package com.mocentre.tehui.goods.provider;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.SupplierManageService;
import com.mocentre.tehui.backend.model.SupplierInstance;
import com.mocentre.tehui.backend.param.SupplierParam;
import com.mocentre.tehui.backend.param.SupplierQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.mapper.SupplierMapper;
import com.mocentre.tehui.goods.model.Supplier;
import com.mocentre.tehui.goods.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台活动接口实现 Created by yukaiji on 2017/01/16.
 */
public class SupplierManageServiceImpl implements SupplierManageService {

    @Autowired
    private ISupplierService supplierService;


    @Override
    public ListJsonResult<SupplierInstance> querySupplierPage(SupplierQueryParam supplierQuery) {
        ListJsonResult<SupplierInstance> lr = new ListJsonResult<SupplierInstance>();
        lr.setRequestId(supplierQuery.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderBy", supplierQuery.getOrderBy());
            paramMap.put("column", supplierQuery.getOrderColumn());
            Requirement require = new Requirement(supplierQuery.getDraw(), supplierQuery.getStart(),
                    supplierQuery.getLength(), paramMap);
            ListJsonResult<Supplier> result = supplierService.querySupplierPage(require);
            List<Supplier> supplierList = result.getData();
            List<SupplierInstance> supplierInsList = SupplierMapper.toInstanceList(supplierList);
            lr.setData(supplierInsList);
            lr.setDraw(result.getDraw());
            lr.setRecordsFiltered(result.getRecordsFiltered());
            lr.setRecordsTotal(result.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("querySupplierPage", e);
            lr.setErrorMessage("999", "系统错误");
        }
        return lr;
    }

    @Override
    public PlainResult<SupplierInstance> SupplierEdit(Long id, String requestId) {
        PlainResult<SupplierInstance> pr = new PlainResult<SupplierInstance>();
        pr.setRequestId(requestId);
        try {
            Supplier supplier = supplierService.getSupplierById(id);
            pr.setData(SupplierMapper.toSupplierInstance(supplier));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("SupplierEdit", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

    @Override
    public BaseResult addSupplier(SupplierParam supplierParam) {
        BaseResult br = new BaseResult();
        br.setRequestId(supplierParam.getRequestId());
        try {
            Supplier supplier = supplierService.addSupplier(supplierParam);
            if (supplier.getId() == null) {
                br.setErrorMessage("1001", "添加失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addSupplier", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult updateSupplier(SupplierParam supplierParam) {
        BaseResult br = new BaseResult();
        br.setRequestId(supplierParam.getRequestId());
        try {
            Long updateNum = supplierService.updateSupplier(supplierParam);
            if (updateNum < 0) {
                br.setErrorMessage("1001", "修改失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateSupplier", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult deleteSupplier(Long id, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            int num = supplierService.delSupplierById(id);
            if (num <= 0) {
                br.setErrorMessage("1001", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteSupplier", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }
}
