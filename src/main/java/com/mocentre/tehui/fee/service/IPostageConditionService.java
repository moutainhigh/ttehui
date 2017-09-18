package com.mocentre.tehui.fee.service;

import java.util.List;
import java.util.Map;

import com.mocentre.tehui.fee.model.PostageCondition;

/**
 * 包邮条件信息接口.
 * 
 * Created by yukaiji on 2016/11/04.
 */
public interface IPostageConditionService {

	/**
	 * 根据Id获取获取包邮条件的信息
	 * 
	 * @param id
	 * @return 包邮条件信息
	 */
	PostageCondition getPostageCondition(Long id);

	/**
	 * 根据条件查询包邮条件信息
	 * 
	 * @param paramMap
	 * @return
	 */
	List<PostageCondition> queryPostageCondition(Map<String, Object> paramMap);

	/**
	 * 添加包邮条件信息
	 * 
	 * @param paramMap
	 *            包邮条件信息对象
	 * @return
	 */
	Long addPostageCondition(Map<String, Object> paramMap);

	/**
	 * 修改包邮条件信息
	 * 
	 * @param paramMap
	 *            包邮条件信息对象
	 * @return
	 */
	Long editPostageCondition(Map<String, Object> paramMap);

	/**
	 * 删除包邮条件
	 * 
	 * @param idList
	 *            id列表
	 * 
	 */
	void deletePostageCondition(List<Long> idList);
}
