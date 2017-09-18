package com.mocentre.tehui.fee.dao;

import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.fee.model.FreightMould;

/**
 * 邮费模板信息数据库操作接口 Created by yukaiji on 2016/11/04.
 */
public interface IFreightMouldDao {

	/**
	 * 根据id和shopid获取运费模板
	 * 
	 * @param paramMap
	 * @return
	 */
	FreightMould getFreightMould(Map<String, Object> paramMap);

	/**
	 * 修改运费模板
	 * 
	 * @param freightMould
	 * @return
	 */
	Long updateEntity(FreightMould freightMould);

	/**
	 * 添加运费模板
	 * 
	 * @param freightMould
	 * @return
	 */
	Long saveEntity(FreightMould freightMould);

	/**
	 * 批量删除
	 * 
	 * @param idList
	 * @return
	 */
	int delByIdList(List<Long> idList);

	/**
	 * 分页查询
	 * 
	 * @param require
	 * @return
	 */
	ListJsonResult<FreightMould> queryDatatablesPage(Requirement require);

}
