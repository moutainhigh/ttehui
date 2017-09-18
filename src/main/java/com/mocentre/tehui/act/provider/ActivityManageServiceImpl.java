package com.mocentre.tehui.act.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.ActivityManageService;
import com.mocentre.tehui.act.enums.ActivityShowLocal;
import com.mocentre.tehui.act.mapper.ActivityGoodsMapper;
import com.mocentre.tehui.act.mapper.ActivityMapper;
import com.mocentre.tehui.act.model.Activity;
import com.mocentre.tehui.act.model.ActivityGoods;
import com.mocentre.tehui.act.service.IActivityGoodsService;
import com.mocentre.tehui.act.service.IActivityService;
import com.mocentre.tehui.backend.model.ActivityAllMsgInstance;
import com.mocentre.tehui.backend.model.ActivityGoodsInstance;
import com.mocentre.tehui.backend.model.ActivityInstance;
import com.mocentre.tehui.backend.model.GoodsStorageInstance;
import com.mocentre.tehui.backend.param.*;
import com.mocentre.tehui.bak.model.MallHome;
import com.mocentre.tehui.bak.service.IMallHomeService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.mapper.GoodsStorageMapper;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.model.GoodsStorage;
import com.mocentre.tehui.goods.service.IGoodsService;
import com.mocentre.tehui.goods.service.IGoodsStorageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台活动接口实现 Created by yukaiji on 2017/01/16.
 */
public class ActivityManageServiceImpl implements ActivityManageService {

    @Autowired
    private IActivityService activityService;
    @Autowired
    private IActivityGoodsService activityGoodsService;
    @Autowired
    private IGoodsStorageService goodsStorageService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IMallHomeService mallHomeService;

