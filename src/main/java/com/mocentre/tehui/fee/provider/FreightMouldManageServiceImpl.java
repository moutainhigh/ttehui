package com.mocentre.tehui.fee.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.FreightMouldManageService;
import com.mocentre.tehui.backend.model.FreightMouldAllMsgInstance;
import com.mocentre.tehui.backend.model.FreightMouldInstance;
import com.mocentre.tehui.backend.param.CarryModeParam;
import com.mocentre.tehui.backend.param.FreightMouldParam;
import com.mocentre.tehui.backend.param.FreightMouldQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.fee.mapper.FreightMouldMapper;
import com.mocentre.tehui.fee.model.FreightMould;
import com.mocentre.tehui.fee.model.FreightMouldAllMsg;
import com.mocentre.tehui.fee.service.ICarryModeService;
import com.mocentre.tehui.fee.service.IFreightMouldService;

/**
 * 邮费模板信息接口. Created by yukaiji on 2016/11/07.
 */
public class FreightMouldManageServiceImpl implements FreightMouldManageService {

	@Autowired
	private IFreightMouldService freightMouldService;
	@Autowired
	private ICarryModeService carryModeService;

	@Override
	public ListJsonResult<FreightMouldInstance> queryFreightMouldPage(FreightMouldQueryParam freightMouldQuery) {
		ListJsonResult<FreightMouldInstance> result = new ListJsonResult<FreightMouldInstance>();
		String requestId = freightMouldQuery.getRequestId();
		result.setRequestId(requestId);
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("shopId", freightMouldQuery.getShopId());
			Requirement require = new Requirement(freightMouldQuery.getDraw(), freightMouldQuery.getStart(),
					freightMouldQuery.getLength(), paramMap);
			ListJsonResult<FreightMould> pageInfo = freightMouldService.queryFreightMouldPage(require);
			List<FreightMould> data = pageInfo.getData();
			result.setData(FreightMouldMapper.toFreightMouldInstanceList(data));
		} catch (Exception e) {
			LoggerUtil.tehuiLog.error("queryFreightMouldPage", e);
			result.setErrorMessage("999", "系统错误");
		}
		return result;
	}

	@Override
	public PlainResult<FreightMouldAllMsgInstance> getFreightMouldById(Long id, Long shopId, String requestId) {
		PlainResult<FreightMouldAllMsgInstance> result = new PlainResult<FreightMouldAllMsgInstance>();
		try {
			result.setRequestId(requestId);
			FreightMouldAllMsg freAllMsg = freightMouldService.getfreightMouldAllMsgById(id, shopId);
			FreightMouldAllMsgInstance freAllMsgIns = FreightMouldMapper.toFreightMouldAllMsgInstance(freAllMsg);
			result.setData(freAllMsgIns);
		} catch (Exception e) {
			LoggerUtil.tehuiLog.error("getFreightMouldById", e);
			result.setErrorMessage("999", "系统错误");
		}
		return result;
	}

	@Override
	public BaseResult addFreightMould(FreightMouldParam freightMouldParam, List<CarryModeParam> carryModeParamList) {
		BaseResult result = new BaseResult();
		result.setRequestId(freightMouldParam.getRequestId());
		try {
			FreightMould freight = freightMouldService.addFreightMould(freightMouldParam);
			if (freight.getId() != null) {
				for (CarryModeParam carryModeParam : carryModeParamList) {
					carryModeParam.setFreightId(freight.getId());
				}
				carryModeService.addCarryMode(carryModeParamList);
			}
		} catch (Exception e) {
			LoggerUtil.tehuiLog.error("addSubject", e);
			result.setErrorMessage("999", "系统错误");
		}
		return result;
	}

	@Override
	public BaseResult editFreightMould(FreightMouldParam freightMouldParam, List<CarryModeParam> carryModeParamList) {
		BaseResult result = new BaseResult();
		result.setRequestId(freightMouldParam.getRequestId());
		try {
			freightMouldService.editFreightMould(freightMouldParam);
			Long freightId = freightMouldParam.getId();
			for (CarryModeParam carryModeParam : carryModeParamList) {
				carryModeParam.setFreightId(freightId);
			}
			carryModeService.editCarryMode(carryModeParamList, freightId);
		} catch (Exception e) {
			LoggerUtil.tehuiLog.error("editFreightMould", e);
			result.setErrorMessage("999", "系统错误");
		}
		return result;
	}

	@Override
	public BaseResult deleteFreightMould(List<Long> idList, String requestId) {
		BaseResult result = new BaseResult();
		result.setRequestId(requestId);
		try {
			int num = freightMouldService.deleteFreightMould(idList);
			if (num == 0) {
				result.setErrorMessage("1002", "删除失败");
			}
		} catch (Exception e) {
			LoggerUtil.tehuiLog.error("deleteFreightMould", e);
			result.setErrorMessage("999", "系统错误");
		}
		return result;
	}

}
