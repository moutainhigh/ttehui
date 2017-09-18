package com.mocentre.tehui.ntc.provider;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.NtcMailManageService;
import com.mocentre.tehui.backend.model.NtcMailInstance;
import com.mocentre.tehui.backend.param.NtcMailParam;
import com.mocentre.tehui.backend.param.NtcMailQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.ntc.mapper.NtcMailMapper;
import com.mocentre.tehui.ntc.model.NtcMail;
import com.mocentre.tehui.ntc.service.INtcMailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知邮件dubbo实现
 *
 * Created by wangxueying on 17/08/08
 */
public class NtcMailManageServiceImpl implements NtcMailManageService {

    @Autowired
    private INtcMailService ntcMailService;

    @Override
    public ListJsonResult<NtcMailInstance> queryNtcMailPage(NtcMailQueryParam ntcMailQuery) {
        ListJsonResult<NtcMailInstance> result = new ListJsonResult<NtcMailInstance>();
        String requestId = ntcMailQuery.getRequestId();
        result.setRequestId(requestId);
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("noticeType", ntcMailQuery.getNoticeType());
            paramMap.put("orderBy", ntcMailQuery.getOrderBy());
            paramMap.put("orderColumn", ntcMailQuery.getOrderColumn());
            Requirement require = new Requirement(ntcMailQuery.getDraw(), ntcMailQuery.getStart(),
                    ntcMailQuery.getLength(), paramMap);
            result = ntcMailService.queryNtcMailPage(require);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryNtcMailPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }


    @Override
    public BaseResult addNtcMail(NtcMailParam ntcMailParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(ntcMailParam.getRequestId());
        try {
            NtcMail ntcMail = ntcMailService.addNtcMail(ntcMailParam);
            if (ntcMail.getId() == null) {
                result.setErrorMessage("1001", "添加失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addNtcMail", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult updateNtcMail(NtcMailParam ntcMailParam) {
        BaseResult result = new BaseResult();
        result.setRequestId(ntcMailParam.getRequestId());
        try {
            Long updateNum = ntcMailService.updateNtcMail(ntcMailParam);
            if (updateNum < 0) {
                result.setErrorMessage("1001", "修改失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateNtcMail", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult deleteNtcMail(Long id, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            int num = ntcMailService.delById(id);
            if (num <= 0) {
                result.setErrorMessage("1001", "删除失败");
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteNtcMail", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<NtcMailInstance> getById(Long id, String requestId) {
        PlainResult<NtcMailInstance> result = new PlainResult();
        result.setRequestId(requestId);
        try {
            NtcMail ntcMail = ntcMailService.getNtcMailById(id);
            NtcMailInstance ntcMailInstance = NtcMailMapper.toNtcMailInstance(ntcMail);
            result.setData(ntcMailInstance);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateNtcMail", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
