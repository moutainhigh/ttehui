package com.mocentre.tehui.ntc.service;

import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.NtcMailInstance;
import com.mocentre.tehui.backend.param.NtcMailParam;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.ntc.model.NtcMail;

/**
 * 通知邮件接口 Created by wangxueying on 17/08/08
 */
public interface INtcMailService {

    /**
     * 获取所有的邮件地址
     * 
     * @return 所有的邮件地址
     */
    ListJsonResult<NtcMailInstance> queryNtcMailPage(Requirement require);

    /**
     * 根据通知类型获取邮件列表
     * 
     * @return
     */
    List<NtcMail> getNtcMailList(String noticeType);

    /**
     * 通过id查询
     * 
     * @param id
     * @return
     */
    NtcMail getNtcMailById(Long id);

    /**
     * 添加一个通知邮件
     * 
     * @param ntcMail
     * @return id
     */
    NtcMail addNtcMail(NtcMailParam ntcMail);

    /**
     * 修改一个专题
     * 
     * @param ntcMail
     * @return id
     */
    Long updateNtcMail(NtcMailParam ntcMail);

    /**
     * 根据id删除
     * 
     * @param id
     * @return
     */
    int delById(Long id);

    /**
     * 发送邮件通知
     * 
     * @param type
     * @param title
     * @param content
     */
    void sendNotice(String type, String title, String content);

    /**
     * 创建订单邮件
     * 
     * @param orderNumList
     */
    void createBuyNotice(List<String> orderNumList);

    /**
     * 创建退款邮件
     * 
     * @param orderNum
     */
    void createRefundNotice(String orderNum);

}
