package com.mocentre.tehui.fee.provider;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mocentre.tehui.PostageConditionManageService;
import com.mocentre.tehui.fee.service.IPostageConditionService;

/**
 * 包邮条件信息接口.
 * 
 * Created by yukaiji on 2016/11/07.
 */
public class PostageConditionManageServiceImpl implements PostageConditionManageService {

	@Autowired
	private IPostageConditionService postageConditionService;

	@Override
	public String getPostageCondition(Long id) {
		return JSON.toJSONString(postageConditionService.getPostageCondition(id));
	}

	@Override
	public String queryPostageCondition(Map<String, Object> paramMap) {
		return JSON.toJSONString(postageConditionService.queryPostageCondition(paramMap));
	}

	@Override
	public Long addPostageCondition(Map<String, Object> paramMap) {
		return postageConditionService.addPostageCondition(paramMap);
	}

	@Override
	public Long editPostageCondition(Map<String, Object> paramMap) {
		return postageConditionService.editPostageCondition(paramMap);
	}

	@Override
	public void deletePostageCondition(List<Long> idList) {
		postageConditionService.deletePostageCondition(idList);
	}



}
