package com.mocentre.tehui.fee.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.fee.dao.IPostageConditionDao;
import com.mocentre.tehui.fee.model.PostageCondition;
import com.mocentre.tehui.fee.service.IPostageConditionService;

/**
 * 包邮条件信息操作接口实现类. Created by yukaiji on 2016/11/04.
 */
@Component
public class PostageConditionService implements IPostageConditionService {

    @Autowired
    private IPostageConditionDao iPostageConditionDao;

    @Override
    @DataSource(value = "read")
    public PostageCondition getPostageCondition(Long id) {
        return iPostageConditionDao.get(id);
    }

    @Override
    @DataSource(value = "write")
    public Long addPostageCondition(Map<String, Object> paramMap) {
        PostageCondition PostageCondition = new PostageCondition();
        PostageCondition.setFreight_id(Long.valueOf((String) paramMap.get("freight_id")));
        PostageCondition.setPost_area((String) paramMap.get("post_area"));
        PostageCondition.setPost_way(Integer.valueOf((String) paramMap.get("post_way")));
        PostageCondition.setNeed_con(Integer.valueOf((String) paramMap.get("need_con")));
        PostageCondition.setCon_value((String) paramMap.get("con_value"));
        PostageCondition.setSend_way((String) paramMap.get("send_way"));
        return iPostageConditionDao.saveEntity(PostageCondition);
    }

    @Override
    @DataSource(value = "read")
    public List<PostageCondition> queryPostageCondition(Map<String, Object> paramMap) {
        return iPostageConditionDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public Long editPostageCondition(Map<String, Object> paramMap) {
        PostageCondition PostageCondition = new PostageCondition();
        PostageCondition.setId(Long.valueOf((String) paramMap.get("id")));
        PostageCondition.setFreight_id(Long.valueOf((String) paramMap.get("freight_id")));
        PostageCondition.setPost_area((String) paramMap.get("post_area"));
        PostageCondition.setPost_way(Integer.valueOf((String) paramMap.get("post_way")));
        PostageCondition.setNeed_con(Integer.valueOf((String) paramMap.get("need_con")));
        PostageCondition.setCon_value((String) paramMap.get("con_value"));
        PostageCondition.setSend_way((String) paramMap.get("send_way"));
        return iPostageConditionDao.updateEntity(PostageCondition);
    }

    @Override
    @DataSource(value = "write")
    public void deletePostageCondition(List<Long> idList) {
        for (Long id : idList) {
            iPostageConditionDao.logicRemoveById(id);
        }
    }

}
