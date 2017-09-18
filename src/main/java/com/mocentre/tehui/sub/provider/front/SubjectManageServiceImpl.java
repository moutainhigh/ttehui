package com.mocentre.tehui.sub.provider.front;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.PlainResult;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.frontend.model.SubjectAllMsgFTInstance;
import com.mocentre.tehui.frontend.param.SubjectQueryParam;
import com.mocentre.tehui.frontend.service.SubjectManageService;
import com.mocentre.tehui.sub.mapper.SubjectGoodsMapper;
import com.mocentre.tehui.sub.mapper.SubjectMapper;
import com.mocentre.tehui.sub.model.Subject;
import com.mocentre.tehui.sub.model.SubjectGoods;
import com.mocentre.tehui.sub.service.ISubjectGoodsService;
import com.mocentre.tehui.sub.service.ISubjectService;

/**
 * 专题接口 Created by yukaiji on 2017/01/22.
 */
public class SubjectManageServiceImpl implements SubjectManageService {

    @Autowired
    private ISubjectService      subjectService;
    @Autowired
    private ISubjectGoodsService subjectGoodsService;

    @Override
    public PlainResult<SubjectAllMsgFTInstance> getSubjectWithGoods(SubjectQueryParam subParam) {
        PlainResult<SubjectAllMsgFTInstance> result = new PlainResult<SubjectAllMsgFTInstance>();
        SubjectAllMsgFTInstance subAllIns = new SubjectAllMsgFTInstance();
        result.setRequestId(subParam.getRequestId());
        try {
            Long subjectId = subParam.getSubjectId();
            boolean paramValid = subjectId == null;
            if (paramValid) {
                result.setErrorMessage("1000", "参数错误");
                return result;
            }
            Integer start = subParam.getStart();
            Integer length = subParam.getLength();
            Subject subject = subjectService.getSubjectById(subjectId);
            if (subject != null) {
                PageInfo<SubjectGoods> pageInfo = subjectGoodsService.querySubjectPage(subjectId, start, length);
                subAllIns.setSubject(SubjectMapper.toSubjectFTInstance(subject));
                subAllIns.setSubjectGoodsList(SubjectGoodsMapper.toSubjectGoodsFTInstanceList(pageInfo.getRows()));
            } else {
                result.setErrorMessage("1001", "查无数据");
            }
            result.setData(subAllIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getSubjectWithGoods", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
