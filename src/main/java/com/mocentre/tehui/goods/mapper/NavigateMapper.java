package com.mocentre.tehui.goods.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.act.service.IActivityService;
import com.mocentre.tehui.backend.model.NavigateInstance;
import com.mocentre.tehui.backend.param.NavigateParam;
import com.mocentre.tehui.frontend.model.NavigateFTInstance;
import com.mocentre.tehui.goods.enums.NavigateType;
import com.mocentre.tehui.goods.model.Navigate;
import com.mocentre.tehui.sub.model.Subject;
import com.mocentre.tehui.sub.service.ISubjectService;

/**
 * 导航栏实体类类型转换 Created by 王雪莹 on 2016/12/20.
 */
@Component
public class NavigateMapper {

    @Autowired
    private ISubjectService  subjectService;
    @Autowired
    private IActivityService activityService;

    public NavigateInstance toNavigateInstance(Navigate navigate) {
        NavigateInstance instance = new NavigateInstance();
        instance.setId(navigate.getId());
        instance.setShopId(navigate.getShopId());
        instance.setType(navigate.getType());
        instance.setId(navigate.getId());
        instance.setSorting(navigate.getSorting());
        instance.setTitle(navigate.getTitle());
        instance.setShowImg(navigate.getShowImg());
        instance.setTypeId(navigate.getTypeId());
        instance.setIsShow(navigate.getIsShow());
        return instance;
    }

    public List<NavigateInstance> toNavigateInstanceList(List<Navigate> navigateList) {
        List<NavigateInstance> instanceList = new ArrayList<>();
        for (Navigate n : navigateList) {
            instanceList.add(toNavigateInstance(n));
        }
        return instanceList;
    }

    public Navigate toNavigate(NavigateParam param) {
        Navigate navigate = new Navigate();
        navigate.setId(param.getId());
        navigate.setShopId(param.getShopId());
        navigate.setType(param.getType());
        navigate.setSorting(param.getSorting());
        navigate.setTitle(param.getTitle());
        navigate.setShowImg(param.getShowImg());
        navigate.setTypeId(param.getTypeId());
        navigate.setIsShow(param.getIsShow());
        return navigate;
    }

    public List<Navigate> toNavigateList(List<NavigateParam> paramList) {
        List<Navigate> navigateList = new ArrayList<>();
        for (NavigateParam param : paramList) {
            navigateList.add(toNavigate(param));
        }
        return navigateList;
    }

    public NavigateFTInstance toNavigateFTInstance(Navigate navigate) {
        String type = navigate.getType();
        Long typeId = navigate.getTypeId();
        NavigateFTInstance instance = new NavigateFTInstance();
        instance.setId(navigate.getId());
        instance.setTitle(navigate.getTitle());
        instance.setShowImg(navigate.getShowImg());
        instance.setShopId(navigate.getShopId());
        instance.setType(type);
        instance.setTypeId(typeId);
        if (NavigateType.SUBJECT.getCodeValue().equals(type)) {
            Subject subject = subjectService.getSubjectById(typeId);
            instance.setSubTmpCode(subject.getSubTmpCode());
        } else if (NavigateType.ACTIVITY.getCodeValue().equals(type)) {
            //Activity act = activityService.getActivityById(typeId);
            //instance.setSubTmpCode("");
        } else if (NavigateType.ALL.getCodeValue().equals(type)) {
            instance.setSubTmpCode("all");
        }
        instance.setIsShow(navigate.getIsShow());
        return instance;
    }

    public List<NavigateFTInstance> toNavigateFTInstance(List<Navigate> navigateList) {
        List<NavigateFTInstance> instanceList = new ArrayList<NavigateFTInstance>();
        if (navigateList == null) {
            return instanceList;
        }
        for (Navigate navigate : navigateList) {
            instanceList.add(toNavigateFTInstance(navigate));
        }
        return instanceList;
    }
}
