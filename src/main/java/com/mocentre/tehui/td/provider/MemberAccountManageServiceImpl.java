package com.mocentre.tehui.td.provider;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.MemberAccountManageService;
import com.mocentre.tehui.backend.model.MemberAccountInstance;
import com.mocentre.tehui.backend.model.MemberAccountKeyInstance;
import com.mocentre.tehui.backend.param.MemberAccountParam;
import com.mocentre.tehui.backend.param.MemberAccountQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.td.mapper.MemberAccountMapper;
import com.mocentre.tehui.td.model.MemberAccount;
import com.mocentre.tehui.td.service.impl.MemberAccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第三方账户管理 Created by 王雪莹 on 2017/6/20.
 */
public class MemberAccountManageServiceImpl implements MemberAccountManageService {

    @Autowired
    private MemberAccountService memberAccountService;

    @Override
    public BaseResult delById(Long id, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            memberAccountService.delById(id);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("delById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<MemberAccountKeyInstance> update(MemberAccountParam param) {
        PlainResult<MemberAccountKeyInstance> result = new PlainResult<MemberAccountKeyInstance>();
        result.setRequestId(param.getRequestId());
        try {
            Long id = param.getId();
            String keymark = param.getKeymark();
            if (id == null || StringUtils.isBlank(keymark)) {
                result.setErrorMessage("1000", "参数错误");
                return result;
            }
            Boolean exists = memberAccountService.getMemberAccountExist(keymark, id);
            if (exists) {
                result.setErrorMessage("1003", "标识已存在，请重新填写");
                return result;
            }
            MemberAccount ma = MemberAccountMapper.toMemberAccount(param);
            MemberAccount oldMebAut = memberAccountService.getById(id);
            if (oldMebAut == null) {
                result.setErrorMessage("1001", "数据不存在");
                return result;
            }
            Long count = memberAccountService.updateMemberAccount(ma);
            if (count == 0) {
                result.setErrorMessage("1002", "操作失败");
                return result;
            }
            MemberAccountKeyInstance kvs = new MemberAccountKeyInstance();
            kvs.setOldAppkey(oldMebAut.getAppKey());
            kvs.setOldKeymark(oldMebAut.getKeymark());
            kvs.setAppKey(ma.getAppKey());
            kvs.setAppSecret(ma.getAppSecret());
            kvs.setAbcAppsecret(ma.getAbcAppsecret());
            kvs.setKeymark(ma.getKeymark());
            kvs.setPageAddress(ma.getPageAddress());
            result.setData(kvs);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("update", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<MemberAccountKeyInstance> save(MemberAccountParam param) {
        PlainResult<MemberAccountKeyInstance> result = new PlainResult<MemberAccountKeyInstance>();
        result.setRequestId(param.getRequestId());
        try {
            MemberAccount ma = MemberAccountMapper.toMemberAccount(param);
            String keymark = ma.getKeymark();
            Boolean exists = memberAccountService.getMemberAccountExist(keymark, null);
            if (exists) {
                result.setErrorMessage("1002", "标识已存在，请重新填写");
                return result;
            }
            Long count = memberAccountService.saveMemberAccount(ma);
            if (count == 0) {
                result.setErrorMessage("1001", "操作失败");
                return result;
            }
            MemberAccountKeyInstance kvs = new MemberAccountKeyInstance();
            kvs.setAppKey(ma.getAppKey());
            kvs.setAppSecret(ma.getAppSecret());
            kvs.setAbcAppsecret(ma.getAbcAppsecret());
            kvs.setKeymark(ma.getKeymark());
            kvs.setPageAddress(ma.getPageAddress());
            result.setData(kvs);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("save", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListJsonResult<MemberAccountInstance> getPage(MemberAccountQueryParam queryParam) {
        ListJsonResult<MemberAccountInstance> result = new ListJsonResult<>();
        result.setRequestId(queryParam.getRequestId());
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Requirement require = new Requirement(queryParam.getDraw(), queryParam.getStart(), queryParam.getLength(),
                    paramMap);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("abcAppid", queryParam.getAbcAppid());
            params.put("appKey", queryParam.getAppKey());
            params.put("remark", queryParam.getRemark());
            require.setMap(params);
            ListJsonResult<MemberAccount> pageInfo = memberAccountService.getPage(require);
            List<MemberAccountInstance> instanceList = MemberAccountMapper.toMemberAccountInstnceList(pageInfo
                    .getData());
            result.setData(instanceList);
            result.setDraw(pageInfo.getDraw());
            result.setRecordsFiltered(pageInfo.getRecordsFiltered());
            result.setRecordsTotal(pageInfo.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<MemberAccountInstance> getById(Long id, String requestId) {
        PlainResult<MemberAccountInstance> result = new PlainResult<>();
        result.setRequestId(requestId);
        try {
            MemberAccount memberAccount = memberAccountService.getById(id);
            MemberAccountInstance instance = MemberAccountMapper.toMemberAccountInstnce(memberAccount);
            result.setData(instance);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getById", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult updateIsDeny(Long id, String isDeny, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            memberAccountService.updateIsDeny(id, isDeny);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateIsDeny", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
