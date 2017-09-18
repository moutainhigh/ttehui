package com.mocentre.tehui.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.dao.IAttributeDao;
import com.mocentre.tehui.goods.model.Attribute;
import com.mocentre.tehui.goods.service.IAttributeService;

/**
 * 商品接口service实现 Created by 王雪莹 on 2016/11/5.
 */
@Component
public class AttributeService implements IAttributeService {

    @Autowired
    private IAttributeDao attributeDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<Attribute> getAttributePage(Requirement require) {
        ListJsonResult<Attribute> pageInfo = attributeDao.queryDatatablesPage(require);
        return pageInfo;
    }

    @Override
    @DataSource(value = "read")
    public List<Attribute> getAttributes() {
        return attributeDao.getAll();
    }

    @Override
    @DataSource(value = "read")
    public List<Attribute> getAttributesList(Map<String, Object> param) {
        return attributeDao.queryList(param);
    }

    @Override
    @DataSource(value = "read")
    public Attribute getAttributesByCode(String code) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", code);
        return attributeDao.queryUniquely(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public Long addAttribute(Attribute attribute) {
        return attributeDao.saveEntity(attribute);
    }

    @Override
    @DataSource(value = "write")
    public Long addAttribute(Map<String, Object> param) {
        Attribute attribute = bindAttribute(param);
        return attributeDao.saveEntity(attribute);
    }

    @Override
    @DataSource(value = "write")
    public Long updateAttribute(Attribute attribute) {
        return attributeDao.updateEntity(attribute);
    }

    @Override
    @DataSource(value = "write")
    public Long updateAttribute(Map<String, Object> param) {
        Attribute attribute = bindAttribute(param);
        return attributeDao.updateEntity(attribute);
    }

    @Override
    @DataSource(value = "write")
    public int delAttribute(Long id) {
        return attributeDao.delById(id);
    }

    @Override
    @DataSource(value = "read")
    public Attribute getById(Long id) {
        return attributeDao.get(id);
    }

    @Override
    @DataSource(value = "write")
    public int delAttribute(List<Long> ids) {
        return attributeDao.delById(ids);
    }

    /**
     * 将map转成Attribute
     * 
     * @param param
     * @return
     */
    private Attribute bindAttribute(Map<String, Object> param) {
        Attribute atrribute = new Attribute();
        if (param.get("name") != null) {
            atrribute.setName(param.get("name").toString());
        }
        if (param.get("code") != null) {
            atrribute.setCode(param.get("code").toString());
        }
        if (param.get("isImg") != null) {
            atrribute.setIsImg(param.get("isImg").toString());
        }
        if (param.get("id") != null) {
            atrribute.setId(Long.parseLong(param.get("isImg").toString()));
        }
        return atrribute;
    }

}
