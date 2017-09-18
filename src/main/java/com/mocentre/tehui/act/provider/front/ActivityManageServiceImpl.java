package com.mocentre.tehui.act.provider.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.act.enums.ActivityShowLocal;
import com.mocentre.tehui.act.mapper.ActivityGoodsMapper;
import com.mocentre.tehui.act.mapper.ActivityMapper;
import com.mocentre.tehui.act.model.Activity;
import com.mocentre.tehui.act.model.ActivityGoods;
import com.mocentre.tehui.act.service.IActivityGoodsService;
import com.mocentre.tehui.act.service.IActivityService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.frontend.model.ActivityAllMsgFTInstance;
import com.mocentre.tehui.frontend.service.ActivityManageService;
import com.mocentre.tehui.system.enums.ImageSeat;
import com.mocentre.tehui.system.model.Image;
import com.mocentre.tehui.system.service.IImageService;

/**
 * 前台活动接口实现 Created by yukaiji on 2017/01/16.
 */
public class ActivityManageServiceImpl implements ActivityManageService {

    @Autowired
    private IActivityService      activityService;
    @Autowired
    private IImageService         imageService;
    @Autowired
    private IActivityGoodsService activityGoodsService;

    @Override
    public PlainResult<ActivityAllMsgFTInstance> queryActivityWithGoods(Long shopId, Long activityId, String requestId) {
        PlainResult<ActivityAllMsgFTInstance> result = new PlainResult<>();
        ActivityAllMsgFTInstance actAllIns = new ActivityAllMsgFTInstance();
        result.setRequestId(requestId);
        try {
            Activity activity = activityService.getActivityById(activityId, shopId);
            if (activity != null) {
                List<Image> images = imageService.queryImageList(activityId, ImageSeat.ACTIVITY.getCodeValue());
                List<ActivityGoods> actGoods = activityGoodsService.getActivityGoodsListByActivityId(activityId);
                activity.setImageList(images);
                actAllIns.setActFTIns(ActivityMapper.toActivityFTInstance(activity));
                actAllIns.setActGoodsListFTIns(ActivityGoodsMapper.toActivityGoodsFTInstanceList(actGoods));
            }
            result.setData(actAllIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryActivityWithGoods", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListResult<ActivityAllMsgFTInstance> queryActivityIndex(String actType, String requestId) {
        ListResult<ActivityAllMsgFTInstance> result = new ListResult<ActivityAllMsgFTInstance>();
        List<ActivityAllMsgFTInstance> actList = new ArrayList<ActivityAllMsgFTInstance>();
        result.setRequestId(requestId);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("type", actType);
        paramMap.put("isShow", "1");
        paramMap.put("showLocal", ActivityShowLocal.MALL.getCodeValue());
        try {
            List<Activity> activityList = activityService.getActivityByParam(paramMap);
            if (activityList != null && !activityList.isEmpty()) {
                Activity activity = activityList.get(0);
                // 获取第一个活动banner图,用于首页展示
                List<Image> images = imageService.queryImageList(activity.getId(), ImageSeat.ACTIVITY.getCodeValue());
                // 获取第一个活动关联商品,用于首页展示
                List<ActivityGoods> actGoods = activityGoodsService.getActivityGoodsListByActivityId(activity.getId());
                for (int i = 0; i < activityList.size(); i++) {
                    Activity act = activityList.get(i);
                    ActivityAllMsgFTInstance actAll = new ActivityAllMsgFTInstance();
                    if (i == 0) {
                        act.setImageList(images);
                        actAll.setActGoodsListFTIns(ActivityGoodsMapper.toActivityGoodsFTInstanceList(actGoods));
                    }
                    actAll.setActFTIns(ActivityMapper.toActivityFTInstance(act));
                    actList.add(actAll);
                }
            }
            result.setData(actList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryActivityIndex", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
