package com.mocentre.tehui.goods.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.util.StringUtils;
import com.mocentre.common.BaseResult;
import com.mocentre.common.CommonResultCode;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.AttributeManageService;
import com.mocentre.tehui.backend.model.AttributeInstance;
import com.mocentre.tehui.backend.param.AttributeParam;
import com.mocentre.tehui.backend.param.AttributeQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.mapper.AttributeMapper;
import com.mocentre.tehui.goods.model.Attribute;
import com.mocentre.tehui.goods.service.IAttributeService;

/**
 * 商品属性实现 Created by wangxueying on 2016/11/9.
 */
public class AttributeManageServiceImpl implements AttributeManageService {

    @Autowired
    private IAttributeService attributeService;

    /**
     * 获取所有的商品属性
     * 
     * @return
     */
    @Override
    public ListJsonResult<AttributeInstance> getPage(AttributeQueryParam queryParam) {
        ListJsonResult<AttributeInstance> ljr = new ListJsonResult<AttributeInstance>();
        ljr.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("code", queryParam.getCode());
            paramMap.put("name", queryParam.getName());
            paramMap.put("orderBy", queryParam.getOrderBy() == null ? "desc" : queryParam.getOrderBy());
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            ListJsonResult<Attribute> pageInfo = attributeService.getAttributePage(require);
            List<Attribute> attList = pageInfo.getData();
            List<AttributeInstance> resultList = new ArrayList<AttributeInstance>();
            if (attList != null) {
                for (Attribute attribute : attList) {
                    AttributeInstance attributeInstance = AttributeMapper.toInstance(attribute);
                    resultList.add(attributeInstance);
                }
            }
            ljr.setData(resultList);
            ljr.setDraw(pageInfo.getDraw());
            ljr.setRecordsFiltered(pageInfo.getRecordsFiltered());
            ljr.setRecordsTotal(pageInfo.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getPage", e);
        }
        return ljr;
    }

    /**
     * 根据id获取商品
     * 
     * @param id
     * @return
     */
    @Override
    public PlainResult<AttributeInstance> getById(Long id, String requestId) {
        PlainResult<AttributeInstance> pr = new PlainResult<AttributeInstance>();
        pr.setRequestId(requestId);
        Attribute attribute = attributeService.getById(id);
        AttributeInstance attributeInstance = AttributeMapper.toInstance(attribute);
        pr.setData(attributeInstance);
        return pr;
    }

    /**
     * 查询商品属性
     * 
     * @param paramMap
     * @return
     */
    @Override
    public ListResult<AttributeInstance> queryList(String requestId) {
        ListResult<AttributeInstance> lr = new ListResult<AttributeInstance>();
        lr.setRequestId(requestId);
        try {
            List<Attribute> attributes = attributeService.getAttributes();
            List<AttributeInstance> resultList = new ArrayList<AttributeInstance>();
            for (Attribute attribute : attributes) {
                AttributeInstance attributeInstance = AttributeMapper.toInstance(attribute);
                resultList.add(attributeInstance);
            }
            lr.setData(resultList);
            lr.setCount(resultList.size());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryList", e);
        }
        return lr;
    }

    /**
     * 删除商品属性
     * 
     * @param ids
     * @return
     */
    @Override
    public BaseResult del(List<Long> ids, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        int res = attributeService.delAttribute(ids);
        if (res < 1) {
            CommonResultCode.ERROR_DB.mixIn(br);
        }
        return br;
    }

    @Override
    public BaseResult update(Long id, AttributeParam attrParam) {
        BaseResult br = new BaseResult();
        br.setRequestId(attrParam.getRequestId());
        String name = attrParam.getName();
        String code = attrParam.getCode();
        Boolean needImg = attrParam.getNeedImg();
        boolean paramValid = StringUtils.isEmpty(name) || StringUtils.isEmpty(code);
        if (paramValid) {
            br.setErrorMessage("1000", "参数错误");
        }
        try {
            if (br.isSuccess()) {
                Attribute attr = attributeService.getAttributesByCode(code);
                if (attr != null && attr.getId() != id) {
                    br.setErrorMessage("1001", "属性编码已存在");
                    return br;
                }
                Attribute attribute = new Attribute();
                attribute.setId(id);
                if (needImg != null && needImg) {
                    attribute.setIsImg("1");
                } else {
                    attribute.setIsImg("0");
                }
                attribute.setCode(code);
                attribute.setName(name);
                Long count = attributeService.updateAttribute(attribute);
                if (count == 0) {
                    br.setErrorMessage("1002", "修改失败");
                }
            }
        } catch (Exception e) {
            br.setErrorMessage("999", "接口异常");
            LoggerUtil.tehuiLog.error("update", e);
        }
        return br;
    }

    @Override
    public BaseResult addAttribute(AttributeParam attrParam) {
        BaseResult br = new BaseResult();
        br.setRequestId(attrParam.getRequestId());
        try {
            String name = attrParam.getName();
            String code = attrParam.getCode();
            Boolean needImg = attrParam.getNeedImg();
            boolean paramValid = StringUtils.isEmpty(name) || StringUtils.isEmpty(code);
            if (paramValid) {
                br.setErrorMessage("1000", "参数错误");
            }
            if (br.isSuccess()) {
                Attribute attr = attributeService.getAttributesByCode(code);
                if (attr != null) {
                    br.setErrorMessage("1001", "属性编码已存在");
                    return br;
                }
                Attribute attribute = new Attribute();
                if (needImg != null && needImg) {
                    attribute.setIsImg("1");
                } else {
                    attribute.setIsImg("0");
                }
                attribute.setCode(code);
                attribute.setName(name);
                Long count = attributeService.addAttribute(attribute);
                if (count == 0) {
                    br.setErrorMessage("1002", "添加失败");
                }
            }
        } catch (Exception e) {
            br.setErrorMessage("999", "接口异常");
            LoggerUtil.tehuiLog.error("addAttribute", e);
        }
        return br;
    }

}
