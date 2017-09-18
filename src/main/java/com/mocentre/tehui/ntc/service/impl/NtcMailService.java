package com.mocentre.tehui.ntc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.NtcMailInstance;
import com.mocentre.tehui.backend.param.NtcMailParam;
import com.mocentre.tehui.common.utils.SendMailUtil;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.job.mailqueue.MailMsgVo;
import com.mocentre.tehui.job.mailqueue.MailQueue;
import com.mocentre.tehui.ntc.dao.INtcMailDao;
import com.mocentre.tehui.ntc.enums.NoticeType;
import com.mocentre.tehui.ntc.mapper.NtcMailMapper;
import com.mocentre.tehui.ntc.model.NtcMail;
import com.mocentre.tehui.ntc.service.INtcMailService;

/**
 * 通知邮件service实现 Created by wangxueying on 17/08/08
 */

@Component
public class NtcMailService implements INtcMailService {

    @Autowired
    private INtcMailDao          ntcMailDao;
    @Autowired
    private MailQueue<MailMsgVo> mailQueue;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<NtcMailInstance> queryNtcMailPage(Requirement require) {
        ListJsonResult<NtcMailInstance> result = new ListJsonResult<NtcMailInstance>();
        ListJsonResult<NtcMail> pageInfo = ntcMailDao.queryDatatablesPage(require);
        List<NtcMail> mailList = pageInfo.getData();
        List<NtcMailInstance> instanceList = NtcMailMapper.toNtcMailInstanceList(mailList);
        result.setData(instanceList);
        result.setDraw(pageInfo.getDraw());
        result.setRecordsFiltered(pageInfo.getRecordsFiltered());
        result.setRecordsTotal(pageInfo.getRecordsTotal());
        return result;
    }

    @Override
    @DataSource(value = "read")
    public List<NtcMail> getNtcMailList(String noticeType) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("noticeType", noticeType);
        return ntcMailDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public NtcMail getNtcMailById(Long id) {
        return ntcMailDao.getById(id);
    }

    @Override
    @DataSource(value = "write")
    public NtcMail addNtcMail(NtcMailParam param) {
        NtcMail ntcMail = NtcMailMapper.toNtcMail(param);
        ntcMailDao.saveEntity(ntcMail);
        return ntcMail;
    }

    @Override
    @DataSource(value = "write")
    public Long updateNtcMail(NtcMailParam param) {
        NtcMail ntcMail = NtcMailMapper.toNtcMail(param);
        return ntcMailDao.updateEntity(ntcMail);
    }

    @Override
    @DataSource(value = "write")
    public int delById(Long id) {
        return ntcMailDao.logicRemoveById(id);
    }

    @Override
    @DataSource(value = "read")
    public void sendNotice(String type, String title, String content) {
        ArrayList<String> addressList = new ArrayList<>();
        List<NtcMail> ntcMailList = null;
        if (NoticeType.REFUND.getCodeValue().equals(type)) {
            ntcMailList = getNtcMailList(NoticeType.REFUND.getCodeValue());
        } else if (NoticeType.BUY.getCodeValue().equals(type)) {
            ntcMailList = getNtcMailList(NoticeType.BUY.getCodeValue());
        }
        if (ntcMailList != null) {
            for (NtcMail nm : ntcMailList) {
                addressList.add(nm.getMail());
            }
        }
        SendMailUtil.sendmail(addressList, title, content);
    }

    @Override
    public void createBuyNotice(List<String> orderNumList) {
        String orderNums = "";
        for (String orderNum : orderNumList) {
            orderNums += orderNum + "，";
        }
        if (StringUtils.isNotBlank(orderNums)) {
            orderNums = orderNums.substring(0, orderNums.length() - 1);
        }
        MailMsgVo mailMsg = new MailMsgVo();
        mailMsg.setTitle("新订单邮件");
        mailMsg.setType(NoticeType.BUY.getCodeValue());
        mailMsg.setContent("您有新的订单，请及时处理。订单编号：" + orderNums);
        mailQueue.pushFromTail(mailMsg);
    }

    @Override
    public void createRefundNotice(String orderNum) {
        MailMsgVo mailMsg = new MailMsgVo();
        mailMsg.setTitle("退款申请邮件");
        mailMsg.setType(NoticeType.REFUND.getCodeValue());
        mailMsg.setContent("订单编号：" + orderNum + "申请退款，请及时处理");
        mailQueue.pushFromTail(mailMsg);
    }

}
