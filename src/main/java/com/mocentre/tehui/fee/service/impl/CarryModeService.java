package com.mocentre.tehui.fee.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.backend.param.CarryModeParam;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.fee.dao.ICarryModeDao;
import com.mocentre.tehui.fee.mapper.FreightMouldMapper;
import com.mocentre.tehui.fee.model.CarryMode;
import com.mocentre.tehui.fee.service.ICarryModeService;

/**
 * 运送方式信息操作接口实现类. Created by yukaiji on 2016/11/04.
 */
@Component
public class CarryModeService implements ICarryModeService {

    @Autowired
    private ICarryModeDao carryModeDao;

    @Override
    @DataSource(value = "read")
    public List<CarryMode> getAllCarryMode(Long freightId) {
        return carryModeDao.queryAll(freightId);
    }

    @Override
    @DataSource(value = "write")
    public Long addCarryMode(List<CarryModeParam> carryModeParamList) {
        List<CarryMode> carryModeList = FreightMouldMapper.toCarryModeList(carryModeParamList);
        return carryModeDao.addCarryMode(carryModeList);
    }

    @Override
    @DataSource(value = "write")
    public Long editCarryMode(List<CarryModeParam> carryModeParamList, Long freightId) {
        carryModeDao.removeById(freightId);
        List<CarryMode> carryModeList = FreightMouldMapper.toCarryModeList(carryModeParamList);
        return carryModeDao.addCarryMode(carryModeList);
    }

    @Override
    @DataSource(value = "write")
    public void deleteCarryMode(Long freight_id) {
        carryModeDao.removeById(freight_id);
    }

    @Override
    @DataSource(value = "write")
    public void delByFreightIdList(List<Long> freightIdList) {
        carryModeDao.delByFreightIdList(freightIdList);
    }

    @Override
    @DataSource(value = "read")
    public List<CarryMode> getCarryModeByParam(CarryModeParam carryModeParam) {
        CarryMode carryMode = FreightMouldMapper.toCarryMode(carryModeParam);
        return carryModeDao.queryByParam(carryMode);
    }

}
