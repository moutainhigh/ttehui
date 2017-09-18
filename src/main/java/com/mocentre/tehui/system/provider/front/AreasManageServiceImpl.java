package com.mocentre.tehui.system.provider.front;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.ListResult;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.frontend.model.AreasFTInstance;
import com.mocentre.tehui.frontend.service.AreasManageService;
import com.mocentre.tehui.ps.service.ICustomerAddressService;
import com.mocentre.tehui.system.enums.AreasType;
import com.mocentre.tehui.system.mapper.AreasMapper;
import com.mocentre.tehui.system.model.Areas;
import com.mocentre.tehui.system.service.IAreasService;

/**
 * 前端区域信息接口实现类. Created by yukaiji on 2017/02/14.
 */
public class AreasManageServiceImpl implements AreasManageService {

    @Autowired
    private IAreasService           areasService;
    @Autowired
    private ICustomerAddressService cumAddsService;

    @Override
    public ListResult<AreasFTInstance> getAllProvinceThreeLinkage(String requestId) {
        ListResult<AreasFTInstance> result = new ListResult<AreasFTInstance>();
        result.setRequestId(requestId);
        try {
            List<Areas> allAreas = areasService.getAreasFromCache();
            List<AreasFTInstance> provinceInsList = new ArrayList<AreasFTInstance>();
            List<AreasFTInstance> cityInsList = new ArrayList<AreasFTInstance>();
            List<AreasFTInstance> areasInsList = new ArrayList<AreasFTInstance>();
            for (Areas areas : allAreas) {
                AreasFTInstance areasIns = AreasMapper.toAreasFTInstance(areas);
                if (AreasType.PROVINCE.getCodeValue().equals(areas.getType())) {
                    provinceInsList.add(areasIns);
                }
                if (AreasType.CITY.getCodeValue().equals(areas.getType())) {
                    cityInsList.add(areasIns);
                }
                if (AreasType.AREA.getCodeValue().equals(areas.getType())) {
                    areasInsList.add(areasIns);
                }
            }

            for (AreasFTInstance citys : cityInsList) {
                List<AreasFTInstance> childrenList = new ArrayList<AreasFTInstance>();
                for (AreasFTInstance areas : areasInsList) {
                    if (citys.getCode().equals(areas.getpCode())) {
                        childrenList.add(areas);
                    }
                }
                citys.setChildrenList(childrenList);
            }

            for (AreasFTInstance province : provinceInsList) {
                List<AreasFTInstance> childrenList = new ArrayList<AreasFTInstance>();
                for (AreasFTInstance citys : cityInsList) {
                    if (province.getCode().equals(citys.getpCode())) {
                        childrenList.add(citys);
                    }
                }
                province.setChildrenList(childrenList);
            }
            result.setData(provinceInsList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getAllProvinceThree", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListResult<AreasFTInstance> getCityList(String requestId) {
        ListResult<AreasFTInstance> result = new ListResult<AreasFTInstance>();
        List<AreasFTInstance> cityInsList = new ArrayList<AreasFTInstance>();
        result.setRequestId(requestId);
        try {
            List<Areas> cityList = areasService.getCityFromCache();
            cityInsList = AreasMapper.toAreasFTInstanceList(cityList);
            result.setData(cityInsList);
            result.setCount(cityInsList.size());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getCityList", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
