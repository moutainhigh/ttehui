package com.mocentre.tehui.fee.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.CarryModeParam;
import com.mocentre.tehui.backend.param.FreightMouldParam;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.fee.dao.IFreightMouldDao;
import com.mocentre.tehui.fee.enums.SendWayType;
import com.mocentre.tehui.fee.mapper.FreightMouldMapper;
import com.mocentre.tehui.fee.model.CarryMode;
import com.mocentre.tehui.fee.model.FreightMould;
import com.mocentre.tehui.fee.model.FreightMouldAllMsg;
import com.mocentre.tehui.fee.service.ICarryModeService;
import com.mocentre.tehui.fee.service.IFreightMouldService;
import com.mocentre.tehui.frontend.model.FreightFTInstance;
import com.mocentre.tehui.frontend.param.FreightCalculateParam;

/**
 * 邮费模板信息操作接口实现类. Created by yukaiji on 2016/11/04.
 */
@Component
public class FreightMouldService implements IFreightMouldService {

    @Autowired
    private IFreightMouldDao  freightMouldDao;
    @Autowired
    private ICarryModeService carryModeService;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<FreightMould> queryFreightMouldPage(Requirement require) {
        ListJsonResult<FreightMould> result = freightMouldDao.queryDatatablesPage(require);
        return result;
    }

    @Override
    @DataSource(value = "read")
    public FreightMouldAllMsg getfreightMouldAllMsgById(Long id, Long shopId) {
        FreightMouldAllMsg freAllMsg = new FreightMouldAllMsg();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("shopId", shopId);
        FreightMould freightMould = freightMouldDao.getFreightMould(paramMap);
        List<CarryMode> carryModeList = carryModeService.getAllCarryMode(id);
        freAllMsg.setCarryModeList(carryModeList);
        freAllMsg.setFreightMould(freightMould);
        return freAllMsg;
    }

    @Override
    @DataSource(value = "write")
    public FreightMould addFreightMould(FreightMouldParam freightMouldParam) {
        FreightMould freightMould = FreightMouldMapper.toFreightMould(freightMouldParam);
        freightMouldDao.saveEntity(freightMould);
        return freightMould;
    }

    @Override
    @DataSource(value = "write")
    public FreightMould editFreightMould(FreightMouldParam freightMouldParam) {
        FreightMould freightMould = FreightMouldMapper.toFreightMould(freightMouldParam);
        freightMouldDao.updateEntity(freightMould);
        return freightMould;
    }

    @Override
    @DataSource(value = "write")
    public int deleteFreightMould(List<Long> idList) {
        int num = freightMouldDao.delByIdList(idList);
        carryModeService.delByFreightIdList(idList);
        return num;
    }

    @Override
    @DataSource(value = "read")
    public FreightMould getFreightMould(Long freightId, Long shopId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", freightId);
        paramMap.put("shopId", shopId);
        return freightMouldDao.getFreightMould(paramMap);
    }

    /**
     * 特殊情况： 1.地区在一个某一个运送方式下有，其余没有 2.地区在所有运送方式下都没有 3.地区在某一个运送方式下有重复
     * 4.地区在所有运送方式下都有 5.商品包邮. 6.运费模板只配置了两项运送方式.
     */
    @Override
    @DataSource(value = "read")
    public List<FreightFTInstance> getSendWayAndPriceList(FreightCalculateParam fcParam) {
        List<FreightFTInstance> result = new ArrayList<FreightFTInstance>();
        // 模板Id
        Long freightId = fcParam.getFreightId();
        // 根据模板id 获取基本信息
        FreightMould freightMould = this.getFreightMould(freightId, fcParam.getShopId());
        // 判断如果该模板包邮。直接返回运费0
        if (!freightMould.getIsFree().equals("free")) {
            // 未包含指定地区的默认运送方式信息(isDefult = 1)
            List<CarryMode> carryModeDefault = new ArrayList<CarryMode>();
            // 模板所关联的所有运送方式信息
            List<CarryMode> carryModeList = carryModeService.getAllCarryMode(freightId);
            // 循环计算所属地区的各种运送方式的邮费价格
            for (CarryMode carryMode : carryModeList) {
                // 添加到默认运送List
                if (carryMode.getIsDefault() == 1) {
                    carryModeDefault.add(carryMode);
                } else {
                    // 拆分特殊地区
                    String[] areas = carryMode.getArriveArea().split(",");
                    // 循环判断特殊地区中是否包含提交的地区id
                    for (String area : areas) {
                        if (fcParam.getAreaId().equals(area)) {
                            FreightFTInstance freIns = new FreightFTInstance();
                            // 运费价格
                            Long price = null;
                            // 根据商品数量计算运费
                            if (fcParam.getFreeNum() == 1) {
                                price = carryMode.getFirstFree();
                            } else {
                                price = carryMode.getFirstFree()
                                        + ((fcParam.getFreeNum() - 1) * carryMode.getNextFree());
                            }
                            freIns.setSendWay(carryMode.getSendWay());
                            freIns.setPostage(price.toString());
                            // 为防止有多个特殊运费标准中含有相同城市,会计算出多种价格。取第一次计算出的价格
                            boolean isRepeat = false;
                            for (FreightFTInstance Ins : result) {
                                if (Ins.getSendWay().equals(carryMode.getSendWay())) {
                                    isRepeat = true;
                                    break;
                                }
                            }
                            if (isRepeat) {
                                break;
                            }
                            // 添加到返回List中
                            result.add(freIns);
                        }
                    }
                }
            }
            // 目的1:防止只有一种快递配置特殊地区标准。从而只返回一个。
            // 目的2:没有针对提交的城市寻找到特殊标准，同一按照默认计算。
            if (result.size() < 3) {
                for (CarryMode carryMode : carryModeDefault) {
                    FreightFTInstance freIns = new FreightFTInstance();
                    Long price = null;
                    if (fcParam.getFreeNum() == 1) {
                        price = carryMode.getFirstFree();
                    } else {
                        price = carryMode.getFirstFree() + ((fcParam.getFreeNum() - 1) * carryMode.getNextFree());
                    }
                    freIns.setSendWay(carryMode.getSendWay());
                    freIns.setPostage(price.toString());
                    // 为防止特殊地区运费 与 默认计算出的运费重复 发送冲突.
                    boolean isRepeat = false;
                    for (FreightFTInstance Ins : result) {
                        if (Ins.getSendWay().equals(carryMode.getSendWay())) {
                            isRepeat = true;
                            break;
                        }
                    }
                    if (isRepeat) {
                        break;
                    }
                    result.add(freIns);
                }
            }
        } else {
            // 如果包邮，全部为0
            // 循环插入List 运费为0
            for (SendWayType sendWay : SendWayType.values()) {
                FreightFTInstance freIns = new FreightFTInstance();
                freIns.setSendWay(sendWay.getNameValue());
                freIns.setPostage("0");
                result.add(freIns);
            }
        }
        return result;
    }

