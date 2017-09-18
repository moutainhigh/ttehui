package com.mocentre.tehui.td.provider.front;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.PlainResult;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.frontend.model.MemberAccountFTInstance;
import com.mocentre.tehui.frontend.service.MemberAccountManageService;
import com.mocentre.tehui.td.mapper.MemberAccountMapper;
import com.mocentre.tehui.td.model.MemberAccount;
import com.mocentre.tehui.td.service.impl.MemberAccountService;

/**
 * 第三方账户管理 Created by 王雪莹 on 2017/6/20.
 */
public class MemberAccountManageServiceImpl implements MemberAccountManageService {

    @Autowired
    private MemberAccountService memberAccountService;

    @Override
    public PlainResult<MemberAccountFTInstance> getMemberAccount(String appKey, String cityCode, String requestId) {
        PlainResult<MemberAccountFTInstance> result = new PlainResult<>();
        result.setRequestId(requestId);
        try {
            MemberAccount memberAccount = memberAccountService.getMemberAccountByAppKey(appKey);
            MemberAccountFTInstance instance = MemberAccountMapper.toMemberAccountFTInstnce(memberAccount);
            result.setData(instance);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getMemberAccount", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<MemberAccountFTInstance> getMemberAccountByAppKey(String appKey, String requestId) {
        PlainResult<MemberAccountFTInstance> pr = new PlainResult<MemberAccountFTInstance>();
        pr.setRequestId(requestId);
        try {
            MemberAccount memberAccount = memberAccountService.getMemberAccountFromCache(appKey);
            if (memberAccount == null) {
                pr.setErrorMessage("1001", "数据不存在");
                return pr;
            }
            MemberAccountFTInstance maIns = MemberAccountMapper.toMemberAccountFTInstnce(memberAccount);
            pr.setData(maIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getMemberAccountByAppKey", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

    @Override
    public PlainResult<MemberAccountFTInstance> getMemberAccountByKeymark(String keymark, String requestId) {
        PlainResult<MemberAccountFTInstance> pr = new PlainResult<MemberAccountFTInstance>();
        pr.setRequestId(requestId);
        try {
            MemberAccount memberAccount = memberAccountService.getMemberAccountByKeymark(keymark);
            if (memberAccount == null) {
                pr.setErrorMessage("1001", "数据不存在");
                return pr;
            }
            MemberAccountFTInstance maIns = MemberAccountMapper.toMemberAccountFTInstnce(memberAccount);
            pr.setData(maIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getMemberAccountByKeymark", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

}
