package com.mocentre.tehui.goods.provider.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.ListResult;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.frontend.model.NavigateFTInstance;
import com.mocentre.tehui.frontend.service.NavigateManageService;
import com.mocentre.tehui.goods.mapper.NavigateMapper;
import com.mocentre.tehui.goods.model.Navigate;
import com.mocentre.tehui.goods.service.INavigateService;

/**
 * 导航栏 Created by 王雪莹 on 2016/12/22.
 */
public class NavigateManageServiceImpl implements NavigateManageService {

    @Autowired
    private INavigateService navigateService;
    @Autowired
    private NavigateMapper   navigateMapper;

    @Override
    public ListResult<NavigateFTInstance> getAll(String requestId) {
        ListResult<NavigateFTInstance> result = new ListResult<>();
        try {
            List<Navigate> navigateList = navigateService.getShowNavigateSortedList();
            List<NavigateFTInstance> instanceList = navigateMapper.toNavigateFTInstance(navigateList);
            result.setData(instanceList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getAll", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

}
