package com.mocentre.tehui.weixin.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.ShareConfigManageService;
import com.mocentre.tehui.backend.model.ShareConfigInstance;
import com.mocentre.tehui.backend.param.ShareConfigParam;
import com.mocentre.tehui.backend.param.ShareConfigQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.weixin.mapper.ShareConfigMapper;
import com.mocentre.tehui.weixin.model.ShareConfig;
import com.mocentre.tehui.weixin.service.IShareConfigService;

/**
 * 微信分享配置接口实现 Created by yukaiji on 2017/5/4.
 */
public class ShareConfigManageServiceImpl implements ShareConfigManageService {

    @Autowired
    private IShareConfigService shareConfigService;

    @Override
    public ListJsonResult<ShareConfigInstance> queryShareConfigPage(ShareConfigQueryParam param) {
        ListJsonResult<ShareConfigInstance> lr = new ListJsonResult<ShareConfigInstance>();
        lr.setRequestId(param.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderBy", param.getOrderBy());
            paramMap.put("column", param.getOrderColumn());
            Requirement require = new Requirement(param.getDraw(), param.getStart(), param.getLength(), paramMap);
            ListJsonResult<ShareConfig> result = shareConfigService.queryShareConfigPage(require);
            List<ShareConfig> shareConfigs = result.getData();
            lr.setData(ShareConfigMapper.toShareConfigInsList(shareConfigs));
            lr.setDraw(result.getDraw());
            lr.setRecordsTotal(result.getRecordsTotal());
            lr.setRecordsFiltered(result.getRecordsFiltered());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryShareConfigPage", e);
            lr.setErrorMessage("999", "系统错误");
        }
        return lr;
    }

    @Override
    public PlainResult<ShareConfigInstance> getShareConfigById(Long id) {
        PlainResult<ShareConfigInstance> pr = new PlainResult<ShareConfigInstance>();
        try {
            ShareConfig shareConfig = shareConfigService.getShareConfigById(id);
            pr.setData(ShareConfigMapper.toShareConfigIns(shareConfig));
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getShareConfigById", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

    @Override
    public BaseResult addShareConfig(ShareConfigParam shareConfigParam) {
        BaseResult br = new BaseResult();
        try {
            ShareConfig shareConfig = shareConfigService.addShareConfig(shareConfigParam);
            if (shareConfig.getId() == null) {
                br.setErrorMessage("1001", "添加失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addShareConfig", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult updateShareConfig(ShareConfigParam shareConfigParam) {
        BaseResult br = new BaseResult();
        try {
            shareConfigService.updateShareConfig(shareConfigParam);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateShareConfig", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult delShareConfigById(Long id) {
        BaseResult br = new BaseResult();
        try {
            int updateNum = shareConfigService.delShareConfigById(id);
            if (updateNum < 0) {
                br.setErrorMessage("1001", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("delShareConfigById", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public PlainResult<ShareConfigInstance> getDefaultShareConfig(String requestId) {
        PlainResult<ShareConfigInstance> pr = new PlainResult<ShareConfigInstance>();
        try {
            ShareConfig shareConfig = shareConfigService.getDefaultShareConfig();
            ShareConfigInstance shareConfigIns = null;
            if (shareConfig != null) {
                shareConfigIns = ShareConfigMapper.toShareConfigIns(shareConfig);
            }
            pr.setData(shareConfigIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getDefaultShareConfig", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }
}