    @Override
    @DataSource(value = "read")
    public Long getPrice(FreightCalculateParam fcParam) {
        // 返回的最终运费结果
        Long price = 0L;
        // 是否特殊地区运费计算
        boolean flag = false;
        // 默认的运送方式信息
        CarryMode carryModeDefault = new CarryMode();
        FreightMould freightMould = new FreightMould();
        // 获取邮费模板的运送方式信息（包含默认1和非默认0）
        CarryModeParam carryModeParam = new CarryModeParam();
        carryModeParam.setFreightId(fcParam.getFreightId());
        carryModeParam.setSendWay(fcParam.getSendWay());
        List<CarryMode> carryModeList = carryModeService.getCarryModeByParam(carryModeParam);
        // 根据模板id获取模板信息
        freightMould = this.getFreightMould(fcParam.getFreightId(), fcParam.getShopId());
        // 判断是否卖家承担运费
        if (!(freightMould.getIsFree().equals("free"))) {
            // 循环判断是否是默认的运送方式信息。如果是，存到carryModeDefault
            // 否则判断运送特殊地区是否包含用户所在的地区。
            // 如果包含，计算运费、flag设置为true，结束循环
            for (CarryMode carryMode : carryModeList) {
                if (carryMode.getIsDefault() == 1) {
                    carryModeDefault = carryMode;
                } else {
                    String[] areas = carryMode.getArriveArea().split(",");
                    for (String area : areas) {
                        if (area.equals(fcParam.getAreaId())) {
                            flag = true;
                            if (fcParam.getFree().equals("num")) {
                                if (fcParam.getFreeNum() == 1) {
                                    price = carryMode.getFirstFree();
                                } else {
                                    price = carryMode.getFirstFree()
                                            + ((fcParam.getFreeNum() - 1) * carryMode.getNextFree());
                                }
                                break;
                            } else if (fcParam.getFree().equals("weight")) {
                                if (fcParam.getFreeNum() <= Long.valueOf(carryMode.getFirstNum())) {
                                    price = carryMode.getFirstFree();
                                } else {
                                    price = carryMode.getFirstFree()
                                            + ((fcParam.getFreeNum() - Long.valueOf(carryMode.getFirstNum())) / Long
                                                    .valueOf(carryMode.getNextNum())) * carryMode.getNextFree();
                                }
                                break;
                            }
                        }
                    }
                }
            }
            // 判断是否在特殊地区，如果不在则计算默认运费
            if (!flag) {
                if (fcParam.getFree().equals("num")) {
                    if (fcParam.getFreeNum() == 1) {
                        price = carryModeDefault.getFirstFree();
                    } else {
                        price = carryModeDefault.getFirstFree()
                                + ((fcParam.getFreeNum() - 1) * carryModeDefault.getNextFree());
                    }
                } else if (fcParam.getFree().equals("weight")) {
                    if (fcParam.getFreeNum() <= Long.valueOf(carryModeDefault.getFirstNum())) {
                        price = carryModeDefault.getFirstFree();
                    } else {
                        price = carryModeDefault.getFirstFree()
                                + ((fcParam.getFreeNum() - Long.valueOf(carryModeDefault.getFirstNum())) / Long
                                        .valueOf(carryModeDefault.getNextNum())) * carryModeDefault.getNextFree();
                    }
                }
            }
        }
        return price;
    }

}