    @Override
    public ListJsonResult<ActivityInstance> queryActivityPage(ActivityQueryParam activityQuery) {
        ListJsonResult<ActivityInstance> lr = new ListJsonResult<ActivityInstance>();
        lr.setRequestId(activityQuery.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("shopId", activityQuery.getShopId());
            paramMap.put("orderBy", activityQuery.getOrderBy());
            paramMap.put("column", activityQuery.getOrderColumn());
            Requirement require = new Requirement(activityQuery.getDraw(), activityQuery.getStart(),
                    activityQuery.getLength(), paramMap);
            ListJsonResult<Activity> result = activityService.queryActivityPage(require);
            List<Activity> actList = result.getData();
            List<ActivityInstance> actInsList = ActivityMapper.toActivityInstanceList(actList);
            lr.setData(actInsList);
            lr.setDraw(result.getDraw());
            lr.setRecordsFiltered(result.getRecordsFiltered());
            lr.setRecordsTotal(result.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryActivityPage", e);
            lr.setErrorMessage("999", "系统错误");
        }
        return lr;
    }

    @Override
    public ListJsonResult<ActivityGoodsInstance> queryActivityGoodsPage(ActivityGoodsQueryParam activityGoodsQuery) {
        ListJsonResult<ActivityGoodsInstance> lr = new ListJsonResult<ActivityGoodsInstance>();
        lr.setRequestId(activityGoodsQuery.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("activityId", activityGoodsQuery.getActivityId());
            Requirement require = new Requirement(activityGoodsQuery.getDraw(), activityGoodsQuery.getStart(),
                    activityGoodsQuery.getLength(), paramMap);
            ListJsonResult<ActivityGoods> result = activityGoodsService.queryActivityPage(require);
            List<ActivityGoods> lists = result.getData();
            List<ActivityGoodsInstance> listData = ActivityGoodsMapper.toActivityGoodsInstanceList(lists);
            lr.setData(listData);
            lr.setDraw(result.getDraw());
            lr.setCode(result.getCode());
            lr.setMessage(result.getMessage());
            lr.setRecordsTotal(result.getRecordsTotal());
            lr.setRecordsFiltered(result.getRecordsFiltered());
            lr.setSuccess(result.isSuccess());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryActivityGoodsPage", e);
            lr.setErrorMessage("999", "系统错误");
        }
        return lr;
    }

    @Override
    public BaseResult addActivity(ActivityParam activityParam) {
        BaseResult br = new BaseResult();
        try {
            Activity activity = activityService.addActivity(activityParam);
            if (activity.getId() == null) {
                br.setErrorMessage("1001", "基本信息添加失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addActivity", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult updateActivity(ActivityParam activityParam) {
        BaseResult br = new BaseResult();
        try {
            Long updateNum = activityService.updateActivity(activityParam);
            if (updateNum < 0) {
                br.setErrorMessage("1001", "修改失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateActivity", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult addActivityGoods(ActivityGoodsParam actGoodsParam) {
        BaseResult br = new BaseResult();
        try {
            List<GoodsStorageParam> gsList = actGoodsParam.getGoodsSP();
            if (gsList == null || gsList.size() == 0) {
                br.setErrorMessage("1000", "参数错误");
                return br;
            }
            ActivityGoods actGoods = ActivityGoodsMapper.toActivityGoods(actGoodsParam);
            Activity activity = activityService.getActivityById(actGoods.getActivityId());
            if (activity == null) {
                br.setErrorMessage("1001", "添加商品失败");
                return br;
            }
            Goods goods = goodsService.getGoodsById(actGoods.getGoodsId());
            if (goods == null) {
                br.setErrorMessage("1001", "添加商品失败");
                return br;
            }
            actGoods.setActivityType(activity.getType());
            actGoods.setGoodsName(goods.getTitle());
            List<GoodsStorage> storageList = GoodsStorageMapper.toGoodsStorageList(gsList);
            ActivityGoods activityGoods = activityGoodsService.insertActivityGoods(actGoods, storageList);
            //            Long goodsId = activityGoods.getGoodsId();
            //            if (actGoodsId != null) {
            //                goodsStorageService.updateActGoodsStorageAndCache(goodsId, actGoodsId, storageList);
            //            } else {
            //                br.setErrorMessage("1001", "添加商品失败");
            //            }
            if (activityGoods == null || activityGoods.getId() == null) {
                br.setErrorMessage("1001", "添加商品失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addActivityGoods", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult updateActivityGoods(ActivityGoodsParam actGoodsParam) {
        BaseResult br = new BaseResult();
        try {
            Long goodsId = actGoodsParam.getGoodsId();
            Long actGoodsId = actGoodsParam.getId();
            List<GoodsStorageParam> gsList = actGoodsParam.getGoodsSP();
            boolean paramValid = goodsId == null || actGoodsId == null || gsList == null || gsList.size() == 0;
            if (paramValid) {
                br.setErrorMessage("1000", "参数错误");
            }
            Goods goods = goodsService.getGoodsById(goodsId);
            if (goods == null) {
                br.setErrorMessage("1001", "添加商品失败");
            }
            ActivityGoods actGoods = activityGoodsService.getActivityGoodsById(actGoodsId);
            if (actGoods == null) {
                br.setErrorMessage("1001", "添加商品失败");
            }
            if (br.isSuccess()) {
                List<GoodsStorage> storageList = GoodsStorageMapper.toGoodsStorageList(gsList);
                ActivityGoods activityGoods = ActivityGoodsMapper.toActivityGoods(actGoodsParam);
                activityGoods.setActivityType(actGoods.getActivityType());
                activityGoods.setGoodsName(actGoods.getGoodsName());
                Long updateNum = activityGoodsService.updateActivityGoods(activityGoods, storageList);
                //                if (updateNum >= 0) {
                //                    if (gsList != null && gsList.size() > 0) {
                //                        goodsStorageService.updateActGoodsStorageAndCache(goodsId, actGoodsId, storageList);
                //                    }
                //                } else {
                //                    br.setErrorMessage("1001", "修改失败");
                //                }
                if (updateNum == 0) {
                    br.setErrorMessage("1001", "修改失败");
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateActivityGoods", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult show(String id, String show) {
        BaseResult br = new BaseResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("isShow", show);
        try {
            if ("1".equals(show)) {
                List<ActivityGoods> actGoods = activityGoodsService.getActivityGoodsListByActivityId(Long.valueOf(id));
                if (actGoods.size() <= 0) {
                    br.setErrorMessage("1001", "活动未关联商品");
                    return br;
                }
            }
            int num = activityService.show(paramMap);
            if (num <= 0) {
                br.setErrorMessage("1001", "修改展示状态失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("show", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public PlainResult<ActivityInstance> activityEdit(Long id, Long shopId) {
        PlainResult<ActivityInstance> result = new PlainResult<ActivityInstance>();
        ActivityInstance actIns = new ActivityInstance();
        try {
            Activity activity = activityService.getActivityById(id, shopId);
            actIns = ActivityMapper.toActivityInstance(activity);
            result.setData(actIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("activityEdit", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<ActivityAllMsgInstance> activityGoodsEdit(Long actId, Long actGoodsId) {
        PlainResult<ActivityAllMsgInstance> pr = new PlainResult<ActivityAllMsgInstance>();
        ActivityAllMsgInstance actAllMsgIns = new ActivityAllMsgInstance();
        try {
            ActivityGoods actGoods = activityGoodsService.getActivityGoodsById(actGoodsId);
            if (actGoods == null) {
                pr.setErrorMessage("1001", "数据不存在");
                return pr;
            }
            Activity act = activityService.getActivityById(actId);
            if (act == null) {
                pr.setErrorMessage("1001", "数据不存在");
                return pr;
            }
            ActivityInstance actIns = ActivityMapper.toActivityInstance(act);
            Long goodsId = actGoods.getGoodsId();
            Goods goods = goodsService.getGoodsById(goodsId);
            if (goods == null) {
                pr.setErrorMessage("1001", "数据不存在");
                return pr;
            }
            List<GoodsStorage> actGoodsStorageList = goodsStorageService.queryGoodsStorageByGoosid(goodsId, actGoodsId);
            ActivityGoodsInstance actGoodsIns = ActivityGoodsMapper.toActivityGoodsInstance(actGoods);
            List<GoodsStorageInstance> actGoodsStorgeInsList = GoodsStorageMapper
                    .toGoodsStorageInstancesList(actGoodsStorageList);
            String actHoldStandard = actGoods.getHoldStandard();
            String holdStandard = goods.getHoldStandard();
            JSONArray actHdStandArr = JSON.parseArray(actHoldStandard);
            JSONArray hdStandArr = JSON.parseArray(holdStandard);
            List<Map<String, String>> standardList = new ArrayList<Map<String, String>>();
            if (hdStandArr != null) {
                for (int i = 0; i < hdStandArr.size(); i++) {
                    Map<String, String> standardMap = new HashMap<String, String>();
                    JSONObject jobj = hdStandArr.getJSONObject(i);
                    String code = jobj.getString("code");
                    String name = jobj.getString("name");
                    if (actHdStandArr != null) {
                        for (int j = 0; j < actHdStandArr.size(); j++) {
                            JSONObject jobja = actHdStandArr.getJSONObject(j);
                            String acode = jobja.getString("code");
                            if (code.equals(acode)) {
                                standardMap.put("checked", "1");
                                break;
                            } else {
                                standardMap.put("checked", "0");
                            }
                        }
                    } else {
                        standardMap.put("checked", "0");
                    }
                    standardMap.put("code", code);
                    standardMap.put("name", name);
                    standardList.add(standardMap);
                }
            }
            actGoodsIns.setStandardList(standardList);
            actGoodsIns.setGoodsSP(actGoodsStorgeInsList);
            actAllMsgIns.setActivityInstance(actIns);
            actAllMsgIns.setActivityGoodsInstance(actGoodsIns);
            pr.setData(actAllMsgIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("activityGoodsEdit", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

    @Override
    public BaseResult deleteActivity(Long id, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            Activity activity = activityService.getActivityById(id);
            if (ActivityShowLocal.ABC.getCodeValue().equals(activity.getShowLocal())) {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("activityId", id);
                List<MallHome> mallHomeList = mallHomeService.getMallHomeByActivity(id);
                if (mallHomeList.size() > 0) {
                    List<ActivityGoods> actGoodsList = activityGoodsService.getActivityGoodsListByActivityId(id);
                    for (MallHome mallHome : mallHomeList) {
                        for (ActivityGoods actGoods : actGoodsList) {
                            if (mallHome.getActGoodsId().equals(actGoods.getId())) {
                                br.setErrorMessage("1001", "有商品在农行首页中关联");
                                return br;
                            }
                        }
                    }
                }
            }
            int num = activityService.delActivityById(id);
            if (num <= 0) {
                br.setErrorMessage("1001", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteActivity", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult deleteActivityGoods(Long id, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            ActivityGoods activityGoods = activityGoodsService.getActivityGoodsById(id);
            Activity activity = activityService.getActivityById(activityGoods.getActivityId());
            if (ActivityShowLocal.ABC.getCodeValue().equals(activity.getShowLocal())) {
                List<MallHome> mallHomeList = mallHomeService.getMallHomeByActGoods(id);
                if (mallHomeList.size() > 0) {
                    br.setErrorMessage("1001", "农行首页已关联此商品");
                    return br;
                }
            }
            int num = activityGoodsService.deleteActGoodsById(id);
            if (num <= 0) {
                br.setErrorMessage("1001", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteActivityGoods", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public ListResult<ActivityInstance> getShowActivityList() {
        ListResult<ActivityInstance> lr = new ListResult<ActivityInstance>();
        try {
            List<Activity> actList = activityService.getShowActivityList();
            List<ActivityInstance> actInsList = ActivityMapper.toActivityInstanceList(actList);
            lr.setData(actInsList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getShowActivityList", e);
            lr.setErrorMessage("999", "系统错误");
        }
        return lr;
    }

    @Override
    public ListResult<ActivityInstance> queryAbcActivity(Long shopId, String type, String requestId) {
        ListResult<ActivityInstance> lr = new ListResult<ActivityInstance>();
        lr.setRequestId(requestId);
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("showLocal", ActivityShowLocal.ABC.getCodeValue());
            map.put("isShow", "1");
            map.put("shopId", shopId);
            map.put("type", type);
            List<Activity> actList = activityService.getActivityByParam(map);
            List<ActivityInstance> actInsList = ActivityMapper.toActivityInstanceList(actList);
            lr.setData(actInsList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryActivityByShowLocal", e);
            lr.setErrorMessage("999", "系统错误");
        }
        return lr;
    }

    @Override
    public ListResult<ActivityGoodsInstance> queryActivityGoodsByActivityId(Long activityId, String requestId) {
        ListResult<ActivityGoodsInstance> lr = new ListResult<ActivityGoodsInstance>();
        lr.setRequestId(requestId);
        try {
            List<ActivityGoods> actList = activityGoodsService.getActivityGoodsListByActivityId(activityId);
            List<ActivityGoodsInstance> actInsList = ActivityGoodsMapper.toActivityGoodsInstanceList(actList);
            lr.setData(actInsList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryActivityGoodsByActivityId", e);
            lr.setErrorMessage("999", "系统错误");
        }
        return lr;
    }

    @Override
    public PlainResult<ActivityGoodsInstance> queryActivityGoodsById(Long id, String requestId) {
        PlainResult<ActivityGoodsInstance> pr = new PlainResult<ActivityGoodsInstance>();
        pr.setRequestId(requestId);
        try {
            ActivityGoods activityGoods = activityGoodsService.getActivityGoodsById(id);
            ActivityGoodsInstance activityGoodsIns = ActivityGoodsMapper.toActivityGoodsInstance(activityGoods);
            pr.setData(activityGoodsIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryActivityGoodsById", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

    @Override
    public BaseResult updateAllActivityGoodsTime(Long activityId, String beginTime, String endTime) {
        BaseResult br = new BaseResult();
        try {
            Long updateNum = activityGoodsService.updateActivityGoodsTime(activityId, beginTime, endTime);
            if (updateNum < 0){
                br.setErrorMessage("999","修改失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateAllActivityGoodsTime", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

}
