package com.mocentre.tehui.goods.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.NavigateMangeService;
import com.mocentre.tehui.act.model.Activity;
import com.mocentre.tehui.act.service.IActivityService;
import com.mocentre.tehui.backend.model.NavigateInstance;
import com.mocentre.tehui.backend.param.NavigateParam;
import com.mocentre.tehui.backend.param.NavigateQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.enums.NavigateType;
import com.mocentre.tehui.goods.mapper.NavigateMapper;
import com.mocentre.tehui.goods.model.Navigate;
import com.mocentre.tehui.goods.service.INavigateService;
import com.mocentre.tehui.sub.model.Subject;
import com.mocentre.tehui.sub.service.ISubjectService;

/**
 * 导航栏管理 Created by 王雪莹 on 2016/12/20.
 */
public class NavigateMangeServiceImpl implements NavigateMangeService {

    @Autowired
    private INavigateService navigateService;
    @Autowired
    private ISubjectService  subjectService;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private NavigateMapper   navigateMapper;

    /**
     * 根据店铺id获取全部
     * 
     * @return
     */
    @Override
    public ListJsonResult<NavigateInstance> getNavigatePage(NavigateQueryParam queryParam) {
        ListJsonResult<NavigateInstance> ljr = new ListJsonResult<NavigateInstance>();
        ljr.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("title", queryParam.getTitle());
            paramMap.put("orderBy", queryParam.getOrderBy() == null ? "desc" : queryParam.getOrderBy());
            paramMap.put("orderColumn", queryParam.getOrderColumn());
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            ListJsonResult<Navigate> pageInfo = navigateService.getNavigatePage(require);
            List<Navigate> list = pageInfo.getData();
            List<NavigateInstance> instanceList = new ArrayList<NavigateInstance>();
            if (list != null) {
                instanceList = navigateMapper.toNavigateInstanceList(list);
                for (NavigateInstance navIns : instanceList) {
                    String type = navIns.getType();
                    Long typeId = navIns.getTypeId();
                    if (NavigateType.SUBJECT.getCodeValue().equals(type)) {
                        Subject subject = subjectService.getSubjectById(typeId);
                        if (subject != null) {
                            navIns.setTypeName(subject.getTitle());
                        }
                    } else if (NavigateType.ACTIVITY.getCodeValue().equals(type)) {
                        Activity activity = activityService.getActivityById(typeId);
                        if (activity != null) {
                            navIns.setTypeName(activity.getTitle());
                        }
                    }
                }
            }
            ljr.setData(instanceList);
            ljr.setDraw(pageInfo.getDraw());
            ljr.setRecordsFiltered(pageInfo.getRecordsFiltered());
            ljr.setRecordsTotal(pageInfo.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getNavigatePage", e);
            ljr.setErrorMessage("999", "查询出错");
        }
        return ljr;
    }

    /**
     * 保存
     * 
     * @param navigateParam
     * @param requestId
     * @return
     */
    @Override
    public BaseResult addNavigate(NavigateParam navigateParam) {
        BaseResult br = new BaseResult();
        br.setRequestId(navigateParam.getRequestId());
        try {
            Navigate navigate = navigateMapper.toNavigate(navigateParam);
            if (navigate.getId() == null) {
                navigateService.saveNavigate(navigate);
            } else {
                navigateService.updateNavigate(navigate);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addNavigate", e);
            br.setErrorMessage("999", "系统异常");
        }
        return br;
    }

    @Override
    public BaseResult delByIdList(List<Long> idList, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            navigateService.delByIdList(idList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("delByIdList", e);
            br.setErrorMessage("999", "系统异常");
        }
        return br;
    }

    @Override
    public PlainResult<NavigateInstance> getNavigateById(Long id, String requestId) {
        PlainResult<NavigateInstance> pr = new PlainResult<NavigateInstance>();
        try {
            Navigate nav = navigateService.getNavigateById(id);
            NavigateInstance navIns = navigateMapper.toNavigateInstance(nav);
            pr.setData(navIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("NavigateInstance", e);
            pr.setErrorMessage("999", "系统异常");
        }
        return pr;
    }
}
