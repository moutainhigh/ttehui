package com.mocentre.tehui.fee.mapper;

import java.util.ArrayList;
import java.util.List;

import com.mocentre.tehui.backend.model.CarryModeInstance;
import com.mocentre.tehui.backend.model.FreightMouldAllMsgInstance;
import com.mocentre.tehui.backend.model.FreightMouldInstance;
import com.mocentre.tehui.backend.param.CarryModeParam;
import com.mocentre.tehui.backend.param.FreightMouldParam;
import com.mocentre.tehui.fee.model.CarryMode;
import com.mocentre.tehui.fee.model.FreightMould;
import com.mocentre.tehui.fee.model.FreightMouldAllMsg;

/**
 * 类FreightMouldMapper.java的实现描述：专题转换
 * 
 * @author sz.gong 2016年12月12日 下午4:43:07
 */
public class FreightMouldMapper {
	
    public static FreightMouldInstance toFreightMouldInstance(FreightMould freightMould) {
        FreightMouldInstance freIns = new FreightMouldInstance();
        freIns.setId(freightMould.getId());
        freIns.setName(freightMould.getName());
        freIns.setCalcWay(freightMould.getCalcWay());
        freIns.setGmtCreated(freightMould.getGmtCreated());
        freIns.setIsFree(freightMould.getIsFree());
        freIns.setPostRequire(freightMould.getPostRequire());
        freIns.setSendAddr(freightMould.getSendAddr());
        freIns.setSendDate(freightMould.getSendDate());
        freIns.setShopId(freightMould.getShopId());
        return freIns;
    }
    
    public static List<FreightMouldInstance> toFreightMouldInstanceList(List<FreightMould> freightMouldList) {
    	List<FreightMouldInstance> freInsList = new ArrayList<FreightMouldInstance>();
    	if (freightMouldList == null || freightMouldList.size() < 1){
            return freInsList;
        }
    	for (FreightMould freightMould : freightMouldList) {
    		freInsList.add(toFreightMouldInstance(freightMould));
		}
        return freInsList;
    }

	public static CarryModeInstance toCarryMode(CarryMode carryMode) {
		CarryModeInstance carryModeIns = new CarryModeInstance();
		carryModeIns.setArriveArea(carryMode.getArriveArea());
		carryModeIns.setFirstFree(carryMode.getFirstFree());
		carryModeIns.setFirstNum(carryMode.getFirstNum());
		carryModeIns.setFreightId(carryMode.getFreightId());
		carryModeIns.setGmtCreated(carryMode.getGmtCreated());
		carryModeIns.setId(carryMode.getId());
		carryModeIns.setIsDefault(carryMode.getIsDefault());
		carryModeIns.setNextFree(carryMode.getNextFree());
		carryModeIns.setNextNum(carryMode.getNextNum());
		carryModeIns.setSendWay(carryMode.getSendWay());
		return carryModeIns;
	}

	public static List<CarryModeInstance> toCarryModeInsList(List<CarryMode> carryModeList) {
		if (carryModeList == null || carryModeList.size() < 1){
            return null;
        }
		List<CarryModeInstance> carryModeInsList = new ArrayList<CarryModeInstance>();
		for (CarryMode carryMode : carryModeList) {
			carryModeInsList.add(toCarryMode(carryMode));
		}
		return carryModeInsList;
	}
    
	public static FreightMouldAllMsgInstance toFreightMouldAllMsgInstance(FreightMouldAllMsg freAllMsg) {
		FreightMouldAllMsgInstance freAllMsgIns = new FreightMouldAllMsgInstance();
        FreightMouldInstance freIns = toFreightMouldInstance(freAllMsg.getFreightMould());
        freAllMsgIns.setFreMouldIns(freIns);
        freAllMsgIns.setCarryModeInsList(toCarryModeInsList(freAllMsg.getCarryModeList()));
        return freAllMsgIns;
	}

	public static CarryMode toCarryMode(CarryModeParam carryModeParam) {
		CarryMode carryMode = new CarryMode();
		carryMode.setArriveArea(carryModeParam.getArriveArea());
		carryMode.setFirstFree(carryModeParam.getFirstFree());
		carryMode.setFirstNum(carryModeParam.getFirstNum());
		carryMode.setFreightId(carryModeParam.getFreightId());
		carryMode.setId(carryModeParam.getId());
		carryMode.setIsDefault(carryModeParam.getIsDefault());
		carryMode.setNextFree(carryModeParam.getNextFree());
		carryMode.setNextNum(carryModeParam.getNextNum());
		carryMode.setSendWay(carryModeParam.getSendWay());
		return carryMode;
	}
	
	public static List<CarryMode> toCarryModeList(List<CarryModeParam> carryModeParamList) {
		if (carryModeParamList == null || carryModeParamList.size() < 1){
            return null;
        }
		List<CarryMode> carryModeList = new ArrayList<CarryMode>();
		for (CarryModeParam carryModeParam : carryModeParamList) {
			carryModeList.add(toCarryMode(carryModeParam));
		}
		return carryModeList;
	}
	
	public static FreightMould toFreightMould(FreightMouldParam freightMouldParam) {
		FreightMould freightMould = new FreightMould();
		freightMould.setCalcWay(freightMouldParam.getCalcWay());
		freightMould.setId(freightMouldParam.getId());
		freightMould.setIsFree(freightMouldParam.getIsFree());
		freightMould.setName(freightMouldParam.getName());
		freightMould.setPostRequire(freightMouldParam.getPostRequire());
		freightMould.setSendAddr(freightMouldParam.getSendAddr());
		freightMould.setSendDate(freightMouldParam.getSendDate());
		freightMould.setShopId(freightMouldParam.getShopId());
		return freightMould;
	}
    

}
